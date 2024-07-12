import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class CliParamsParser implements Parser<Map<CliParamsEnum, String>, String[]> {
    private static final String directoryTarget = "--directory";

    @Override
    public Map<CliParamsEnum, String> parse(String[] args) throws IOException {
        Map<CliParamsEnum, String> response = new HashMap<>();

        Optional<String> directory = IntStream.range(0, args.length - 1)
                .filter(i -> args[i].equals(directoryTarget))
                .mapToObj(i -> args[i + 1])
                .findFirst();

        directory.ifPresent((v) -> response.put(CliParamsEnum.FILE_DIRECTORY, v));

        return response;
    }
}
