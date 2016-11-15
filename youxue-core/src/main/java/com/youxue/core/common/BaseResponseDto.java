package com.youxue.core.common;

import java.io.Serializable;

public class BaseResponseDto implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int result;
	protected String resultDesc;

	public static BaseResponseDto errorDto()
	{
		BaseResponseDto error = new BaseResponseDto();
		error.setResult(-1);
		error.setDesc("系统出错！");
		return error;
	}

	public static BaseResponseDto exceptionDto()
	{
		BaseResponseDto except = new BaseResponseDto();
		except.setResult(-100);
		except.setDesc("抛出异常！");
		return except;
	}

	public static BaseResponseDto notLoginDto()
	{
		BaseResponseDto noLogin = new BaseResponseDto();
		noLogin.setResult(-2);
		noLogin.setDesc("用户未登录！");
		return noLogin;
	}

	public static BaseResponseDto successDto()
	{
		BaseResponseDto success = new BaseResponseDto();
		success.setResult(100);
		success.setDesc("success！");
		return success;
	}

	public static BaseResponseDto resultDto(int result, String desc)
	{
		BaseResponseDto resInfo = new BaseResponseDto();
		resInfo.setResult(result);
		resInfo.setDesc(desc);
		return resInfo;
	}

	public boolean isSuccess()
	{
		//100表示成功,其他表示失败
		if (result == 100 || result == 103)
		{
			return true;
		}
		return false;
	}

	public int getResult()
	{
		return result;
	}

	public void setResult(int result)
	{
		this.result = result;
	}

	public String getResultDesc()
	{
		return resultDesc;
	}

	public void setResultDesc(String resultDesc)
	{
		this.resultDesc = resultDesc;
	}

	public BaseResponseDto setDesc(String resultDesc)
	{
		this.resultDesc = resultDesc;
		return this;
	}

	@Override
	public String toString()
	{
		return "BaseResponseDto [result=" + result + ", resultDesc=" + resultDesc + "]";
	}
}
