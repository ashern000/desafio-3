
CREATE TABLE product (
    id varchar(36) NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL,
    description VARCHAR(1250),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    price FLOAT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    deleted_at DATETIME(6) NULL
);