package com.laioffer.twitch.favorite;

import com.laioffer.twitch.db.FavoriteRecordRepository;
import com.laioffer.twitch.db.ItemRepository;
import com.laioffer.twitch.db.entity.FavoriteRecordEntity;
import com.laioffer.twitch.db.entity.ItemEntity;
import com.laioffer.twitch.db.entity.UserEntity;
import com.laioffer.twitch.model.TypeGroupedItemList;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class FavoriteService {
    private final ItemRepository itemRepository;
    private final FavoriteRecordRepository favoriteRecordRepository;

    public FavoriteService(ItemRepository itemRepository, FavoriteRecordRepository favoriteRecordRepository) {
        this.itemRepository = itemRepository;
        this.favoriteRecordRepository = favoriteRecordRepository;
    }

    // Transaction ensures that a series of database operations (e.g., reads, writes, updates, deletes) are executed as a single unit of work.
    // If any operation within the transaction fails, the entire transaction is rolled back, ensuring data consistency.
    @CacheEvict(cacheNames = "recommend_items", key = "#user")
    @Transactional
    public void setFavoriteItem(UserEntity user, ItemEntity item) {
        ItemEntity persistedItem = itemRepository.findByTwitchId(item.twitchId());
        if (persistedItem == null) {
            persistedItem = itemRepository.save(item);
        }
        if (favoriteRecordRepository.existsByUserIdAndItemId(user.id(), persistedItem.id())) {
            throw new DuplicateFavoriteException();
        }
        FavoriteRecordEntity favoriteRecord = new FavoriteRecordEntity(null, user.id(), persistedItem.id(), Instant.now());
        favoriteRecordRepository.save(favoriteRecord);
    }

    // This is to solve the following problem
        // when you add favor item and do recommendation, results will be cached
        // and when you unset favorite item and do recommendation again,
        // the cached result won't be updated, cacheEvict forces an update
    @CacheEvict(cacheNames = "recommend_items", key = "#user")
    public void unsetFavoriteItem(UserEntity user, String twitchId){
        ItemEntity item = itemRepository.findByTwitchId(twitchId);
        if(item != null){
            favoriteRecordRepository.delete(user.id(), item.id());
        }
    }

    public List<ItemEntity> getFavoriteItems(UserEntity user) {
        List<Long> favoriteIds = favoriteRecordRepository.findFavoriteItemIdsByUserId(user.id());
        return itemRepository.findAllById(favoriteIds);
    }

    public TypeGroupedItemList getGroupedFavoriteItems(UserEntity user) {
        List<ItemEntity> items = getFavoriteItems(user);
        return new TypeGroupedItemList(items);
    }
}
