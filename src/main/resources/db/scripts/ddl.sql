--- Note: Drop children before parents ---
drop table if exists cart_products Cascade;
drop table if exists carts;
drop table if exists order_products Cascade;
drop table if exists orders;
drop table if exists products;
drop table if exists users;
drop table if exists categories;
drop table if exists roles;


--- DDL Script ---
create table roles (
	id varchar,
	name varchar not null unique,
	constraint pk_role_id primary key (id)
);

create table categories (
	id varchar,
	name varchar not null unique,
	constraint pk_category_id primary key (id)
);

create table products (
	id varchar,
	name varchar not null,
	description text,
	price float not null,
	category_id varchar not null,
	constraint pk_product_id primary key (id),
	constraint fk_category_id foreign key (category_id) references categories (id) ON DELETE CASCADE
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
	constraint pk_cart_id primary key (id),
	constraint fk_user_id foreign key (user_id) references users (id)
);

create table cart_products (
	cart_id varchar,
	product_id varchar,
	quantity int not null,
	cost float not null,
	constraint pk_cart_product primary key (cart_id, product_id),
	constraint fk_cart_id foreign key (cart_id) references carts (id),
	constraint fk_product_id foreign key (product_id) references products (id) ON DELETE CASCADE
);

create table orders (
	id varchar,
	pending varchar not null,
	payment_method varchar not null,
	user_id varchar,
	constraint pk_order primary key (id),
	constraint fk_user_id foreign key (user_id) references users (id),
	created_time timestamp without time zone default current_timestamp
);

create table order_products (
	order_id varchar,
	product_id varchar,
	quantity int not null,
	cost float not null,
	constraint pk_orders_products primary key (order_id, product_id),
	constraint fk_order_id foreign key (order_id) references orders (id),
	constraint fk_product_id foreign key (product_id) references products (id) ON DELETE CASCADE
);

--- triggers---
create or replace function update_total()
returns trigger as $$
begin
	new.cost := new.quantity * (SELECT price FROM products WHERE id = new.product_id);
	return new;
end;
$$ language plpgsql;

create trigger update_total_cart_products
before insert or update on cart_products
for each row
execute procedure update_total();

create trigger update_total_order_products
before insert or update on order_products
for each row
execute procedure update_total();