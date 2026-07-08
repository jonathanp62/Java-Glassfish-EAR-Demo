package net.jmp.demo.glassfish.war.security;

/*
 * (#)SecurityConfig.java   0.1.0   07/08/2026
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

import jakarta.enterprise.context.ApplicationScoped;

import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;

import jakarta.security.enterprise.identitystore.InMemoryIdentityStoreDefinition;

/// The application security configuration class
///
/// Found a way to test the security configuration
/// Using Safari, open a private window and navigate to a servlet
/// Once the authentication has been made, close the private window,
/// open a new one, then navigate to the same or a different servlet
///
@BasicAuthenticationMechanismDefinition(realmName = "demo-realm")
@InMemoryIdentityStoreDefinition({
        @InMemoryIdentityStoreDefinition.Credentials(
                callerName = "jonathan",
                password = "secret",
                groups = {"user"}
        )
})
@ApplicationScoped
public class SecurityConfig {
}
