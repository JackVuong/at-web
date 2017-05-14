/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.dhp.it.attendancetracking.domain.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vn.dhp.it.attendancetracking.domain.user.UserRole;
import vn.dhp.it.attendancetracking.domain.user.User;
import vn.dhp.it.attendancetracking.domain.user.UserRepository;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepo;

    public AuthService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<User> getLoginUser() {
        String login = getLogin();
        if (login == null) {
            return Optional.empty();
        }

        return userRepo.findOneByUsername(login);
    }

    public String getLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        String username = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                username = userDetails.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                username = (String) authentication.getPrincipal();
            }
        }

        return username;
    }

    public UserRole getRole() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String authority = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                authority = userDetails.getAuthorities().toArray()[0].toString();
            } else if (authentication.getPrincipal() instanceof String) {
                authority = authentication.getAuthorities().toArray()[0].toString();
            }
        }

        return UserRole.valueOf(authority);
    }

}
