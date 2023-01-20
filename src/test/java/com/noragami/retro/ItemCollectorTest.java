package com.noragami.retro;

import com.noragami.retro.domain.Item;
import com.noragami.retro.domain.Move;
import com.noragami.retro.domain.Room;
import com.noragami.retro.domain.exception.NoItemsToCollectException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ItemCollectorTest {

    ItemCollector itemCollector;

    @Test
    void should_call_depth_first_search(@Mock final DepthFirstSearch depthFirstSearch) {
        doNothing().when(depthFirstSearch).searchNextRoom(any(), any(), any(), any());

        this.itemCollector = new ItemCollector(depthFirstSearch);

        final Room room = new Room(1, "a", null, null);
        final Set<Item> items = new HashSet<>();
        items.add(new Item("item"));
        final List<Move> moves = this.itemCollector.collectAllItems(room, items);

        verify(depthFirstSearch, times(1)).searchNextRoom(any(), any(), any(), any());

        assertNotNull(moves);
    }

    @Test
    void should_throw_no_items_exception_when_nothing_to_collect(@Mock final DepthFirstSearch depthFirstSearch) {
        doNothing().when(depthFirstSearch).searchNextRoom(any(), any(), any(), any());

        this.itemCollector = new ItemCollector(depthFirstSearch);

        final Room room = new Room(1, "a", null, null);
        final NoItemsToCollectException exception = assertThrows(
                NoItemsToCollectException.class,
                () -> this.itemCollector.collectAllItems(room, new HashSet<>())
        );

        assertEquals("No items to collect!", exception.getMessage());
    }

    @Test
    void should_stay_on_start_room_when_no_other_rooms() {
        this.itemCollector = new ItemCollector(new DepthFirstSearch());

        final Set<Item> itemsToCollect = new HashSet<>();
        itemsToCollect.add(new Item("item"));
        final Room startRoom = new Room(1, "Room 1", new HashSet<>(), itemsToCollect);

        final List<Move> moves = this.itemCollector.collectAllItems(startRoom, itemsToCollect);

        assertNotNull(moves);
        assertEquals(1, moves.size());
        assertThat(moves).map(Move::getRoom).contains(startRoom);
    }

    @Test
    void should_collect_all_items() {
        this.itemCollector = new ItemCollector(new DepthFirstSearch());

        final Set<Item> itemsToCollect = new HashSet<>();
        final Item item = new Item("item 1");
        final Item anotherItem = new Item("item 2");
        itemsToCollect.add(item);
        itemsToCollect.add(anotherItem);
        final Room nextRoomWithItem = new Room(2, "Room 2", new HashSet<>(), Collections.singleton(item));
        final Room startRoomWithItem = new Room(1, "Room 1", Collections.singleton(nextRoomWithItem), Collections.singleton(anotherItem));

        final List<Move> moves = this.itemCollector.collectAllItems(startRoomWithItem, itemsToCollect);

        assertNotNull(moves);
        assertEquals(2, moves.size());
        assertThat(moves).map(Move::getRoom).containsExactly(startRoomWithItem, nextRoomWithItem);
    }

    @Test
    void should_not_visit_next_room_if_all_items_collected() {
        this.itemCollector = new ItemCollector(new DepthFirstSearch());

        final Set<Item> itemsToCollect = new HashSet<>();
        itemsToCollect.add(new Item("item"));
        final Room nextRoomWithoutItem = new Room(2, "Room 2", new HashSet<>(), new HashSet<>());
        final Room startRoom = new Room(1, "Room 1", Collections.singleton(nextRoomWithoutItem), itemsToCollect);


        final List<Move> moves = this.itemCollector.collectAllItems(startRoom, itemsToCollect);

        assertNotNull(moves);
        assertEquals(1, moves.size());
        assertThat(moves).map(Move::getRoom).containsExactly(startRoom);
    }

    @Test
    void should_collect_all_items_and_stop_if_all_items_collected() {
        this.itemCollector = new ItemCollector(new DepthFirstSearch());

        final Set<Item> itemsToCollect = new HashSet<>();
        final Item item = new Item("item 1");
        final Item anotherItem = new Item("item 2");
        itemsToCollect.add(item);
        itemsToCollect.add(anotherItem);
        final Room nextRoomWithoutItem = new Room(3, "Room 3", new HashSet<>(), new HashSet<>());
        final Room nextRoomWithItem = new Room(2, "Room 2", Collections.singleton(nextRoomWithoutItem), Collections.singleton(item));
        final Room startRoomWithItem = new Room(1, "Room 1", Collections.singleton(nextRoomWithItem), Collections.singleton(anotherItem));

        final List<Move> moves = this.itemCollector.collectAllItems(startRoomWithItem, itemsToCollect);

        assertNotNull(moves);
        assertEquals(2, moves.size());
        assertThat(moves).map(Move::getRoom).containsExactly(startRoomWithItem, nextRoomWithItem);
    }

}