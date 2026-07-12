package net.jmp.demo.glassfish.ejb.dto;

/*
 * (#)Car.java  0.2.0   07/09/2026
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

import org.jspecify.annotations.Nullable;

/// The car entity that is stored in the database.
@Entity
@Table(name = "cars")
public class Car implements Serializable {
    /// The serialization identifier
    private static final long serialVersionUID = 1L;

    /// The identifier
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Nullable
    private Long id;

    /// The year
    @Column(name = "year")
    @Nullable
    private Integer year;

    /// The make
    @Column(name = "make")
    @Nullable
    private String make;

    /// The model
    @Column(name = "model")
    @Nullable
    private String model;

    /// The color
    @Column(name = "color")
    @Nullable
    private String color;

    /// The style
    @Column(name = "style")
    @Nullable
    private String style;

    /// The default constructor
    public Car() {
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

    /// Get the year
    ///
    /// @return java.lang.Integer
    public @Nullable Integer getYear() {
        return this.year;
    }

    /// Set the year
    ///
    /// @param  year  java.lang.Integer
    public void setYear(final Integer year) {
        this.year = year;
    }

    /// Get the make
    ///
    /// @return java.lang.String
    public @Nullable String getMake() {
        return this.make;
    }

    /// Set the make
    ///
    /// @param  make  java.lang.String
    public void setMake(final String make) {
        this.make = make;
    }

    /// Get the model
    ///
    /// @return java.lang.String
    public @Nullable String getModel() {
        return this.model;
    }

    /// Set the model
    ///
    /// @param  model  java.lang.String
    public void setModel(final String model) {
        this.model = model;
    }

    /// Get the color
    ///
    /// @return java.lang.String
    public @Nullable String getColor() {
        return this.color;
    }

    /// Set the color
    ///
    /// @param  color  java.lang.String
    public void setColor(final String color) {
        this.color = color;
    }

    /// Get the style
    ///
    /// @return java.lang.String
    public @Nullable String getStyle() {
        return this.style;
    }

    /// Set the style
    ///
    /// @param  style  java.lang.String
    public void setStyle(final String style) {
        this.style = style;
    }
}
