package com.sp.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.store.entity.Order;
import com.sp.store.mapper.OrderMapper;
import com.sp.store.service.IOrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
}
