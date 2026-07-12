package net.jmp.demo.glassfish.ejb.message;

/*
 * (#)RegistrationMessageBean.java  0.2.0   07/11/2026
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

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;

import jakarta.jms.JMSDestinationDefinition;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.jmp.util.logging.LoggerUtils.entryWith;
import static net.jmp.util.logging.LoggerUtils.exit;

/// The message-driven bean that handles registration messages
@JMSDestinationDefinition(
        name = "java:global/jms/DemoQueue",
        interfaceName = "jakarta.jms.Queue",
        destinationName = "DemoQueue"
)
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:global/jms/DemoQueue")
})
public class RegistrationMessageBean implements MessageListener {
    // Initialize the SLF4J Logger
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /// The default constructor
    public RegistrationMessageBean() {
        super();
    }

    /// Receives a JMS message from the demo queue
    ///
    /// @param  message jakarta.jms.Message
    @Override
    public void onMessage(final Message message) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(message));
        }

        if (message instanceof TextMessage textMessage) {
            try {
                final String text = textMessage.getText();

                if (this.logger.isInfoEnabled()) {
                    this.logger.info("Received registration message: {}", text);
                }
            } catch (final JMSException e) {
                throw new RuntimeException(e);
            }
        } else {
            if (this.logger.isWarnEnabled()) {
                this.logger.warn("Received unsupported message type: {}", message.getClass().getName());
            }
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
