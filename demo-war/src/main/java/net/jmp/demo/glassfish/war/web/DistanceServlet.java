package net.jmp.demo.glassfish.war.web;

/*
 * (#)DistanceServlet.java  0.3.0   07/12/2026
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

import jakarta.inject.Inject;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;

import java.util.List;

import net.jmp.demo.glassfish.ejb.dto.DistanceData;

import net.jmp.demo.glassfish.ejb.service.DistanceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.jmp.util.logging.LoggerUtils.*;

/// The distance servlet class
@WebServlet(urlPatterns = "/servlet/distance")
@DeclareRoles("user")
@ServletSecurity(@HttpConstraint(rolesAllowed = "user"))
public class DistanceServlet extends HttpServlet {
    /// The serial version UID
    @Serial
    private static final long serialVersionUID = 1L;

    /// The distance JSP
    private static final String DISTANCE_JSP = "/WEB-INF/jsp/distance.jsp";

    // Initialize the SLF4J Logger
    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    /// The distance service
    @Inject
    @SuppressWarnings("NullAway")
    private transient DistanceService distanceService;

    /// Default constructor
    /// It is required since there is a parameterized constructor.
    public DistanceServlet() {
        super();
    }

    /// Constructor for testing
    ///
    /// @param  distanceService net.jmp.demo.glassfish.war.service.DistanceService
    @SuppressWarnings("UnnecessaryAssignment")
    DistanceServlet(final DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    /// The GET method. Called from /servlet/distance.
    ///
    /// @param  request     jakarta.servlet.http.HttpServletRequest
    /// @param  response    jakarta.servlet.http.HttpServletResponse
    /// @throws             jakarta.servlet.ServletException When an error occurs
    /// @throws             java.io.IOException When an I/O error occurs
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(request, response));
        }

        final List<DistanceData> distances = this.distanceService.getAll();

        request.setAttribute("distances", distances);

        request.getRequestDispatcher(DISTANCE_JSP).forward(request, response);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
