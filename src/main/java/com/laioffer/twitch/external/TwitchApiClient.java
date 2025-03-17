package com.laioffer.twitch.external;

import com.laioffer.twitch.external.model.ClipResponse;
import com.laioffer.twitch.external.model.GameResponse;
import com.laioffer.twitch.external.model.StreamResponse;
import com.laioffer.twitch.external.model.VideoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// interface is a completely abstract class
    // methods are default public & abstract
    // attributes are default public, static and final
// @FeignClient annotation tells Spring to create a proxy bean to make HTTP requests
@FeignClient(name = "twitch-api")
public interface TwitchApiClient {
    // getGames is an abstract method
    // Feign is a declarative HTTP client, meaning that you only need to define WHAT
    // you want to do, and it takes care of HOW to do it
    @GetMapping("/games")
    GameResponse getGames(@RequestParam("name") String name);

    @GetMapping("/games/top")
    GameResponse getTopGames();

    @GetMapping("/videos/")
    VideoResponse getVideos(
            @RequestParam("game_id") String gameId,
            @RequestParam("first") int first
    );

    @GetMapping("/clips/")
    ClipResponse getClips(
            @RequestParam("game_id") String gameId,
            @RequestParam("first") int first
    );

    @GetMapping("/streams/")
    StreamResponse getStreams(
            @RequestParam("game_id") List<String> gameIds,
            @RequestParam("first") int first
    );
}
