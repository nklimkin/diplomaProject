DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS history;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS restaurants;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users (
    id INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    registered DATE DEFAULT CURRENT_DATE
);

CREATE TABLE user_roles (
    user_id INTEGER NOT NULL,
    role VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

ALTER TABLE user_roles ADD CONSTRAINT user_roles UNIQUE (user_id, role);

CREATE TABLE restaurants (
    id INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    label VARCHAR(255) UNIQUE NOT NULL,
    rating DECIMAL DEFAULT 0,
    number_of_voters INT DEFAULT 0
);

CREATE TABLE dishes (
    id INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    restaurant_id INT NOT NULL,
    label VARCHAR(255) NOT NULL,
    price DECIMAL NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE
);

ALTER TABLE dishes ADD CONSTRAINT dish_restaurant UNIQUE (label, restaurant_id);

CREATE TABLE history (
    id INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    user_id INTEGER NOT NULL,
    restaurant_id INTEGER NOT NULL,
    date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    grade INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
)