package com.youxue.core.vo;

import com.youxue.core.constant.ImgConstant;

public class CampsTraceVo
{
	private String traceId;

	private String campusId;

	private String traceName;

	private String traceDesc;

	private Integer traceWeight;

	private String tracePhotos;
	private String realTracePhotos;

	public String getTraceId()
	{
		return traceId;
	}

	public void setTraceId(String traceId)
	{
		this.traceId = traceId == null ? null : traceId.trim();
	}

	public String getCampusId()
	{
		return campusId;
	}

	public void setCampusId(String campusId)
	{
		this.campusId = campusId == null ? null : campusId.trim();
	}

	public String getTraceName()
	{
		return traceName;
	}

	public void setTraceName(String traceName)
	{
		this.traceName = traceName == null ? null : traceName.trim();
	}

	public String getTraceDesc()
	{
		return traceDesc;
	}

	public void setTraceDesc(String traceDesc)
	{
		this.traceDesc = traceDesc == null ? null : traceDesc.trim();
	}

	public Integer getTraceWeight()
	{
		return traceWeight;
	}

	public void setTraceWeight(Integer traceWeight)
	{
		this.traceWeight = traceWeight;
	}

	public String getTracePhotos()
	{
		return tracePhotos;
	}

	public void setTracePhotos(String tracePhotos)
	{
		this.tracePhotos = tracePhotos == null ? null : tracePhotos.trim();
	}

	public String getRealTracePhotos()
	{
		return ImgConstant.getHttpImgUrls(tracePhotos);
	}

	public void setRealTracePhotos(String realTracePhotos)
	{
		this.realTracePhotos = realTracePhotos;
	}
}