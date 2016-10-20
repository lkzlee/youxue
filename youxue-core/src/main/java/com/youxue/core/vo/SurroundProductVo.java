package com.youxue.core.vo;

import java.math.BigDecimal;

public class SurroundProductVo {
    private String productId;

    private String productName;

    private String productDesc;

    private String productPhotos;

    private BigDecimal productPrice;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc == null ? null : productDesc.trim();
    }

    public String getProductPhotos() {
        return productPhotos;
    }

    public void setProductPhotos(String productPhotos) {
        this.productPhotos = productPhotos == null ? null : productPhotos.trim();
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
}