CREATE TABLE accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(255) NOT NULL,
    account_holder_name VARCHAR(255) NOT NULL
);

CREATE TABLE cards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(255) NOT NULL,
    expiry_date VARCHAR(255) NOT NULL,
    account_id BIGINT,
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);