DELETE FROM USER_ROLES;
DELETE FROM DISHES;
DELETE FROM HISTORY;
DELETE FROM USERS;
DELETE FROM RESTAURANTS;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO USERS (NAME, SURNAME, EMAIL, PASSWORD)
    VALUES ('user1', 'user1_surName', 'user1@email.ru', 'user1_pass'),
           ('user2', 'user2_surName', 'user2@email.ru', 'user2_pass'),
           ('admin1', 'admin1_surName', 'admin1@email.ru', 'admin1_pass');

INSERT INTO USER_ROLES (USER_ID, ROLE)
    VALUES ('100000', 'USER'),
           ('100001', 'USER'),
           ('100002', 'ADMIN');

INSERT INTO RESTAURANTS (LABEL, RATING, NUMBER_OF_VOTERS)
    VALUES ('restaurant1', '4.0', '10'),
           ('restaurant2', '3.0', '1'),
           ('restaurant3', '4.1', '3');

INSERT INTO DISHES (RESTAURANT_ID, LABEL, PRICE)
    VALUES ('100003', 'dish11', '500'),
           ('100003', 'dish21', '100.43'),
           ('100003', 'dish31', '100'),
           ('100004', 'dish12', '200'),
           ('100005', 'dish13', '10'),
           ('100005', 'dish23', '160');


