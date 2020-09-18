package com.example.pay.enums;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import lombok.Getter;

/**
 * 支付类型枚举类
 * 支付平台:1-支付宝,2-微信
 *
 * @Author: iwen大大怪
 * @DateTime: 2020/9/17 13:22
 */
@Getter
public enum  PayPlatformEnum {
    /**
     * 支付宝
     */
    ALIPAY(1),
    /**
     * 微信
     */
    WX(2),
    ;

    Integer code;

    PayPlatformEnum(Integer code) {
        this.code = code;
    }

    /**
     * 判断数据库中取出的数据的支付类型的方法
     * @param bestPayTypeEnum 传入判断的代码
     * @return 支付类型
     */
    public static PayPlatformEnum getByBestPayTypeEnum(BestPayTypeEnum bestPayTypeEnum){
//        if (bestPayTypeEnum.getPlatform().name() .equals(PayPlatformEnum.ALIPAY.name())){
//            // 说明使用支付宝支付
//            return PayPlatformEnum.ALIPAY;
//        }else if (bestPayTypeEnum.getPlatform().name() .equals(PayPlatformEnum.WX.name())){
//            // 说明使用微信支付
//            return PayPlatformEnum.WX;
//        }

        for (PayPlatformEnum payPlatformEnum : PayPlatformEnum.values()){
            if (bestPayTypeEnum.getPlatform().name() .equals(payPlatformEnum.name())){
                return payPlatformEnum;
            }
        }
        throw new RuntimeException("错误的支付平台:"+bestPayTypeEnum.name());

    }
}
