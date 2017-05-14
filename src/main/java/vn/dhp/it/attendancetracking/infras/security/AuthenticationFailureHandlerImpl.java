/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.dhp.it.attendancetracking.infras.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import org.springframework.stereotype.Component;
import vn.dhp.it.attendancetracking.domain.user.User;
import vn.dhp.it.attendancetracking.config.SecurityConfig;
import vn.dhp.it.attendancetracking.domain.user.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler{
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        String username=httpServletRequest.getParameter(SecurityConfig.PARA_USERNAME);
        String password=httpServletRequest.getParameter(SecurityConfig.PARA_CREDENTIAL);
        HttpSession session = httpServletRequest.getSession();

        session.setAttribute(SecurityConfig.PARA_USERNAME, username);
        session.setAttribute(SecurityConfig.PARA_CREDENTIAL, password);

        httpServletResponse.sendRedirect("/signin?error=not-exist");
    }
}
