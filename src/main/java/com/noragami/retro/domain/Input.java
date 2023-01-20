package com.noragami.retro.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.util.Set;

@Data
@AllArgsConstructor
public class Input {

    private File mapFile;
    private Integer roomId;
    private Set<String> itemsToCollect;

}
