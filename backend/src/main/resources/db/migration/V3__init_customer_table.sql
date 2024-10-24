CREATE TABLE customer (
    customer_id INT PRIMARY KEY,
    customer_email VARCHAR(255) NOT NULL,
    total_spending DECIMAL(10, 2) NOT NULL DEFAULT 0,
    customer_segment VARCHAR(255) NOT NULL DEFAULT 'LOW_SPEND',
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);