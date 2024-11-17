CREATE TABLE url
(
    hash       VARCHAR(6)   NOT NULL PRIMARY KEY,
    url        VARCHAR(255) NOT NULL,
    created_at TIMESTAMP
);

CREATE TABLE hash
(
    hash VARCHAR(6) NOT NULL PRIMARY KEY
);

CREATE SEQUENCE unique_number_seq
    START WITH 1
    INCREMENT BY 1;