package io.codecrafters.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Response {
    private String headers;

    private String bodyString;
    private byte[] bodyByte;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public byte[] getHeadersBytes() {
        return this.headers.getBytes();
    }
}
