CREATE TABLE IF NOT EXIST "client_access_key"(
    id UUID PRIMARY KEY,
    is_enabled BOOLEAN NOT NULL
);