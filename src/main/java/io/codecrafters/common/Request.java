package io.codecrafters.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;


@Builder
@Getter
public class Request {
    private Method method;

    private String path;

    private String httpVersion;

    private String host;

    private Map<String, String> headers;

    private String body;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}


