package com.example.pay.controller;

import com.example.pay.pojo.PayInfo;
import com.example.pay.service.impl.PayService;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 渲染二维码网页
 *
 * @Author: iwen大大怪
 * @DateTime: 2020/9/17 1:27
 */
@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private PayService payService;

    @Autowired
    private WxPayConfig wxPayConfig;

    /**
     * 将orderId、amount传递进来
     *
     * @return 二维码视图
     */
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("amount") BigDecimal amount,
                               @RequestParam("payType") BestPayTypeEnum bestPayTypeEnum) {

        // 接收接口实现类返回的数据
        PayResponse response = payService.create(orderId, amount, bestPayTypeEnum);


        // 支付方式不同，渲染就不同：微信使用二维码codeUrl；支付宝pc使用表单body
        Map<String, String> map = new HashMap<>();
//        map.put("codeUrl","weixin://wxpay/bizpayurl?pr=UZcPt4Z00");
        // 判断是哪种支付方式
        if (bestPayTypeEnum == BestPayTypeEnum.WXPAY_NATIVE){
            // 使用微信native
            map.put("codeUrl", response.getCodeUrl());
            map.put("orderId", orderId);
            map.put("returnUrl", wxPayConfig.getReturnUrl());
            // 返回数据给模板渲染
            return new ModelAndView("createForWxNative", map);
        }else if (bestPayTypeEnum == BestPayTypeEnum.ALIPAY_PC){
            // 使用支付宝pc支付
            map.put("body", response.getBody());
            // 返回数据给模板渲染
            return new ModelAndView("createForAlipayPc", map);
        }
        throw new RuntimeException("暂时不支持的支付类型");
    }

    /**
     * 微信异步回调通知api
     *
     * @param notifyData 通知内容
     */
    @PostMapping("/notify")
    @ResponseBody
    public String asyncNotify(@RequestBody String notifyData) {
        // 打印日志
        // log.info("notifyData={}",notifyData);
        // 调用
        return payService.asyncNotify(notifyData);
    }

    @GetMapping("/queryByOrderId")
    @ResponseBody
    public PayInfo queryByOrderId(@RequestParam String orderId){
        log.info("查询支付记录...");
        return payService.queryByOrderId(orderId);
    }
}
