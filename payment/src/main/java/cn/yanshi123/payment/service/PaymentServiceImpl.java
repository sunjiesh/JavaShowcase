package cn.yanshi123.payment.service;

import cn.yanshi123.payment.PaymentService;
import cn.yanshi123.payment.factory.PaymentFactory;
import cn.yanshi123.payment.model.PaymentRequest;
import cn.yanshi123.payment.model.PaymentResponse;

/**
 * 统一支付服务实现
 */
public class PaymentServiceImpl {
    
    /**
     * 创建支付订单
     * @param request 支付请求
     * @return 支付响应
     */
    public PaymentResponse createPayment(PaymentRequest request) {
        // 根据支付类型获取对应的支付服务
        PaymentService paymentService = PaymentFactory.getPaymentService(request.getPayType());
        return paymentService.createPayment(request);
    }

    /**
     * 查询支付结果
     * @param orderNo 订单号
     * @param payType 支付类型
     * @return 支付响应
     */
    public PaymentResponse queryPayment(String orderNo, String payType) {
        PaymentService paymentService = PaymentFactory.getPaymentService(payType);
        return paymentService.queryPayment(orderNo);
    }

    /**
     * 关闭支付订单
     * @param orderNo 订单号
     * @param payType 支付类型
     * @return 支付响应
     */
    public PaymentResponse closePayment(String orderNo, String payType) {
        PaymentService paymentService = PaymentFactory.getPaymentService(payType);
        return paymentService.closePayment(orderNo);
    }
}