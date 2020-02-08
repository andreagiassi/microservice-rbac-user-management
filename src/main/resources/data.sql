
DELETE FROM roles;

INSERT INTO roles(id, role) VALUES (1, 'USER');
INSERT INTO roles(id, role) VALUES (2, 'ADMINISTRATOR');

DELETE FROM users;

INSERT INTO users(id, username, name, surname, gender, role_id) VALUES (1, 'andrea', 'Andrea', 'Test', 0, 2);
INSERT INTO users(id, username, name, surname, gender, role_id) VALUES (2, 'mario', 'Mario', 'Rossi', 0, 1);
INSERT INTO users(id, username, name, surname, gender, role_id) VALUES (3, 'stefania', 'Stefania', 'Verdi', 1, 1);
INSERT INTO users(id, username, name, surname, gender, role_id) VALUES (4, 'veronica', 'Veronica', 'Gialli', 1, 1);
INSERT INTO users(id, username, name, surname, gender, role_id) VALUES (5, 'mark', 'Mark', 'Green', 0, 1);
INSERT INTO users(id, username, name, surname, gender, role_id) VALUES (6, 'paul',  'Paul', 'Ludwing', 0, 1);
INSERT INTO users(id, username, name, surname, gender, role_id) VALUES (7, 'jennifer', 'Jennifer', 'Red', 0, 1);
INSERT INTO users(id, username, name, surname, gender, role_id) VALUES (8, 'karina', 'Karina', 'Yellow', 1, 1);

INSERT INTO contacts(id, user_id, email, phone) VALUES (1, 1, 'andrea.test@gmail.com', NULL);
INSERT INTO contacts(id, user_id, email, phone) VALUES (2, 2, 'mario.rossi@gmail.com', NULL);
INSERT INTO contacts(id, user_id, email, phone) VALUES (3, 3, 'stefania.verdi@gmail.com', NULL);
INSERT INTO contacts(id, user_id, email, phone) VALUES (4, 4, 'veronica.gialli@gmail.com', NULL);
INSERT INTO contacts(id, user_id, email, phone) VALUES (5, 5, 'mark.green@gmail.com', NULL);
INSERT INTO contacts(id, user_id, email, phone) VALUES (6, 6, 'paul.ludwing@gmail.com', NULL);
INSERT INTO contacts(id, user_id, email, phone) VALUES (7, 7, 'jennifer.red@gmail.com', NULL);
INSERT INTO contacts(id, user_id, email, phone) VALUES (8, 8, 'karina.yellow@gmail.com', NULL);
