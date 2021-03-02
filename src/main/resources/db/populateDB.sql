DELETE FROM ;
DELETE FROM DISHES;
DELETE FROM HISTORY;
DELETE FROM USERS;
DELETE FROM RESTAURANTS;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO USERS (NAME, SURNAME, EMAIL, PASSWORD)
    VALUES ('user1', 'user1_surName', 'user1@email.ru', 'user1_pass'),
           ('user2', 'user2_surName', 'user2@email.ru', 'user2_pass'),
           ('admin1', 'admin1_surName', 'admin1@email.ru', 'admin1_pass');

INSERT INTO RESTAURANTS (LABEL, )