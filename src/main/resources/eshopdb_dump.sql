-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 28, 2019 at 08:56 PM
-- Server version: 5.5.25
-- PHP Version: 5.2.12
--
-- Database: `eshopdb`
--

-- HOTO!!!!:  run script from command line:
-- mysql -u databaseUser -p < eshopdb_dump.sql

-- --------------------------------------------------------
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

INSERT INTO eshopdb.users (userId, login, password, name, address, comment, role) VALUES (1, 'admin', '1e8949373cf0dc044b49c9dd3ddd1835', 'Alex', 'Kiev', '-', 'ROLE_ADMIN');
INSERT INTO eshopdb.users (userId, login, password, name, address, comment, role) VALUES (2, 'alex@gmail.com', '-5039f9f6cecff39cf359c1de1fbc2b7f', 'Alexander', 'Kiev', 'call before delivery', 'ROLE_USER');
INSERT INTO eshopdb.users (userId, login, password, name, address, comment, role) VALUES (3, 'julia@gmail.com', '53df562cd13c57455c2c1bbe1854458f', 'julia', 'Kiev', 'd''ont call', 'ROLE_USER');
INSERT INTO eshopdb.users (userId, login, password, name, address, comment, role) VALUES (4, 'mash198@ukr.net', '53df562cd13c57455c2c1bbe1854458f', 'Maria', 'Lviv', 'd''ont call', 'ROLE_USER');

INSERT INTO eshopdb.products (productId, name, category, price, description, image) VALUES (1, 'Nora Naviano Imressive dusty blue', 1, 3450, 'Blue evening dress with an embroidered bodice and a narrow belt. Model of 2019 year. Main material: atlas. Light shine of a fabric and  the romantic muffled shade of blue will submit the most dreamy girls.', 'dusty-blue-400x650_3400.jpg');
INSERT INTO eshopdb.products (productId, name, category, price, description, image) VALUES (2, 'Very berry marsala', 1, 1654, 'Very berry. Long satin dress with a guipure top, a high waistline and a boat neckline. Color: Marsala.', 'evening_dress_f1_2300.jpg');
INSERT INTO eshopdb.products (productId, name, category, price, description, image) VALUES (3, 'Dress Felicia', 1, 3400, 'Silk satin dress. The top of the dress is laced. Silk satin skirt fits perfectly on the hips due to the folds. Dresses of A-shaped cut look amazing in a satin version, especially in pastel colors: ashy rose, peach, coffee colors.', 'evening_dress_felicia_4500.jpg');
INSERT INTO eshopdb.products (productId, name, category, price, description, image) VALUES (4, 'Shoes ROSE GOLD Rock Glitter Ankle', 2, 2100, 'Comfortable stylish shoes decorated with sequins', 'baletki_1255.jpg');
INSERT INTO eshopdb.products (productId, name, category, price, description, image) VALUES (5, 'Dolce & Gabbana', 2, 3500, 'Shoes Dolce & Gabbana, velvet wine shade, decorated with rhinestones', 'Dolce & Gabbana_3500.jpg');
INSERT INTO eshopdb.products (productId, name, category, price, description, image) VALUES (6, 'Rene Caovilla', 2, 3750, 'Evening shoes Rene Caovilla, black velvet with rhinestones', 'Rene_Caovilla_4300.jpg');
INSERT INTO eshopdb.products (productId, name, category, price, description, image) VALUES (7, 'Lady bag Parfois 163918-BU', 3, 1429, 'Portugal, size 28 x 29 x 13 см', 'parfois_163918-BU.jpg');
INSERT INTO eshopdb.products (productId, name, category, price, description, image) VALUES (8, 'Lady bag Furla', 3, 1200, 'Italy, size 22 х 4,5 х 15 см', 'furla_1.jpg');
INSERT INTO eshopdb.products (productId, name, category, price, description, image) VALUES (9, 'Evening clutch with rhinestones', 3, 1200, 'The outer part of the bag is completely covered with rhinestone ornament. The back of the accessory is silver brocade. The case is rigid, the metal frame is silver. The size 22 х 4,5 х 12 см', 'klatch.jpg');

INSERT INTO eshopdb.carts (cartId, userId) VALUES (1, 1);
INSERT INTO eshopdb.carts (cartId, userId) VALUES (2, 2);

INSERT INTO eshopdb.carts_products (cartId, productId, quantity) VALUES (1, 2, 1);
INSERT INTO eshopdb.carts_products (cartId, productId, quantity) VALUES (1, 1, 3);
INSERT INTO eshopdb.carts_products (cartId, productId, quantity) VALUES (2, 3, 2);
