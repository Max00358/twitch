package com.laioffer.twitch;

import com.laioffer.twitch.db.FavoriteRecordRepository;
import com.laioffer.twitch.db.ItemRepository;
import com.laioffer.twitch.db.entity.ItemEntity;
import com.laioffer.twitch.db.entity.UserEntity;
import com.laioffer.twitch.external.model.ItemType;
import com.laioffer.twitch.favorite.FavoriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class FavoriteServiceTests {
    @Mock private ItemRepository itemRepository;
    @Mock private FavoriteRecordRepository favoriteRecordRepository;
    private FavoriteService favoriteService;

    @BeforeEach
    public void setUp() {
        favoriteService = new FavoriteService(itemRepository, favoriteRecordRepository);
    }

    // common naming convention for unit tests following "Given-When-Then" pattern
        // Given: initial condition (whenItemNotExist)
        // When: the actions being tested
        // Then: expected outcome or behavior
    @Test
    public void whenItemNotExist_setFavoriteItem_shouldSaveItem() {
        UserEntity user = new UserEntity(1L, "user", "foo", "bar", "123456");
        ItemEntity item = new ItemEntity(null, "twitchId", "title", "url", "thumb", "broadcaster", "gameId", ItemType.VIDEO);
        ItemEntity persisted = new ItemEntity(1L, "twitchId", "title", "url", "thumb", "broadcaster", "gameId", ItemType.VIDEO);

        // Mock the desired behavior when calling methods
        Mockito.when(itemRepository.findByTwitchId("twitchId")).thenReturn(null);
        Mockito.when(itemRepository.save(item)).thenReturn(persisted);

        // Test execution & verify result
        favoriteService.setFavoriteItem(user, item);
        Mockito.verify(itemRepository).save(item);
    }

    @Test
    public void whenItemExist_setFavoriteItem_shouldNotSaveItem() {
        UserEntity user = new UserEntity(1L, "user", "foo", "bar", "123456");
        ItemEntity item = new ItemEntity(null, "twitchId", "title", "url", "thumb", "broadcaster", "gameId", ItemType.VIDEO);
        ItemEntity persisted = new ItemEntity(1L, "twitchId", "title", "url", "thumb", "broadcaster", "gameId", ItemType.VIDEO);

        Mockito.when(itemRepository.findByTwitchId("twitchId")).thenReturn(persisted);

        favoriteService.setFavoriteItem(user, item);
        Mockito.verify(itemRepository, Mockito.never()).save(item);
    }

    @Test
    public void whenItemNotExist_unsetFavoriteItem_shouldNotDeleteFavoriteRecord() {
        UserEntity user = new UserEntity(1L, "user", "foo", "bar", "123456");
        Mockito.when(itemRepository.findByTwitchId("twitchId")).thenReturn(null);

        favoriteService.unsetFavoriteItem(user, "twitchId");
        Mockito.verifyNoInteractions(favoriteRecordRepository);
    }

    // Note that here we didn't put persisted item into fav record
    // it's because it's already done in setUp() method
    @Test
    public void whenItemExist_unsetFavoriteItem_shouldDeleteFavoriteRecord() {
        UserEntity user = new UserEntity(1L, "user", "foo", "bar", "123456");
        ItemEntity persisted = new ItemEntity(1L, "twitchId", "title", "url", "thumb", "broadcaster", "gameId", ItemType.VIDEO);
        Mockito.when(itemRepository.findByTwitchId("twitchId")).thenReturn(persisted);

        favoriteService.unsetFavoriteItem(user, "twitchId");
        Mockito.verify(favoriteRecordRepository).delete(1L, 1L);
    }
}
