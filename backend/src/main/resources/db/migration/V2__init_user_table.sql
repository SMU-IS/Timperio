CREATE TYPE role_enum AS ENUM ('MARKETING', 'SALES', 'ADMIN');

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    user_email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    role role_enum NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

INSERT INTO users (user_email, password, user_name, role, created_at, updated_at)
VALUES
    ('alice@timperio.com', 'password123', 'Alice Johnson', 'MARKETING', NOW(), NOW()),
    ('bob@timperio.com', 'password123', 'Bob Smith', 'SALES', NOW(), NOW()),
    ('charlie@timperio.com', 'password123', 'Charlie Lee', 'ADMIN', NOW(), NOW());