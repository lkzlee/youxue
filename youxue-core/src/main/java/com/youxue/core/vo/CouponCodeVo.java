package com.youxue.core.vo;

import java.math.BigDecimal;
import java.util.Date;

public class CouponCodeVo
{
	public static final int NORMAL = 1;
	public static final int INIT = 0;

	protected int result;
	protected String resultDesc;

	private String codeId;
	private String codeName;

	private String codeValue;

	private BigDecimal codeAmount;

	private Date createTime;

	private Date startTime;

	private Date endTime;

	private Integer status;
	private String statusStr;

	private Integer useCount;

	private String categoryIds;
	private String categorys;//后台管理系统前端显示字段
	private String creator;

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

	public String getCodeId()
	{
		return codeId;
	}

	public void setCodeId(String codeId)
	{
		this.codeId = codeId == null ? null : codeId.trim();
	}

	public String getCodeValue()
	{
		return codeValue;
	}

	public void setCodeValue(String codeValue)
	{
		this.codeValue = codeValue == null ? null : codeValue.trim();
	}

	public BigDecimal getCodeAmount()
	{
		return codeAmount;
	}

	public void setCodeAmount(BigDecimal codeAmount)
	{
		this.codeAmount = codeAmount;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	public Date getEndTime()
	{
		return endTime;
	}

	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Integer getUseCount()
	{
		return useCount;
	}

	public void setUseCount(Integer useCount)
	{
		this.useCount = useCount;
	}

	public String getCategoryIds()
	{
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds)
	{
		this.categoryIds = categoryIds == null ? null : categoryIds.trim();
	}

	@Override
	public String toString()
	{
		return "CouponCodeVo [result=" + result + ", resultDesc=" + resultDesc + ", codeId=" + codeId + ", codeValue="
				+ codeValue + ", codeAmount=" + codeAmount + ", createTime=" + createTime + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", status=" + status + ", useCount=" + useCount + ", categoryIds="
				+ categoryIds + "]";
	}

	public String getCodeName()
	{
		return codeName;
	}

	public void setCodeName(String codeName)
	{
		this.codeName = codeName;
	}

	public String getCategorys()
	{
		return categorys;
	}

	public void setCategorys(String categorys)
	{
		this.categorys = categorys;
	}

	public String getCreator()
	{
		return creator;
	}

	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	public String getStatusStr()
	{
		return status == 0 ? "下架状态" : "在线状态";
	}

	public void setStatusStr(String statusStr)
	{
		this.statusStr = statusStr;
	}
}