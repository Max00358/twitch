package com.laioffer.twitch;

import com.laioffer.twitch.favorite.DuplicateFavoriteException;
import com.laioffer.twitch.model.TwitchErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// @ControllerAdvice will let GlobalControllerExceptionHandler handle any exceptions thrown by any controller in our application
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<TwitchErrorResponse> handleDefaultExceptions(Exception e) {
        logger.error("", e);
        return new ResponseEntity<>(
            new TwitchErrorResponse(
                    "Something went wrong, please try again later",
                    e.getClass().getName(),
                    e.getMessage()
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // This annotation tells Spring to use this method to handle DuplicateFavoriteException
    @ExceptionHandler(DuplicateFavoriteException.class)
    public final ResponseEntity<TwitchErrorResponse> handleDuplicateFavoriteException(DuplicateFavoriteException e) {
        return new ResponseEntity<>(
                new TwitchErrorResponse("Duplicate entry error.",
                        e.getClass().getName(),
                        e.getMessage()
                ),
                HttpStatus.BAD_REQUEST
        );
    }
}
