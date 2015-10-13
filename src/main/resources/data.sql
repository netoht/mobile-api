
insert into user_application(id, name, login, password) values (1,'Super Administrador','root', '$2a$10$jdyLF5uGYsxq8OOXzmoG8el4XzWmHkN2z6c2xeI03p/B0s3TvT8qO');
insert into user_application(id, name, login, password) values (2,'Administrador','admin', '$2a$10$wEi5RWabZMpfDTHypbtt7e4nu32TIZuc/ArwTCmVt15q/c5CrfAye');
insert into user_application(id, name, login, password) values (3,'Usuário','usuario', '$2a$10$h7qmL10mvPtt6aZD6JNxPeQ9Ag0BMTnbAOPDxKF4dVTxDlSZ/a/wm');

insert into role_application(id, name) values (1,'ROLE_ROOT');
insert into role_application(id, name) values (2,'ROLE_USER');
insert into role_application(id, name) values (3,'ROLE_ADMIN');

insert into user_role_application(user_id, role_id) values (1,1);
insert into user_role_application(user_id, role_id) values (2,2);
insert into user_role_application(user_id, role_id) values (2,3);
insert into user_role_application(user_id, role_id) values (3,2);

insert into oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) values ('mobile-73hZFgSJ2Gdc48BJBkuWhe0nPw8TdZYdP8HeCv2bUR0y8l2Wy6', '/api', 'cCTemqOcYFUy2NOqb265vS9l8RRiFjtAkKiqoUkqeARWBPjm', 'read,write', 'password,authorization_code,client_credentials', 'http://localhost', 'APP', 0, 0, null, null);
-- insert into oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) values ('mobile-73hZFgSJ2Gdc48BJBkuWhe0nPw8TdZYdP8HeCv2bUR0y8l2Wy6', '/api', 'cCTemqOcYFUy2NOqb265vS9l8RRiFjtAkKiqoUkqeARWBPjm', 'read,write', 'password,authorization_code,client_credentials,refresh_token', 'http://localhost', 'APP', 0, 0, null, null);

--INSERT INTO public.driver(name, car_plate, active, location) VALUES ('TAXISTA LOU', 'AAA-0001', true, 'POINT(-23.570344 -46.691477)');
--INSERT INTO public.driver(name, car_plate, active, location) VALUES ('TAXISTA REBOUÇAS', 'AAA-0002', true, 'POINT(-23.570885 -46.690481)');
--INSERT INTO public.driver(name, car_plate, active, location) VALUES ('TAXISTA METRO FARIA LIMA', 'AAA-0003', true, 'POINT(-23.567323 -46.694098)');
--INSERT INTO public.driver(name, car_plate, active, location) VALUES ('TAXISTA CEMITÉRIO VM', 'AAA-0004', true, 'POINT(-23.559565 -46.685913)');
--INSERT INTO public.driver(name, car_plate, active, location) VALUES ('TAXISTA PONTO ACIMA DO CEMITÉRIO VM', 'AAA-0005', true, 'POINT(-23.555743 -46.682439)');
--INSERT INTO public.driver(name, car_plate, active, location) VALUES ('TAXISTA METRÔ SUMARÉ', 'AAA-0006', true, 'POINT(-23.550937 -46.678033)');
