package net.jmp.demo.glassfish.ejb.service;

/*
 * (#)TestCarService.java  0.2.0   07/09/2026
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

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import net.jmp.demo.glassfish.ejb.dto.Car;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestCarService {
    @Test
    void testGetAll() {
        final EntityManager em = mock(EntityManager.class);
        final TypedQuery<Car> query = mock(TypedQuery.class);
        final Car car = new Car();

        car.setId(1L);
        car.setYear(2020);
        car.setMake("Toyota");
        car.setModel("Camry");
        car.setColor("Blue");
        car.setStyle("Sedan");

        when(em.createQuery("SELECT c FROM Car c", Car.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(car));

        final CarService service = new CarService(em);
        final List<Car> results = service.getAll();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(Long.valueOf(1L), results.get(0).getId());
        assertEquals(Integer.valueOf(2020), results.get(0).getYear());
        assertEquals("Toyota", results.get(0).getMake());
        assertEquals("Camry", results.get(0).getModel());
        assertEquals("Blue", results.get(0).getColor());
        assertEquals("Sedan", results.get(0).getStyle());
    }

    @Test
    void testGetAllWithEmptyResult() {
        final EntityManager em = mock(EntityManager.class);
        final TypedQuery<Car> query = mock(TypedQuery.class);

        when(em.createQuery("SELECT c FROM Car c", Car.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of());

        final CarService service = new CarService(em);
        final List<Car> results = service.getAll();

        assertNotNull(results);
        assertEquals(0, results.size());
    }
}
