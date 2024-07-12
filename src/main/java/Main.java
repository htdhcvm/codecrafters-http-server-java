
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

