package com.revshop.service;

import com.revshop.dao.*;
import com.revshop.model.CartItem;
import com.revshop.model.Order;
import com.revshop.model.OrderItem;
import com.revshop.model.Product;
import com.revshop.util.LoggerUtil;

import java.util.List;

public class OrderServiceImpl implements IOrderService {

    private final IOrderDAO orderDao = new OrderDAOImpl();
    private final IOrderItemDAO orderItemDao = new OrderItemDAOImpl();
    private final ICartDAO cartDao = new CartDAOImpl();
    private final IProductDAO productDao = new ProductDAOImpl();

    @Override
    public boolean placeOrder(Order order) {
        LoggerUtil.logInfo("Placing order for user ID: " + order.getUserId());

        try {
            //  Fetch cart items
            List<CartItem> cartItems = cartDao.getCartItems(order.getUserId());
            if (cartItems == null || cartItems.isEmpty()) {
                LoggerUtil.logWarning("Order failed: Cart is empty for user " + order.getUserId());
                return false;
            }

            // Validate stock + calculate total
            double totalAmount = 0;
            for (CartItem item : cartItems) {
                Product product = productDao.getProductById(item.getProductId());

                if (product == null) {
                    LoggerUtil.logWarning("Product not found: " + item.getProductId());
                    return false;
                }

                if (product.getStockQuantity() < item.getQuantity()) {
                    LoggerUtil.logWarning(
                            "Insufficient stock for product ID: " + product.getProductId() +
                                    ", Name: " + product.getName()
                    );
                    return false;
                }

                totalAmount += product.getPrice() * item.getQuantity();
            }

            order.setTotalAmount(totalAmount);
            LoggerUtil.logInfo("Total amount calculated for order: " + totalAmount);

            //  Create order
            int orderId = orderDao.insertOrder(order);
            if (orderId == -1) {
                LoggerUtil.logError("Failed to create order record for user " + order.getUserId(), null);
                return false;
            }
            LoggerUtil.logInfo("Order created successfully. Order ID: " + orderId);

            // Insert order items + reduce stock
            for (CartItem item : cartItems) {
                Product product = productDao.getProductById(item.getProductId());

                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(orderId);
                orderItem.setProductId(item.getProductId());
                orderItem.setSellerId(product.getSellerId());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setSubtotal(product.getPrice() * item.getQuantity());

                boolean itemSaved = orderItemDao.insertOrderItem(orderItem);
                if (!itemSaved) {
                    LoggerUtil.logError("Failed to save order item for order ID " + orderId +
                            ", product ID " + item.getProductId(), null);
                    return false;
                }

                // reduce stock
                boolean stockUpdated = productDao.updateStock(item.getProductId(), item.getQuantity());
                if (stockUpdated) {
                    LoggerUtil.logInfo("Stock updated for product ID: " + item.getProductId() +
                            ", quantity reduced: " + item.getQuantity());
                } else {
                    LoggerUtil.logWarning("Failed to update stock for product ID: " + item.getProductId());
                }
            }

            //  Clear cart
            cartDao.clearCart(order.getUserId());
            LoggerUtil.logInfo("Cart cleared for user ID: " + order.getUserId());

            LoggerUtil.logInfo("Order placed successfully. Order ID: " + orderId);
            return true;

        } catch (Exception e) {
            LoggerUtil.logError("Exception occurred while placing order for user " + order.getUserId(), e);
            return false;
        }
    }
}
