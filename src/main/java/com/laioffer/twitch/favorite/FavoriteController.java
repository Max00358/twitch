package com.laioffer.twitch.favorite;

import com.laioffer.twitch.db.entity.UserEntity;
import com.laioffer.twitch.external.model.FavoriteRequestBody;
import com.laioffer.twitch.model.TypeGroupedItemList;
import com.laioffer.twitch.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    private final UserService userService;
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService, UserService userService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
    }

    @GetMapping
    public TypeGroupedItemList getFavoriteItems(@AuthenticationPrincipal UserEntity user) {
        UserEntity userEntity = userService.findByUsername(user.username());
        return favoriteService.getGroupedFavoriteItemList(userEntity);
    }

    @PostMapping
    public void setFavoriteItem(@AuthenticationPrincipal UserEntity user, @RequestBody FavoriteRequestBody body){
        UserEntity userEntity = userService.findByUsername(user.username());
        try {
            favoriteService.setFavoriteItem(userEntity, body.favorite());
        } catch (DuplicateFavoriteException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping
    public void deleteFavoriteItem(@AuthenticationPrincipal UserEntity user, @RequestBody FavoriteRequestBody body) {
        UserEntity userEntity = userService.findByUsername(user.username());
        favoriteService.unsetFavoriteItem(userEntity, body.favorite().twitchId());
    }
}