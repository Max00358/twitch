package com.laioffer.twitch.external;

import com.laioffer.twitch.external.model.Clip;
import com.laioffer.twitch.external.model.Game;
import com.laioffer.twitch.external.model.Stream;
import com.laioffer.twitch.external.model.Video;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// @Service annotation marks class as a Spring bean (an obj managed by Spring)
// allowing Spring to create & manage instances of it
    // in this case, TwitchApiClient bean is injected into TwitchService Bean
    // making application easier to maintain
@Service
public class TwitchService{
// We create TwitchService class to handle JSON data response returned by TwitchApiClient

    // twitchApiClient is private & final:
    // private so that it can only be accessed in TwitchService class
    // final so that TwitchApiClient fields cannot be re-assigned after its initialization
    private final TwitchApiClient twitchApiClient;

    public TwitchService(TwitchApiClient twitchApiClient) {
        this.twitchApiClient = twitchApiClient;
    }

    public List<Game> getTopGames() {
        return twitchApiClient.getTopGames().data();
    }
    public List<Game> getGames(String name){
        return twitchApiClient.getGames(name).data();
    }
    public List<Stream> getStreams(List<String> gameIds, int first) {
        return twitchApiClient.getStreams(gameIds, first).data();
    }
    public List<Video> getVideos(String gameId, int first){
        return twitchApiClient.getVideos(gameId, first).data();
    }
    public List<Clip> getClips(String gameId, int first){
        return twitchApiClient.getClips(gameId, first).data();
    }
    public List<String> getTopGameIds(){
        List<String> topGameIds = new ArrayList<>();
        List<Game> topGames = getTopGames();
        for(Game g : topGames){
            topGameIds.add(g.id());
        }
        return topGameIds;
    }
}
