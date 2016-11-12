package com.youxue.core.service.mobileCode;

import java.util.HashMap;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.youxue.core.common.BaseResponseDto;

@Service
public class MobileCodeServiceImpl implements MobileCodeService
{
	public static final Long INTERVAL = 5L; // 发送短信的最短间隔, 单位min
	public static final String TEMPLATE_ID = "39618"; // 发送短信的模板, 1: 测试模板
	public static final String HOST_NAME = "app.cloopen.com"; // 服务器地址,不需要写https://
	public static final String PORT = "8883"; // 服务器端口号
	public static final String ACCOUNT_SID = "8a48b5514ff457cc014ff89c86450b36"; // 主账号ID
	public static final String AUTH_TOKEN = "848dc97721ab4e338f7d5b89ca956239"; // 主帐号TOKEN
	public static final String APP_ID = "8a48b5514ff923b40150037744331a2f"; // 应用ID

	private BaseResponseDto sendMobileCode(String phone, String randomCode)
	{
		BaseResponseDto responseDto = new BaseResponseDto();
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
		restAPI.init(HOST_NAME, PORT); // 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN); // 初始化主帐号和主帐号TOKEN
		restAPI.setAppId(APP_ID); // 初始化应用ID
		HashMap<String, Object> result = null;
		result = restAPI.sendTemplateSMS(phone, TEMPLATE_ID, new String[]
		{ randomCode, INTERVAL.toString() });
		logger.info("SDKTestSendTemplateSMS result={}", result);
		if ("000000".equals(result.get("statusCode")))
		{
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
		}
		else
		{
			//异常返回输出错误码和错误信息
			responseDto.setReturnCode(-1);
			responseDto.setReturnMessage(result.get("statusMsg").toString());
			logger.info("发送短信失败,错误码={},错误信息={}", result.get("statusCode"), result.get("statusMsg"));
		}
		return responseDto;
	}

	private String generateMobileCode()
	{
		Random random = new Random();
		Integer randomInteger = random.nextInt(900000) + 100000;
		return randomInteger.toString();
	}
}
