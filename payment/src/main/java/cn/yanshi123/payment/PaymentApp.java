package cn.yanshi123.payment;

import cn.yanshi123.payment.factory.PaymentFactory;
import cn.yanshi123.payment.model.PaymentRequest;
import cn.yanshi123.payment.model.PaymentResponse;
import cn.yanshi123.payment.service.PaymentServiceImpl;

import java.math.BigDecimal;

/**
 * 聚合支付示例应用
 */
public class PaymentApp {

    public static void main(String[] args) {
        // 创建统一支付服务实例
        PaymentServiceImpl paymentService = new PaymentServiceImpl();

        // 演示支付宝支付
        System.out.println("=== 支付宝支付演示 ===");
        PaymentRequest aliPayRequest = createPaymentRequest("ALIPAY", "ORDER_001", new BigDecimal("100.00"));
        PaymentResponse aliPayResponse = paymentService.createPayment(aliPayRequest);
        System.out.println("支付宝支付创建结果: " + aliPayResponse.isSuccess());
        System.out.println("支付二维码URL: " + aliPayResponse.getQrCodeUrl());

        // 演示微信支付
        System.out.println("\n=== 微信支付演示 ===");
        PaymentRequest weChatPayRequest = createPaymentRequest("WECHAT", "ORDER_002", new BigDecimal("200.00"));
        PaymentResponse weChatPayResponse = paymentService.createPayment(weChatPayRequest);
        System.out.println("微信支付创建结果: " + weChatPayResponse.isSuccess());
        System.out.println("支付二维码URL: " + weChatPayResponse.getQrCodeUrl());

        // 演示查询支付结果
        System.out.println("\n=== 查询支付结果演示 ===");
        PaymentResponse queryResponse = paymentService.queryPayment("ORDER_001", "ALIPAY");
        System.out.println("查询结果: " + queryResponse.getData());

        // 演示关闭支付订单
        System.out.println("\n=== 关闭支付订单演示 ===");
        PaymentResponse closeResponse = paymentService.closePayment("ORDER_002", "WECHAT");
        System.out.println("关闭结果: " + closeResponse.getData());
    }

    /**
     * 创建支付请求对象
     * @param payType 支付类型
     * @param orderNo 订单号
     * @param amount 金额
     * @return 支付请求对象
     */
    private static PaymentRequest createPaymentRequest(String payType, String orderNo, BigDecimal amount) {
        PaymentRequest request = new PaymentRequest();
        request.setPayType(payType);
        request.setOrderNo(orderNo);
        request.setAmount(amount);
        request.setSubject("测试商品-" + orderNo);
        request.setDescription("这是一笔测试支付-" + orderNo);
        request.setNotifyUrl("http://yourdomain.com/notify");

        return request;
    }
}