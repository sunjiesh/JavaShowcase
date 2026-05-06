package cn.yanshi123.payment;

import cn.yanshi123.payment.model.PaymentRequest;
import cn.yanshi123.payment.model.PaymentResponse;

/**
 * 支付服务接口
 */
public interface PaymentService {
    /**
     * 创建支付订单
     * @param request 支付请求
     * @return 支付响应
     */
    PaymentResponse createPayment(PaymentRequest request);

    /**
     * 查询支付结果
     * @param orderNo 订单号
     * @return 支付响应
     */
    PaymentResponse queryPayment(String orderNo);

    /**
     * 关闭支付订单
     * @param orderNo 订单号
     * @return 支付响应
     */
    PaymentResponse closePayment(String orderNo);
}