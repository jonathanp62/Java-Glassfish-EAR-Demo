package net.jmp.demo.glassfish.war.web;

/*
 * (#)TestDistanceServlet.java  0.4.0   07/21/2026
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

import java.util.List;

import net.jmp.demo.glassfish.ejb.dto.DistanceData;

import net.jmp.demo.glassfish.ejb.service.DistanceService;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestDistanceServlet {
    @Test
    void testDoGet() throws Exception {
        final DistanceService distanceService = mock(DistanceService.class);
        final DistanceServlet servlet = new DistanceServlet(distanceService);

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        final DistanceData distanceData = new DistanceData();

        distanceData.setFromZipCode("01234");
        distanceData.setToZipCode("56789");
        distanceData.setToCity("Boston");
        distanceData.setToState("MA");
        distanceData.setDistanceInMiles(1234.567);
        distanceData.setDistanceInKilometers(1987.123);

        final List<DistanceData> distances = List.of(distanceData);

        when(distanceService.getAll()).thenReturn(distances);
        when(request.getRequestDispatcher("/WEB-INF/jsf/distance.xhtml")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("distances"), eq(distances));
        verify(dispatcher).forward(request, response);
    }
}
