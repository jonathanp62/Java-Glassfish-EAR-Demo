package net.jmp.demo.glassfish.ejb.service;

/*
 * (#)TestDistanceService.java  0.3.0   07/12/2026
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

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.util.List;

import net.jmp.demo.glassfish.ejb.dto.DistanceData;

import org.bson.Document;
import org.bson.types.ObjectId;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DistanceServiceTest {
    @Mock
    private MongoDatabase mongoDatabase;

    @Test
    void testGetAllMapsDocuments() {
        @SuppressWarnings("unchecked")
        final MongoCollection<Document> collection = (MongoCollection<Document>) mock(MongoCollection.class);

        @SuppressWarnings("unchecked")
        final FindIterable<Document> iterable = (FindIterable<Document>) mock(FindIterable.class);

        @SuppressWarnings("unchecked")
        final MongoCursor<Document> cursor = (MongoCursor<Document>) mock(MongoCursor.class);

        final ObjectId id1 = new ObjectId();

        final Document doc1 = new Document()
                .append("_id", id1)
                .append("fromZipCode", "10001")
                .append("toZipCode", "90210")
                .append("toCity", "Beverly Hills")
                .append("toState", "CA")
                .append("distanceInMiles", 10.5d)
                .append("distanceInKilometers", 16.9d);

        final Document doc2 = new Document()
                .append("fromZipCode", "30301")
                .append("toZipCode", "33101")
                .append("toCity", "Miami")
                .append("toState", "FL");

        when(this.mongoDatabase.getCollection("distance")).thenReturn(collection);
        when(collection.find()).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(cursor);

        when(cursor.hasNext()).thenReturn(true, true, false);
        when(cursor.next()).thenReturn(doc1, doc2);

        final DistanceService service = new DistanceService(this.mongoDatabase);
        final List<DistanceData> result = service.getAll();

        assertEquals(2, result.size());

        final DistanceData first = result.getFirst();
        assertEquals(id1.toHexString(), first.getDocumentId());
        assertEquals("10001", first.getFromZipCode());
        assertEquals("90210", first.getToZipCode());
        assertEquals("Beverly Hills", first.getToCity());
        assertEquals("CA", first.getToState());
        assertEquals(10.5d, first.getDistanceInMiles());
        assertEquals(16.9d, first.getDistanceInKilometers());

        final DistanceData second = result.get(1);
        assertNull(second.getDocumentId());
        assertEquals("30301", second.getFromZipCode());
        assertEquals("33101", second.getToZipCode());
        assertEquals("Miami", second.getToCity());
        assertEquals("FL", second.getToState());
        assertEquals(0.0d, second.getDistanceInMiles());
        assertEquals(0.0d, second.getDistanceInKilometers());
    }
}
