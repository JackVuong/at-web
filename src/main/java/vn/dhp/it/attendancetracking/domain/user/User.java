/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.dhp.it.attendancetracking.domain.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "users")
@Entity
@Data
@EqualsAndHashCode(of = "username")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
