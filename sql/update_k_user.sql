-- 新增 email, pwd_reset_count, pwd_reset_window_start 欄位，並加上 Unique Index

ALTER TABLE k_user 
MODIFY COLUMN Email VARCHAR(255) NOT NULL;

ALTER TABLE k_user 
ADD UNIQUE INDEX idx_k_user_email (Email);

ALTER TABLE k_user 
ADD COLUMN PwdResetCount INT DEFAULT 0 COMMENT '密碼重設次數',
ADD COLUMN PwdResetWindowStart DATETIME DEFAULT NULL COMMENT '密碼重設30天週期起算日';
