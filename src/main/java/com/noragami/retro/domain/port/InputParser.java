package com.noragami.retro.domain.port;

import com.noragami.retro.domain.Input;

public interface InputParser {
    Input parse(final String[] args);
}
