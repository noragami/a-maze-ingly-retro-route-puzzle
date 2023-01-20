package com.noragami.retro.domain.exception;

public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException(final Integer roomId) {
        super(String.format("Room with id %d not found", roomId));
    }

}
