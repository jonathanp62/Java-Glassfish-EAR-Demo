package net.jmp.demo.glassfish.ejb.dto;

/*
 * (#)User.java 0.2.0   07/10/2026
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

import java.sql.Timestamp;

import org.jspecify.annotations.Nullable;

/// The user representation that is stored in the database.
public class User {
    /// The user identifier
    @Nullable
    private Integer userId;

    /// The first name
    @Nullable
    private String firstName;

    /// The last name
    @Nullable
    private String lastName;

    /// The email address
    @Nullable
    private String email;

    /// The age
    @Nullable
    private Integer age;

    /// The role
    @Nullable
    private String role;

    /// The project identifier
    @Nullable
    private Integer projectId;

    /// The created timestamp
    @Nullable
    private Timestamp createdAt;

    /// Get the user identifier
    ///
    /// @return java.lang.Integer
    public @Nullable Integer getUserId() {
        return this.userId;
    }

    /// Set the user identifier
    ///
    /// @param  userId  java.lang.Integer
    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    /// Get the first name
    ///
    /// @return java.lang.String
    public @Nullable String getFirstName() {
        return this.firstName;
    }

    /// Set the first name
    ///
    /// @param  firstName   java.lang.String
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /// Get the last name
    ///
    /// @return java.lang.String
    public @Nullable String getLastName() {
        return this.lastName;
    }

    /// Set the last name
    ///
    /// @param  lastName    java.lang.String
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /// Get the email address
    ///
    /// @return java.lang.String
    public @Nullable String getEmail() {
        return this.email;
    }

    /// Set the email address
    ///
    /// @param  email   java.lang.String
    public void setEmail(final String email) {
        this.email = email;
    }

    /// Get the age
    ///
    /// @return java.lang.Integer
    public @Nullable Integer getAge() {
        return this.age;
    }

    /// Set the age
    ///
    /// @param  age java.lang.Integer
    public void setAge(final Integer age) {
        this.age = age;
    }

    /// Get the role
    ///
    /// @return java.lang.String
    public @Nullable String getRole() {
        return this.role;
    }

    /// Set the role
    ///
    /// @param  role    java.lang.String
    public void setRole(final String role) {
        this.role = role;
    }

    /// Get the project identifier
    ///
    /// @return java.lang.Integer
    public @Nullable Integer getProjectId() {
        return this.projectId;
    }

    /// Set the project identifier
    ///
    /// @param  projectId   java.lang.Integer
    public void setProjectId(final Integer projectId) {
        this.projectId = projectId;
    }

    /// Get the created timestamp
    ///
    /// @return java.sql.Timestamp
    public @Nullable Timestamp getCreatedAt() {
        return this.createdAt;
    }

    /// Set the created timestamp
    ///
    /// @param  createdAt   java.sql.Timestamp
    public void setCreatedAt(final Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
