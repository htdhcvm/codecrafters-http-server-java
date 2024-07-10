package processors;

import java.io.IOException;

public interface Processor {
    String process(String line) throws IOException;
}
