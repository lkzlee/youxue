package com.youxue.pc.uc.dto;

import java.io.Serializable;

public class UserInfoDto implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nickName;

	private Integer gender;

	private String birthTime;

	private String loveCity;;

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public String getLoveCity()
	{
		return loveCity;
	}

	public void setLoveCity(String loveCity)
	{
		this.loveCity = loveCity;
	}

	public Integer getGender()
	{
		return gender;
	}

	public void setGender(Integer gender)
	{
		this.gender = gender;
	}

	public String getBirthTime()
	{
		return birthTime;
	}

	public void setBirthTime(String birthTime)
	{
		this.birthTime = birthTime;
	}

	@Override
	public String toString()
	{
		return "UserInfoDto [nickName=" + nickName + ", gender=" + gender + ", birthTime=" + birthTime + ", loveCity="
				+ loveCity + "]";
	}

}