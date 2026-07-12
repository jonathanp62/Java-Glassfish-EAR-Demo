package net.jmp.demo.glassfish.war.web;

/*
 * (#)UsersServlet.java 0.2.0   07/10/2026
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

import net.jmp.demo.glassfish.ejb.service.UserService;

import org.jspecify.annotations.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.jmp.util.logging.LoggerUtils.*;

/// The users servlet class
@WebServlet(urlPatterns = "/users")
@DeclareRoles("user")
@ServletSecurity(@HttpConstraint(rolesAllowed = "user"))
public class UsersServlet extends HttpServlet {
    /// The serial version UID
    @Serial
    private static final long serialVersionUID = 1L;

    /// The users JSP
    private static final String USERS_JSP = "/WEB-INF/jsp/users.jsp";

    // Initialize the SLF4J Logger
    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    /// The user service
    @EJB
    @SuppressWarnings("NullAway")
    private transient UserService userService;

    /// Default constructor
    UsersServlet() {
        super();
    }

    /// Constructor for testing
    ///
    /// @param  userService net.jmp.demo.glassfish.war.service.UserService
    UsersServlet(final UserService userService) {
        this.userService = userService;
    }

    /// The GET method. Called from /users.
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

        final Integer projectId = this.readProjectId(request);

        if (projectId != null) {
            request.setAttribute("projectId", projectId);
            request.setAttribute("users", this.userService.getForProject(projectId));
            request.getRequestDispatcher(USERS_JSP).forward(request, response);
        } else {
            throw new ServletException("Project ID is null or invalid");
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Read the project ID from the request
    ///
    /// @param  request     jakarta.servlet.http.HttpServletRequest
    /// @return             java.lang.Integer
    private @Nullable Integer readProjectId(final HttpServletRequest request) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(request));
        }

        Integer projectId = null;

        final String projectIdParam = request.getParameter("projectId");

        if (projectIdParam == null || projectIdParam.isBlank()) {
            this.logger.error("Required request parameter 'projectId' is missing or blank");
        } else {
            try {
                projectId = Integer.valueOf(projectIdParam);
            } catch (final NumberFormatException e) {
                this.logger.error("Invalid 'projectId' request parameter: {}", projectIdParam, e);
            }
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(projectId));
        }

        return projectId;
    }
}
