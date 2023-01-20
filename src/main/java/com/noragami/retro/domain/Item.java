package com.noragami.retro.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @NonNull
    private String name;

    @Override
    public String toString() {
        return this.name;
    }

}
