package com.laioffer.twitch.item;

import com.laioffer.twitch.model.TypeGroupedItemList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
    private final ItemService itemService;

    // instead of creating a new instance of twitchApiService, twitchController and itemService
    // I can just pass in itemService obj instead of writing out all the unnecessary details
    // this is called Dependency Injection
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/search")
    public TypeGroupedItemList search(@RequestParam("game_id") String gameId) {
        return itemService.getItems(gameId);
    }
}
