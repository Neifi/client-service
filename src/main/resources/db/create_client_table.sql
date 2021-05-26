CREATE TABLE IF NOT EXIST client(
    client_id UUID default random_uuid(),
    gym_id UUID,
    legal_id VARCHAR NOT NULL,
    name VARCHAR NOT NULL,
    surnames VARCHAR NOT NULL,
    date_of_birth DATE NOT NULL,
    email VARCHAR NOT NULL,
    avatar VARCHAR NOT NULL,
    creation_date DATE NOT NULL,
    street VARCHAR NOT NULL,
    postal_code VARCHAR NOT NULL,
    city VARCHAR NOT NULL,
    state VARCHAR NOT NULL,
    role VARCHAR NOT NULL,
    is_activated BOOLEAN NOT NULL,
    client_access_key UUID
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_client_id primary key (client_id)

);