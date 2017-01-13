package com.youxue.pc.campsDetail.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.youxue.core.common.BaseResponseDto;
import com.youxue.core.vo.CampsTraceVo;

public class CampsDetailDto extends BaseResponseDto implements Serializable
{
	private int shopCartCount;

	public String getCampsId()
	{
		return campsId;
	}

	public void setCampsId(String campsId)
	{
		this.campsId = campsId;
	}

	public String getCampsName()
	{
		return campsName;
	}

	public void setCampsName(String campsName)
	{
		this.campsName = campsName;
	}

	public String getCampsTitle()
	{
		return campsTitle;
	}

	public void setCampsTitle(String campsTitle)
	{
		this.campsTitle = campsTitle;
	}

	public String getCampsDesc()
	{
		return campsDesc;
	}

	public void setCampsDesc(String campsDesc)
	{
		this.campsDesc = campsDesc;
	}

	public String getCampsImages()
	{
		return campsImages;
	}

	public void setCampsImages(String campsImages)
	{
		this.campsImages = campsImages;
	}

	public String getRealCampsImages()
	{
		return realCampsImages;
	}

	public void setRealCampsImages(String realCampsImages)
	{
		this.realCampsImages = realCampsImages;
	}

	public String getCampsLocale()
	{
		return campsLocale;
	}

	public void setCampsLocale(String campsLocale)
	{
		this.campsLocale = campsLocale;
	}

	public String getCampsLocaleId()
	{
		return campsLocaleId;
	}

	public void setCampsLocaleId(String campsLocaleId)
	{
		this.campsLocaleId = campsLocaleId;
	}

	public String getCampsSubjectId()
	{
		return campsSubjectId;
	}

	public void setCampsSubjectId(String campsSubjectId)
	{
		this.campsSubjectId = campsSubjectId;
	}

	public String getOrientedPeople()
	{
		return orientedPeople;
	}

	public void setOrientedPeople(String orientedPeople)
	{
		this.orientedPeople = orientedPeople;
	}

	public String getFeature()
	{
		return feature;
	}

	public void setFeature(String feature)
	{
		this.feature = feature;
	}

	public String getServiceSupport()
	{
		return serviceSupport;
	}

	public void setServiceSupport(String serviceSupport)
	{
		this.serviceSupport = serviceSupport;
	}

	public String getCourseDesc()
	{
		return courseDesc;
	}

	public void setCourseDesc(String courseDesc)
	{
		this.courseDesc = courseDesc;
	}

	public String getActivityDesc()
	{
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc)
	{
		this.activityDesc = activityDesc;
	}

	public String getCampsFoodDesc()
	{
		return campsFoodDesc;
	}

	public void setCampsFoodDesc(String campsFoodDesc)
	{
		this.campsFoodDesc = campsFoodDesc;
	}

	public String getCampsFoodsPhotos()
	{
		return campsFoodsPhotos;
	}

	public void setCampsFoodsPhotos(String campsFoodsPhotos)
	{
		this.campsFoodsPhotos = campsFoodsPhotos;
	}

	public String getCampsHotelDesc()
	{
		return campsHotelDesc;
	}

	public void setCampsHotelDesc(String campsHotelDesc)
	{
		this.campsHotelDesc = campsHotelDesc;
	}

	public String getCampsHotelPhotos()
	{
		return campsHotelPhotos;
	}

	public void setCampsHotelPhotos(String campsHotelPhotos)
	{
		this.campsHotelPhotos = campsHotelPhotos;
	}

	public String getFeeDesc()
	{
		return feeDesc;
	}

	public void setFeeDesc(String feeDesc)
	{
		this.feeDesc = feeDesc;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public BigDecimal getTotalPrice()
	{
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice)
	{
		this.totalPrice = totalPrice;
	}

	public Long getDoneCount()
	{
		return doneCount;
	}

	public void setDoneCount(Long doneCount)
	{
		this.doneCount = doneCount;
	}

	public Date getDeadlineDate()
	{
		return deadlineDate;
	}

	public void setDeadlineDate(Date deadlineDate)
	{
		this.deadlineDate = deadlineDate;
	}

	public Date getDepartureDate()
	{
		return departureDate;
	}

	public void setDepartureDate(Date departureDate)
	{
		this.departureDate = departureDate;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Integer getDurationTime()
	{
		return durationTime;
	}

	public void setDurationTime(Integer durationTime)
	{
		this.durationTime = durationTime;
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

	private static final long serialVersionUID = 1L;
	private List<CampsTraceVo> traces;
	private String campsId;

	private String campsName;

	private String campsTitle;

	private String campsDesc;

	private String campsImages;
	private String realCampsImages;

	private String campsLocale;
	private String campsLocaleId;//目的地分类id
	private String campsSubjectId;//主题类型id
	private String orientedPeople;

	private String feature;

	private String serviceSupport;

	private String courseDesc;

	private String activityDesc;

	private String campsFoodDesc;

	private String campsFoodsPhotos;
	private String realCampsFoodsPhotos;

	private String campsHotelDesc;

	private String campsHotelPhotos;
	private String realCampsHotelPhotos;

	private String feeDesc;

	private Integer status;

	private BigDecimal totalPrice;

	private Long doneCount;

	private Date deadlineDate;

	private Date departureDate;

	private Date startDate;

	private Integer durationTime;//持续天数

	private Date createTime;

	private Date updateTime;

	private Integer shopCartCount;

	public List<CampsTraceVo> getTraces()
	{
		return traces;
	}

	public void setTraces(List<CampsTraceVo> traces)
	{
		this.traces = traces;
	}

	public String getRealCampsHotelPhotos()
	{
		return realCampsHotelPhotos;
	}

	public void setRealCampsHotelPhotos(String realCampsHotelPhotos)
	{
		this.realCampsHotelPhotos = realCampsHotelPhotos;
	}

	public String getRealCampsFoodsPhotos()
	{
		return realCampsFoodsPhotos;
	}

	public void setRealCampsFoodsPhotos(String realCampsFoodsPhotos)
	{
		this.realCampsFoodsPhotos = realCampsFoodsPhotos;
	}

	public int getShopCartCount()
	{
		return shopCartCount;
	}

	public void setShopCartCount(int shopCartCount)
	{
		this.shopCartCount = shopCartCount;
	}

}
