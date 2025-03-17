package com.laioffer.twitch.external;

import com.laioffer.twitch.external.model.Game;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// GameController class is responsible for searching a specific game/top games from Twitch directly
@RestController
public class GameController {
    private final TwitchService twitchService;

    public GameController(TwitchService twitchService) {
        this.twitchService = twitchService;
    }

    @GetMapping("/game")
    public List<Game> getGames(@RequestParam(value = "game_name", required = false) String gameName) {
        return (gameName == null) ? twitchService.getTopGames() : twitchService.getGames(gameName);
    }
}
