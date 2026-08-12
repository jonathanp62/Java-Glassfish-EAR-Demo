package net.jmp.demo.glassfish.war.api;

/*
 * (#)PeopleResource.java   0.5.0   08/12/2026
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

import jakarta.inject.Inject;
import jakarta.inject.Named;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.ResourceBundle;

import net.jmp.demo.glassfish.ejb.dto.Person;

import net.jmp.demo.glassfish.ejb.service.PeopleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.jmp.util.logging.LoggerUtils.entry;
import static net.jmp.util.logging.LoggerUtils.exitWith;

/// The people resource class
@Path("/people")
public class PeopleResource {
    // Initialize the SLF4J Logger
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /// The messages resource bundle
    private final ResourceBundle bundle;

    /// The people service
    @EJB
    @SuppressWarnings("NullAway")
    private PeopleService peopleService;

    /// The constructor
    ///
    /// @param  bundle  java.util.ResourceBundle
    @Inject
    public PeopleResource(@Named("messages") final ResourceBundle bundle) {
        super();

        this.bundle = bundle;
    }

    /// The GET method that returns a JSON response
    ///
    /// @return jakarta.ws.rs.core.Response
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response people() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<Person> people = this.peopleService.getAll();
        final Response response = Response.ok(people).build();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(response));
        }

        return response;
    }
}
