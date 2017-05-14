/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.dhp.it.attendancetracking.web.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by congle on 4/3/2017.
 */
@Data
public class ChangePasswordForm {

    @NotEmpty(message ="{change.password.form.empty}")
    @Pattern(
        regexp = "((^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[_\\W])[A-Za-z_\\d\\W]{6,128}$)|)",
        message = "{change.password.form.validate}")
    private String currentPassword;

    @NotEmpty(message ="{change.password.form.empty}")
    @Pattern(
        regexp = "((^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[_\\W])[A-Za-z_\\d\\W]{6,128}$)|)",
        message = "{change.password.form.validate}")
    private String newPassword;

    @NotEmpty(message ="{change.password.form.empty}")
    private String confirmNewPassword;

}
