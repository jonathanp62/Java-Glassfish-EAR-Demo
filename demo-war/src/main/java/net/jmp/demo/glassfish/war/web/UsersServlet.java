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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

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

        final @Nullable Integer projectId = this.readProjectId(request);

        if (projectId != null) {
            request.setAttribute("projectId", projectId);
            request.setAttribute("users", this.userService.getForProject(projectId));
            request.getRequestDispatcher(USERS_JSF).forward(request, response);
        } else {
            throw new ServletException("Project ID is null, blank or invalid");
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

        // Registry taking a String input and returning an Integer

        final EvaluationRegistry<String, Integer> registry = new EvaluationRegistry<>();

        // Register the rules

        registry.registerRule(
                val -> val == null,
                val -> {
                    this.logger.error("Required request parameter 'projectId' is missing");

                    return null;
                }
        );

        registry.registerRule(
                String::isBlank,
                val -> {
                    logger.error("Required request parameter 'projectId' is blank");

                    return null;
                }
        );

        registry.registerRule(
                val -> !val.isBlank(),
                val -> {
                    Integer value = null;

                    try {
                        value = Integer.valueOf(val);
                    } catch (final NumberFormatException e) {
                        logger.error("Invalid 'projectId' request parameter: {}", val, e);
                    }

                    return value;
                }
        );

        final Integer projectId = registry.evaluate(request.getParameter("projectId"));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(projectId));
        }

        return projectId;
    }

    /// The evaluation registry class
    ///
    /// @param  <T>   The type of the input
    /// @param  <R>   The type of the output
    static class EvaluationRegistry<T, R> {
        // Maps a Condition (Predicate) -> Action that returns a value (Function)
        private final Map<Predicate<T>, Function<T, @Nullable R>> rules = new LinkedHashMap<>();

        /// Register a rule: "If condition applies to input T, execute function and return R"
        ///
        /// @param  condition   java.util.function.Predicate<T>
        /// @param  action      java.util.function.Function<T, R>
        public void registerRule(Predicate<T> condition, Function<T, @Nullable R> action) {
            rules.put(condition, action);
        }

        /// Evaluate input against rules and return the resulting value
        ///
        /// @param  input   T
        /// @return         R
        public @Nullable R evaluate(final T input) {
            for (final Map.Entry<Predicate<T>, Function<T, @Nullable R>> entry : rules.entrySet()) {
                if (entry.getKey().test(input)) {
                    return entry.getValue().apply(input); // Return the calculated result
                }
            }

            throw new IllegalArgumentException("No matching rule for input: " + input);
        }
    }
}
