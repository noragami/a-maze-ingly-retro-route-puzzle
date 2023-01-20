package com.noragami.retro.domain.exception;

public class NoItemsToCollectException extends RuntimeException {

    public NoItemsToCollectException() {
        super("No items to collect!");
    }

}
