package com.youxue.core.vo;

public class CampsCategoryRelationVo {
    private String campsId;

    private String categoryId;

    public String getCampsId() {
        return campsId;
    }

    public void setCampsId(String campsId) {
        this.campsId = campsId == null ? null : campsId.trim();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }
}