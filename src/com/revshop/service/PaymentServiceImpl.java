package com.revshop.service;

import com.revshop.dao.IPaymentDAO;
import com.revshop.dao.PaymentDAOImpl;
import com.revshop.model.Payment;
import com.revshop.util.LoggerUtil;

import java.util.List;

public class PaymentServiceImpl implements IPaymentService {

    private IPaymentDAO paymentDAO;

    public PaymentServiceImpl() {
        try {
            paymentDAO = new PaymentDAOImpl();
            LoggerUtil.logInfo("PaymentDAO initialized successfully");
        } catch (Exception e) {
            LoggerUtil.logError("Failed to initialize PaymentDAO", e);
            throw new RuntimeException("Failed to initialize PaymentDAO", e);
        }
    }

    @Override
    public boolean processPayment(Payment payment) {
        try {
            if (payment.getOrderId() <= 0) {
                LoggerUtil.logWarning("Invalid orderId: " + payment.getOrderId());
                throw new IllegalArgumentException("Order Id must be greater than 0");
            }

            if (payment.getAmount() <= 0) {
                LoggerUtil.logWarning("Invalid payment amount: " + payment.getAmount());
                throw new IllegalArgumentException("Amount must be greater than 0");
            }

            if (payment.getPaymentStatus() == null) {
                payment.setPaymentStatus("SUCCESS");
                LoggerUtil.logInfo("Payment status defaulted to SUCCESS for order: " + payment.getOrderId());
            }

            boolean success = paymentDAO.savePayment(payment);
            if (success) {
                LoggerUtil.logInfo("Payment processed successfully. Order ID: " + payment.getOrderId()
                        + ", Amount: " + payment.getAmount());
            } else {
                LoggerUtil.logWarning("Payment processing failed for order: " + payment.getOrderId());
            }
            return success;

        } catch (Exception e) {
            LoggerUtil.logError("Error processing payment for order: " + payment.getOrderId(), e);
            return false;
        }
    }

    @Override
    public Payment viewPayment(int paymentId) {
        try {
            Payment payment = paymentDAO.getPaymentById(paymentId);
            if (payment != null) {
                LoggerUtil.logInfo("Viewed payment details for Payment ID: " + paymentId);
            } else {
                LoggerUtil.logWarning("Payment not found with ID: " + paymentId);
            }
            return payment;
        } catch (Exception e) {
            LoggerUtil.logError("Error viewing payment ID: " + paymentId, e);
            return null;
        }
    }

    @Override
    public List<Payment> viewAllPayments() {
        try {
            List<Payment> payments = paymentDAO.getAllPayments();
            LoggerUtil.logInfo("Fetched all payments. Total records: " + payments.size());
            return payments;
        } catch (Exception e) {
            LoggerUtil.logError("Error fetching all payments", e);
            return null;
        }
    }

    @Override
    public boolean updatePaymentStatus(int paymentId, String status) {
        try {
            boolean updated = paymentDAO.updatePaymentStatus(paymentId, status);
            if (updated) {
                LoggerUtil.logInfo("Payment status updated. Payment ID: " + paymentId + ", Status: " + status);
            } else {
                LoggerUtil.logWarning("Failed to update payment status. Payment ID: " + paymentId);
            }
            return updated;
        } catch (Exception e) {
            LoggerUtil.logError("Error updating payment status for Payment ID: " + paymentId, e);
            return false;
        }
    }

    @Override
    public boolean removePayment(int paymentId) {
        try {
            boolean deleted = paymentDAO.deletePayment(paymentId);
            if (deleted) {
                LoggerUtil.logInfo("Payment removed successfully. Payment ID: " + paymentId);
            } else {
                LoggerUtil.logWarning("Failed to remove payment. Payment ID: " + paymentId);
            }
            return deleted;
        } catch (Exception e) {
            LoggerUtil.logError("Error removing payment ID: " + paymentId, e);
            return false;
        }
    }
}
