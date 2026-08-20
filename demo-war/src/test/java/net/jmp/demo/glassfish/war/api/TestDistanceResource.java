package net.jmp.demo.glassfish.war.api;

/*
 * (#)TestDistanceResource.java 0.5.0   08/20/2026
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

import net.jmp.demo.glassfish.ejb.dto.DistanceData;

import net.jmp.demo.glassfish.ejb.service.DistanceService;

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
class TestDistanceResource {
    @Mock
    private DistanceService distanceService;

    @InjectMocks
    private DistanceResource distanceResource;

    @Test
    void testDistance() {
        final DistanceData data = new DistanceData();

        data.setFromZipCode("10001");
        data.setToZipCode("90210");
        data.setToCity("Beverly Hills");
        data.setToState("CA");
        data.setDistanceInMiles(10.5d);
        data.setDistanceInKilometers(16.9d);

        final List<DistanceData> distances = List.of(data);

        when(this.distanceService.getAll()).thenReturn(distances);

        final Response.ResponseBuilder builder = mock(Response.ResponseBuilder.class);
        final Response expectedResponse = mock(Response.class);

        when(builder.build()).thenReturn(expectedResponse);
        when(expectedResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(expectedResponse.getEntity()).thenReturn(distances);

        try (final MockedStatic<Response> mockedResponse = mockStatic(Response.class)) {
            mockedResponse.when(() -> Response.ok(distances)).thenReturn(builder);

            final Response response = this.distanceResource.distance();

            assertNotNull(response);
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(distances, response.getEntity());
        }
    }

    @Test
    void testDistanceWithEmptyResult() {
        final List<DistanceData> distances = List.of();

        when(this.distanceService.getAll()).thenReturn(distances);

        final Response.ResponseBuilder builder = mock(Response.ResponseBuilder.class);
        final Response expectedResponse = mock(Response.class);

        when(builder.build()).thenReturn(expectedResponse);
        when(expectedResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(expectedResponse.getEntity()).thenReturn(distances);

        try (final MockedStatic<Response> mockedResponse = mockStatic(Response.class)) {
            mockedResponse.when(() -> Response.ok(distances)).thenReturn(builder);

            final Response response = this.distanceResource.distance();

            assertNotNull(response);
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(0, ((List<?>) response.getEntity()).size());
        }
    }
}
