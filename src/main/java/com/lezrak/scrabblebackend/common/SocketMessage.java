package com.lezrak.scrabblebackend.common;

public class SocketMessage {
    private String header;
    private Object body;

    public SocketMessage(String header, Object body) {
        this.header = header;
        this.body = body;
    }

    public SocketMessage(String header) {
        this.header = header;
        this.body = null;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
