package net.jmp.demo.glassfish.war.web;

/*
 * (#)UsersServlet.java 0.4.0   07/25/2026
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

import java.util.HashMap;
import java.util.Map;

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

    /// The users JSF
    private static final String USERS_JSF = "/WEB-INF/jsf/users.xhtml";

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
            request.getRequestDispatcher(USERS_JSF).forward(request, response);
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

        /*
         * Create a registry of project ID handlers
         * This is for demonstration only and is quite
         * inappropriate for a production application
         */

        final ProjectIdRegistry registry = new ProjectIdRegistry();

        registry.register("Null", new NullProjectIdHandler());
        registry.register("Blank", new BlankProjectIdHandler());
        registry.register("String", new StringProjectIdHandler());

        Integer projectId;

        final String projectIdParam = request.getParameter("projectId");

        if (projectIdParam == null) {
            projectId = registry.getHandler("Null").handle(projectIdParam, this.logger);
        } else if (projectIdParam.isBlank()) {
            projectId = registry.getHandler("Blank").handle(projectIdParam, this.logger);
        } else {
            projectId = registry.getHandler("String").handle(projectIdParam, this.logger);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(projectId));
        }

        return projectId;
    }

    /// The project ID handler interface
    interface ProjectIdHandler {
        /// Handle the project ID
        ///
        /// @param  projectId java.lang.String
        /// @param  logger    org.slf4j.Logger
        /// @return           java.lang.Integer
        @Nullable Integer handle(final @Nullable String projectId, final Logger logger);
    }

    /// The null project ID handler
    static class NullProjectIdHandler implements ProjectIdHandler {
        /// Handle a null project ID
        ///
        /// @param  projectId java.lang.String
        /// @param  logger    org.slf4j.Logger
        /// @return           java.lang.Integer
        public @Nullable Integer handle(final @Nullable String projectId, final Logger logger) {
            logger.error("Required request parameter 'projectId' is missing");

            return null;
        }
    }

    /// The blank project ID handler
    static class BlankProjectIdHandler implements ProjectIdHandler {
        /// Handle a blank project ID
        ///
        /// @param  projectId java.lang.String
        /// @param  logger    org.slf4j.Logger
        /// @return           java.lang.Integer
        public @Nullable Integer handle(final @Nullable String projectId, final Logger logger) {
            logger.error("Required request parameter 'projectId' is blank");

            return null;
        }
    }

    /// The string project ID handler
    static class StringProjectIdHandler implements ProjectIdHandler {
        /// Handle a string project ID
        ///
        /// @param  projectId java.lang.String
        /// @param  logger    org.slf4j.Logger
        /// @return           java.lang.Integer
        public @Nullable Integer handle(final @Nullable String projectId, final Logger logger) {
            Integer value = null;

            if (projectId == null) {
                logger.error("'projectId' request parameter is null");
                return null;
            }

            try {
                value = Integer.valueOf(projectId);
            } catch (final NumberFormatException e) {
                logger.error("Invalid 'projectId' request parameter: {}", projectId, e);
            }

            return value;
        }
    }

    /// The project ID registry
    static class ProjectIdRegistry {
        /// The handlers
        private final Map<String, ProjectIdHandler> handlers = new HashMap<>();

        /// Register a type with a hdnler
        ///
        /// @param  type    java.lang.String
        /// @param  handler net.jmp.demo.glassfish.war.web.UsersServlet.ProjectIdHandler
        public void register(final String type, final ProjectIdHandler handler) {
            handlers.put(type.toUpperCase(), handler);
        }

        /// Get a handler by type
        ///
        /// @param  type    java.lang.String
        /// @return         net.jmp.demo.glassfish.war.web.UsersServlet.ProjectIdHandler
        public ProjectIdHandler getHandler(final String type) {
            ProjectIdHandler handler = handlers.get(type.toUpperCase());

            if (handler == null) {
                throw new IllegalArgumentException("Unsupported type: " + type);
            }

            return handler;
        }
    }
}
