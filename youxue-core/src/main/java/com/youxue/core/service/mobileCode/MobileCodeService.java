package com.youxue.core.service.mobileCode;

import com.youxue.core.common.BaseResponseDto;

public interface MobileCodeService
{
	BaseResponseDto sendMobileCode(String phone);

	boolean checkMobileCode(String phone, String code);

}
