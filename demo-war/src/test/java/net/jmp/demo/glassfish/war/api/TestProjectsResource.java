package net.jmp.demo.glassfish.war.api;

/*
 * (#)TestProjectsResource.java 0.5.0   08/20/2026
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

import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

import net.jmp.demo.glassfish.ejb.dto.Project;
import net.jmp.demo.glassfish.ejb.dto.User;

import net.jmp.demo.glassfish.ejb.service.ProjectService;
import net.jmp.demo.glassfish.ejb.service.UserService;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestProjectsResource {
    @Mock
    private ProjectService projectService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProjectsResource projectsResource;

    @Test
    void testProjects() {
        final Project project = new Project();

        project.setProjectId(1);
        project.setProjectName("Demo Project");
        project.setStatus("Active");

        final List<Project> projects = List.of(project);

        when(this.projectService.getAll()).thenReturn(projects);

        final Response.ResponseBuilder builder = mock(Response.ResponseBuilder.class);
        final Response expectedResponse = mock(Response.class);

        when(builder.build()).thenReturn(expectedResponse);
        when(expectedResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(expectedResponse.getEntity()).thenReturn(projects);

        try (final MockedStatic<Response> mockedResponse = mockStatic(Response.class)) {
            mockedResponse.when(() -> Response.ok(projects)).thenReturn(builder);

            final Response response = this.projectsResource.projects();

            assertNotNull(response);
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(projects, response.getEntity());
        }
    }

    @Test
    void testProjectsWithEmptyResult() {
        final List<Project> projects = List.of();

        when(this.projectService.getAll()).thenReturn(projects);

        final Response.ResponseBuilder builder = mock(Response.ResponseBuilder.class);
        final Response expectedResponse = mock(Response.class);

        when(builder.build()).thenReturn(expectedResponse);
        when(expectedResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(expectedResponse.getEntity()).thenReturn(projects);

        try (final MockedStatic<Response> mockedResponse = mockStatic(Response.class)) {
            mockedResponse.when(() -> Response.ok(projects)).thenReturn(builder);

            final Response response = this.projectsResource.projects();

            assertNotNull(response);
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(0, ((List<?>) response.getEntity()).size());
        }
    }

    @Test
    void testProjectByIdFound() {
        final Project project = new Project();

        project.setProjectId(1);
        project.setProjectName("Demo Project");
        project.setStatus("Active");

        when(this.projectService.getByProjectId(1)).thenReturn(Optional.of(project));

        final Response.ResponseBuilder builder = mock(Response.ResponseBuilder.class);
        final Response expectedResponse = mock(Response.class);

        when(builder.build()).thenReturn(expectedResponse);
        when(expectedResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(expectedResponse.getEntity()).thenReturn(project);

        try (final MockedStatic<Response> mockedResponse = mockStatic(Response.class)) {
            mockedResponse.when(() -> Response.ok(project)).thenReturn(builder);

            final Response response = this.projectsResource.projectById("1");

            assertNotNull(response);
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(project, response.getEntity());
        }
    }

    @Test
    void testProjectByIdNotFound() {
        when(this.projectService.getByProjectId(99)).thenReturn(Optional.empty());

        final Response.ResponseBuilder builder = mock(Response.ResponseBuilder.class);
        final Response expectedResponse = mock(Response.class);

        when(builder.build()).thenReturn(expectedResponse);
        when(expectedResponse.getStatus()).thenReturn(Response.Status.NOT_FOUND.getStatusCode());

        try (final MockedStatic<Response> mockedResponse = mockStatic(Response.class)) {
            mockedResponse.when(() -> Response.status(Response.Status.NOT_FOUND)).thenReturn(builder);

            final Response response = this.projectsResource.projectById("99");

            assertNotNull(response);
            assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        }
    }

    @Test
    void testProjectUsersById() {
        final User user = new User();

        user.setUserId(1);
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.com");
        user.setRole("Developer");
        user.setProjectId(1);

        final List<User> users = List.of(user);

        when(this.userService.getForProject(1)).thenReturn(users);

        final Response.ResponseBuilder builder = mock(Response.ResponseBuilder.class);
        final Response expectedResponse = mock(Response.class);

        when(builder.build()).thenReturn(expectedResponse);
        when(expectedResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(expectedResponse.getEntity()).thenReturn(users);

        try (final MockedStatic<Response> mockedResponse = mockStatic(Response.class)) {
            mockedResponse.when(() -> Response.ok(users)).thenReturn(builder);

            final Response response = this.projectsResource.projectUsersById("1");

            assertNotNull(response);
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(users, response.getEntity());
        }
    }

    @Test
    void testProjectUsersByIdWithEmptyResult() {
        final List<User> users = List.of();

        when(this.userService.getForProject(99)).thenReturn(users);

        final Response.ResponseBuilder builder = mock(Response.ResponseBuilder.class);
        final Response expectedResponse = mock(Response.class);

        when(builder.build()).thenReturn(expectedResponse);
        when(expectedResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(expectedResponse.getEntity()).thenReturn(users);

        try (final MockedStatic<Response> mockedResponse = mockStatic(Response.class)) {
            mockedResponse.when(() -> Response.ok(users)).thenReturn(builder);

            final Response response = this.projectsResource.projectUsersById("99");

            assertNotNull(response);
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(0, ((List<?>) response.getEntity()).size());
        }
    }
}
