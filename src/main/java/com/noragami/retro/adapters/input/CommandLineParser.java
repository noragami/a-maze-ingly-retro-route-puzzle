package com.noragami.retro.adapters.input;

import com.noragami.retro.RouteFinderUseCase;
import com.noragami.retro.domain.Input;
import com.noragami.retro.domain.port.InputParser;
import lombok.NoArgsConstructor;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Stream;

@NoArgsConstructor
public class CommandLineParser implements InputParser {

    private final Option mapOption = Option.builder("m").longOpt("map")
            .argName("map")
            .desc("JSON file containing rooms map")
            .hasArg()
            .type(File.class)
            .required()
            .build();
    private final Option roomOption = Option.builder("r").longOpt("room")
            .argName("roomId")
            .desc("ID of the room where the search starts")
            .hasArg()
            .type(Number.class)
            .required()
            .build();
    private final Option itemsOption = Option.builder("i").longOpt("items")
            .argName("itemsToCollect")
            .desc("Items to collect in the rooms map")
            .numberOfArgs(Option.UNLIMITED_VALUES)
            .build();

    @Override
    public Input parse(final String[] args) {
        final Options options = new Options();
        try {
            Stream.of(this.mapOption, this.roomOption, this.itemsOption).forEach(options::addOption);
            final org.apache.commons.cli.CommandLineParser parser = new DefaultParser();
            final CommandLine cli = parser.parse(options, args);
            final File mapFile = (File) cli.getParsedOptionValue(this.mapOption.getOpt());
            final Integer roomId = ((Number) cli.getParsedOptionValue(this.roomOption.getOpt())).intValue();
            final String[] items = Optional.ofNullable(cli.getOptionValues(this.itemsOption.getOpt()))
                    .orElse(new String[0]);
            return new Input(mapFile, roomId, new HashSet<>(Arrays.asList(items)));
        } catch (final ParseException ex) {
            System.err.printf("Can't parse arguments. %s%n", ex.getMessage());
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(RouteFinderUseCase.class.getName(), options, true);
            return null;
        }
    }

}
