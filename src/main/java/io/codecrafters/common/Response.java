package io.codecrafters.common;

public class Response {

    private String message;

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "io.codecrafters.common.Response{" +
                "message='" + message + '\'' +
                '}';
    }

    public byte[] getMessageBytes() {
        return this.message.getBytes();
    }
}
