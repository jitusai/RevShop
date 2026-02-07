package Test;

import com.revshop.dao.IFavoriteDAO;
import com.revshop.model.Favorite;
import com.revshop.service.FavoriteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FavoriteServiceImplTest {

    private FavoriteServiceImpl favoriteService;
    private IFavoriteDAO mockDao;

    @BeforeEach
    void setUp() throws Exception {

        // Create Service Object
        favoriteService = new FavoriteServiceImpl();

        // Create Mock DAO
        mockDao = mock(IFavoriteDAO.class);

        // Inject Mock into private field using Reflection
        Field daoField =
                FavoriteServiceImpl.class.getDeclaredField("favoriteDao");

        daoField.setAccessible(true);
        daoField.set(favoriteService, mockDao);
    }

    // Test Add Favorite
    @Test
    void testAddFavorite() {

        Favorite favorite = new Favorite();
        favorite.setFavoriteId(1);
        favorite.setUserId(10);
        favorite.setProductId(101);

        when(mockDao.addFavorite(favorite)).thenReturn(true);

        boolean result = favoriteService.addFavorite(favorite);

        assertTrue(result);

        verify(mockDao, times(1)).addFavorite(favorite);
    }

    // Test View Favorites
    @Test
    void testViewFavorites() {

        Favorite f1 = new Favorite();
        f1.setFavoriteId(1);

        Favorite f2 = new Favorite();
        f2.setFavoriteId(2);

        List<Favorite> mockList = new ArrayList<>();
        mockList.add(f1);
        mockList.add(f2);

        when(mockDao.getFavoritesByUser(10)).thenReturn(mockList);

        List<Favorite> result = favoriteService.viewFavorites(10);

        assertEquals(2, result.size());

        verify(mockDao, times(1)).getFavoritesByUser(10);
    }

    // Test Remove Favorite
    @Test
    void testRemoveFavorite() {

        doNothing().when(mockDao).removeFavorite(1);

        favoriteService.removeFavorite(1);

        verify(mockDao, times(1)).removeFavorite(1);
    }
}
