package Test;

import com.revshop.dao.ISellerDAO;
import com.revshop.model.Seller;
import com.revshop.service.SellerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SellerServiceImplTest {

    private SellerServiceImpl sellerService;
    private ISellerDAO mockSellerDAO;

    @BeforeEach
    void setUp() throws Exception {

        // Create Service Object
        sellerService = new SellerServiceImpl();

        // Create Mock DAO
        mockSellerDAO = mock(ISellerDAO.class);

        // Inject Mock into Service using Reflection
        Field field = SellerServiceImpl.class.getDeclaredField("sellerDAO");
        field.setAccessible(true);
        field.set(sellerService, mockSellerDAO);
    }

    // TEST 1: Register Null Seller (Should Not Call DAO)
    @Test
    void testRegisterSeller_NullSeller() {

        sellerService.registerSeller(null);

        verify(mockSellerDAO, never()).addSeller(any());
    }

    // TEST 2: Invalid UserId (Should Not Call DAO)
    @Test
    void testRegisterSeller_InvalidUserId() {

        Seller seller = new Seller();
        seller.setUserId(0);
        seller.setBusinessName("Amazon Store");

        sellerService.registerSeller(seller);

        verify(mockSellerDAO, never()).addSeller(any());
    }

    // ✅ TEST 3: Blank Business Name (Should Not Call DAO)
    @Test
    void testRegisterSeller_BlankBusinessName() {

        Seller seller = new Seller();
        seller.setUserId(10);
        seller.setBusinessName("");

        sellerService.registerSeller(seller);

        verify(mockSellerDAO, never()).addSeller(any());
    }

    // TEST 4: Valid Seller Registration (DAO Should Be Called Once)
    @Test
    void testRegisterSeller_ValidSeller() {

        Seller seller = new Seller();
        seller.setUserId(101);
        seller.setBusinessName("Flipkart Store");

        sellerService.registerSeller(seller);

        verify(mockSellerDAO, times(1)).addSeller(seller);
    }

    // ✅ TEST 5: Get Seller By ID Success
    @Test
    void testGetSellerById_ReturnsSeller() {

        Seller seller = new Seller();
        seller.setSellerId(1);
        seller.setBusinessName("Seller One");

        when(mockSellerDAO.getSellerById(1)).thenReturn(seller);

        Seller result = sellerService.getSellerById(1);

        assertNotNull(result);
        assertEquals("Seller One", result.getBusinessName());

        verify(mockSellerDAO, times(1)).getSellerById(1);
    }}

// TEST 6: Get Seller By UserId Success
