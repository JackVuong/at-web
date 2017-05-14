/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.dhp.it.attendancetracking.domain.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findOneByUsername(String username);
    Optional<User> findOneByEmail(String email);
    //Optional<List<User>> findByEmailIgnoreCaseContainingOrUsernameIgnoreCaseContaining(String email, String username);
    User findOne(Long id);
}
