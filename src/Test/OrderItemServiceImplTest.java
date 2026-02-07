package Test;

import com.revshop.dao.IOrderItemDAO;
import com.revshop.model.OrderItem;
import com.revshop.service.OrderItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderItemServiceImplTest {

    private OrderItemServiceImpl orderItemService;
    private IOrderItemDAO mockDao;

    @BeforeEach
    void setUp() throws Exception {

        // Create Service Object
        orderItemService = new OrderItemServiceImpl();

        // Create Mock DAO
        mockDao = mock(IOrderItemDAO.class);

        // Inject Mock DAO using Reflection
        Field daoField =
                OrderItemServiceImpl.class.getDeclaredField("dao");

        daoField.setAccessible(true);
        daoField.set(orderItemService, mockDao);
    }

    // Test Add Order Item (Subtotal Calculation + DAO Call)
    @Test
    void testAddOrderItem() {

        // Create Dummy OrderItem
        OrderItem item = new OrderItem();
        item.setUnitPrice(500.0);
        item.setQuantity(2);

        // Expected subtotal = 500 * 2 = 1000
        double expectedSubtotal = 1000.0;

        // Mock DAO Response
        when(mockDao.insertOrderItem(item)).thenReturn(true);

        // Call Service Method
        boolean result = orderItemService.addOrderItem(item);

        //  Check subtotal calculated correctly
        assertEquals(expectedSubtotal, item.getSubtotal());

        // Check service returns true
        assertTrue(result);

        // Verify DAO Method Called Once
        verify(mockDao, times(1)).insertOrderItem(item);
    }
}
