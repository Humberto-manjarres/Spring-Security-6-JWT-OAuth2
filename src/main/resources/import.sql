-- CREACIÓN DE MODULOS
INSERT INTO module (name, base_path) VALUES ('PRODUCT', '/products');
INSERT INTO module (name, base_path) VALUES ('CATEGORY', '/categories');
INSERT INTO module (name, base_path) VALUES ('CUSTOMER', '/customers');
INSERT INTO module (name, base_path) VALUES ('AUTH', '/auth');

-- CREACIÓN DE OPERACIONES
--Productos
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_PRODUCTS','', 'GET', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_PRODUCT','/[0-9]*', 'GET', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_PRODUCT','', 'POST', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('UPDATE_ONE_PRODUCT','/[0-9]*', 'PUT', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('DISABLE_ONE_PRODUCT','/[0-9]*/disabled', 'PUT', false, 1);
--Categorías
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_CATEGORIES','', 'GET', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_CATEGORY','/[0-9]*', 'GET', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_CATEGORY','', 'POST', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('UPDATE_ONE_CATEGORY','/[0-9]*', 'PUT', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('DISABLE_ONE_CATEGORY','/[0-9]*/disabled', 'PUT', false, 2);
--Customer
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_CUSTOMERS','', 'GET', false, 3);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('REGISTER_ONE','', 'POST', true, 3);
--Autenticación
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('AUTHENTICATE','/authenticate', 'POST', true, 4);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('VALIDATE-TOKEN','/validate-token', 'GET', true, 4);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_MY_PROFILE','/profile','GET', false, 4);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('LOGOUT','/logout','POST', true, 4);


-- CREACIÓN DE ROLES
INSERT INTO role (name) VALUES ('CUSTOMER');
INSERT INTO role (name) VALUES ('ASSISTANT_ADMINISTRATOR');
INSERT INTO role (name) VALUES ('ADMINISTRATOR');

-- CREACIÓN DE PERMISOS
--Customer
INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 15);
--Assistant_administrator
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 1);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 2);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 4);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 6);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 7);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 9);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 15);
--Administrator
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 1);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 2);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 3);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 4);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 5);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 6);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 7);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 8);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 9);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 10);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 15);


--CREACIÓN DE USUARIO
INSERT INTO "user" (username, name, password, role_id) VALUES ('lmarquez', 'luis márquez', '$2a$10$XbkybRxCUpLyMESl7b1WOuQqy0ofWy4yzNl26.i/BqiZcsuaPW/1G', 1);
INSERT INTO "user" (username, name, password, role_id) VALUES ('fperez', 'fulano pérez', '$2a$10$3fWJd3XZb1UEohmNI7fWmudG.W53WJ5du8h0FQbZ1vQ9UECOCEpPi', 2);
INSERT INTO "user" (username, name, password, role_id) VALUES ('mhernandez', 'mengano hernández', '$2a$10$GLlz9.qYyg/xJYh2uRJ.P.rSnvj2hFxYI1zDXge.z2gCkYAYB2wK.', 3);

--CREACIÓN DE CATEGORÍAS
INSERT INTO category (name, status) VALUES ('Electrónica', 'ENABLED');
INSERT INTO category (name, status) VALUES ('Ropa', 'ENABLED');
INSERT INTO category (name, status) VALUES ('Deportes', 'ENABLED');
INSERT INTO category (name, status) VALUES ('Hogar', 'ENABLED');

--CREACIÓN DE PRODUCTOS
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
