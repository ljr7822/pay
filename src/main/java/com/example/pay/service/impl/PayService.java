package com.example.pay.service.impl;

import com.example.pay.dao.PayInfoMapper;
import com.example.pay.enums.PayPlatformEnum;
import com.example.pay.pojo.PayInfo;
import com.example.pay.service.IPayService;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
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

    @Autowired
    private PayInfoMapper payInfoMapper;

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
        PayInfo payInfo = new PayInfo(Long.parseLong(orderId),
                PayPlatformEnum.getByBestPayTypeEnum(bestPayTypeEnum).getCode(),
                OrderStatusEnum.NOTPAY.name(),
                amount);
        payInfoMapper.insertSelective(payInfo);

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
        // 比较严重的情况：发出告警：使用钉钉、短信告警
        PayInfo payInfo = payInfoMapper.selectByOrderNo(Long.parseLong(payResponse.getOrderId()));
        // 判断是否存在这条数据
        if (payInfo == null){
            throw new RuntimeException("通过oderNo查询到的数据是NUll");
        }
        // 判断是否已经支付
        if (!payInfo.getPlatformStatus().equals(OrderStatusEnum.SUCCESS.name())){
            // 不是已支付
            if (payInfo.getPayAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount())) != 0){
                // 金额不相等，发出告警：使用钉钉、短信告警
                throw new RuntimeException("异步通知中的金额和数据库不一致,orderNo="+payResponse.getOrderId());
            }
            // 3.修改订单支付状态
            payInfo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());
            // 交易流水号
            payInfo.setPlatformNumber(payResponse.getOutTradeNo());
            // 更新时间
            //payInfo.setUpdateTime(null);
            payInfoMapper.updateByPrimaryKeySelective(payInfo);
        }

        // TODO pay发送MQ消息，mall接收MQ消息

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

    @Override
    public PayInfo queryByOrderId(String orderId) {
        return payInfoMapper.selectByOrderNo(Long.parseLong(orderId));
    }
}
