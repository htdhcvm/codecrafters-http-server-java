package io.codecrafters.parser;

import java.io.IOException;

public interface Parser<R, P> {
     R parse(P in) throws IOException;
}
