package net.jmp.demo.glassfish.ejb.service;

/*
 * (#)TestPeopleService.java 0.2.0   07/10/2026
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

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import net.jmp.demo.glassfish.ejb.dto.Person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestPeopleService {
    @Test
    void testGetAll() {
        final EntityManager em = mock(EntityManager.class);
        final TypedQuery<Person> query = mock(TypedQuery.class);
        final Person person = new Person();

        person.setId(1L);
        person.setName("John");
        person.setEmail("john@example.com");
        person.setComment("A comment");

        when(em.createQuery("SELECT p FROM Person p", Person.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(person));

        final PeopleService service = new PeopleService(em);
        final List<Person> results = service.getAll();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(Long.valueOf(1L), results.get(0).getId());
        assertEquals("John", results.get(0).getName());
        assertEquals("john@example.com", results.get(0).getEmail());
        assertEquals("A comment", results.get(0).getComment());
    }

    @Test
    void testGetAllWithEmptyResult() {
        final EntityManager em = mock(EntityManager.class);
        final TypedQuery<Person> query = mock(TypedQuery.class);

        when(em.createQuery("SELECT p FROM Person p", Person.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of());

        final PeopleService service = new PeopleService(em);
        final List<Person> results = service.getAll();

        assertNotNull(results);
        assertEquals(0, results.size());
    }

    @Test
    void testSaveNewPerson() {
        final EntityManager em = mock(EntityManager.class);
        final Person person = new Person();

        person.setName("John");
        person.setEmail("john@example.com");
        person.setComment("A comment");

        final PeopleService service = new PeopleService(em);
        final Person result = service.save(person);

        assertNotNull(result);
        assertEquals(person, result);
        assertEquals("John", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("A comment", result.getComment());

        verify(em).persist(person);
    }

    @Test
    void testSaveExistingPerson() {
        final EntityManager em = mock(EntityManager.class);
        final Person person = new Person();

        person.setId(1L);
        person.setName("John");
        person.setEmail("john@example.com");
        person.setComment("A comment");

        final Person mergedPerson = new Person();

        mergedPerson.setId(1L);
        mergedPerson.setName("John");
        mergedPerson.setEmail("john@example.com");
        mergedPerson.setComment("A comment");

        when(em.merge(person)).thenReturn(mergedPerson);

        final PeopleService service = new PeopleService(em);
        final Person result = service.save(person);

        assertNotNull(result);
        assertEquals(mergedPerson, result);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals("John", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("A comment", result.getComment());
    }
}
