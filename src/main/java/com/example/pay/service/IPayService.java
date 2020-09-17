package com.example.pay.service;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;

import java.math.BigDecimal;

/**
 * 支付接口
 *
 * @Author: iwen大大怪
 * @DateTime: 2020/9/16 22:16
 */
public interface IPayService {

    /**
     * 创建或发起支付方法
     *
     * @param orderId
     * @param amount
     * @return
     */
    PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum);

    /**
     * 异步通知处理
     *
     * @param notifyData
     * @return 返回结果给微信
     */
    String asyncNotify(String notifyData);

}
