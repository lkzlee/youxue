package com.youxue.core.vo;

import java.math.BigDecimal;
import java.util.Date;

public class CampsVo {
    private String campsId;

    private String campsName;

    private String campsTitle;

    private String campsDesc;

    private String campsImages;

    private String campsLocale;

    private String orientedPeople;

    private String feature;

    private String serviceSupport;

    private String courseDesc;

    private String activityDesc;

    private String campsFoodDesc;

    private String campsFoodsPhotos;

    private String campsHotelDesc;

    private String campsHotelPhotos;

    private String feeDesc;

    private Integer status;

    private BigDecimal totalPrice;

    private Long doneCount;

    private Date deadlineDate;

    private Date departureDate;

    private Date startDate;

    private Date durationTime;

    private Date createTime;

    private Date updateTime;

    public String getCampsId() {
        return campsId;
    }

    public void setCampsId(String campsId) {
        this.campsId = campsId == null ? null : campsId.trim();
    }

    public String getCampsName() {
        return campsName;
    }

    public void setCampsName(String campsName) {
        this.campsName = campsName == null ? null : campsName.trim();
    }

    public String getCampsTitle() {
        return campsTitle;
    }

    public void setCampsTitle(String campsTitle) {
        this.campsTitle = campsTitle == null ? null : campsTitle.trim();
    }

    public String getCampsDesc() {
        return campsDesc;
    }

    public void setCampsDesc(String campsDesc) {
        this.campsDesc = campsDesc == null ? null : campsDesc.trim();
    }

    public String getCampsImages() {
        return campsImages;
    }

    public void setCampsImages(String campsImages) {
        this.campsImages = campsImages == null ? null : campsImages.trim();
    }

    public String getCampsLocale() {
        return campsLocale;
    }

    public void setCampsLocale(String campsLocale) {
        this.campsLocale = campsLocale == null ? null : campsLocale.trim();
    }

    public String getOrientedPeople() {
        return orientedPeople;
    }

    public void setOrientedPeople(String orientedPeople) {
        this.orientedPeople = orientedPeople == null ? null : orientedPeople.trim();
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature == null ? null : feature.trim();
    }

    public String getServiceSupport() {
        return serviceSupport;
    }

    public void setServiceSupport(String serviceSupport) {
        this.serviceSupport = serviceSupport == null ? null : serviceSupport.trim();
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc == null ? null : courseDesc.trim();
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc == null ? null : activityDesc.trim();
    }

    public String getCampsFoodDesc() {
        return campsFoodDesc;
    }

    public void setCampsFoodDesc(String campsFoodDesc) {
        this.campsFoodDesc = campsFoodDesc == null ? null : campsFoodDesc.trim();
    }

    public String getCampsFoodsPhotos() {
        return campsFoodsPhotos;
    }

    public void setCampsFoodsPhotos(String campsFoodsPhotos) {
        this.campsFoodsPhotos = campsFoodsPhotos == null ? null : campsFoodsPhotos.trim();
    }

    public String getCampsHotelDesc() {
        return campsHotelDesc;
    }

    public void setCampsHotelDesc(String campsHotelDesc) {
        this.campsHotelDesc = campsHotelDesc == null ? null : campsHotelDesc.trim();
    }

    public String getCampsHotelPhotos() {
        return campsHotelPhotos;
    }

    public void setCampsHotelPhotos(String campsHotelPhotos) {
        this.campsHotelPhotos = campsHotelPhotos == null ? null : campsHotelPhotos.trim();
    }

    public String getFeeDesc() {
        return feeDesc;
    }

    public void setFeeDesc(String feeDesc) {
        this.feeDesc = feeDesc == null ? null : feeDesc.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getDoneCount() {
        return doneCount;
    }

    public void setDoneCount(Long doneCount) {
        this.doneCount = doneCount;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Date durationTime) {
        this.durationTime = durationTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}