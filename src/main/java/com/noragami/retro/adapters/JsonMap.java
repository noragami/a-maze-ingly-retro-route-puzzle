package com.noragami.retro.adapters;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class JsonMap {

    @NonNull
    private final List<JsonRoom> rooms;

}
