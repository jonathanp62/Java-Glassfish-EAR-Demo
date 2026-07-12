package net.jmp.demo.glassfish.ejb.service;

/*
 * (#)TestProjectService.java 0.2.0   07/10/2026
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.List;

import javax.sql.DataSource;

import net.jmp.demo.glassfish.ejb.dto.Project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestProjectService {
    @Test
    void testGetAll() throws Exception {
        final DataSource dataSource = mock(DataSource.class);
        final Connection connection = mock(Connection.class);
        final PreparedStatement statement = mock(PreparedStatement.class);
        final ResultSet resultSet = mock(ResultSet.class);

        final Timestamp createdAt = new Timestamp(System.currentTimeMillis());

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement("SELECT project_id, project_name, status, created_at FROM projects")).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("project_id")).thenReturn(1);
        when(resultSet.getString("project_name")).thenReturn("Project Alpha");
        when(resultSet.getString("status")).thenReturn("active");
        when(resultSet.getTimestamp("created_at")).thenReturn(createdAt);

        final ProjectService service = new ProjectService(dataSource);
        final List<Project> results = service.getAll();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(Integer.valueOf(1), results.get(0).getProjectId());
        assertEquals("Project Alpha", results.get(0).getProjectName());
        assertEquals("active", results.get(0).getStatus());
        assertEquals(createdAt, results.get(0).getCreatedAt());

        verify(resultSet).close();
        verify(statement).close();
        verify(connection).close();
    }

    @Test
    void testGetAllWithEmptyResult() throws Exception {
        final DataSource dataSource = mock(DataSource.class);
        final Connection connection = mock(Connection.class);
        final PreparedStatement statement = mock(PreparedStatement.class);
        final ResultSet resultSet = mock(ResultSet.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement("SELECT project_id, project_name, status, created_at FROM projects")).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        final ProjectService service = new ProjectService(dataSource);
        final List<Project> results = service.getAll();

        assertNotNull(results);
        assertEquals(0, results.size());
    }
}
