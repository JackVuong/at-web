/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.dhp.it.attendancetracking.web.form;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import vn.dhp.it.attendancetracking.domain.user.User;
import vn.dhp.it.attendancetracking.infras.validation.FieldUnique;

@Data
public class RegistrationForm {
    @Length(min = 1, max = 50)
    private String username;

    @Email(message = "Email không đúng")
    @Length(max = 100)
    @FieldUnique(
        field = "email",
        entity = User.class,
        message = "Email đã tồn tại")
    @NotEmpty(message = "Email không được trống")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
