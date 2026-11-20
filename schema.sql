CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL,
    email VARCHAR NOT NULL
);

CREATE TABLE issues (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR,
    priority ENUM('BAJA', 'MEDIA', 'ALTA') NOT NULL,
    status ENUM('ABIERTA', 'EN_CURSO', 'CERRADA') NOT NULL,
    created_at DATE DEFAULT CURRENT_DATE NOT NULL,
    assigned_user_id BIGINT,
    CONSTRAINT fk_issues_assigned_user FOREIGN KEY (assigned_user_id) REFERENCES users (id)
);
