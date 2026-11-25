package com.egtdigital.automation.api.support;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * Minimal in-process HTTP stub on a random port so the API smoke scenario runs offline and
 * deterministically.
 *
 * <p>Deliberately <b>not</b> a Spring bean: a running server is scenario state. {@code ApiHooks}
 * creates a fresh instance per scenario and stops it in the {@code @After} hook, so one scenario can
 * never shut down the server another scenario is still using.
 */
public class StubApiServer {

    private static final String HEALTH_BODY = "{\"status\":\"UP\"}";
    private static final int ANY_FREE_PORT = 0;

    private HttpServer server;

    public void start() {
        if (server != null) {
            return;
        }
        try {
            server = HttpServer.create(new InetSocketAddress("127.0.0.1", ANY_FREE_PORT), 0);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to start the stub API server", e);
        }
        server.createContext("/health", this::handleHealth);
        server.start();
    }

    public String baseUri() {
        if (server == null) {
            throw new IllegalStateException("Stub API server has not been started");
        }
        return "http://127.0.0.1:" + server.getAddress().getPort();
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
            server = null;
        }
    }

    private void handleHealth(HttpExchange exchange) throws IOException {
        byte[] body = HEALTH_BODY.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, body.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(body);
        }
    }
}
