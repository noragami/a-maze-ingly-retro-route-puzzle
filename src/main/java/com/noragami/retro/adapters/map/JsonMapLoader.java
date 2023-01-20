package com.noragami.retro.adapters.map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.noragami.retro.adapters.JsonMap;
import com.noragami.retro.adapters.JsonRoom;
import com.noragami.retro.domain.port.MapLoader;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@AllArgsConstructor
public class JsonMapLoader implements MapLoader<JsonRoom> {

    private final Gson mapper;

    @Override
    public Map<Integer, JsonRoom> loadMap(final File f) throws IOException {
        final JsonReader reader = new JsonReader(new FileReader(f));
        final JsonMap jsonMap = this.mapper.fromJson(reader, JsonMap.class);
        final Map<Integer, JsonRoom> roomMap = mapRooms(jsonMap);
        fillAdjacentRooms(roomMap);
        return roomMap;
    }

    private Map<Integer, JsonRoom> mapRooms(final JsonMap jsonMap) {
        return jsonMap.getRooms().stream()
                .collect(toMap(JsonRoom::getId, identity()));
    }

    private void fillAdjacentRooms(final Map<Integer, JsonRoom> roomMap) {
        roomMap.values().forEach(r -> r.fillAdjacentRooms(roomMap));
    }

}
