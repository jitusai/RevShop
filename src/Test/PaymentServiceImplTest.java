package Test;

import com.revshop.dao.IPaymentDAO;
import com.revshop.model.Payment;
import com.revshop.service.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    private PaymentServiceImpl paymentService;
    private IPaymentDAO mockDao;

    @BeforeEach
    void setUp() throws Exception {

        // Create Service Object
        paymentService = new PaymentServiceImpl();

        // Create Mock DAO
        mockDao = mock(IPaymentDAO.class);

        // Inject Mock DAO using Reflection
        Field daoField =
                PaymentServiceImpl.class.getDeclaredField("paymentDAO");

        daoField.setAccessible(true);
        daoField.set(paymentService, mockDao);
    }

    // ✅ TEST 1: Process Payment Success
    @Test
    void testProcessPayment_Success() {

        Payment payment = new Payment();
        payment.setOrderId(101);
        payment.setAmount(5000);
        payment.setPaymentStatus("SUCCESS");

        when(mockDao.savePayment(payment)).thenReturn(true);

        boolean result = paymentService.processPayment(payment);

        assertTrue(result);

        verify(mockDao, times(1)).savePayment(payment);
    }

    // ✅ TEST 2: OrderId Invalid → Exception
    @Test
    void testProcessPayment_InvalidOrderId_ThrowsException() {

        Payment payment = new Payment();
        payment.setOrderId(0);
        payment.setAmount(500);

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                paymentService.processPayment(payment)
        );

        assertEquals("Order Id must be greater than 0", ex.getMessage());
    }

    // ✅ TEST 3: Amount Invalid → Exception
    @Test
    void testProcessPayment_InvalidAmount_ThrowsException() {

        Payment payment = new Payment();
        payment.setOrderId(101);
        payment.setAmount(0);

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                paymentService.processPayment(payment)
        );

        assertEquals("Amount must be greater than 0", ex.getMessage());
    }

    // ✅ TEST 4: Payment Status Null → Default SUCCESS
    @Test
    void testProcessPayment_StatusNull_DefaultSuccess() {

        Payment payment = new Payment();
        payment.setOrderId(200);
        payment.setAmount(1500);
        payment.setPaymentStatus(null);

        when(mockDao.savePayment(payment)).thenReturn(true);

        boolean result = paymentService.processPayment(payment);

        assertTrue(result);

        // Default should be set
        assertEquals("SUCCESS", payment.getPaymentStatus());

        verify(mockDao).savePayment(payment);
    }

    // ✅ TEST 5: View Payment By Id
    @Test
    void testViewPayment() {

        Payment payment = new Payment();
        payment.setPaymentId(1);

        when(mockDao.getPaymentById(1)).thenReturn(payment);

        Payment result = paymentService.viewPayment(1);

        assertNotNull(result);
        assertEquals(1, result.getPaymentId());

        verify(mockDao, times(1)).getPaymentById(1);
    }

    // ✅ TEST 6: View All Payments
    @Test
    void testViewAllPayments() {

        Payment p1 = new Payment();
        Payment p2 = new Payment();

        List<Payment> mockList = new ArrayList<>();
        mockList.add(p1);
        mockList.add(p2);

        when(mockDao.getAllPayments()).thenReturn(mockList);

        List<Payment> result = paymentService.viewAllPayments();

        assertEquals(2, result.size());

        verify(mockDao, times(1)).getAllPayments();
    }

    // ✅ TEST 7: Update Payment Status
    @Test
    void testUpdatePaymentStatus() {

        when(mockDao.updatePaymentStatus(1, "FAILED")).thenReturn(true);

        boolean result = paymentService.updatePaymentStatus(1, "FAILED");

        assertTrue(result);

        verify(mockDao, times(1))
                .updatePaymentStatus(1, "FAILED");
    }

    // ✅ TEST 8: Remove Payment
    @Test
    void testRemovePayment() {

        when(mockDao.deletePayment(1)).thenReturn(true);

        boolean result = paymentService.removePayment(1);

        assertTrue(result);

        verify(mockDao, times(1)).deletePayment(1);
    }
}
