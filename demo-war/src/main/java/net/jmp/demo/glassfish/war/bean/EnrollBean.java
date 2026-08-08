package net.jmp.demo.glassfish.war.bean;

/*
 * (#)EnrollBean.java   0.4.0   08/08/2026
 *
 * @author   Jonathan Parker
 * @version  0.4.0
 * @since    0.4.0
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

import jakarta.annotation.PostConstruct;

import jakarta.faces.annotation.ManagedProperty;

import jakarta.faces.view.ViewScoped;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serial;
import java.io.Serializable;

import java.util.ResourceBundle;

import org.jspecify.annotations.Nullable;

import static net.jmp.util.logging.LoggerUtils.entryWith;
import static net.jmp.util.logging.LoggerUtils.exit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// The enroll backing bean class
@Named("enrollBean")
@ViewScoped
public class EnrollBean implements Serializable {
    /// The serial version UID
    @Serial
    private static final long serialVersionUID = 1L;

    // Initialize the SLF4J Logger
    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    /// The messages resource bundle
    @Inject
    @ManagedProperty("#{msg}")
    private transient ResourceBundle bundle;

    /// The first name
    @NotBlank(message = "First name is required")
    private @Nullable String firstName;

    /// The last name
    @NotBlank(message = "Last name is required")
    private @Nullable String lastName;

    /// The address
    @NotBlank(message = "Address is required")
    private @Nullable String address;

    /// The city
    @NotBlank(message = "City is required")
    private @Nullable String city;

    /// The state
    @NotBlank(message = "State is required")
    private @Nullable String state;

    /// The zip code
    @NotBlank(message = "Zip code is required")
    @Digits(integer = 5, fraction = 0, message = "Zip code must be 5 digits")
    private @Nullable String zipCode;

    /// The phone number
    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^(\\+1[-.\\s]?)?\\(?([2-9][0-9]{2})\\)?[-.\\s]?([2-9][0-9]{2})[-.\\s]?([0-9]{4})$",
            message = "Invalid US phone number format"
    )
    private @Nullable String phoneNumber;

    /// The email address
    @NotBlank(message = "{servlet.form.validation.required.email}")
    @Email(message = "{servlet.form.validation.email}")
    private @Nullable String email;

    /**
     * Creates a new {@code EnrollBean}.
     */
    public EnrollBean() {
        super();
    }

    /**
     * Initializes the bean after dependency injection.
     */
    @PostConstruct
    public void init() {
        this.logger.info("Bundle injected : {}", (bundle != null));
    }

    /**
     * Returns the first name.
     *
     * @return the first name
     */
    public @Nullable String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(@Nullable String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name.
     *
     * @return the last name
     */
    public @Nullable String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the last name
     */
    public void setLastName(@Nullable String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the address.
     *
     * @return the address
     */
    public @Nullable String getAddress() {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address the address
     */
    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    /**
     * Returns the city.
     *
     * @return the city
     */
    public @Nullable String getCity() {
        return city;
    }

    /**
     * Sets the city.
     *
     * @param city the city
     */
    public void setCity(@Nullable String city) {
        this.city = city;
    }

    /**
     * Returns the state.
     *
     * @return the state
     */
    public @Nullable String getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state the state
     */
    public void setState(@Nullable String state) {
        this.state = state;
    }

    /**
     * Returns the zip code.
     *
     * @return the zip code
     */
    public @Nullable String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the zip code.
     *
     * @param zipCode the zip code
     */
    public void setZipCode(@Nullable String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Returns the phone number.
     *
     * @return the phone number
     */
    public @Nullable String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(@Nullable String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the email address.
     *
     * @return the email address
     */
    public @Nullable String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email the email address
     */
    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    /// The submit method. Called when the enroll form is submitted.
    /// JSF Bean Validation runs before this method is invoked. If validation
    /// fails, this method is not called and messages are shown automatically.
    ///
    /// @return java.lang.String    Return null to stay on the same view
    public @Nullable String submit() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(this.email));
        }

        final String nonNullFirstName = this.firstName != null ? this.firstName : "";
        final String nonNullLastName = this.lastName != null ? this.lastName : "";
        final String nonNullAddress = this.address != null ? this.address : "";
        final String nonNullCity = this.city != null ? this.city : "";
        final String nonNullState = this.state != null ? this.state : "";
        final String nonNullZipCode = this.zipCode != null ? this.zipCode : "";
        final String nonNullPhoneNumber = this.phoneNumber != null ? this.phoneNumber : "";
        final String nonNullEmail = this.email != null ? this.email : "";

        if (this.logger.isInfoEnabled()) {
            this.logger.info("First name: {}", nonNullFirstName);   // @todo Create language entries
            this.logger.info("Last name: {}", nonNullLastName);
            this.logger.info("Address: {}", nonNullAddress);
            this.logger.info("City: {}", nonNullCity);
            this.logger.info("State: {}", nonNullState);
            this.logger.info("Zip code: {}", nonNullZipCode);
            this.logger.info("Phone number: {}", nonNullPhoneNumber);
            this.logger.info("Email: {}", nonNullEmail);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }

        return null;
    }
}
