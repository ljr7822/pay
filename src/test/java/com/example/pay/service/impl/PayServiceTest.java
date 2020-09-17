package com.example.pay.service.impl;

import com.example.pay.PayApplicationTests;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * 测试类
 *
 * @Author: iwen大大怪
 * @DateTime: 2020/9/16 22:50
 */
public class PayServiceTest extends PayApplicationTests {

    @Autowired
    private PayService payService;

    @Test
    public void create() {
        payService.create("123456789123489", BigDecimal.valueOf(0.01), BestPayTypeEnum.WXPAY_NATIVE);
    }
}