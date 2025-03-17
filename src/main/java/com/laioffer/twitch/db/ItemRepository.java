package com.laioffer.twitch.db;

import com.laioffer.twitch.db.entity.ItemEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface ItemRepository extends ListCrudRepository<ItemEntity, Long> {
    // Query Method Naming:
    // Spring Data JDBC automatically generate SQL query by analyzing the method name
    // Spring parses method name and translates it into query like:
        // SELECT * FROM item_entity WHERE twitch_id =: twitchId;
        // For complicated queries, use @Query("insert SQL query command")
    ItemEntity findByTwitchId(String twitchId);
}
