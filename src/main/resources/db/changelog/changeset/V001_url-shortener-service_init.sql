CREATE TABLE url (
    hash VARCHAR(6) PRIMARY KEY,
    url TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE hash (
    hash VARCHAR(6) PRIMARY KEY
);

CREATE SEQUENCE unique_number_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1




