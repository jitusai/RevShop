package com.revshop.dao;

import com.revshop.model.Order;

public interface IOrderDAO {

    boolean placeOrder(Order order);

    int insertOrder(Order order);
}
