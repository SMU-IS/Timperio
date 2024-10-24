CREATE TYPE role_enum AS ENUM ('MARKETING', 'SALES', 'ADMIN');

CREATE TABLE users (
    user_id INT PRIMARY KEY,
    user_email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    role role_enum NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

INSERT INTO users (user_id, user_email, password, user_name, role, created_at, updated_at)
VALUES
    (1, 'alice@timperio.com', 'password123', 'Alice Johnson', 'MARKETING', NOW(), NOW()),
    (2, 'bob@timperio.com', 'password123', 'Bob Smith', 'SALES', NOW(), NOW()),
    (3, 'charlie@timperio.com', 'password123', 'Charlie Lee', 'ADMIN', NOW(), NOW());