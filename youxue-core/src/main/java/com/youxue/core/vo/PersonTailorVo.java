package com.youxue.core.vo;

import java.util.Date;

public class PersonTailorVo
{

	private String id;

	private String name;
	private String email;

	private String phone;

	private String destination;

	private Date departureTime;

	private String descs;

	private String remark;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id == null ? null : id.trim();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name == null ? null : name.trim();
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone == null ? null : phone.trim();
	}

	public String getDestination()
	{
		return destination;
	}

	public void setDestination(String destination)
	{
		this.destination = destination == null ? null : destination.trim();
	}

	public Date getDepartureTime()
	{
		return departureTime;
	}

	public void setDepartureTime(Date departureTime)
	{
		this.departureTime = departureTime;
	}

	public String getDescs()
	{
		return descs;
	}

	public void setDescs(String descs)
	{
		this.descs = descs == null ? null : descs.trim();
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
}