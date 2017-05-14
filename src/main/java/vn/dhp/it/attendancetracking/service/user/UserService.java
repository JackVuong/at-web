/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.dhp.it.attendancetracking.service.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.dhp.it.attendancetracking.domain.user.Language;
import vn.dhp.it.attendancetracking.domain.user.User;
import vn.dhp.it.attendancetracking.domain.user.UserRole;
import vn.dhp.it.attendancetracking.domain.common.MailingService;
import vn.dhp.it.attendancetracking.domain.user.UserRepository;

import static org.bitbucket.dollar.Dollar.$;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {
    private static final Long SECOND_DECREASE = 10 * 24 * 60 * 60L;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final MailingService mailingService;
    private String validCharacters = $('0', '9').join() + $('A', 'Z').join();
    @Value("${app.activation-expire-seconds}")
    private int activationExpireSeconds;

    @Value("${app.reset-expire-seconds}")
    private int resetPasswordExpireSeconds;

    private String randomPassword(int length) {
        return $(validCharacters).shuffle().slice(length).toString();
    }

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, MailingService mailingService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.mailingService = mailingService;
    }

    public List<Language> getAllowedLanguages(Locale locale) {
        List<Language> languages = new ArrayList<>();

        languages.add(new Language("vi", Locale.forLanguageTag("vi").getDisplayLanguage(locale)));
        languages.add(new Language("en", Locale.forLanguageTag("en").getDisplayLanguage(locale)));

        return languages;
    }

    public void registerUser(User rawUser) {
        String password = randomPassword(6);
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();

        user.setId(null);
        user.setUsername(rawUser.getUsername());
        user.setEmail(rawUser.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(UserRole.USER);
        userRepo.save(user);
        mailingService.sendActivationEmailAsync(user, password);

    }

    /*public void updateProfileUser(String username, String firstName, String lastName, String languageTag, String avatar) {
        userRepo.findOneByUsername(username).ifPresent(user -> {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setLanguageTag(languageTag);
            user.setAvatar(avatar);

            userRepo.save(user);
        });
    }*/

    /*public ActivateUserStatus activateUser(String activationToken) {
        User user = userRepo.findOneByActivationKey(activationToken).orElse(null);

        if (user == null) {
            return ActivateUserStatus.TOKEN_NOT_FOUND;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        Long period = ChronoUnit.SECONDS.between(user.getCreatedAt(), currentTime);
        if (period <= activationExpireSeconds) {
            user.setActivated(true);
            user.setActivationKey(null);
            userRepo.save(user);

            return ActivateUserStatus.ACTIVATE_SUCCESS;
        }

        return ActivateUserStatus.EXPIRED;
    }*/

    public ChangePasswordError validateChangePassword(String username, String currentPassword, String newPassword) {
        User user = userRepo.findOneByUsername(username).orElse(null);

        if (user != null) {
            String validPassword = user.getPassword();
            String validNewPassword = passwordEncoder.encode(newPassword);

            if (!passwordEncoder.matches(currentPassword, validPassword)) {
                return ChangePasswordError.INVALID_CURRENT_PASSWORD;
            } else if (passwordEncoder.matches(currentPassword, validNewPassword)) {
                return ChangePasswordError.SAME_CURRENT_PASSWORD;
            }
        }

        return null;
    }

    /*public User findOneByResetkey(String resetKey) {
        return userRepo.findOneByResetKey(resetKey).orElse(null);
    }*/


    /*public List<User> findByUsernameKeyword(String keyword) {
        return userRepo.findByEmailIgnoreCaseContainingOrUsernameIgnoreCaseContaining(keyword, keyword).orElse(null);
    }*/

    public User findOne(Long id) {
        return userRepo.findOne(id);
    }

    public enum ActivateUserStatus {
        ACTIVATE_SUCCESS,
        TOKEN_NOT_FOUND,
        EXPIRED,
        ALREADY_ACTIVATED
    }

    public enum ChangePasswordError {
        INVALID_CURRENT_PASSWORD,
        SAME_CURRENT_PASSWORD;
    }

}
