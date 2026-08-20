package net.jmp.demo.glassfish.war.api;

/*
 * (#)TestPeopleResource.java 0.5.0   08/20/2026
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

import java.util.List;

import net.jmp.demo.glassfish.ejb.dto.Person;

import net.jmp.demo.glassfish.ejb.service.PeopleService;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestPeopleResource {
    @Mock
    private PeopleService peopleService;

    @InjectMocks
    private PeopleResource peopleResource;

    @Test
    void testPeople() {
        final Person person = new Person();

        person.setId(1L);
        person.setName("Jane Doe");
        person.setEmail("jane.doe@example.com");
        person.setComment("Test user");

        final List<Person> people = List.of(person);

        when(this.peopleService.getAll()).thenReturn(people);

        final Response.ResponseBuilder builder = mock(Response.ResponseBuilder.class);
        final Response expectedResponse = mock(Response.class);

        when(builder.build()).thenReturn(expectedResponse);
        when(expectedResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(expectedResponse.getEntity()).thenReturn(people);

        try (final MockedStatic<Response> mockedResponse = mockStatic(Response.class)) {
            mockedResponse.when(() -> Response.ok(people)).thenReturn(builder);

            final Response response = this.peopleResource.people();

            assertNotNull(response);
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(people, response.getEntity());
        }
    }

    @Test
    void testPeopleWithEmptyResult() {
        final List<Person> people = List.of();

        when(this.peopleService.getAll()).thenReturn(people);

        final Response.ResponseBuilder builder = mock(Response.ResponseBuilder.class);
        final Response expectedResponse = mock(Response.class);

        when(builder.build()).thenReturn(expectedResponse);
        when(expectedResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(expectedResponse.getEntity()).thenReturn(people);

        try (final MockedStatic<Response> mockedResponse = mockStatic(Response.class)) {
            mockedResponse.when(() -> Response.ok(people)).thenReturn(builder);

            final Response response = this.peopleResource.people();

            assertNotNull(response);
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(0, ((List<?>) response.getEntity()).size());
        }
    }
}
