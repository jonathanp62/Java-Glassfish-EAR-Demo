package net.jmp.demo.glassfish.ejb.service;

/*
 * (#)TestRegistrationService.java 0.2.0   07/11/2026
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

import java.lang.reflect.Field;

import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestRegistrationService {
    private static void setJmsContext(final RegistrationService service, final JMSContext jmsContext) throws Exception {
        final Field field = RegistrationService.class.getDeclaredField("jmsContext");

        field.setAccessible(true);
        field.set(service, jmsContext);
    }

    private static void setDemoQueue(final RegistrationService service, final Queue demoQueue) throws Exception {
        final Field field = RegistrationService.class.getDeclaredField("demoQueue");

        field.setAccessible(true);
        field.set(service, demoQueue);
    }

    @Test
    void testRegister() throws Exception {
        final RegistrationService service = new RegistrationService();
        final JMSContext jmsContext = mock(JMSContext.class);
        final JMSProducer producer = mock(JMSProducer.class);
        final Queue demoQueue = mock(Queue.class);

        when(jmsContext.createProducer()).thenReturn(producer);

        setJmsContext(service, jmsContext);
        setDemoQueue(service, demoQueue);

        service.register("bob@example.com");

        verify(producer).send(demoQueue, "Registered: bob@example.com");
    }

    @Test
    void testRegisterWithNullEmail() throws Exception {
        final RegistrationService service = new RegistrationService();
        final JMSContext jmsContext = mock(JMSContext.class);
        final JMSProducer producer = mock(JMSProducer.class);
        final Queue demoQueue = mock(Queue.class);

        when(jmsContext.createProducer()).thenReturn(producer);

        setJmsContext(service, jmsContext);
        setDemoQueue(service, demoQueue);

        service.register(null);

        verify(producer).send(demoQueue, "Registered: null");
    }
}
