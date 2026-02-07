package com.revshop.service;

import com.revshop.dao.IOrderItemDAO;
import com.revshop.dao.OrderItemDAOImpl;
import com.revshop.model.OrderItem;
import com.revshop.util.LoggerUtil;

public class OrderItemServiceImpl implements IOrderItemService {

    private final IOrderItemDAO dao;

    public OrderItemServiceImpl() {
        dao = new OrderItemDAOImpl();
    }

    @Override
    public boolean addOrderItem(OrderItem item) {
        LoggerUtil.logInfo("Adding order item for product ID: " + item.getProductId() +
                ", quantity: " + item.getQuantity() +
                ", unit price: " + item.getUnitPrice());
        try {
            double subtotal = item.getUnitPrice() * item.getQuantity();
            item.setSubtotal(subtotal);
            boolean success = dao.insertOrderItem(item);
            if (success) {
                LoggerUtil.logInfo("Order item added successfully: product ID " + item.getProductId() +
                        ", subtotal: " + subtotal);
            } else {
                LoggerUtil.logWarning("Failed to add order item for product ID: " + item.getProductId());
            }
            return success;
        } catch (Exception e) {
            LoggerUtil.logError("Error adding order item for product ID: " + item.getProductId(), e);
            return false;
        }
    }
}
