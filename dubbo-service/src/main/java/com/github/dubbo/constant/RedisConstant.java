package com.github.dubbo.constant;

public class RedisConstant {
    
    public static final String GOODS_ITEM_PREFIX = "g:item:"; // 商品hash key 前缀
    
    public static final String GOODS_ITEM_STAT_PREFIX = "g:item:stat"; // 商品hash key 前缀
    
    public static final String GOODS_ITEM_ID_SALECOUNT_ZSET = "itemid:sale";
    
    public static final String GOODS_ITEM_ID_PUBLISHTIME_ZSET = "itemid:time";
    
    public static final String GOODS_ITEM_ID_CATEGORY_SET = "itemid:cate:";
    
    public static final String GOODS_ITEM_ID_BRAND_SET = "itemid:brand:";
    
    public static final String GOODS_BRAND_SET = "brand";
    
    public static final String GOODS_BRAND_PREFIX = "brand:";
    
    public static final String GOODS_CATEGORY_SET = "category";
    
    public static final String GOODS_CATEGORY_PREFIX = "cate:";
    
    public static final String GOODS_ITEM_STAT_FIELD_STOCK = "stock";
    
    public static final String GOODS_ITEM_STAT_FIELD_SALECOUNT = "saleCount";
    
    public static final int SECONDS_INTERSECT_SET = 60;
    
    public static final int SECONDS_GOODS_ITEM = 10;
    
    public static final int SECONDS_ITEMID_SALECOUNT_ZSET = 300;
    
    public static final int SECONDS_ITEMID_PUBLISHTIME_ZSET = 600;
    
    public static final int SECONDS_ITEMID_CATEGORY_SET = 700;
    
    public static final int SECONDS_ITEMID_BRAND_SET = 730;
    
    public static final int SECONDS_CATEGORY_SET = 7000;
    
    public static final int SECONDS_BRAND_SET = 7200;
    
    public static final int NUMBER_SALECOUNT_ZSET = 100;
    
    public static final int NUMBER_PUBLISHTIME_ZSET = 100;
    
    public static final String LOCK_KEY_STOCK = "lock_stock";
}
