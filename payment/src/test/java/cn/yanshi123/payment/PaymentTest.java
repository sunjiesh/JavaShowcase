package cn.yanshi123.payment;

import cn.yanshi123.payment.factory.PaymentFactory;
import cn.yanshi123.payment.model.PaymentRequest;
import cn.yanshi123.payment.model.PaymentResponse;
import cn.yanshi123.payment.service.PaymentServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * 支付模块测试类
 */
public class PaymentTest {

    @Test
    public void testPaymentFactory() {
        // 测试支付宝支付服务获取
        assertNotNull(PaymentFactory.getPaymentService(PaymentFactory.ALI_PAY));
        
        // 测试微信支付服务获取
        assertNotNull(PaymentFactory.getPaymentService(PaymentFactory.WECHAT_PAY));
        
        // 测试不支持的支付类型
        try {
            PaymentFactory.getPaymentService("UNSUPPORTED");
            fail("Expected IllegalArgumentException for unsupported payment type");
        } catch (IllegalArgumentException e) {
            assertEquals("Unsupported payment type: UNSUPPORTED", e.getMessage());
        }
    }

    @Test
    public void testCreatePayment() {
        PaymentServiceImpl paymentService = new PaymentServiceImpl();
        
        // 测试支付宝支付创建
        PaymentRequest aliPayRequest = createTestPaymentRequest("ALIPAY", "TEST_ORDER_001", new BigDecimal("100.00"));
        PaymentResponse aliPayResponse = paymentService.createPayment(aliPayRequest);
        
        assertTrue(aliPayResponse.isSuccess());
        assertNotNull(aliPayResponse.getQrCodeUrl());
        assertEquals("TEST_ORDER_001", aliPayResponse.getOrderNo());
        
        // 测试微信支付创建
        PaymentRequest weChatPayRequest = createTestPaymentRequest("WECHAT", "TEST_ORDER_002", new BigDecimal("200.00"));
        PaymentResponse weChatPayResponse = paymentService.createPayment(weChatPayRequest);
        
        assertTrue(weChatPayResponse.isSuccess());
        assertNotNull(weChatPayResponse.getQrCodeUrl());
        assertEquals("TEST_ORDER_002", weChatPayResponse.getOrderNo());
    }

    @Test
    public void testQueryPayment() {
        PaymentServiceImpl paymentService = new PaymentServiceImpl();
        
        // 测试支付宝支付查询
        PaymentResponse aliPayResponse = paymentService.queryPayment("TEST_ORDER_001", "ALIPAY");
        assertTrue(aliPayResponse.isSuccess());
        assertEquals("TEST_ORDER_001", aliPayResponse.getOrderNo());
        
        // 测试微信支付查询
        PaymentResponse weChatPayResponse = paymentService.queryPayment("TEST_ORDER_002", "WECHAT");
        assertTrue(weChatPayResponse.isSuccess());
        assertEquals("TEST_ORDER_002", weChatPayResponse.getOrderNo());
    }

    @Test
    public void testClosePayment() {
        PaymentServiceImpl paymentService = new PaymentServiceImpl();
        
        // 测试支付宝支付关闭
        PaymentResponse aliPayResponse = paymentService.closePayment("TEST_ORDER_001", "ALIPAY");
        assertTrue(aliPayResponse.isSuccess());
        assertEquals("TEST_ORDER_001", aliPayResponse.getOrderNo());
        
        // 测试微信支付关闭
        PaymentResponse weChatPayResponse = paymentService.closePayment("TEST_ORDER_002", "WECHAT");
        assertTrue(weChatPayResponse.isSuccess());
        assertEquals("TEST_ORDER_002", weChatPayResponse.getOrderNo());
    }

    /**
     * 创建测试支付请求对象
     * @param payType 支付类型
     * @param orderNo 订单号
     * @param amount 金额
     * @return 支付请求对象
     */
    private PaymentRequest createTestPaymentRequest(String payType, String orderNo, BigDecimal amount) {
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