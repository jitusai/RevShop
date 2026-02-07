package Test;

import com.revshop.dao.IProductDAO;
import com.revshop.model.Product;
import com.revshop.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private ProductServiceImpl productService;
    private IProductDAO mockDao;

    @BeforeEach
    void setUp() throws Exception {

        // Create Service Object
        productService = new ProductServiceImpl();

        // Create Mock DAO
        mockDao = mock(IProductDAO.class);

        // Inject Mock DAO using Reflection
        Field daoField =
                ProductServiceImpl.class.getDeclaredField("productDAO");

        daoField.setAccessible(true);
        daoField.set(productService, mockDao);
    }

    // ✅ TEST 1: Add Product Success
    @Test
    void testAddProduct_Success() {

        Product product = new Product();
        product.setProductId(1);
        product.setName("Mobile");
        product.setPrice(15000);

        when(mockDao.addProduct(product)).thenReturn(true);

        boolean result = productService.addProduct(product);

        assertTrue(result);
        verify(mockDao, times(1)).addProduct(product);
    }

    // ✅ TEST 2: Add Product Exception → Returns False
    @Test
    void testAddProduct_Exception_ReturnsFalse() {

        Product product = new Product();
        product.setName("Laptop");

        when(mockDao.addProduct(product))
                .thenThrow(new RuntimeException("DB Error"));

        boolean result = productService.addProduct(product);

        assertFalse(result);
        verify(mockDao, times(1)).addProduct(product);
    }

    // ✅ TEST 3: Get Product By Id
    @Test
    void testGetProductById() {

        Product product = new Product();
        product.setProductId(10);
        product.setName("TV");

        when(mockDao.getProductById(10)).thenReturn(product);

        Product result = productService.getProductById(10);

        assertNotNull(result);
        assertEquals("TV", result.getName());

        verify(mockDao, times(1)).getProductById(10);
    }

    // ✅ TEST 4: Update Product
    @Test
    void testUpdateProduct() {

        when(mockDao.updateProduct(any(Product.class))).thenReturn(true);

        boolean result =
                productService.updateProduct(5, "Shoes", 2000);

        assertTrue(result);

        verify(mockDao, times(1))
                .updateProduct(any(Product.class));
    }

    // ✅ TEST 5: Delete Product
    @Test
    void testDeleteProduct() {

        when(mockDao.deleteProduct(5)).thenReturn(true);

        boolean result = productService.deleteProduct(5);

        assertTrue(result);

        verify(mockDao, times(1)).deleteProduct(5);
    }

    // ✅ TEST 6: Product Exists
    @Test
    void testProductExists() {

        when(mockDao.productExists(100)).thenReturn(true);

        boolean result = productService.productExists(100);

        assertTrue(result);

        verify(mockDao, times(1)).productExists(100);
    }

    // ✅ TEST 7: Get All Products
    @Test
    void testGetAllProducts() {

        List<Product> mockList = new ArrayList<>();
        mockList.add(new Product());
        mockList.add(new Product());

        when(mockDao.getAllProducts()).thenReturn(mockList);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());

        verify(mockDao, times(1)).getAllProducts();
    }

    // ✅ TEST 8: Get Products By Seller
    @Test
    void testGetProductsBySeller() {

        List<Product> sellerProducts = new ArrayList<>();
        sellerProducts.add(new Product());

        when(mockDao.getProductsBySeller(50)).thenReturn(sellerProducts);

        List<Product> result =
                productService.getProductsBySeller(50);

        assertEquals(1, result.size());

        verify(mockDao, times(1))
                .getProductsBySeller(50);
    }

    // ✅ TEST 9: Get Products By Category
    @Test
    void testGetProductsByCategory() {

        List<Product> categoryProducts = new ArrayList<>();
        categoryProducts.add(new Product());
        categoryProducts.add(new Product());

        when(mockDao.getProductsByCategory(3)).thenReturn(categoryProducts);

        List<Product> result =
                productService.getProductsByCategory(3);

        assertEquals(2, result.size());

        verify(mockDao, times(1))
                .getProductsByCategory(3);
    }

    // ✅ TEST 10: Update Stock
    @Test
    void testUpdateStock() {

        when(mockDao.updateStock(10, 5)).thenReturn(true);

        boolean result =
                productService.updateStock(10, 5);

        assertTrue(result);

        verify(mockDao, times(1))
                .updateStock(10, 5);
    }
}
