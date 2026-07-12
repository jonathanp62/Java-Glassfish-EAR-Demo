package net.jmp.demo.glassfish.ejb.dto;

/*
 * (#)Project.java  0.2.0   07/10/2026
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

/// The project representation that is stored in the database.
public class Project {
    /// The project identifier
    @Nullable
    private Integer projectId;

    /// The project name
    @Nullable
    private String projectName;

    /// The project status
    @Nullable
    private String status;

    /// The created timestamp
    @Nullable
    private Timestamp createdAt;

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

    /// Get the project name
    ///
    /// @return java.lang.String
    public @Nullable String getProjectName() {
        return this.projectName;
    }

    /// Set the project name
    ///
    /// @param  projectName  java.lang.String
    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    /// Get the project status
    ///
    /// @return java.lang.String
    public @Nullable String getStatus() {
        return this.status;
    }

    /// Set the project status
    ///
    /// @param  status  java.lang.String
    public void setStatus(final String status) {
        this.status = status;
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
