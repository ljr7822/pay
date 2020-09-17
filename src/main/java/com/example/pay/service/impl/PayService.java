package com.example.pay.service.impl;

import com.example.pay.service.IPayService;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 支付接口的实现类
 *
 * @Author: iwen大大怪
 * @DateTime: 2020/9/16 22:18
 */
@Slf4j
@Service
public class PayService implements IPayService {

    @Autowired
    private BestPayService bestPayService;

    /**
     * 创建或发起支付
     *
     * @param orderId 订单号
     * @param amount  订单金额
     * @param bestPayTypeEnum 支付方式
     * @return 支付二维码链接
     */
    @Override
    public PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum) {
        // 写入数据库


        PayRequest request = new PayRequest();
        request.setOrderName("4559066-最好的支付sdk");
        request.setOrderId(orderId);
        request.setOrderAmount(amount.doubleValue());
        // 一定要选择支付类型为：WXPAY_NATIVE
        // request.setPayTypeEnum(BestPayTypeEnum.WXPAY_NATIVE);
        request.setPayTypeEnum(bestPayTypeEnum);

        PayResponse response = bestPayService.pay(request);
        log.info("response={}", response);

        return response;
    }

    /**
     * 实现异步通知处理
     *
     * @param notifyData
     */
    @Override
    public String asyncNotify(String notifyData) {
        // 1.签名校验
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("payResponse={}", payResponse);

        // 2.金额校验（从数据库查询订单）

        // 3.修改订单支付状态

        if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.WX){
            // 4.告诉微信不用通知了
            return "<xml>\n" +
                    "   <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "   <return_msg><![CDATA[OK]]></return_msg>\n" +
                    "</xml>";
        }else if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.ALIPAY){
            // 4.告诉支付宝不用通知了
            return "success";
        }
        throw new RuntimeException("异步通知中错误的平台");
    }
}
