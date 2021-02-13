CREATE TABLE IF NOT EXISTS user (
    id SERIAL PRIMARY KEY,
    username VARCHAR(200) UNIQUE NOT NULL,
    password VARCHAR(500) NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL,
    state VARCHAR(50) NOT NULL
);

INSERT INTO user(username, password, email, state) VALUES('vinicius', '', 'v.fernandesdias@gmail.com', 'REQUIRES_PASSWORD_CHANGE');
