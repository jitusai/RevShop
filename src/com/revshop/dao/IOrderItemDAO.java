package com.revshop.dao;

import com.revshop.model.OrderItem;

public interface IOrderItemDAO {
    boolean insertOrderItem(OrderItem item);
}
