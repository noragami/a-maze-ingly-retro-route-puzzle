package com.noragami.retro;

import com.noragami.retro.domain.Item;
import com.noragami.retro.domain.Move;
import com.noragami.retro.domain.Room;

import java.util.*;

public class DepthFirstSearch {

    public void searchNextRoom(final List<Move> route,
                               final Set<Room> visitedRooms,
                               final Deque<Room> roomsToVisit,
                               final Set<Item> itemsToCollect) {
        if (roomsToVisit.isEmpty() || itemsToCollect.isEmpty()) {
            return;
        }

        // lookup the first room and mark it as visited
        final Room currentRoom = roomsToVisit.peek();
        visitedRooms.add(currentRoom);

        // collect only the needed items
        final Set<Item> collectedItems = new HashSet<>(currentRoom.getItems());
        collectedItems.retainAll(itemsToCollect);

        // add the room to the route
        route.add(new Move(currentRoom, collectedItems));

        // remove collected items from the list
        itemsToCollect.removeAll(collectedItems);

        // check for adjacent rooms and remove already visited ones
        final Set<Room> adjacentRooms = new HashSet<>(currentRoom.getAdjacentRooms());
        adjacentRooms.removeAll(visitedRooms);

        final Optional<Room> adjacentRoom = adjacentRooms.stream().findFirst();

        if (adjacentRoom.isPresent()) {
            // select the next room to visit
            roomsToVisit.push(adjacentRoom.get());
        } else {
            // remove visited room
            roomsToVisit.pop();
        }
        // recursively search
        searchNextRoom(route, visitedRooms, roomsToVisit, itemsToCollect);
    }

}
