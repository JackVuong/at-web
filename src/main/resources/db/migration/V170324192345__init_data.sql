-- password: Admin@123
INSERT INTO users(username, password, email, role)
  VALUES ('admin', '$2a$10$NiK.3NQeblayPGlcmHM70uOBoBGeRxC8xa86XydNTHREjO5WTc5uC', 'luanvuong@kms-technology.com', 'ADMIN');

INSERT INTO mon_hoc(ma_mon_hoc, ten_mon_hoc)
   VALUES ('MH01','Cơ sở lập trình');

INSERT INTO mon_hoc(ma_mon_hoc, ten_mon_hoc)
   VALUES ('MH02','Trí tuệ nhân tạo');

INSERT INTO lop_hoc(ma_mon_hoc, nhom_mon_hoc, to_mon_hoc, nam_hoc, hoc_ky)
   VALUES ('MH02',1,1,'2016-2017','I');

INSERT INTO lop_hoc(ma_mon_hoc, nhom_mon_hoc, to_mon_hoc, nam_hoc, hoc_ky)
   VALUES ('MH01',1,1,'2016-2017','I');

INSERT INTO lop_hoc(ma_mon_hoc, nhom_mon_hoc, to_mon_hoc, nam_hoc, hoc_ky)
   VALUES ('MH02',1,2,'2016-2017','I');

INSERT INTO lop_hoc(ma_mon_hoc, nhom_mon_hoc, to_mon_hoc, nam_hoc, hoc_ky)
   VALUES ('MH01',1,2,'2016-2017','I');

INSERT INTO diem_danh(mssv,ma_lop_hoc,ngay_gio)
    VALUES('51303110',2,'04/04/2017');
INSERT INTO diem_danh(mssv,ma_lop_hoc,ngay_gio)
    VALUES('51303094',1,'04/04/2017');
