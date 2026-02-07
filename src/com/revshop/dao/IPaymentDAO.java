package com.revshop.dao;

import com.revshop.model.Payment;
import java.util.List;

public interface IPaymentDAO {

    boolean savePayment(Payment payment);

    Payment getPaymentById(int paymentId);

    List<Payment> getAllPayments();

    boolean updatePaymentStatus(int paymentId, String status);

    boolean deletePayment(int paymentId);
}
