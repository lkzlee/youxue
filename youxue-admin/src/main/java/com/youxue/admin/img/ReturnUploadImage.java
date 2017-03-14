package com.youxue.admin.img;

import java.io.Serializable;

public class ReturnUploadImage implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String state;//上传状态SUCCESS 一定要大写
	private String url;//上传地址
	private String title;//图片名称demo.jpg
	private String original;//图片名称demo.jpg
	private long size;
	private String type;

	public long getSize()
	{
		return size;
	}

	public void setSize(long size)
	{
		this.size = size;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getOriginal()
	{
		return original;
	}

	public void setOriginal(String original)
	{
		this.original = original;
	}

}