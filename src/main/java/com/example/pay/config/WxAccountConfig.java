package com.example.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 将yml配置文件里面的东西放到Java中
 *
 * @Author: iwen大大怪
 * @DateTime: 2020/9/17 15:37
 */
@Component
@ConfigurationProperties(prefix = "wx")
@Data
public class WxAccountConfig {
    private String appId;

    private String mchId;

    private String mchKey;

    private String notifyUrl;

    private String returnUrl;
}
