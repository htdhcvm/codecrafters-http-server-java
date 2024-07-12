import java.io.IOException;
import java.util.Map;

public class ServerFactory {

    public Server create(Map<CliParamsEnum, String> params) throws IOException {
        Server server;

        if (params.containsKey(CliParamsEnum.FILE_DIRECTORY)) {
            server = new FileServer(4221, 10, params.get(CliParamsEnum.FILE_DIRECTORY));
        } else {
            server = new SimpleServer(4221, 10);
        }

        return server;
    }
}
