/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.dhp.it.attendancetracking.domain.user;

public enum UserRole {
    ADMIN,
    USER,
    TONGVU;

    public String getAuthority() {
        return "ROLE_" + name();
    }
}
