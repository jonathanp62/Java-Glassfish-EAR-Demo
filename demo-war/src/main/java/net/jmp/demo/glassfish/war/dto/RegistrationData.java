package net.jmp.demo.glassfish.war.dto;

/*
 * (#)RegistrationData.java 0.2.0   07/11/2026
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

import org.jspecify.annotations.Nullable;

/// The registration form data class
public class RegistrationData {
    /// The email address
    @NotBlank(message = "{servlet.form.validation.required.email}")
    @Email(message = "{servlet.form.validation.email}")
    private final @Nullable String email;

    /// The constructor
    ///
    /// @param  email   java.lang.String
    public RegistrationData(final @Nullable String email) {
        super();

        this.email = email;
    }

    /// The get email method
    ///
    /// @return java.lang.String
    public @Nullable String getEmail() {
        return this.email;
    }
}
