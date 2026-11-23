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

INSERT INTO users (name, email) VALUES
    ('Ana', 'ana.garcia@example.com'),
    ('Luis', 'luis.perez@example.com');

INSERT INTO issues (title, description, priority, status, assigned_user_id) VALUES
    ('Error al iniciar sesion', 'El usuario no puede acceder con sus credenciales.', 'ALTA', 'ABIERTA',
     (SELECT id FROM users WHERE email = 'ana.garcia@example.com')),
    ('Actualizar datos de contacto', 'Cambiar el telefono de contacto del cliente.', 'BAJA', 'EN_CURSO',
     (SELECT id FROM users WHERE email = 'luis.perez@example.com'));
