CREATE TABLE feedback (
                          id BIGSERIAL PRIMARY KEY,
                          user_id BIGINT NOT NULL,
                          message VARCHAR(1000) NOT NULL,
                          active VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                          version BIGINT NOT NULL DEFAULT 0,
                          created_at TIMESTAMP,
                          updated_at TIMESTAMP
);