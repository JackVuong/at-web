CREATE TABLE users(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(64),
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(12) NOT NULL,
    UNIQUE (username),
    UNIQUE (email)
);

CREATE TABLE mon_hoc(
    ma_mon_hoc VARCHAR(20) NOT NULL,
    ten_mon_hoc VARCHAR(20) NOT NULL,
    PRIMARY KEY(ma_mon_hoc)
);

CREATE TABLE lop_hoc(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    ma_mon_hoc VARCHAR(12),
    nhom_mon_hoc INTEGER,
    to_mon_hoc INTEGER,
    nam_hoc VARCHAR(12),
    hoc_ky VARCHAR(20)
);

CREATE TABLE diem_danh(
    id BIGSERIAL NOT NULL,
    mssv VARCHAR(20) NOT NULL,
    ma_lop_hoc BIGSERIAL NOT NULL,
    ngay_gio VARCHAR(20),
    PRIMARY KEY(id)
);
