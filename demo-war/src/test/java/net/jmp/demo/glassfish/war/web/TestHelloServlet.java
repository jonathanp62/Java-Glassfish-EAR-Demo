package net.jmp.demo.glassfish.war.web;

/*
 * (#)TestHelloServlet.java 0.1.0   07/07/2026
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

import jakarta.servlet.RequestDispatcher;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Field;

import net.jmp.demo.glassfish.ejb.service.HelloService;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestHelloServlet {
    private static void setHelloService(final HelloServlet servlet, final HelloService service) throws Exception {
        final Field field = HelloServlet.class.getDeclaredField("helloService");

        field.setAccessible(true);
        field.set(servlet, service);
    }

    @Test
    void testDoGet() throws Exception {
        final HelloServlet servlet = new HelloServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        final HelloService helloService = mock(HelloService.class);

        when(helloService.hello("Jonathan")).thenReturn("Hello, Jonathan, from the hello service");
        when(request.getRequestDispatcher("/WEB-INF/jsp/hello.jsp")).thenReturn(dispatcher);

        setHelloService(servlet, helloService);

        servlet.doGet(request, response);

        verify(request).setAttribute("greeting", "Hello, Jonathan, from the hello service");
        verify(dispatcher).forward(request, response);
    }
}
