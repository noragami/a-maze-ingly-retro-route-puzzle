package com.noragami.retro;

import com.google.gson.Gson;
import com.noragami.retro.adapters.input.CommandLineParser;
import com.noragami.retro.adapters.map.JsonMapLoader;
import com.noragami.retro.adapters.render.AsciiTableRouteRender;
import com.noragami.retro.domain.Input;
import com.noragami.retro.domain.Item;
import com.noragami.retro.domain.Move;
import com.noragami.retro.domain.Room;
import com.noragami.retro.domain.exception.RoomNotFoundException;
import com.noragami.retro.domain.port.InputParser;
import com.noragami.retro.domain.port.RouteRender;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@AllArgsConstructor
public class RouteFinderUseCase {

    private final Map<Integer, ? extends Room> puzzle;

    public static void main(final String[] args) throws IOException {
        final InputParser commandLineParser = new CommandLineParser();
        final Input input = commandLineParser.parse(args);
        if (input == null) {
            System.exit(1);
        }

        final Map<Integer, ? extends Room> map = new JsonMapLoader(new Gson())
                .loadMap(input.getMapFile());

        final RouteFinderUseCase puzzleRouteFinderUseCase = new RouteFinderUseCase(map);

        final Integer startingRoomId = input.getRoomId();

        final Set<Item> itemsToCollect = input.getItemsToCollect().stream()
                .map(Item::new)
                .collect(toSet());

        final List<Move> route = puzzleRouteFinderUseCase.solve(startingRoomId, itemsToCollect);

        final RouteRender routeRender = new AsciiTableRouteRender(route);

        System.out.println(routeRender.render());

    }

    List<Move> solve(final Integer roomId, final Set<Item> itemsToCollect) {
        final Room startRoom = Optional.ofNullable(this.puzzle.get(roomId))
                .orElseThrow(() -> new RoomNotFoundException(roomId));
        final ItemCollector collector = new ItemCollector(new DepthFirstSearch());
        return collector.collectAllItems(startRoom, itemsToCollect);
    }

}
