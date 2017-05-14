/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.dhp.it.attendancetracking.web.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import vn.dhp.it.attendancetracking.domain.common.AuthService;
import vn.dhp.it.attendancetracking.domain.user.User;
import vn.dhp.it.attendancetracking.service.user.UserService;
import vn.dhp.it.attendancetracking.web.form.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerForm(RegistrationForm registrationForm) {
        return "user/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@Valid RegistrationForm registrationForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        User rawUser = new User();
        BeanUtils.copyProperties(registrationForm, rawUser);
        userService.registerUser(rawUser);

        return "redirect:/user/register?success";
    }

}
