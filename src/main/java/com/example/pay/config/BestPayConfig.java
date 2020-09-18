package com.example.pay.config;

import com.lly835.bestpay.config.AliPayConfig;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 支付配置类
 *
 * @Author: iwen大大怪
 * @DateTime: 2020/9/17 9:04
 */
@Component
public class BestPayConfig {

    @Autowired
    private WxAccountConfig wxAccountConfig;

    /**
     * @return “@Bean”希望项目启动的时候执行
     */
    @Bean
    public BestPayService bestPayService(WxPayConfig wxPayConfig) {
        /**
         * 支付宝配置类
         */
        AliPayConfig aliPayConfig = new AliPayConfig();
        aliPayConfig.setAppId("2018062960540016");
        aliPayConfig.setPrivateKey("MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCC/tk1oqymli5HpP9MxOkNofKkON1a6EqKg/S0++mZoKLInLasLkNuz0F8nSg/nBZGMv8XcIOXeOsK1ZT9tibxlgzun22lnBpHLueCAZlqITx33XO8SwiVULar2NIVFJwuUdvHPf/2Wj361cTd+zw+uJTPy3vIBljeOHDajzqBg9m5JrCIOgUze+4MZBT5/74gwwwpMDhRHRfR+4FTFmxqqzbTBGaFv4gCLSRB/3VpuruAP5AKCTR2ATHXgFO9veCemNwx5sI3F9Tw43+16xzAZh2T/6Xb3d31H9k31h9Iai7t2s00qY6eKsFpdi4ySK5qB2G9lbFvbpBv+3gz/5UFAgMBAAECggEANWn7Yc5yPf8w4c/atg/4w7FMgkAZqm7brj1+M6ogp58DoW+dYPoATxe9qTVf6wgXYQp8T692SPZqSOGsPgPfP1Ui8s4ZZJURdgMfLTi/uLHSuUGYZqfl2RSvV8UybJwZzx1b05NpYqeLbFNsUeOWvjB/pXdggsBrzjkPc19ByPtERzjiEFzvxGdYPDdvgNUmvbQj647X8umdyDAcNAbd8rkSQW87zBT12OQ/i/QR74BBg4VBJqTg9APk9mdVNufvxMwi9+lyGsGRFdnjmPCLHJvnT3Nh9KSHWuLrOeUjkGVEozd40iz/8LVwr+BZNeL6EjtZ3ztfphnIl92lMFNhQQKBgQDlm0t5N0REM0al/L0BygkwLsHHvAmjYhzjDJeooLgH0NyYYP/ruqqF6oPxDDaw17pciEypyOdoQ1JFaw9Uq41PfZlqL74PehSD3KWe7pPzlWIukaDq1aPit/pox92+ZO0lqwHQa80Tf1PO55xAVJQPltrhScnAmZiVIG1TD9z+sQKBgQCSDbGW2lVx5b9s1R0l8SRvoHQc83iIIyqx/+UJEEwxEMwSJ4ygAfUPcrJOYs2gOB9Ny08xqtNEQKlghwgwFqIec705AiU67Bw3TjWHnxgzvhzLPCrty/yJbWMmpXDjMm/VvwASkBL6pijjh/GUrRsJvh0McgWkDIg8OgIRu5rYlQKBgQChJ+nGb7sTn2XT9Vv4GIVwczEB7wJY0fFyj5EXA4+HtNpQfazDGOa9TchD9Q2h/BjK/8PHW8LIVJA3NxiwliR+CasXc+ET3dzuXH1G2y+vRUd/ZimrCj6YUAeLadVC4HXu/WMtlayAJdt+GuR55qNxebGxdOgNrgEBkpwJM9YqUQKBgCvFcNit/HeTDEiYohCx8WKG6uWWTiQ2reAEueZ6fOsjhpVWRv3ZOFF15Vw6njeLOk59RPG0qXZGDr0AGwMWdWW8+BOyweejxV0J0l8f3gf7zPNXx+HWhYvGPbXiVS+x+PRNNr9ZcGawD4cJQex16KmF0XzeWzRsERRDqkUYPXL1AoGBAJVzao5Irdt0wj/R8NG3uVeEMbESNLKNLZ7M9hPaZn2xacoPHXXLKg2m546RYNOngYLB5l9dbpake5BoXtuGylmtD1tZvGSxLmznfrxQpZr0vuR2iCIgsVGdPhJlIRbKyeocofsRdaXgD413O4MCi/WmJ4E1ydyOqv+2cCQQAJBB");
        aliPayConfig.setAliPayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtojdtkETo4OEsQLeyyPwtWK9ZqYJANq6jjXC74vk9n/r88yW577y7VdxcK9X/F/wvR7D8of7lndYdhg6xZro0eO2skPZTU+A549J7tfzahVbIBAS+x1WPFJwPtVrfBBvkwHL8PT+YnMcxKyBxOa6wo8fzJs1NgU1+qnDCpwUFyv59GUfdzBvTPL1fY3ZzvRHFHbapevVltbO/jNV0thb8dafmcJXl8lnjQy3XlH3eTH28tlVfqickacfRl/WSD8WN3dGgF7dTDKYfSR7YB7jsHe6VzoHM3UnD9/yQbi/Z3ZrL7yOxEjq4tfrKlZIW7ZCoUpOU4QdPIRhLeC6nWyGrQIDAQAB");
        aliPayConfig.setNotifyUrl("http://iwen-mall.natapp1.cc/pay/notify");
        aliPayConfig.setReturnUrl("http://127.0.0.1");

        // 支付类, 所有方法都在这个类里
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);
        bestPayService.setAliPayConfig(aliPayConfig);

        // 返回配置
        return bestPayService;
    }

    /**
     * 微信支付相关的配置
     *
     * @return 配置列表
     */
    @Bean
    public WxPayConfig wxPayConfig() {
        /**
         * 微信支付配置
         */
        WxPayConfig wxPayConfig = new WxPayConfig();
        // appid：一定要是公众号的
        wxPayConfig.setAppId(wxAccountConfig.getAppId());
        // 商户号
        wxPayConfig.setMchId(wxAccountConfig.getMchId());
        // 商户密钥
        wxPayConfig.setMchKey(wxAccountConfig.getMchKey());
        // 回调的url：这里使用了内网穿透
        wxPayConfig.setNotifyUrl(wxAccountConfig.getNotifyUrl());
        wxPayConfig.setReturnUrl(wxAccountConfig.getReturnUrl());
        return wxPayConfig;
    }
}
