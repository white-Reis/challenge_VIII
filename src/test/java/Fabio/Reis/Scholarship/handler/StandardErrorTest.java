package Fabio.Reis.Scholarship.handler;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StandardErrorTest {

    @Test
    void testConstructor() {
        LocalDateTime timestamp = LocalDateTime.now();
        Integer status = 404;
        String error = "Not Found";
        String path = "/api/something";

        StandardError standardError = new StandardError(timestamp, status, error, path);

        assertEquals(timestamp, standardError.getTimestamp());
        assertEquals(status, standardError.getStatus());
        assertEquals(error, standardError.getError());
        assertEquals(path, standardError.getPath());
    }
}