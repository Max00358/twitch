package com.laioffer.twitch.db.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.laioffer.twitch.external.model.Clip;
import com.laioffer.twitch.external.model.ItemType;
import com.laioffer.twitch.external.model.Stream;
import com.laioffer.twitch.external.model.Video;

// stream, video & clip are all ItemEntity obj with the below attributes
@Table("items")
public record ItemEntity(
    @Id Long id,
    @JsonProperty("twitch_id") String twitchId,
    String title,
    String url,
    @JsonProperty("thumbnail_url") String thumbnailUrl,
    @JsonProperty("broadcaster_name") String broadcasterName,
    @JsonProperty("game_id") String gameId,
    @JsonProperty("item_type") ItemType type
){
    // when having multiple constructors, use constructor chaining
        // this(...attributes) to avoid duplication logic
    // when having only 1 constructor, use direct assigning
        // this.attribute1 = assigned_attribute1
    public ItemEntity(String gameId, Video video){
        this(
            null,
            video.id(),
            video.title(),
            video.url(),
            video.thumbnailUrl(),
            video.userName(),
            gameId,
            ItemType.VIDEO
        );
    }
    public ItemEntity(Clip clip){
        this(
            null,
            clip.id(),
            clip.title(),
            clip.url(),
            clip.thumbnailUrl(),
            clip.broadcasterName(),
            clip.gameId(),
            ItemType.CLIP
        );
    }
    public ItemEntity(Stream stream){
        this(
            null,
            stream.id(),
            stream.title(),
            "https://www.twitch.tv/" + stream.userLogin(),
            stream.thumbnail_url(),
            stream.userName(),
            stream.gameId(),
            ItemType.STREAM
        );
    }
}
