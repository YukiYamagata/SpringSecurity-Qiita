INSERT INTO users(username, password, name, rolename) VALUES ('admin', '$2a$10$bCR1jXhdqbh1oC8ckXplxePYW5Kyb/VjN28MZx2PwXf1ybzLIFUQG', 'admin-name', 'ADMIN');
INSERT INTO users(username, password, name, rolename) VALUES ('user' , '$2a$10$yyT1siJCep647RT/I7KjcuUB5noFVU6RBo0FUXUJX.hb2MIlWTbDe', 'user-name' , 'USER' );

INSERT INTO access_authorization(rolename, uri) VALUES ('ADMIN', '/loginForm');
INSERT INTO access_authorization(rolename, uri) VALUES ('ADMIN', '/home');
INSERT INTO access_authorization(rolename, uri) VALUES ('ADMIN', '/adminPage');
INSERT INTO access_authorization(rolename, uri) VALUES ('ADMIN', '/logout');
INSERT INTO access_authorization(rolename, uri) VALUES ('ADMIN', '/accessDeniedPage');

INSERT INTO access_authorization(rolename, uri) VALUES ('USER',  '/loginForm');
INSERT INTO access_authorization(rolename, uri) VALUES ('USER',  '/home');
INSERT INTO access_authorization(rolename, uri) VALUES ('USER',  '/userPage');
INSERT INTO access_authorization(rolename, uri) VALUES ('USER',  '/logout');
INSERT INTO access_authorization(rolename, uri) VALUES ('USER',  '/accessDeniedPage');