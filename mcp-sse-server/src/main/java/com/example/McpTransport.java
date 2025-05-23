package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.transport.WebMvcSseServerTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.web.servlet.function.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class McpTransport extends WebMvcSseServerTransport {

    public McpTransport(ObjectMapper objectMapper, String messageEndpoint) {
        super(objectMapper, messageEndpoint);
    }

    @Override
    public Mono<Void> sendMessage(McpSchema.JSONRPCMessage message) {
        return super.sendMessage(message);
    }

    @Override
    public Mono<Void> connect(Function<Mono<McpSchema.JSONRPCMessage>, Mono<McpSchema.JSONRPCMessage>> connectionHandler) {
        return super.connect(connectionHandler);
    }

    public McpTransport(ObjectMapper objectMapper, String messageEndpoint, String sseEndpoint) {
        super(objectMapper, messageEndpoint, sseEndpoint);
    }

}
