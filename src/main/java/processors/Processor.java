package processors;

import java.io.IOException;

public interface Processor {
    void process(String line) throws IOException;
}
