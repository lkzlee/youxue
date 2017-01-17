package com.youxue.core.vo;

import java.util.Date;

public class AboutUsVo
{
	private String type;

	private String imgurl;

	private String descs;

	private Date createTime;

	private Date updateTime;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type == null ? null : type.trim();
	}

	public String getImgurl()
	{
		return imgurl;
	}

	public void setImgurl(String imgurl)
	{
		this.imgurl = imgurl == null ? null : imgurl.trim();
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	@Override
	public String toString()
	{
		return "AboutUsVo [type=" + type + ", imgurl=" + imgurl + ", descs=" + descs + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}

	public String getDescs()
	{
		return descs;
	}

	public void setDescs(String descs)
	{
		this.descs = descs;
	}

}