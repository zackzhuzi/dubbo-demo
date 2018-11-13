package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GoodsItemDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 267264093203959304L;
    private int        goodsItemId;
    private String     goodsItemName;
    private int        goodsId;
    private String     goodsName;
    private String     goodsDesc;
    private BigDecimal price;
    private BigDecimal marketPrice;
    private BigDecimal shopPrice;
    private int        stock;
    private int        saleCount;
    private String     categoryName;
    private String     brandName;
    private int        categoryId;
    private int        brandId;
    private String     brandDesc;
    private String     brandLogo;
    private Date       createDate;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getGoodsItemId() {
        return goodsItemId;
    }

    public void setGoodsItemId(int goodsItemId) {
        this.goodsItemId = goodsItemId;
    }

    public String getGoodsItemName() {
        return goodsItemName;
    }

    public void setGoodsItemName(String goodsItemName) {
        this.goodsItemName = goodsItemName;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(BigDecimal shopPrice) {
        this.shopPrice = shopPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandDesc() {
        return brandDesc;
    }

    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "GoodsItemDTO [goodsItemId=" + goodsItemId + ", goodsItemName=" + goodsItemName + ", goodsId=" + goodsId
               + ", goodsName=" + goodsName + ", goodsDesc=" + goodsDesc + ", price=" + price + ", marketPrice="
               + marketPrice + ", shopPrice=" + shopPrice + ", stock=" + stock + ", saleCount=" + saleCount
               + ", categoryName=" + categoryName + ", brandName=" + brandName + ", categoryId=" + categoryId
               + ", brandId=" + brandId + ", brandDesc=" + brandDesc + ", brandLogo=" + brandLogo + ", createDate="
               + createDate + "]";
    }

}
