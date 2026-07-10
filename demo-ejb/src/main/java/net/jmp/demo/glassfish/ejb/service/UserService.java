package net.jmp.demo.glassfish.ejb.service;

/*
 * (#)UserService.java  0.2.0   07/10/2026
 *
 * @author    Jonathan Parker
 * @version   0.2.0
 * @since     0.2.0
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

import jakarta.annotation.Resource;

import jakarta.ejb.Stateless;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import net.jmp.demo.glassfish.ejb.dto.User;

import org.jspecify.annotations.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.jmp.util.logging.LoggerUtils.entryWith;
import static net.jmp.util.logging.LoggerUtils.exitWith;

/// The user service
@Stateless
public class UserService {
    // Initialize the SLF4J Logger
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /// The SQLite data source
    @Resource(lookup = "jdbc/sqlite")
    @SuppressWarnings("NullAway")
    private @Nullable DataSource dataSource;

    /// Default constructor
    /// It is required by Glassfish since
    /// there is a parameterized constructor.
    public UserService() {
        super();
    }

    /// Constructor for testing
    ///
    /// @param  dataSource  javax.sql.DataSource
    UserService(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /// Read all users for a project from the SQLite database
    ///
    /// @param  projectId   java.lang.Integer
    /// @return             java.util.List
    public List<User> getForProject(final Integer projectId) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(projectId));
        }

        final List<User> users = new ArrayList<>();
        final String sql = "SELECT user_id, first_name, last_name, email, age, role, project_id, created_at FROM users WHERE project_id = ?";

        if (this.dataSource == null) {
            this.logger.error("The data source is null");
        } else {
            try (final Connection connection = this.dataSource.getConnection();
                 final PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, projectId);

                try (final ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        final User user = new User();

                        user.setUserId(resultSet.getInt("user_id"));
                        user.setFirstName(resultSet.getString("first_name"));
                        user.setLastName(resultSet.getString("last_name"));
                        user.setEmail(resultSet.getString("email"));
                        user.setAge(resultSet.getInt("age"));
                        user.setRole(resultSet.getString("role"));
                        user.setProjectId(resultSet.getInt("project_id"));
                        user.setCreatedAt(resultSet.getTimestamp("created_at"));

                        users.add(user);
                    }
                }
            } catch (final SQLException e) {
                this.logger.error("Error reading users from database", e);
            }
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Read {} users for project {}", users.size(), projectId);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(users));
        }

        return users;
    }
}
