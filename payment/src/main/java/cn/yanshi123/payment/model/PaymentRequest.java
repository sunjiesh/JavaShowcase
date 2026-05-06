package cn.yanshi123.payment.model;

import java.math.BigDecimal;

/**
 * 支付请求模型
 */
public class PaymentRequest {
    // 订单号
    private String orderNo;
    // 支付金额
    private BigDecimal amount;
    // 商品标题
    private String subject;
    // 商品描述
    private String description;
    // 支付方式类型
    private String payType;
    // 回调地址
    private String notifyUrl;
    // 扩展参数
    private Object extraParams;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public Object getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(Object extraParams) {
        this.extraParams = extraParams;
    }
}