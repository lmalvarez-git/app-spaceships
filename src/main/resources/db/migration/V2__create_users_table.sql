DROP TABLE IF EXISTS userlogin;
CREATE TABLE userlogin (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(45) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(45) NOT NULL
);

INSERT INTO userlogin (user_name, password, role) VALUES
('ADMIN', '$2a$12$QyiEyAawo61cqiItonnO.utHo.xWDgRbtk3gIqWKXKYiq61/lu.xG', 'admin'),
('USER', '$2a$12$ueqFIZMJcNOIUzWXZXeksODZuzJvH8YhClkIllbpfRZs./O8hV2aG', 'user');
