package com.noragami.retro.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.Set;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Move {

    @NonNull
    private final Room room;
    @NonNull
    private final Set<Item> collectedItems;

}
