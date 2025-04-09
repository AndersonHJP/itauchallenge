CREATE TABLE client (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    participation DECIMAL(10, 2) NOT NULL CHECK (participation >= 0)
);