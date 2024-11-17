CREATE TABLE url (
                     hash VARCHAR(6) PRIMARY KEY,
                     url VARCHAR(2048) NOT NULL,
                     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE hash (
                      hash VARCHAR(6) PRIMARY KEY
);

CREATE SEQUENCE unique_number_seq START WITH 1;