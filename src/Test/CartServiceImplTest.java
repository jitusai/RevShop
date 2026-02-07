package Test;

import com.revshop.dao.CartDAOImpl;
import com.revshop.model.CartItem;
import com.revshop.service.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceImplTest {

    private CartServiceImpl cartService;
    private CartDAOImpl spyDao;

    @BeforeEach
    void setUp() throws Exception {

        cartService = new CartServiceImpl();

        // ✅ Use SPY instead of MOCK
        spyDao = spy(new CartDAOImpl());

        // Inject spy into private dao field
        Field daoField = CartServiceImpl.class.getDeclaredField("dao");
        daoField.setAccessible(true);
        daoField.set(cartService, spyDao);
    }

    @Test
    void testAddProductToCart() {

        cartService.addProductToCart(1, 101, 2);

        verify(spyDao, times(1))
                .addToCart(1, 101, 2);
    }

    @Test
    void testViewCartItems() {

        CartItem item = new CartItem();
        List<CartItem> mockList = List.of(item);

        doReturn(mockList).when(spyDao).getCartItems(1);

        List<CartItem> result = cartService.viewCartItems(1);

        assertEquals(1, result.size());
    }

    @Test
    void testUpdateCartItem() {

        cartService.updateCartItem(1, 101, 5);

        verify(spyDao).updateCartItem(1, 101, 5);
    }

    @Test
    void testRemoveProductFromCart() {

        cartService.removeProductFromCart(1, 101);

        verify(spyDao).removeCartItem(1, 101);
    }
}
