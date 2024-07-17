CREATE TABLE users_e(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(260) NOT NULL,
    email VARCHAR(400) NOT NULL,
    password VARCHAR(400) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    role VARCHAR(10) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    deleted_at DATETIME(6) NULL
);