package com.noragami.retro.adapters;

import com.noragami.retro.domain.Item;
import com.noragami.retro.domain.Room;
import com.noragami.retro.domain.exception.RoomNotFoundException;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@AllArgsConstructor
public class JsonRoom extends Room {

    private Integer north;
    private Integer south;
    private Integer west;
    private Integer east;
    private Set<Item> objects;

    public void fillAdjacentRooms(final Map<Integer, JsonRoom> roomMap) {
        Stream.of(this.north, this.south, this.east, this.west)
                .filter(Objects::nonNull)
                .forEach(roomId -> {
                    final JsonRoom room = roomMap.get(roomId);
                    if (room == null) {
                        throw new RoomNotFoundException(roomId);
                    }
                    if (this.adjacentRooms == null) {
                        this.adjacentRooms = new HashSet<>();
                    }
                    this.adjacentRooms.add(room);
                });
    }

    @Override
    public Set<Item> getItems() {
        return new HashSet<>(this.objects);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof JsonRoom) {
            final JsonRoom other = (JsonRoom) o;
            return Objects.equals(this.getId(), other.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return this.name;
    }

}
