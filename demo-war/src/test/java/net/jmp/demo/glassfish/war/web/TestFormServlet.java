package net.jmp.demo.glassfish.war.web;

/*
 * (#)TestFormServlet.java    0.2.0   07/12/2026
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

import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import net.jmp.demo.glassfish.ejb.dto.Person;
import net.jmp.demo.glassfish.ejb.service.PeopleService;
import net.jmp.demo.glassfish.war.dto.FormData;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestFormServlet {
    @Test
    void testDoGet() throws Exception {
        final ResourceBundle bundle = mock(ResourceBundle.class);
        final PeopleService peopleService = mock(PeopleService.class);
        final Validator validator = mock(Validator.class);

        final FormServlet servlet = new FormServlet(bundle, peopleService, validator);

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher("/WEB-INF/jsp/form.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoPostSuccess() throws Exception {
        final ResourceBundle bundle = mock(ResourceBundle.class);
        final PeopleService peopleService = mock(PeopleService.class);
        final Validator validator = mock(Validator.class);

        final FormServlet servlet = new FormServlet(bundle, peopleService, validator);

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter("name")).thenReturn("John Doe");
        when(request.getParameter("email")).thenReturn("john.doe@example.com");
        when(request.getParameter("comment")).thenReturn("This is a comment that is long enough");
        when(validator.validate(any(FormData.class))).thenReturn(Set.of());
        when(bundle.getString("servlet.form.success")).thenReturn("Form submitted successfully");
        when(request.getRequestDispatcher("/WEB-INF/jsp/form.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setCharacterEncoding("UTF-8");
        verify(request).setAttribute("name", "John Doe");
        verify(request).setAttribute("email", "john.doe@example.com");
        verify(request).setAttribute("comment", "This is a comment that is long enough");
        verify(request).setAttribute("successMessage", "Form submitted successfully");
        verify(peopleService).save(argThat(person ->
                "John Doe".equals(person.getName())
                        && "john.doe@example.com".equals(person.getEmail())
                        && "This is a comment that is long enough".equals(person.getComment())));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoPostValidationFailure() throws Exception {
        final ResourceBundle bundle = mock(ResourceBundle.class);
        final PeopleService peopleService = mock(PeopleService.class);
        final Validator validator = mock(Validator.class);

        final FormServlet servlet = new FormServlet(bundle, peopleService, validator);

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        @SuppressWarnings("unchecked")
        final ConstraintViolation<FormData> violation = mock(ConstraintViolation.class);

        when(request.getParameter("name")).thenReturn("John Doe");
        when(request.getParameter("email")).thenReturn("not-an-email");
        when(request.getParameter("comment")).thenReturn("short");
        when(violation.getMessage()).thenReturn("Invalid email");
        when(validator.validate(any(FormData.class))).thenReturn(Set.of(violation));
        when(request.getRequestDispatcher("/WEB-INF/jsp/form.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setCharacterEncoding("UTF-8");
        verify(request).setAttribute("name", "John Doe");
        verify(request).setAttribute("email", "not-an-email");
        verify(request).setAttribute("comment", "short");
        verify(peopleService, never()).save(any(Person.class));
        verify(request).setAttribute("errors", List.of("Invalid email"));
        verify(dispatcher).forward(request, response);
    }
}
