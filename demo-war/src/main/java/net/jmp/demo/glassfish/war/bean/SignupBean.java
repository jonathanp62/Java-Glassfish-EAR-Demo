package net.jmp.demo.glassfish.war.bean;

/*
 * (#)SignupBean.java  0.4.0   08/07/2026
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

import jakarta.faces.application.FacesMessage;

import jakarta.faces.context.FacesContext;

import jakarta.faces.view.ViewScoped;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;

import java.text.MessageFormat;

import java.util.ResourceBundle;

import net.jmp.demo.glassfish.ejb.service.RegistrationService;

import org.jspecify.annotations.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.jmp.util.logging.LoggerUtils.*;

/// The signup backing bean class
@Named("signupBean")
@ViewScoped
public class SignupBean implements Serializable {
    /// The serial version UID
    @Serial
    private static final long serialVersionUID = 1L;

    // Initialize the SLF4J Logger
    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    /// The messages resource bundle
    @Inject
    @ManagedProperty("#{msg}")
    private transient ResourceBundle bundle;

    /// The registration service
    @Inject
    private transient RegistrationService registrationService;

    /// The email address
    @NotBlank(message = "{servlet.form.validation.required.email}")
    @Email(message = "{servlet.form.validation.email}")
    private @Nullable String email;

    /// The success message
    private @Nullable String successMessage;

    /// The default constructor
    public SignupBean() {
        super();
    }

    @PostConstruct
    public void init() {
        this.logger.info("Service injected: {}", (registrationService != null));
        this.logger.info("Bundle injected : {}", (bundle != null));
    }

    /// The get email method
    ///
    /// @return java.lang.String
    public @Nullable String getEmail() {
        return this.email;
    }

    /// The set email method
    ///
    /// @param  email   java.lang.String
    public void setEmail(final @Nullable String email) {
        this.email = email;
    }

    /// The get success message method
    ///
    /// @return java.lang.String
    public @Nullable String getSuccessMessage() {
        return this.successMessage;
    }

    /// The submit method. Called when the signup form is submitted.
    /// JSF Bean Validation runs before this method is invoked. If validation
    /// fails, this method is not called and messages are shown automatically.
    ///
    /// @return java.lang.String    Return null to stay on the same view
    public @Nullable String submit() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final String nonNullEmail = this.email != null ? this.email : "";

        this.registrationService.register(nonNullEmail);

        final String pattern = this.bundle.getString("bean.signup.success");

        this.successMessage = MessageFormat.format(pattern, nonNullEmail);

        final FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, this.successMessage, null));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }

        return null;
    }
}
