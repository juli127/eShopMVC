CREATE DATABASE IF NOT EXISTS eshopdb CHARACTER SET utf8 COLLATE utf8_general_ci;
USE eshopdb;

CREATE TABLE IF NOT EXISTS users_test(
  id INT AUTO_INCREMENT,
  login VARCHAR(30) UNIQUE NOT NULL,
  password VARCHAR(30) NOT NULL,
  name VARCHAR(50),
  address VARCHAR(50),
  comment VARCHAR(100),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS categories (
  id INT AUTO_INCREMENT,
  name VARCHAR(100) UNIQUE NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS products_test (
  id INT AUTO_INCREMENT,
  name VARCHAR(100) UNIQUE NOT NULL,
  category INT,
  price INT,
  description VARCHAR(300),
  image VARCHAR(100),
  PRIMARY KEY (id),
  FOREIGN KEY (category) REFERENCES categories(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS carts_test (
  id INT AUTO_INCREMENT,
  userId INT,
  productId INT,
  quantity INT,
  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES users_test(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  FOREIGN KEY (productId) REFERENCES products_test(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS orders_test (
  id INT AUTO_INCREMENT,
  orderNumber INT,
  status VARCHAR(100),
  userId INT,
  productId INT,
  quantity INT,
  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES users_test(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  FOREIGN KEY (productId) REFERENCES products_test(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

-- from command line:   mysql -u databaseUser -p < db-schema.sql