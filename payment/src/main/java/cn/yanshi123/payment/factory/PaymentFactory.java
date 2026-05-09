package cn.yanshi123.payment.factory;

import cn.yanshi123.payment.PaymentService;
import cn.yanshi123.payment.alipay.AliPayService;
import cn.yanshi123.payment.wechat.WeChatPayService;

/**
 * 支付工厂类
 */
public class PaymentFactory {
    public static final String ALI_PAY = "ALIPAY";
    public static final String WECHAT_PAY = "WECHAT";

    /**
     * 根据支付类型获取支付服务实例
     * @param payType 支付类型
     * @return 支付服务实例
     */
    public static PaymentService getPaymentService(String payType) {
        if (payType == null) {
            throw new IllegalArgumentException("Payment type cannot be null");
        }
        switch (payType.toUpperCase()) {
            case ALI_PAY:
                return new AliPayService();
            case WECHAT_PAY:
                return new WeChatPayService();
            default:
                throw new IllegalArgumentException("Unsupported payment type: " + payType);
        }
    }
}