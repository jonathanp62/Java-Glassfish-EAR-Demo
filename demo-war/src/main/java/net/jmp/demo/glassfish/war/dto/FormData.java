package net.jmp.demo.glassfish.war.dto;

/*
 * (#)FormData.java 0.2.0   07/12/2026
 *
 * @author   Jonathan Parker
 * @version  0.2.0
 * @since    0.2.0
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

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.jspecify.annotations.Nullable;

/// The form data class
public class FormData {
    /// The name
    @NotBlank(message = "{servlet.form.validation.required.name}")
    private final @Nullable String name;

    /// The email address
    @NotBlank(message = "{servlet.form.validation.required.email}")
    @Email(message = "{servlet.form.validation.email}")
    private final @Nullable String email;

    /// The comment
    @NotBlank(message = "{servlet.form.validation.required.comment}")
    @Size(min = 10, message = "{servlet.form.validation.comment}")
    private final @Nullable String comment;

    /// The constructor
    ///
    /// @param  name    java.lang.String
    /// @param  email   java.lang.String
    /// @param  comment java.lang.String
    public FormData(
            final @Nullable String name,
            final @Nullable String email,
            final @Nullable String comment) {
        super();

        this.name = name;
        this.email = email;
        this.comment = comment;
    }

    /// The get name method
    ///
    /// @return java.lang.String
    public @Nullable String getName() {
        return this.name;
    }

    /// The get email method
    ///
    /// @return java.lang.String
    public @Nullable String getEmail() {
        return this.email;
    }

    /// The get comment method
    ///
    /// @return java.lang.String
    public @Nullable String getComment() {
        return this.comment;
    }
}
