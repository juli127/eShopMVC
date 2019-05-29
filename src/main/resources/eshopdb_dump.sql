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
--
-- Create database 'eshopdb'
--

CREATE DATABASE IF NOT EXISTS eshopdb CHARACTER SET utf8 COLLATE utf8_general_ci;
USE eshopdb;

-- --------------------------------------------------------
--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(50) DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  `login` varchar(30) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `UK_ow0gan20590jrb00upg3va2fn` (`login`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userId`, `address`, `comment`, `login`, `name`, `password`) VALUES
(1, 'Kiev', '-', 'admin', 'Alex', '1e8949373cf0dc044b49c9dd3ddd1835'),
(2, 'Kiev', 'call before delivery', 'alex@gmail.com', 'Alexander', '-5039f9f6cecff39cf359c1de1fbc2b7f'),
(3, 'Kiev', 'd''ont call', 'julia@gmail.com', 'julia', '53df562cd13c57455c2c1bbe1854458f'),
(4, 'Lviv', 'd''ont call', 'mash198@ukr.net', 'Maria', '53df562cd13c57455c2c1bbe1854458f');

-- --------------------------------------------------------
--
-- Table structure for table `products`
--

CREATE TABLE IF NOT EXISTS `products` (
  `productId` bigint(20) NOT NULL AUTO_INCREMENT,
  `category` int(11) NOT NULL,
  `description` varchar(300) DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`productId`),
  UNIQUE KEY `UK_o61fmio5yukmmiqgnxf8pnavn` (`name`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`productId`, `category`, `description`, `image`, `name`, `price`) VALUES
(1, 1, 'Blue evening dress with an embroidered bodice and a narrow belt. Model of 2019 year. Main material: atlas. Light shine of a fabric and  the romantic muffled shade of blue will submit the most dreamy girls.', 'dusty-blue-400x650_3400.jpg', 'Nora Naviano Imressive dusty blue', 3450),
(2, 1, 'Very berry. Long satin dress with a guipure top, a high waistline and a boat neckline. Color: Marsala.', 'evening_dress_f1_2300.jpg', 'Very berry marsala', 1654),
(3, 1, 'Silk satin dress. The top of the dress is laced. Silk satin skirt fits perfectly on the hips due to the folds. Dresses of A-shaped cut look amazing in a satin version, especially in pastel colors: ashy rose, peach, coffee colors.', 'evening_dress_felicia_4500.jpg', 'Dress Felicia', 3400),
(4, 2, 'Comfortable stylish shoes decorated with sequins', 'baletki_1255.jpg', 'Shoes ROSE GOLD Rock Glitter Ankle', 2100),
(5, 2, 'Shoes Dolce & Gabbana, velvet wine shade, decorated with rhinestones', 'Dolce & Gabbana_3500.jpg', 'Dolce & Gabbana', 3500),
(6, 2, 'Evening shoes Rene Caovilla, black velvet with rhinestones', 'Rene_Caovilla_4300.jpg', 'Rene Caovilla', 3750),
(7, 3, 'Portugal, size 28 x 29 x 13 см', 'parfois_163918-BU.jpg', 'Lady bag Parfois 163918-BU', 1429),
(8, 3, 'Italy, size 22 х 4,5 х 15 см', 'furla_1.jpg', 'Lady bag Furla', 1200),
(9, 3, 'The outer part of the bag is completely covered with rhinestone ornament. The back of the accessory is silver brocade. The case is rigid, the metal frame is silver. The size 22 х 4,5 х 12 см', 'klatch.jpg', 'Evening clutch with rhinestones', 1200);

-- --------------------------------------------------------
--
-- Table structure for table `carts`
--

CREATE TABLE IF NOT EXISTS `carts` (
  `cartId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  PRIMARY KEY (`cartId`),
  UNIQUE KEY `UK_qv8kq8wwjjtkstm730yvagd3k` (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `carts_products`
--

CREATE TABLE IF NOT EXISTS `carts_products` (
  `cartId` bigint(20) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `productId` bigint(20) NOT NULL,
  PRIMARY KEY (`cartId`,`productId`),
  KEY `FK2n2rk9lxbe0vnin36whtithi2` (`productId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
  `orderId` bigint(20) NOT NULL AUTO_INCREMENT,
  `orderNumber` bigint(20) NOT NULL,
  `status` varchar(20) DEFAULT NULL,
  `userId` bigint(20) NOT NULL,
  PRIMARY KEY (`orderId`),
  KEY `FK6co8q7ko456baksb6tdjq2dfv` (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `orders_products`
--

CREATE TABLE IF NOT EXISTS `orders_products` (
  `orderId` bigint(20) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `productId` bigint(20) NOT NULL,
  PRIMARY KEY (`orderId`,`productId`),
  KEY `FKexx6ud6v30pcvsvsduojigvhf` (`productId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

