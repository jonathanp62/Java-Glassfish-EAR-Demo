package net.jmp.demo.glassfish.ejb.service;

/*
 * (#)RegistrationService.java  0.2.0   07/11/2026
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

import jakarta.annotation.Resource;

import jakarta.enterprise.context.ApplicationScoped;

import jakarta.inject.Inject;

import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.jmp.util.logging.LoggerUtils.entryWith;
import static net.jmp.util.logging.LoggerUtils.exit;

/// The registration service
@ApplicationScoped
public class RegistrationService {
    // Initialize the SLF4J Logger
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /// The JMS context
    @Inject
    @SuppressWarnings("NullAway")
    private JMSContext jmsContext;

    /// The JMS queue
    @Resource(lookup = "jms/DemoQueue")
    @SuppressWarnings("NullAway")
    private Queue demoQueue;

    /// The register method
    ///
    /// @param  email   java.lang.String
    public void register(final String email) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(email));
        }

        final String payload = "Registered: " + email;

        this.jmsContext.createProducer().send(this.demoQueue, payload);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
