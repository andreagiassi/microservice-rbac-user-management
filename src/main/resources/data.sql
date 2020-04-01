
DELETE FROM roles;
DELETE FROM contacts;
DELETE FROM addresses;

INSERT INTO roles(id, role) VALUES (1, 'USER');
INSERT INTO roles(id, role) VALUES (2, 'ADMINISTRATOR');

DELETE FROM users;

INSERT INTO users(id, username, password, salt, name, surname, gender, role_id) VALUES (1, 'andrea', '1d/NZaEqNgtEomytAPrwm/+QjmbudLg33oeEk77Xh88=', 'WZeBXmCI9cAz3LyY9Sdllj9l5iPsXC', 'Andrea', 'Test', 0, 2);
INSERT INTO users(id, username, password, salt, name, surname, gender, role_id) VALUES (2, 'mario', '1d/NZaEqNgtEomytAPrwm/+QjmbudLg33oeEk77Xh88=', 'WZeBXmCI9cAz3LyY9Sdllj9l5iPsXC', 'Mario', 'Rossi', 0, 1);
INSERT INTO users(id, username, password, salt, name, surname, gender, role_id) VALUES (3, 'stefania', '1d/NZaEqNgtEomytAPrwm/+QjmbudLg33oeEk77Xh88=', 'WZeBXmCI9cAz3LyY9Sdllj9l5iPsXC', 'Stefania', 'Verdi', 1, 1);
INSERT INTO users(id, username, password, salt, name, surname, gender, role_id) VALUES (4, 'veronica', '1d/NZaEqNgtEomytAPrwm/+QjmbudLg33oeEk77Xh88=', 'WZeBXmCI9cAz3LyY9Sdllj9l5iPsXC', 'Veronica', 'Gialli', 1, 1);
INSERT INTO users(id, username, password, salt, name, surname, gender, role_id) VALUES (5, 'mark', '1d/NZaEqNgtEomytAPrwm/+QjmbudLg33oeEk77Xh88=', 'WZeBXmCI9cAz3LyY9Sdllj9l5iPsXC', 'Mark', 'Green', 0, 1);
INSERT INTO users(id, username, password, salt, name, surname, gender, role_id) VALUES (6, 'paul', '1d/NZaEqNgtEomytAPrwm/+QjmbudLg33oeEk77Xh88=', 'WZeBXmCI9cAz3LyY9Sdllj9l5iPsXC', 'Paul', 'Ludwing', 0, 1);
INSERT INTO users(id, username, password, salt, name, surname, gender, role_id) VALUES (7, 'jennifer', '1d/NZaEqNgtEomytAPrwm/+QjmbudLg33oeEk77Xh88=', 'WZeBXmCI9cAz3LyY9Sdllj9l5iPsXC', 'Jennifer', 'Red', 0, 1);
INSERT INTO users(id, username, password, salt, name, surname, gender, role_id) VALUES (8, 'karina', '1d/NZaEqNgtEomytAPrwm/+QjmbudLg33oeEk77Xh88=', 'WZeBXmCI9cAz3LyY9Sdllj9l5iPsXC', 'Karina', 'Yellow', 1, 1);

UPDATE users SET ENABLED = false WHERE id = 6;

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
