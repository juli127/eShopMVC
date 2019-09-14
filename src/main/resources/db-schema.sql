CREATE DATABASE IF NOT EXISTS eshopdb CHARACTER SET utf8 COLLATE utf8_general_ci;
USE eshopdb;

create table if not exists products
(
	productId bigint auto_increment
		primary key,
	name varchar(100) not null,
	category int not null,
	price int not null,
	description varchar(300) null,
	image varchar(100) null,
	constraint UK_o61fmio5yukmmiqgnxf8pnavn
		unique (name)
);

create table if not exists users
(
	userId bigint auto_increment
		primary key,
	login varchar(100) not null,
	password varchar(50) not null,
	name varchar(50) not null,
	address varchar(50) not null,
	comment varchar(100) null,
	role varchar(10) default 'ROLE_USER' not null,
	constraint UK_ow0gan20590jrb00upg3va2fn
		unique (login)
);

create table if not exists carts
(
	cartId bigint auto_increment
		primary key,
	userId bigint not null,
	constraint UK_qv8kq8wwjjtkstm730yvagd3k
		unique (userId),
	constraint FK4me2y3nkxk7fj54tt3g5dg9vh
		foreign key (userId) references users (userId)
);

create table if not exists carts_products
(
	cartId bigint not null,
	productId bigint not null,
	quantity int null,
	primary key (cartId, productId),
	constraint FK2n2rk9lxbe0vnin36whtithi2
		foreign key (productId) references products (productId),
	constraint FKrjdnan3si1wuhltvjgac5180b
		foreign key (cartId) references carts (cartId));

create table if not exists orders
(
	orderId bigint auto_increment
		primary key,
	orderNumber bigint not null,
	userId bigint not null,
	status varchar(20) null,
	constraint FK6co8q7ko456baksb6tdjq2dfv
		foreign key (userId) references users (userId)
);

create table if not exists orders_products
(
	orderId bigint not null,
	productId bigint not null,
	quantity int null,
	primary key (orderId, productId),
	constraint FK7f6edi536oe83r32wsqd6qagj
		foreign key (orderId) references orders (orderId),
	constraint FKexx6ud6v30pcvsvsduojigvhf
		foreign key (productId) references products (productId)
);