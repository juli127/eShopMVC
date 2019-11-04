CREATE DATABASE IF NOT EXISTS eshopdb CHARACTER SET utf8 COLLATE utf8_general_ci;
USE eshopdb;

CREATE TABLE IF NOT EXISTS users
(
	userId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	login VARCHAR (100) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
	name VARCHAR(50) NOT NULL,
	address VARCHAR(50) NOT NULL,
	comment VARCHAR(100)
) 
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS roles
(
  roleId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) default 'ROLE_USER' NOT NULL
) 
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS user_roles
(
  userId INT NOT NULL,
  roleId INT NOT NULL,
  FOREIGN KEY (userId) REFERENCES users (userId),
  FOREIGN KEY (roleId) REFERENCES roles (roleId),
  UNIQUE (userId, roleId)
) 
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS products
(
	productId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(100) NOT NULL UNIQUE,
	category int NOT NULL,
	price int NOT NULL,
	description VARCHAR(300),
	image VARCHAR(100)
) 
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS carts
(
	cartId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	userId int NOT NULL,
	FOREIGN KEY (userId) REFERENCES users (userId)
)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS carts_products
(
	cartId INT NOT NULL,
	productId INT NOT NULL,
	quantity INT,
	PRIMARY KEY (cartId, productId),
	FOREIGN KEY (productId) REFERENCES products (productId),
	FOREIGN KEY (cartId) REFERENCES carts (cartId)
) 
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS orders
(
	orderId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	orderNumber INT NOT NULL,
	userId INT NOT NULL,
	status VARCHAR(20),
	FOREIGN KEY (userId) REFERENCES users (userId)
) 
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS orders_products
(
	orderId INT NOT NULL,
	productId INT NOT NULL,
	quantity INT,
	PRIMARY KEY (orderId, productId),
	FOREIGN KEY (orderId) REFERENCES orders (orderId),
	FOREIGN KEY (productId) REFERENCES products (productId)
) 
ENGINE = InnoDB;
