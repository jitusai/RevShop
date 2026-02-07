package com.revshop.service;

import com.revshop.model.Order;

public interface IOrderService {
    boolean placeOrder(Order order);
}
