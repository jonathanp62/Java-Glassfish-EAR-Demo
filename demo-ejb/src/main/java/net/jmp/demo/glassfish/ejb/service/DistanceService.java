package net.jmp.demo.glassfish.ejb.service;

/*
 * (#)DistanceService.java  0.3.0   07/12/2026
 *
 * @author    Jonathan Parker
 * @version   0.3.0
 * @since     0.3.0
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

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import jakarta.enterprise.context.ApplicationScoped;

import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import net.jmp.demo.glassfish.ejb.dto.DistanceData;

import org.bson.Document;
import org.bson.types.ObjectId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.jmp.util.logging.LoggerUtils.entry;
import static net.jmp.util.logging.LoggerUtils.exitWith;

/// The distance service
@ApplicationScoped
public class DistanceService {
    // Initialize the SLF4J Logger
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /// The MongoDB database
    @Inject
    @SuppressWarnings("NullAway")
    private MongoDatabase mongoDatabase;

    /// Default constructor
    /// It is required since there is a parameterized constructor.
    public DistanceService() {
        super();
    }

    /// Constructor for testing
    ///
    /// @param  mongoDatabase   com.mongodb.client.MongoDatabase
    DistanceService(final MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    /// Get all distance documents from the MongoDB database
    ///
    /// @return java.util.List
    public List<DistanceData> getAll() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<DistanceData> distances = new ArrayList<>();
        final MongoCollection<Document> collection = this.mongoDatabase.getCollection("distance");

        for (final Document doc : collection.find()) {
            final DistanceData data = new DistanceData();
            final Object id = doc.get("_id");

            if (id instanceof ObjectId objectId) {
                data.setDocumentId(objectId.toHexString());
            }

            data.setFromZipCode(doc.getString("fromZipCode"));
            data.setToZipCode(doc.getString("toZipCode"));
            data.setToCity(doc.getString("toCity"));
            data.setToState(doc.getString("toState"));

            final Double miles = doc.getDouble("distanceInMiles");

            if (miles != null) {
                data.setDistanceInMiles(miles);
            }

            final Double kilometers = doc.getDouble("distanceInKilometers");

            if (kilometers != null) {
                data.setDistanceInKilometers(kilometers);
            }

            distances.add(data);
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Read {} distance documents", distances.size());
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(distances));
        }

        return distances;
    }
}
