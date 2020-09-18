package com.example.pay.service;

import com.example.pay.pojo.PayInfo;
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
     * @param orderId 订单号
     * @param amount 支付金额
     * @return 二维码链接
     */
    PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum);

    /**
     * 异步通知处理
     *
     * @param notifyData 异步请求数据
     * @return 返回结果给微信
     */
    String asyncNotify(String notifyData);

    /**
     * 查询支付记录（通过订单号）
     *
     * @param orderId 订单号
     * @return 支付记录
     */
    PayInfo queryByOrderId(String orderId);
}
