CREATE TABLE accounts (
    uid BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    contract_id VARCHAR(255) unique not NULL,
    status INT NOT NULL DEFAULT 0
);

CREATE TABLE cards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contract_id VARCHAR(255),
    card_number VARCHAR(255) NOT NULL,
    status INT NOT NULL DEFAULT 0,
    balance DECIMAL(10, 2)
);