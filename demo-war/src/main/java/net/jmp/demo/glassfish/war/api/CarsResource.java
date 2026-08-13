package net.jmp.demo.glassfish.war.api;

/*
 * (#)CarsResource.java 0.5.0   08/11/2026
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

import jakarta.ejb.EJB;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import net.jmp.demo.glassfish.ejb.dto.Car;

import net.jmp.demo.glassfish.ejb.service.CarService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.jmp.util.logging.LoggerUtils.entry;
import static net.jmp.util.logging.LoggerUtils.exitWith;

/// The cars resource class
@Path("/cars")
public class CarsResource {
    // Initialize the SLF4J Logger
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /// The car service
    @EJB
    @SuppressWarnings("NullAway")
    private CarService carService;

    /// The constructor
    public CarsResource() {
        super();
    }

    /// The GET method that returns a JSON response
    ///
    /// @return jakarta.ws.rs.core.Response
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response cars() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<Car> cars = this.carService.getAll();
        final Response response = Response.ok(cars).build();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(response));
        }

        return response;
    }
}
