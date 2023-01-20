package com.noragami.retro.adapters.map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.noragami.retro.adapters.JsonMap;
import com.noragami.retro.adapters.JsonRoom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JsonMapLoaderTest {

    final File mapFile = new File("src/test/resources/test_map.json");
    JsonMapLoader jsonMapLoader;

    @Test
    void should_call_gson_mapper(@Mock final Gson mapper) throws IOException {
        final JsonRoom jsonRoom = new JsonRoom(null, null, null, null, null);
        jsonRoom.setId(1);
        jsonRoom.setName("test");
        final JsonMap jsonMap = new JsonMap(Collections.singletonList(jsonRoom));
        when(mapper.fromJson(any(JsonReader.class), eq(JsonMap.class))).thenReturn(jsonMap);

        this.jsonMapLoader = new JsonMapLoader(mapper);

        final Map<Integer, JsonRoom> result = this.jsonMapLoader.loadMap(this.mapFile);

        verify(mapper, times(1)).fromJson(any(JsonReader.class), eq(JsonMap.class));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertThat(result).containsEntry(1, jsonRoom);
    }

    @Test
    void should_return_the_converted_json_map() throws IOException {
        this.jsonMapLoader = new JsonMapLoader(new Gson());

        final Map<Integer, JsonRoom> result = this.jsonMapLoader.loadMap(this.mapFile);

        assertNotNull(result);
        assertEquals(7, result.size());
        assertThat(result).containsKeys(1, 2, 3, 4, 5, 6, 7);
        assertThat(result.values().stream().map(JsonRoom::getName))
                .containsExactly("Hallway", "Dining Room", "Kitchen", "Sun Room", "Bedroom", "Bathroom", "Living room");
        assertThat(result.values().stream().map(JsonRoom::getAdjacentRooms).findFirst().get()).extracting("name")
                .containsExactly("Dining Room", "Living room");
    }


}