package com.laioffer.twitch.recommendation;

import com.laioffer.twitch.db.entity.ItemEntity;
import com.laioffer.twitch.db.entity.UserEntity;
import com.laioffer.twitch.external.TwitchService;
import com.laioffer.twitch.external.model.Clip;
import com.laioffer.twitch.external.model.Stream;
import com.laioffer.twitch.external.model.Video;
import com.laioffer.twitch.favorite.FavoriteService;
import org.springframework.stereotype.Service;
import com.laioffer.twitch.model.TypeGroupedItemList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class RecommendationService {
    private static final int MAX_GAME_SEED = 3;
    private static final int PER_PAGE_ITEM_SIZE = 20;

    private final TwitchService twitchService;
    private final FavoriteService favoriteService;

    public RecommendationService(TwitchService twitchService, FavoriteService favoriteService){
        this.twitchService = twitchService;
        this.favoriteService = favoriteService;
    }

    public TypeGroupedItemList recommendItems(UserEntity userEntity){
        List<String> gameIds;
        Set<String> exclusions = new HashSet<>();
        if(userEntity == null){
            gameIds = twitchService.getTopGameIds();
        }
        else{
            List<ItemEntity> items = favoriteService.getFavoriteItems(userEntity);
            if(items.isEmpty()){
                gameIds = twitchService.getTopGameIds();
            }
            else{
                Set<String> uniqueGameIds = new HashSet<>();
                for(ItemEntity item : items){
                    uniqueGameIds.add(item.gameId());
                    exclusions.add(item.twitchId());
                }
                gameIds = new ArrayList<>(uniqueGameIds);
            }
        }
        int gameSize = Math.min(gameIds.size(), MAX_GAME_SEED);
        int perGameListSize = PER_PAGE_ITEM_SIZE / gameSize;

        List<ItemEntity> streams = recommendStreams(gameIds, exclusions);
        List<ItemEntity> clips = recommendClips(gameIds.subList(0, gameSize), exclusions, perGameListSize);
        List<ItemEntity> videos = recommendVideos(gameIds.subList(0, gameSize), exclusions, perGameListSize);

        return new TypeGroupedItemList(streams, clips, videos);
    }

    private List<ItemEntity> recommendStreams(List<String> gameIds, Set<String> exclusions){
        List<ItemEntity> res = new ArrayList<>();
        List<Stream> streams = twitchService.getStreams(gameIds, PER_PAGE_ITEM_SIZE);
        for(Stream stream : streams){
            if(!exclusions.contains(stream.id())){
                res.add(new ItemEntity(stream));
            }
        }
        return res;
    }
    private List<ItemEntity> recommendVideos(List<String> gameIds, Set<String> exclusions, int perGameListSize){
        List<ItemEntity> res = new ArrayList<>();
        // gameId represents a genre/game, we get all videos related to that game
        // then remove the ones saved/favored by user
        for(String gameId : gameIds){
            List<Video> videos = twitchService.getVideos(gameId, PER_PAGE_ITEM_SIZE);
            for(Video video : videos){
                if(!exclusions.contains(video.id())){
                    res.add(new ItemEntity(gameId, video));
                }
            }
        }
        return res;
    }
    private List<ItemEntity> recommendClips(List<String> gameIds, Set<String> exclusions, int perGameListSize){
        List<ItemEntity> res = new ArrayList<>();
        for(String gameId : gameIds){
            List<Clip> clips = twitchService.getClips(gameId, PER_PAGE_ITEM_SIZE);
            for(Clip clip : clips){
                if(!exclusions.contains(clip.id())){
                    res.add(new ItemEntity(clip));
                }
            }
        }
        return res;
    }
}
