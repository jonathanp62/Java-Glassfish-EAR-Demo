package net.jmp.demo.glassfish.ejb.service;

/*
 * (#)TestHelloService.java 0.1.0   07/07/2026
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

import java.lang.reflect.Field;

import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestHelloService {
    private static void setBundle(final HelloService service, final ResourceBundle bundle) throws Exception {
        final Field field = HelloService.class.getDeclaredField("bundle");

        field.setAccessible(true);
        field.set(service, bundle);
    }

    @Test
    void testHello() throws Exception {
        final HelloService service = new HelloService();
        final ResourceBundle bundle = mock(ResourceBundle.class);

        when(bundle.getString("service.hello.greeting")).thenReturn("Hello, {0}, from the hello service");

        setBundle(service, bundle);

        final String result = service.hello("Bob");

        assertEquals("Hello, Bob, from the hello service", result);
    }

    @Test
    void testHelloWithNullName() throws Exception {
        final HelloService service = new HelloService();
        final ResourceBundle bundle = mock(ResourceBundle.class);

        when(bundle.getString("service.hello.greeting")).thenReturn("Hello, {0}, from the hello service");

        setBundle(service, bundle);

        final String result = service.hello(null);

        assertEquals("Hello, null, from the hello service", result);
    }
}
