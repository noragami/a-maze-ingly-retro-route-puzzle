package com.noragami.retro;

import com.noragami.retro.domain.Item;
import com.noragami.retro.domain.Move;
import com.noragami.retro.domain.Room;
import com.noragami.retro.domain.exception.NoItemsToCollectException;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.*;

@AllArgsConstructor
public class ItemCollector {

    private final DepthFirstSearch depthFirstSearch;

    public List<Move> collectAllItems(@NonNull final Room startRoom, @NonNull final Set<Item> itemsToCollect) {
        if (itemsToCollect.isEmpty()) {
            throw new NoItemsToCollectException();
        }

        final List<Move> route = new LinkedList<>();
        final Set<Room> visitedRooms = new HashSet<>();
        final Deque<Room> roomsToVisit = new ArrayDeque<>();
        roomsToVisit.push(startRoom);

        this.depthFirstSearch.searchNextRoom(route, visitedRooms, roomsToVisit, itemsToCollect);

        return route;
    }

}
