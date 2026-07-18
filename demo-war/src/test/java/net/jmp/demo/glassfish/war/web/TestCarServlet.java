package net.jmp.demo.glassfish.war.web;

/*
 * (#)TestCarServlet.java  0.2.0   07/09/2026
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

import net.jmp.demo.glassfish.ejb.dto.Car;

import net.jmp.demo.glassfish.ejb.service.CarService;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestCarServlet {
    @Test
    void testDoGet() throws Exception {
        final CarService carService = mock(CarService.class);
        final CarServlet servlet = new CarServlet(carService);

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        final Car car = new Car();

        car.setId(1L);
        car.setYear(2020);
        car.setMake("Toyota");
        car.setModel("Camry");
        car.setColor("Blue");
        car.setStyle("Sedan");

        final List<Car> cars = List.of(car);

        when(carService.getAll()).thenReturn(cars);
        when(request.getRequestDispatcher("/WEB-INF/jsf/cars.xhtml")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("cars"), eq(cars));
        verify(dispatcher).forward(request, response);
    }
}
