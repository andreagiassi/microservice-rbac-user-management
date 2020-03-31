
DELETE FROM roles;
DELETE FROM contacts;
DELETE FROM addresses;

INSERT INTO roles(id, role) VALUES (1, 'USER');
INSERT INTO roles(id, role) VALUES (2, 'ADMINISTRATOR');

DELETE FROM users;

INSERT INTO users(id, username, password, name, surname, gender, role_id) VALUES (1, 'andrea', '123', 'Andrea', 'Test', 0, 2);
INSERT INTO users(id, username, password, name, surname, gender, role_id) VALUES (2, 'mario', '123', 'Mario', 'Rossi', 0, 1);
INSERT INTO users(id, username, password, name, surname, gender, role_id) VALUES (3, 'stefania', '123', 'Stefania', 'Verdi', 1, 1);
INSERT INTO users(id, username, password, name, surname, gender, role_id) VALUES (4, 'veronica', '123', 'Veronica', 'Gialli', 1, 1);
INSERT INTO users(id, username, password, name, surname, gender, role_id) VALUES (5, 'mark', '123', 'Mark', 'Green', 0, 1);
INSERT INTO users(id, username, password, name, surname, gender, role_id) VALUES (6, 'paul', '123',  'Paul', 'Ludwing', 0, 1);
INSERT INTO users(id, username, password, name, surname, gender, role_id) VALUES (7, 'jennifer', '123', 'Jennifer', 'Red', 0, 1);
INSERT INTO users(id, username, password, name, surname, gender, role_id) VALUES (8, 'karina', '123', 'Karina', 'Yellow', 1, 1);

INSERT INTO contacts(user_id, email, phone) VALUES (1, 'andrea.test@gmail.com', NULL);
INSERT INTO contacts(user_id, email, phone) VALUES (2, 'mario.rossi@gmail.com', NULL);
INSERT INTO contacts(user_id, email, phone) VALUES (3, 'stefania.verdi@gmail.com', NULL);
INSERT INTO contacts(user_id, email, phone) VALUES (4, 'veronica.gialli@gmail.com', NULL);
INSERT INTO contacts(user_id, email, phone) VALUES (5, 'mark.green@gmail.com', NULL);
INSERT INTO contacts(user_id, email, phone) VALUES (6, 'paul.ludwing@gmail.com', NULL);
INSERT INTO contacts(user_id, email, phone) VALUES (7, 'jennifer.red@gmail.com', NULL);
INSERT INTO contacts(user_id, email, phone) VALUES (8, 'karina.yellow@gmail.com', NULL);

insert into addresses(user_id, address, city, country, zip_code) values (2,'Via Filzi 2', 'Florence', 'Italy', '50100');
insert into addresses(user_id, address, city, country, zip_code) values (7,'Piazza Grande 12', 'Venice', 'Italy', '30100');
insert into addresses(user_id, address, city, country, zip_code) values (8,'Via Roma 2', 'Trieste', 'Italy', '34100');
