package com.wwls.common.constant;

public class MongoTableConstant {
    public final static String mongoTableProductDetail = "productDetail";//商品详情table
    public final static String commonUserAuthorityManage="commonUserAuthorityManage";
    public static final String mcproductattributeoption="mcproductattributeoption";//属性选项
    public static final String mcGoodsCategory="mcGoodsCategory";//运营分类
    public static final String mcGoodsCategoryOffice="mcGoodsCategoryOffice";//运营分类与机构的关联关系
    public static final String commonNews="commonNews";//新闻资讯
    public static final String mcTradeOrder = "mcTradeOrder";//回收超过30分钟未支付的订单
    public static final String mcTradeOrderItem = "mcTradeOrderItem";//订单详情
    public static final String McTradeOrderProcedure = "McTradeOrderProcedure";
    public static final String MQProductUpDownIdS="MQProductUpDownIdS";//资源分发时候，消息队列
    public static final String MogonDBTradeOrderLog="MogonDBTradeOrderLog";//订单--发货管理，消息队列
    public static final String Detail="Detail";//商品---用于临时用户评论
    public static final String News="news";//资讯---用于临时用户评论
    public static final String CacheManage="CacheManage";//缓存管理，消息队列
    public static final String appMainUser="appMainUser";//用户主表
}
