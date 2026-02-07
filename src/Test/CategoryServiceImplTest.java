package Test;

import com.revshop.dao.ICategoryDAO;
import com.revshop.model.Category;
import com.revshop.service.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    private CategoryServiceImpl categoryService;
    private ICategoryDAO mockDao;

    @BeforeEach
    void setUp() throws Exception {

        // Create Service Object
        categoryService = new CategoryServiceImpl();

        // Create Mock DAO
        mockDao = mock(ICategoryDAO.class);

        // Inject Mock into private field using Reflection
        Field daoField =
                CategoryServiceImpl.class.getDeclaredField("categoryDao");

        daoField.setAccessible(true);
        daoField.set(categoryService, mockDao);
    }

    // ✅ Test Add Category
    @Test
    void testAddCategory() {

        // Dummy Category Object
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("Electronics");

        // Mock DAO Response
        when(mockDao.addCategory(category)).thenReturn(true);

        // Call Service Method
        boolean result = categoryService.addCategory(category);

        // Assert Result
        assertTrue(result);

        // Verify DAO Method Called
        verify(mockDao, times(1)).addCategory(category);
    }

    // ✅ Test Get All Categories
    @Test
    void testGetAllCategories() {

        // Dummy Category List
        Category c1 = new Category();
        c1.setCategoryId(1);
        c1.setCategoryName("Books");

        Category c2 = new Category();
        c2.setCategoryId(2);
        c2.setCategoryName("Fashion");

        List<Category> mockList = new ArrayList<>();
        mockList.add(c1);
        mockList.add(c2);

        // Mock DAO Response
        when(mockDao.getAllCategories()).thenReturn(mockList);

        // Call Service Method
        List<Category> result = categoryService.getAllCategories();

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verify DAO Called
        verify(mockDao, times(1)).getAllCategories();
    }
}
