package com.noragami.retro.domain.port;

import com.noragami.retro.domain.Room;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface MapLoader<T extends Room> {
    Map<Integer, T> loadMap(File f) throws IOException;
}
