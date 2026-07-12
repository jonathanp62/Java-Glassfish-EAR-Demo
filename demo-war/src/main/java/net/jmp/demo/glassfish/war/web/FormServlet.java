package net.jmp.demo.glassfish.war.web;

/*
 * (#)FormServlet.java  0.2.0   07/12/2026
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

import jakarta.inject.Inject;
import jakarta.inject.Named;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.io.IOException;

import java.io.UnsupportedEncodingException;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import net.jmp.demo.glassfish.ejb.dto.Person;

import net.jmp.demo.glassfish.ejb.service.PeopleService;

import net.jmp.demo.glassfish.war.dto.FormData;

import net.jmp.demo.glassfish.war.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.jmp.util.logging.LoggerUtils.*;

/// The form servlet class
@WebServlet(urlPatterns = "/servlet/form")
@DeclareRoles("user")
@ServletSecurity(@HttpConstraint(rolesAllowed = "user"))
public class FormServlet extends HttpServlet {
    // Initialize the SLF4J Logger
    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    /// The messages resource bundle
    private final transient ResourceBundle bundle;

    /// The input validator
    private final transient Validator validator;

    /// The people service
    @EJB
    @SuppressWarnings("NullAway")
    private transient PeopleService peopleService;

    /// The form JSP
    private static final String FORM_JSP = "/WEB-INF/jsp/form.jsp";

    /// The constructor
    ///
    /// @param  bundle  java.util.ResourceBundle
    @Inject
    public FormServlet(@Named("messages") final ResourceBundle bundle) {
        super();

        this.bundle = bundle;

        try (final ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            this.validator = factory.getValidator();
        }
    }

    /// Constructor for testing
    ///
    /// @param  bundle          java.util.ResourceBundle
    /// @param  peopleService   net.jmp.demo.glassfish.ejb.service.PeopleService
    /// @param  validator       jakarta.validation.Validator
    FormServlet(final ResourceBundle bundle, final PeopleService peopleService, final Validator validator) {
        super();

        this.bundle = bundle;
        this.peopleService = peopleService;
        this.validator = validator;
    }

    /// The GET method. Called from /servlet/form.
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

        request.getRequestDispatcher(FORM_JSP).forward(request, response);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// The POST method
    ///
    /// @param  request     jakarta.servlet.http.HttpServletRequest
    /// @param  response    jakarta.servlet.http.HttpServletResponse
    /// @throws             jakarta.servlet.ServletException When an error occurs
    /// @throws             java.io.IOException When an I/O error occurs
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(request, response));
        }

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (final UnsupportedEncodingException uee) {
            this.logger.error("Error setting character encoding to UTF-8", uee);
            throw new ServletException(uee);
        }

        final String name = StringUtils.trimToNull(request.getParameter("name"));
        final String email = StringUtils.trimToNull(request.getParameter("email"));
        final String comment = StringUtils.trimToNull(request.getParameter("comment"));

        final FormData formData = new FormData(name, email, comment);
        final Set<ConstraintViolation<FormData>> violations = this.validator.validate(formData);

        request.setAttribute("name", name);
        request.setAttribute("email", email);
        request.setAttribute("comment", comment);

        if (violations.isEmpty()) {
            request.setAttribute("successMessage", this.bundle.getString("servlet.form.success"));

            final Person person = new Person();

            if (comment != null) {
                person.setComment(comment);
            }

            if (email != null) {
                person.setEmail(email);
            }

            if (name != null) {
                person.setName(name);
            }

            this.peopleService.save(person);
        } else {
            final List<String> errors = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .toList();

            request.setAttribute("errors", errors);
        }

        request.getRequestDispatcher(FORM_JSP).forward(request, response);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
