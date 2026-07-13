package net.jmp.demo.glassfish.ejb.config;

/*
 * (#)MongoDbConfig.java    0.3.0   07/12/2026
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

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;

import jakarta.enterprise.event.Observes;

import jakarta.enterprise.inject.Produces;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jspecify.annotations.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// The configuration class for MongoDB
@ApplicationScoped
public class MongoDbConfig {
    /// The JNDI lookup for the MongoDB connection string
    @Resource(lookup = "jndi/mongoURI")
    @SuppressWarnings("NullAway")
    private String mongoConnectionUri;

    /// The MongoDB client
    private @Nullable MongoClient mongoClient;

    // Initialize the SLF4J Logger
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /// The observer method for the application startup event
    ///
    /// @param  event   java.lang.Object
    void onStartup(@Observes @Initialized(ApplicationScoped.class) final Object event) {
        this.logger.info("MongoDB configuration started");
    }

    /// The post-construct method for the application startup event.
    /// It runs after the dependency injection has been completed.
    @PostConstruct
    @SuppressWarnings("BanJNDI")
    void init() {
        String connectionUri = this.mongoConnectionUri;

        if (connectionUri == null || connectionUri.isBlank()) {
            final String name = "jndi/mongoURI";

            try {
                final Object value = InitialContext.doLookup(name);

                if (value instanceof String s && !s.isBlank()) {
                    connectionUri = s;
                    this.logger.info("MongoDB connection URI resolved via JNDI lookup '{}'", name);
                }

                if (value != null) {
                    this.logger.warn("JNDI lookup '{}' returned non-String type: {}", name, value.getClass().getName());
                }
            } catch (final NamingException ex) {
                this.logger.debug("JNDI lookup '{}' failed: {}", name, ex.getMessage());
            }
        }

        if (connectionUri == null || connectionUri.isBlank()) {
            connectionUri = System.getProperty("mongodb.uri");
        }

        if (connectionUri == null || connectionUri.isBlank()) {
            this.logger.error("MongoDB connection URI not found. Ensure JNDI 'jndi/mongoURI' is injectable or set system property 'mongodb.uri'. MongoDB client will not be available.");
            return;
        }

        this.mongoClient = MongoClients.create(connectionUri);

        this.logger.info("MongoDB client created");

        try {
            this.mongoClient.getDatabase("admin").runCommand(new Document("ping", 1));
            this.logger.info("MongoDB pinged OK");
        } catch (final Exception e) {
            this.logger.warn("MongoDB ping failed: {}", e.getMessage());
        }
    }

    /// The pre-destroy method for the application shutdown event.
    /// Releases the MongoDB client when the application is shutting down.
    @PreDestroy
    void destroy() {
        if (this.mongoClient != null) {
            this.mongoClient.close();
            this.logger.info("MongoDB client closed");
        }
    }

    /// The producer method for the MongoDB client
    ///
    /// @return com.mongodb.client.MongoClient
    @Produces
    @ApplicationScoped
    public MongoClient getMongoClient() {
        if (this.mongoClient == null) {
            throw new IllegalStateException("MongoClient is null");
        }

        return this.mongoClient;
    }

    /// The MongoDB database
    ///
    /// @return com.mongodb.client.MongoDatabase
    @Produces
    @ApplicationScoped
    public MongoDatabase getDatabase() {
        if (this.mongoClient == null) {
            throw new IllegalStateException("MongoClient is null");
        }

        final String databaseName = System.getProperty("mongodb.database", "react_learning");

        return this.mongoClient.getDatabase(databaseName);
    }
}
