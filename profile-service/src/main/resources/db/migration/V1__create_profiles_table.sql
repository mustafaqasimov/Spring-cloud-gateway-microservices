CREATE TABLE profiles (
                          id BIGSERIAL PRIMARY KEY,
                          user_id BIGINT NOT NULL UNIQUE,
                          name VARCHAR(150) NOT NULL,
                          email VARCHAR(150) NOT NULL,
                          bio VARCHAR(500),
                          active VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                          version BIGINT NOT NULL DEFAULT 0,
                          created_at TIMESTAMP,
                          updated_at TIMESTAMP
);