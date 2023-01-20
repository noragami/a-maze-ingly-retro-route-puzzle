package com.noragami.retro.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    protected Integer id;
    protected String name;
    protected Set<Room> adjacentRooms;
    protected Set<Item> items;

}
