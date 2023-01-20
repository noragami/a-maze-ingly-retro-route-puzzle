package com.noragami.retro.adapters.render;

import com.noragami.retro.domain.Move;
import com.noragami.retro.domain.port.RouteRender;
import de.vandermeer.asciitable.AsciiTable;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AsciiTableRouteRender implements RouteRender {

    private final List<Move> route;

    @Override
    public String render() {
        final AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("ID", "Room", "Object collected");
        at.addRule();
        for (final Move m : this.route) {
            at.addRow(
                    m.getRoom().getId(),
                    m.getRoom().getName(),
                    m.getCollectedItems().isEmpty() ? "None" : m.getCollectedItems()
            );
            at.addRule();
        }
        return at.render();
    }

}
