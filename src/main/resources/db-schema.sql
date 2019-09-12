CREATE DATABASE IF NOT EXISTS eshopdb CHARACTER SET utf8 COLLATE utf8_general_ci;
USE eshopdb;

CREATE TABLE IF NOT EXISTS users(
  userId INT AUTO_INCREMENT,
  login VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(30) NOT NULL,
  name VARCHAR(50),
  address VARCHAR(50),
  comment VARCHAR(100),
  PRIMARY KEY (userId)
);

CREATE TABLE IF NOT EXISTS products (
  productId INT AUTO_INCREMENT,
  name VARCHAR(100) UNIQUE NOT NULL,
  category INT,
  price INT,
  description VARCHAR(300),
  image VARCHAR(100),
  PRIMARY KEY (productId)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS orders (
  orderId INT AUTO_INCREMENT,
  orderNumber INT,
  status VARCHAR(100),
  userId INT,
  productId INT,
  quantity INT,
  PRIMARY KEY (orderId),
  FOREIGN KEY (userId) REFERENCES users(userId)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  FOREIGN KEY (productId) REFERENCES products(productId)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS carts (
  cartId INT AUTO_INCREMENT,
  userId INT,
  productId INT,
  quantity INT,
  PRIMARY KEY (cartId),
  FOREIGN KEY (userId) REFERENCES users(userId)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  FOREIGN KEY (productId) REFERENCES products(productId)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

-- from command line:   mysql -u databaseUser -p < db-schema.sql
