package net.jmp.demo.glassfish.war.web;

/*
 * (#)TestRegistrationServlet.java 0.2.0   07/11/2026
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

import jakarta.servlet.RequestDispatcher;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.ResourceBundle;
import java.util.Set;

import net.jmp.demo.glassfish.ejb.service.RegistrationService;
import net.jmp.demo.glassfish.war.dto.RegistrationData;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestRegistrationServlet {
    @Test
    void testDoGet() throws Exception {
        final ResourceBundle bundle = mock(ResourceBundle.class);
        final RegistrationService registrationService = mock(RegistrationService.class);
        final Validator validator = mock(Validator.class);

        final RegistrationServlet servlet = new RegistrationServlet(bundle, registrationService, validator);

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher("/WEB-INF/jsp/register.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoPostSuccess() throws Exception {
        final ResourceBundle bundle = mock(ResourceBundle.class);
        final RegistrationService registrationService = mock(RegistrationService.class);
        final Validator validator = mock(Validator.class);

        final RegistrationServlet servlet = new RegistrationServlet(bundle, registrationService, validator);

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter("email")).thenReturn("bob@example.com");
        when(validator.validate(any(RegistrationData.class))).thenReturn(Set.of());
        when(bundle.getString("servlet.registration.success")).thenReturn("Registered: {0}");
        when(request.getRequestDispatcher("/WEB-INF/jsp/registered.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setCharacterEncoding("UTF-8");
        verify(request).setAttribute("email", "bob@example.com");
        verify(registrationService).register("bob@example.com");
        verify(request).setAttribute("successMessage", "Registered: bob@example.com");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoPostValidationFailure() throws Exception {
        final ResourceBundle bundle = mock(ResourceBundle.class);
        final RegistrationService registrationService = mock(RegistrationService.class);
        final Validator validator = mock(Validator.class);

        final RegistrationServlet servlet = new RegistrationServlet(bundle, registrationService, validator);

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        @SuppressWarnings("unchecked")
        final ConstraintViolation<RegistrationData> violation = mock(ConstraintViolation.class);

        when(request.getParameter("email")).thenReturn("not-an-email");
        when(violation.getMessage()).thenReturn("Invalid email");
        when(validator.validate(any(RegistrationData.class))).thenReturn(Set.of(violation));
        when(request.getRequestDispatcher("/WEB-INF/jsp/registered.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setCharacterEncoding("UTF-8");
        verify(request).setAttribute("email", "not-an-email");
        verify(registrationService, never()).register(any(String.class));
        verify(request).setAttribute("errors", java.util.List.of("Invalid email"));
        verify(dispatcher).forward(request, response);
    }
}
