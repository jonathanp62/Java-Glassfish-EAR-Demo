package net.jmp.demo.glassfish.war.api;

/*
 * (#)TestHelloResource.java 0.5.0   08/20/2026
 *
 * @author   Jonathan Parker
 *
 * MIT License
 *
 * Copyright (c) 2026 Jonathan M. Parker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import jakarta.ws.rs.core.Response;

import java.util.ResourceBundle;

import net.jmp.demo.glassfish.war.api.HelloResource.StatusMessage;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.MockedStatic;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestHelloResource {
    @Mock
    private ResourceBundle bundle;

    @Test
    void testHello() {
        when(this.bundle.getString("resource.hello.greeting")).thenReturn("Hello, World!");

        final HelloResource resource = new HelloResource(this.bundle);

        final StatusMessage statusMessage = new StatusMessage("OK", "Hello, World!");

        final Response.ResponseBuilder builder = mock(Response.ResponseBuilder.class);
        final Response expectedResponse = mock(Response.class);

        when(builder.build()).thenReturn(expectedResponse);
        when(expectedResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(expectedResponse.getEntity()).thenReturn(statusMessage);

        try (final MockedStatic<Response> mockedResponse = mockStatic(Response.class)) {
            mockedResponse.when(() -> Response.ok(statusMessage)).thenReturn(builder);

            final Response response = resource.hello();

            assertNotNull(response);
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(statusMessage, response.getEntity());
        }
    }
}
