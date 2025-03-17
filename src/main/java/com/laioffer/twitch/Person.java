package com.laioffer.twitch;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Person(
        String name,
        String company,
        // JSON response are in snake_case, cannot be directly mapped
        // to Java variable in camelCase, hence we need @JsonProperty
        @JsonProperty("home_address") Address homeAddress,
        @JsonProperty("favorite_book") Book favoriteBook
) {

}
