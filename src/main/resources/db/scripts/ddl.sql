--- Note: Drop children before parents ---
drop table if exists cart_products Cascade;
drop table if exists carts;
drop table if exists orders_products Cascade;
drop table if exists orders;
drop table if exists products;
drop table if exists users;
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
	price float not null,
	constraint pk_product_id primary key (id)
	
);

create table users (
	id varchar,
	email varchar not null unique,
	password varchar not null,
	full_name varchar not null,
	role_id varchar,
	constraint pk_user_id primary key (id),
	constraint fk_role_id foreign key (role_id) references roles (id)
);

create table carts (
	id varchar,
	user_id varchar,
	total_cost money not null,
	constraint pk_cart_id primary key (id),
	constraint fk_user_id foreign key (user_id) references users (id)
);

create table cart_products (
	cart_id varchar,
	product_id varchar,
	quantity int not null,
	constraint pk_cart_product primary key (cart_id, product_id),
	constraint fk_cart_id foreign key (cart_id) references carts (id),
	constraint fk_product_id foreign key (product_id) references products (id)
);

create table orders (
	id varchar,
	pending varchar not null,
	payment_method varchar not null,
	user_id varchar,
	constraint pk_order primary key (id),
	constraint fk_user_id foreign key (user_id) references users (id)
);

create table orders_products (
	order_id varchar,
	product_id varchar,
	quantity int not null,
	constraint pk_orders_products primary key (order_id, product_id),
	constraint fk_order_id foreign key (order_id) references orders (id),
	constraint fk_product_id foreign key (product_id) references products (id)
);


--- for testing ---
insert into roles (id, name) values ('16a4678d-bde4-4c1f-8b7d-5ecb07c80bfb', 'DEFAULT');
insert into roles (id, name) values ('3a4678d-gde4-4clf-8b0d-5emb07c80bew', 'ADMIN');
