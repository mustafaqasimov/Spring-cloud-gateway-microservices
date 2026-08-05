CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       user_name VARCHAR(255) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL,
                       active VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                       created_at TIMESTAMP,
                       updated_at TIMESTAMP
);