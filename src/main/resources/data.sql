
DELETE FROM roles;

INSERT INTO roles(id, role) VALUES (1, 'User');
INSERT INTO roles(id, role) VALUES (2, 'Administrator');

DELETE FROM users;

INSERT INTO users(id, username, name, surname, email, gender, role_id) VALUES (1, 'andrea', 'Andrea', 'Test', 'andrea.test@gmail.com', 0, 2);
INSERT INTO users(id, username, name, surname, email, gender, role_id) VALUES (2, 'mario', 'Mario', 'Rossi', 'mario.rossi@gmail.com', 0, 1);
INSERT INTO users(id, username, name, surname, email, gender, role_id) VALUES (3, 'stefania', 'Stefania', 'Verdi', 'stefania.verdi@gmail.com', 1, 1);
INSERT INTO users(id, username, name, surname, email, gender, role_id) VALUES (4, 'veronica', 'Veronica', 'Gialli', 'veronica.gialli@gmail.com', 1, 1);
INSERT INTO users(id, username, name, surname, email, gender, role_id) VALUES (5, 'mark', 'Mark', 'Green', 'mark.green@gmail.com', 0, 1);
INSERT INTO users(id, username, name, surname, email, gender, role_id) VALUES (6, 'paul',  'Paul', 'Ludwing', 'paul.ludwing@gmail.com', 0, 1);
INSERT INTO users(id, username, name, surname, email, gender, role_id) VALUES (7, 'jennifer', 'Jennifer', 'Red', 'jennifer.red@gmail.com', 0, 1);
INSERT INTO users(id, username, name, surname, email, gender, role_id) VALUES (8, 'karina', 'Karina', 'Yellow', 'karina.yellow@gmail.com', 1, 1);

