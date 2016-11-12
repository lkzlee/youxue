package com.youxue.core.service.mobileCode;

import java.util.HashMap;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.constant.RedisConstant;
import com.youxue.core.redis.JedisProxy;
import com.youxue.core.util.PropertyUtils;

@Service
public class MobileCodeServiceImpl implements MobileCodeService
{
	private final Log LOG = LogFactory.getLog(MobileCodeServiceImpl.class);
	public static final Long INTERVAL = 5L; // 发送短信的最短间隔, 单位min

	@Autowired
	JedisProxy jedisProxy;

	@Override
	public BaseResponseDto sendMobileCode(String phone)
	{
		BaseResponseDto responseDto = new BaseResponseDto();
		// 先查询是否发送过
		Object oldCode = jedisProxy.get(RedisConstant.MOBILE_LOGIN_PHONE_SECCODE + phone);
		if (null != oldCode)
		{
			//发送过，时间间隔还没到, 不可再发送
			responseDto = BaseResponseDto.errorDto().setDesc("发送短信太频繁");
			return responseDto;
		}
		// 未曾发送过, 直接发送，并保存
		String randomCode = generateMobileCode();// 生成随机码

		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
		restAPI.init(PropertyUtils.getProperty("ytx_mobileCode_host_name", "app.cloopen.com"),
				PropertyUtils.getProperty("ytx_mobileCode_host_port", "8883")); // 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.setAccount(PropertyUtils.getProperty("ytx_mobileCode_account_id", "8a216da8584bf8cf0158574b2f1409b6"),
				PropertyUtils.getProperty("ytx_mobileCode_auth_token", "438635517cc24e7798d30eb12bd02953")); // 初始化主帐号和主帐号TOKEN
		restAPI.setAppId(PropertyUtils.getProperty("ytx_mobileCode_appid", "8a216da8584bf8cf0158574b2f5409ba")); // 初始化应用ID
		HashMap<String, Object> result = null;
		result = restAPI.sendTemplateSMS(phone, PropertyUtils.getProperty("ytx_mobileCode_template_id", "1"),
				new String[]
				{ randomCode, INTERVAL.toString() });
		LOG.info("SDKTestSendTemplateSMS result=" + result + ",phone=" + phone + ",code:" + randomCode);
		if ("000000".equals(result.get("statusCode")))
		{
			responseDto.setResult(100);
			responseDto.setDesc("发送成功");
			jedisProxy.setex(RedisConstant.MOBILE_LOGIN_PHONE_SECCODE + phone, randomCode, (int) (INTERVAL * 60));
		}
		else
		{
			//异常返回输出错误码和错误信息
			responseDto = BaseResponseDto.errorDto().setDesc(result.get("statusMsg").toString());
			LOG.info("发送短信失败,错误码=" + result.get("statusCode") + ",错误信息=" + result.get("statusMsg"));
		}
		return responseDto;
	}

	@Override
	public boolean checkMobileCode(String phone, String code)
	{
		// 先查询
		Object oldCode = jedisProxy.get(RedisConstant.MOBILE_LOGIN_PHONE_SECCODE + phone);
		if (null == oldCode)
		{
			//已经失效或者不存在
			LOG.info("验证码失效，请重新发送,phone:" + phone);
			return false;
		}
		String codeInServer = (String) oldCode;
		if (codeInServer.equalsIgnoreCase(code))
		{
			jedisProxy.del(RedisConstant.MOBILE_LOGIN_PHONE_SECCODE + phone);
			return true;
		}
		else
		{
			LOG.info("验证码输入错误,phone:" + phone + ",input code:" + code + ",server code:" + codeInServer);
			return false;
		}
	}

	private String generateMobileCode()
	{
		Random random = new Random();
		Integer randomInteger = random.nextInt(900000) + 100000;
		return randomInteger.toString();
	}
}
