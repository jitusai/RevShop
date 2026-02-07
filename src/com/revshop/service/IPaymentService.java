package com.revshop.service;

import com.revshop.model.Payment;
import java.util.List;

public interface IPaymentService {

    boolean processPayment(Payment payment);

    Payment viewPayment(int paymentId);

    List<Payment> viewAllPayments();

    boolean updatePaymentStatus(int paymentId, String status);

    boolean removePayment(int paymentId);
}
