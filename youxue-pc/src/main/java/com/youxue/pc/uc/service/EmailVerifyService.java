package com.youxue.pc.uc.service;

import com.youxue.core.vo.UserInfoVo;

public interface EmailVerifyService
{

	void sendActiveEmailUrl(UserInfoVo userInfo);

	boolean isValidActiveAccountUrl(String accountId, String key);

}
