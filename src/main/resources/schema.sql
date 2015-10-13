drop table if exists user_role_application;
drop table if exists user_application;
drop table if exists role_application;
drop table if exists driver;
drop table if exists oauth_client_details;
drop table if exists oauth_client_token;
drop table if exists oauth_access_token;
drop table if exists oauth_refresh_token;
drop table if exists oauth_code;

create table driver (
  id  bigserial not null,
  car_plate varchar(8) not null,
  name varchar(100) not null,
  primary key (id)
);

-- auth
create table role_application (
  id  bigserial not null,
  name varchar(255) not null,
  primary key (id)
);

create table user_application (
  id  bigserial not null,
  login varchar(255) not null,
  name varchar(255) not null,
  password varchar(255) not null,
  primary key (id)
);

create table user_role_application (
  user_id serial not null,
  role_id serial not null,
  primary key (user_id, role_id)
);

-- oauth 2.0
create table oauth_client_details (
	client_id varchar(256) primary key,
	resource_ids varchar(256),
	client_secret varchar(256),
	scope varchar(256),
	authorized_grant_types varchar(256),
	web_server_redirect_uri varchar(256),
	authorities varchar(256),
	access_token_validity integer,
	refresh_token_validity integer,
	additional_information varchar(4096),
    autoapprove varchar(256)
);

create table oauth_client_token (
  token_id varchar(256),
  token bytea,
  authentication_id varchar(256) primary key,
  user_name varchar(256),
  client_id varchar(256)
);

create table oauth_access_token (
  token_id varchar(256),
  token bytea,
  authentication_id varchar(256) primary key,
  user_name varchar(256),
  client_id varchar(256),
  authentication bytea,
  refresh_token varchar(256)
);

create table oauth_refresh_token (
  token_id varchar(256),
  token bytea,
  authentication bytea
);

create table oauth_code (
  code varchar(256),
  authentication bytea
);


alter table driver add constraint driver_uk_carplate unique (car_plate);
alter table user_application add constraint user_uk_login unique (login);
alter table user_role_application add constraint user_role_fk_role foreign key (role_id) references role_application;
alter table user_role_application add constraint user_role_fk_user foreign key (user_id) references user_application;
