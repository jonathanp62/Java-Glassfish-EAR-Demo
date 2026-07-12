package net.jmp.demo.glassfish.ejb.service;

/*
 * (#)CarService.java   0.2.0   07/09/2026
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

import jakarta.ejb.Stateless;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

import net.jmp.demo.glassfish.ejb.dto.Car;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.jmp.util.logging.LoggerUtils.entry;
import static net.jmp.util.logging.LoggerUtils.exitWith;

/// The car service
@Stateless
public class CarService {
    /// The entity manager
    @PersistenceContext(unitName = "DemoPU")
    @SuppressWarnings("NullAway")
    private EntityManager em;

    // Initialize the SLF4J Logger
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /// Default constructor
    /// It is required by Glassfish since
    /// there is a parameterized constructor.
    public CarService() {
        super();
    }

    /// Constructor for testing
    ///
    /// @param  em  jakarta.persistence.EntityManager
    CarService(final EntityManager em) {
        this.em = em;
    }

    /// Get all cars
    ///
    /// @return java.util.List<net.jmp.demo.glassfish.ejb.dto.Car>
    public List<Car> getAll() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<Car> results = this.em.createQuery("SELECT c FROM Car c", Car.class).getResultList();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(results));
        }

        return results;
    }
}
