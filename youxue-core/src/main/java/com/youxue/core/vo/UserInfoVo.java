package com.youxue.core.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.youxue.core.common.BaseResponseDto;

public class UserInfoVo extends BaseResponseDto
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String accountId;

	private String nickName;

	private String email;

	private Integer emailActiveStatus;

	private String mobile;

	private Integer gender;

	private String createIp;

	private Date createTime;

	private Date birthTime;

	private BigDecimal credit;

	private String photoUrl;

	private Date updateTime;

	private boolean ifPop;

	private String loveCity;

	private int unReads;

	private String openId;

	private BigDecimal spend;

	public String getAccountId()
	{
		return accountId;
	}

	public void setAccountId(String accountId)
	{
		this.accountId = accountId == null ? null : accountId.trim();
	}

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName == null ? null : nickName.trim();
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email == null ? null : email.trim();
	}

	public Integer getEmailActiveStatus()
	{
		return emailActiveStatus;
	}

	public void setEmailActiveStatus(Integer emailActiveStatus)
	{
		this.emailActiveStatus = emailActiveStatus;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public Integer getGender()
	{
		return gender;
	}

	public void setGender(Integer gender)
	{
		this.gender = gender;
	}

	public String getCreateIp()
	{
		return createIp;
	}

	public void setCreateIp(String createIp)
	{
		this.createIp = createIp == null ? null : createIp.trim();
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getBirthTime()
	{
		return birthTime;
	}

	public void setBirthTime(Date birthTime)
	{
		this.birthTime = birthTime;
	}

	public BigDecimal getCredit()
	{
		return credit;
	}

	public void setCredit(BigDecimal credit)
	{
		this.credit = credit;
	}

	public String getPhotoUrl()
	{
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl)
	{
		this.photoUrl = photoUrl == null ? null : photoUrl.trim();
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public boolean isIfPop()
	{
		return ifPop;
	}

	public void setIfPop(boolean ifPop)
	{
		this.ifPop = ifPop;
	}

	public String getLoveCity()
	{
		return loveCity;
	}

	public void setLoveCity(String loveCity)
	{
		this.loveCity = loveCity;
	}

	public int getUnReads()
	{
		return unReads;
	}

	public void setUnReads(int unReads)
	{
		this.unReads = unReads;
	}

	public String getOpenId()
	{
		return openId;
	}

	public void setOpenId(String openId)
	{
		this.openId = openId;
	}

	@Override
	public String toString()
	{
		return "UserInfoVo [accountId=" + accountId + ", nickName=" + nickName + ", email=" + email
				+ ", emailActiveStatus=" + emailActiveStatus + ", mobile=" + mobile + ", gender=" + gender
				+ ", createIp=" + createIp + ", createTime=" + createTime + ", birthTime=" + birthTime + ", credit="
				+ credit + ", photoUrl=" + photoUrl + ", updateTime=" + updateTime + ", ifPop=" + ifPop + ", loveCity="
				+ loveCity + ", unReads=" + unReads + ", openId=" + openId + "]";
	}

	public BigDecimal getSpend()
	{
		return spend;
	}

	public void setSpend(BigDecimal spend)
	{
		this.spend = spend;
	}
}