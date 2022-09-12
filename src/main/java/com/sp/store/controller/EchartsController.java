package com.sp.store.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.sp.store.common.Result;
import com.sp.store.entity.Order;
import com.sp.store.entity.User;
import com.sp.store.service.IOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Resource
    private IOrderService orderService;
    @GetMapping("/example")
    public Result<?> get(){
        Map<String,Object> map=new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y", CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return Result.success(map);
    }

    @GetMapping("/order")
    public  Result<?> order(){
        List<Order> list=orderService.list();
        int q1=0;
        int q2=0;
        int q3=0;
        int q4=0;
        for (Order user: list) {
            Date createTime=user.getCreateTime();
            Quarter quarter= DateUtil.quarterEnum(createTime);
            switch (quarter){
                case Q1:q1++;break;
                case Q2:q2++;break;
                case Q3:q3++;break;
                case Q4:q4++;break;
                default:break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1,q2,q3,q4));
    }
}
