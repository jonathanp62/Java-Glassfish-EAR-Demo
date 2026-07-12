package net.jmp.demo.glassfish.ejb.dto;

/*
 * (#)Person.java  0.2.0   07/10/2026
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

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;

import org.jspecify.annotations.Nullable;

/// The person entity that is stored in the database.
@Entity
@Table(name = "people")
public class Person implements Serializable {
    /// The serialization identifier
    private static final long serialVersionUID = 1L;

    /// The identifier
    @Id
    @TableGenerator(name = "people_gen", table = "id_gen", pkColumnName = "gen_name", valueColumnName = "gen_val", pkColumnValue = "people_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "people_gen")
    @Column(name = "id")
    @Nullable
    private Long id;

    /// The name
    @Column(name = "name")
    @Nullable
    private String name;

    /// The email
    @Column(name = "email")
    @Nullable
    private String email;

    /// The comment
    @Column(name = "comment")
    @Nullable
    private String comment;

    /// The default constructor
    public Person() {
        super();
    }

    /// Get the identifier
    ///
    /// @return java.lang.Long
    public @Nullable Long getId() {
        return this.id;
    }

    /// Set the identifier
    ///
    /// @param  id  java.lang.Long
    public void setId(final Long id) {
        this.id = id;
    }

    /// Get the name
    ///
    /// @return java.lang.String
    public @Nullable String getName() {
        return this.name;
    }

    /// Set the name
    ///
    /// @param  name  java.lang.String
    public void setName(final String name) {
        this.name = name;
    }

    /// Get the email
    ///
    /// @return java.lang.String
    public @Nullable String getEmail() {
        return this.email;
    }

    /// Set the email
    ///
    /// @param  email  java.lang.String
    public void setEmail(final String email) {
        this.email = email;
    }

    /// Get the comment
    ///
    /// @return java.lang.String
    public @Nullable String getComment() {
        return this.comment;
    }

    /// Set the comment
    ///
    /// @param  comment  java.lang.String
    public void setComment(final String comment) {
        this.comment = comment;
    }
}
