-- 新增 IsTempPassword，用來標註是否為系統發送的一次性臨時密碼 (OTP)
ALTER TABLE k_user 
ADD COLUMN IsTempPassword TINYINT DEFAULT 0 COMMENT '是否為臨時密碼(0:否, 1:是)';
