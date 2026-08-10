package net.jmp.demo.glassfish.war.web;

/*
 * (#)PeopleServlet.java    0.4.0   07/21/2026
 * (#)PeopleServlet.java    0.2.0   07/10/2026
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

import jakarta.annotation.security.DeclareRoles;

import jakarta.ejb.EJB;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;

import net.jmp.demo.glassfish.ejb.service.PeopleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.jmp.util.logging.LoggerUtils.entryWith;
import static net.jmp.util.logging.LoggerUtils.exit;

/// The people servlet class
@WebServlet(urlPatterns = "/servlet/people")
@DeclareRoles("user")
@ServletSecurity(@HttpConstraint(rolesAllowed = "user"))
public class PeopleServlet extends HttpServlet {
    /// The serial version UID
    @Serial
    private static final long serialVersionUID = 1L;

    /// The people JSF
    private static final String PEOPLE_JSF = "/WEB-INF/jsf/people.xhtml";

    // Initialize the SLF4J Logger
    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    /// The people service
    @EJB
    @SuppressWarnings("NullAway")
    private transient PeopleService peopleService;

    /// Default constructor
    /// It is required by Glassfish since
    /// there is a parameterized constructor.
    public PeopleServlet() {
        super();
    }

    /// Constructor for testing
    ///
    /// @param  peopleService   net.jmp.demo.glassfish.war.service.PeopleService
    PeopleServlet(final PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    /// The GET method. Called from /servlet/people.
    ///
    /// @param  request     jakarta.servlet.http.HttpServletRequest
    /// @param  response    jakarta.servlet.http.HttpServletResponse
    /// @throws             jakarta.servlet.ServletException    When an error occurs
    /// @throws             java.io.IOException                 When an I/O error occurs
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(request, response));
        }

        request.setAttribute("people", this.peopleService.getAll());
        request.getRequestDispatcher(PEOPLE_JSF).forward(request, response);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
