--- Note: Drop children before parents ---
drop table if exists users;
drop table if exists products;
drop table if exists checkout;
drop table if exists roles;


--- DDL Script ---
create table roles (
	id varchar,
	name varchar not null unique,
	constraint pk_role_id primary key (id)
);

create table products (
	id varchar,
	name varchar not null,
	description text,
	price money not null,
	constraint pk_product_id primary key (id)
	
);

create table checkout (
	id varchar,
	--- TODO make many to many ---
	--- productList varchar, ---
	success bit not null,
	payment_method varchar not null,
	total_cost money not null,
	constraint pk_checkout_id primary key (id)
	
);

create table users (
	id varchar,
	username varchar not null unique,
	password varchar not null,
	role_id varchar,
	--- TODO make many-to-one ---
	--- checkout_history varchar, ---
	constraint pk_user_id primary key (id),
	constraint fk_role_id foreign key (role_id) references roles (id)
);

--- for testing ---
insert into roles (id, name) values ('16a4678d-bde4-4c1f-8b7d-5ecb07c80bfb', 'DEFAULT');
insert into roles (id, name) values ('3a4678d-gde4-4clf-8b0d-5emb07c80bew', 'ADMIN');