

INSERT INTO "user" (username, name, password, role) VALUES ('lmarquez', 'luis márquez', '$2a$10$XbkybRxCUpLyMESl7b1WOuQqy0ofWy4yzNl26.i/BqiZcsuaPW/1G', 'ROLE_CUSTOMER');
INSERT INTO "user" (username, name, password, role) VALUES ('fperez', 'fulano pérez', '$2a$10$3fWJd3XZb1UEohmNI7fWmudG.W53WJ5du8h0FQbZ1vQ9UECOCEpPi', 'ROLE_ASSISTANT_ADMINISTRATOR');
INSERT INTO "user" (username, name, password, role) VALUES ('mhernandez', 'mengano hernández', '$2a$10$GLlz9.qYyg/xJYh2uRJ.P.rSnvj2hFxYI1zDXge.z2gCkYAYB2wK.', 'ROLE_ADMINISTRATOR');

INSERT INTO category (name, status) VALUES ('Electrónica', 'ENABLED');
INSERT INTO category (name, status) VALUES ('Ropa', 'ENABLED');
INSERT INTO category (name, status) VALUES ('Deportes', 'ENABLED');
INSERT INTO category (name, status) VALUES ('Hogar', 'ENABLED');

INSERT INTO product (name, price, status, category_id) VALUES ('Smartphone', 500.00, 'ENABLED', 1);
INSERT INTO product (name, price, status, category_id) VALUES ('Auriculares Bluetooth', 50.00, 'DISABLED', 1);
INSERT INTO product (name, price, status, category_id) VALUES ('Tablet', 300.00, 'ENABLED', 1);

INSERT INTO product (name, price, status, category_id) VALUES ('Camiseta', 25.00, 'ENABLED', 2);
INSERT INTO product (name, price, status, category_id) VALUES ('Pantalones', 35.00, 'ENABLED', 2);
INSERT INTO product (name, price, status, category_id) VALUES ('Zapatos', 45.00, 'ENABLED', 2);

INSERT INTO product (name, price, status, category_id) VALUES ('Balón de Fútbol', 20.00, 'ENABLED', 3);
INSERT INTO product (name, price, status, category_id) VALUES ('Raqueta de Tenis', 80.00, 'DISABLED', 3);

INSERT INTO product (name, price, status, category_id) VALUES ('Aspiradora', 120.00, 'ENABLED', 4);
INSERT INTO product (name, price, status, category_id) VALUES ('Licuadora', 50.00, 'ENABLED', 4);
