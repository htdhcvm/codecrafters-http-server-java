package io.codecrafters;

import io.codecrafters.common.CliParamsEnum;
import io.codecrafters.parser.CliParamsParser;
import io.codecrafters.server.Server;
import io.codecrafters.server.ServerFactory;

import java.io.*;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        CliParamsParser cliParamsParser = new CliParamsParser();
        ServerFactory serverFactory = new ServerFactory();

        Map<CliParamsEnum, String> params = cliParamsParser.parse(args);

        Server server = serverFactory.create(params);
        server.start();
    }
}

