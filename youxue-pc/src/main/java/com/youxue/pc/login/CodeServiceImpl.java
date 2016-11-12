package com.yingzixiyin.core.service.impl;

import com.cloopen.rest.sdk.CCPRestSDK;
import com.yingzixiyin.api.dto.BaseResponseDto;
import com.yingzixiyin.api.dto.CodeInfo;
import com.yingzixiyin.core.dao.CodeDao;
import com.yingzixiyin.core.entity.Code;
import com.yingzixiyin.core.service.CodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * @author song.shi
 * @date 2015-07-02
 */

@Service("codeService")
public class CodeServiceImpl implements CodeService {
    private static final Logger logger = LoggerFactory.getLogger(CodeServiceImpl.class.getName());

    public static final Long INTERVAL = 5L;              // 发送短信的最短间隔, 单位min
    public static final String TEMPLATE_ID = "39618";                    // 发送短信的模板, 1: 测试模板
    public static final String HOST_NAME = "app.cloopen.com"; // 服务器地址,不需要写https://
    public static final String PORT = "8883";                        // 服务器端口号
    public static final String ACCOUNT_SID = "8a48b5514ff457cc014ff89c86450b36";    // 主账号ID
    public static final String AUTH_TOKEN = "848dc97721ab4e338f7d5b89ca956239";     // 主帐号TOKEN
    public static final String APP_ID = "8a48b5514ff923b40150037744331a2f";         // 应用ID


    @Resource
    CodeDao codeDao;

    @Override
    public Integer insert(Code code) {
        if (null == code || StringUtils.isEmpty(code.getPhone()) || StringUtils.isEmpty(code.getCode()) || null == code.getUpdateTime()) {
            logger.info("code所有字段均不能为空");
            return null;
        }
        return codeDao.insert(code);
    }

    @Override
    public void update(Code code) {
        if (null == code || null == code.getId() || StringUtils.isEmpty(code.getCode()) || null == code.getUpdateTime()) {
            logger.info("id, code, updateTime 字段均不能为空");
            return ;
        }
        codeDao.update(code);
    }

    @Override
    public CodeInfo getCode(Code code) {
        if (null == code) {
            logger.info("code不能为null");
            return null;
        }
        Code result = codeDao.getCode(code);
        return Code.translateBean(result);
    }

    @Override
    public BaseResponseDto sendCodeAndSave(String phone) {
        // 准备返回参数
        BaseResponseDto responseDto = new BaseResponseDto();
        if (StringUtils.isEmpty(phone)) {
            logger.info("phone 不能为空");
            responseDto.setReturnCode(-1);
            responseDto.setReturnMessage("phone不能为空");
            return responseDto;
        }
        // 生成随机码
        String randomCode = generateRandomCode();
        // 先查询
        Code code = new Code();
        code.setPhone(phone);
        Code result = codeDao.getCode(code);
        if (null == result) { // 未曾发送过, 直接发送，入库
            // 发送
            responseDto = sendCode(phone, randomCode);
            // 入库
            code.setCode(randomCode);
            code.setUpdateTime(new Date());
            codeDao.insert(code);
        } else if (new Date().getTime() >  INTERVAL * 60 * 1000 + result.getUpdateTime().getTime()) {// 发送过，时间间隔也过了，发送,更新
            // 发送
            responseDto = sendCode(phone, randomCode);
            // 更新
            Code newCode = new Code();
            newCode.setId(result.getId());
            newCode.setCode(randomCode);
            newCode.setUpdateTime(new Date());
            codeDao.update(newCode);
        } else {  //发送过，时间间隔还没到, 不可再发送
            responseDto.setReturnCode(-1);
            responseDto.setReturnMessage("发送短信太频繁");
            return  responseDto;
        }
        return responseDto;
    }

    @Override
    public BaseResponseDto checkCode(Code code) {
        // 准备返回参数
        BaseResponseDto responseDto = new BaseResponseDto();
        if (null == code || StringUtils.isEmpty(code.getPhone()) || StringUtils.isEmpty(code.getCode())) {
            logger.info("phone和code不能为空");
            responseDto.setReturnCode(-1);
            responseDto.setReturnMessage("phone和code不能为空");
            return responseDto;
        }
        // 先查询
        Code queryCode = new Code();
        queryCode.setPhone(code.getPhone());
        Code result = codeDao.getCode(code);
        // 验证
        if (null == result) {  // 未查到发送记录
            responseDto.setReturnCode(-1);
            responseDto.setReturnMessage("该号码还未发送过验证码");
        } else if (!result.getCode().equals(code.getCode())) { // 发送的验证码不相等, 验证失败
            responseDto.setReturnCode(-1);
            responseDto.setReturnMessage("验证码错误");
        } else if (new Date().getTime() >  INTERVAL * 60 * 1000 + result.getUpdateTime().getTime()) { // 未能在INTERVAL内验证，验证失败
            responseDto.setReturnCode(-1);
            responseDto.setReturnMessage("请在规定时间内输入验证码，过期失效");
        } else {
            responseDto.setReturnCode(0);
            responseDto.setReturnMessage("验证成功");
        }
        return responseDto;
    }

    private BaseResponseDto sendCode(String phone, String randomCode) {
        BaseResponseDto responseDto = new BaseResponseDto();
        CCPRestSDK restAPI = new CCPRestSDK();
        restAPI.init(HOST_NAME, PORT);                  // 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
        restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN);    // 初始化主帐号和主帐号TOKEN
        restAPI.setAppId(APP_ID);                       // 初始化应用ID
        HashMap<String, Object> result = null;
        result = restAPI.sendTemplateSMS(phone, TEMPLATE_ID, new String[]{randomCode, INTERVAL.toString()});
        logger.info("SDKTestSendTemplateSMS result={}", result);
        if("000000".equals(result.get("statusCode"))){
            responseDto.setReturnCode(0);
            responseDto.setReturnMessage("发送成功");
            //正常返回输出data包体信息（map）
            /*
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
            */
        }else{
            //异常返回输出错误码和错误信息
            responseDto.setReturnCode(-1);
            responseDto.setReturnMessage(result.get("statusMsg").toString());
            logger.info("发送短信失败,错误码={},错误信息={}", result.get("statusCode"), result.get("statusMsg"));
        }
        return responseDto;
    }

    private String generateRandomCode() {
        Random random = new Random();
        Integer randomInteger = random.nextInt(900000)+100000;
        return randomInteger.toString();
    }
}
