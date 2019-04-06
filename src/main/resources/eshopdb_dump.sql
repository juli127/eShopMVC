-- phpMyAdmin SQL Dump
-- Host: localhost:3306
-- Generation Time: Mar 21, 2019 at 04:26 PM
-- Server version: 5.7.23

-- Database: `eshopdb`
-- --------------------------------------------------------
--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `login` varchar(40) NOT NULL,
  `password` varchar(80) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `login`, `password`, `name`, `address`, `comment`) VALUES
(1, 'admin', '1e8949373cf0dc044b49c9dd3ddd1835', 'Alexander', 'Odessa', 'admin'),
(2, 'lex@gmail.com', '-5039f9f6cecff39cf359c1de1fbc2b7f', 'Alex', 'Kiev', 'call before delivery'),
(3, 'mash198@ukr.net', '53df562cd13c57455c2c1bbe1854458f', 'Maria', 'Lviv', 'don\'t call'),
(4, 'julia@gmail.com', '-63d66444ee4a31da4af4872610e66a14', 'Julia', 'Kiev', 'don\'t call'),
(5, 'serg@ukr.net', '-63d66444ee4a31da4af4872610e66a14', 'Serg Kovalenko', 'Kiev, Zdolbunovskogo str., 38', '-'),
(6, 't@test.com', '-63d66444ee4a31da4af4872610e66a14', 'test', 'kiev', '');

-- --------------------------------------------------------

-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`) VALUES
(1, 'dresses'),
(2, 'shoes'),
(3, 'accessories');

-- --------------------------------------------------------
--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `category` int(11) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `name`, `category`, `price`, `description`, `image`) VALUES
(1, 'Nora Naviano Imressive dusty blue', 1, 3450, 'Blue evening dress with an embroidered bodice and a narrow belt. Model of 2019 year. Main material: atlas. Light shine of a fabric and the romantic muffled shade of blue will submit the most dreamy girls. ', 'dusty-blue-400x650_3400.jpg'),
(2, 'Very berry marsala', 1, 1654, 'Very berry. Long satin dress with a guipure top, a high waistline and a boat neckline. Color: Marsala.', 'evening_dress_f1_2300.jpg'),
(3, 'Dress \'Felicia\'', 1, 3400, 'Silk satin dress. The top of the dress is laced. Silk satin skirt fits perfectly on the hips due to the folds. Dresses of A-shaped cut look amazing in a satin version, especially in pastel colors: ashy rose, peach, coffee colors.', 'evening_dress_felicia_4500.jpg'),
(4, 'Shoes ROSE GOLD Rock Glitter Ankle', 2, 2100, 'Comfortable stylish shoes decorated with sequins', 'baletki_1255.jpg'),
(5, 'Dolce & Gabbana', 2, 3500, 'Shoes Dolce & Gabbana, velvet wine shade, decorated with rhinestones', 'Dolce & Gabbana_3500.jpg'),
(6, 'Rene Caovilla', 2, 3750, 'Evening shoes Rene Caovilla, black velvet with rhinestones.', 'Rene_Caovilla_4300.jpg'),
(7, 'Lady bag Parfois 163918-BU', 3, 1429, 'Portugal, size 28 x 29 x 13 см', 'parfois_163918-BU.jpg'),
(8, 'Lady bag Furla', 3, 1200, 'Italy, size 22 х 4,5 х 15 см', 'furla_1.jpg'),
(9, 'Evening clutch with rhinestones', 3, 1200, 'The outer part of the bag is completely covered with rhinestone ornament. The back of the accessory is silver brocade. The case is rigid, the metal frame is silver. The size 22 х 4,5 х 12 см', 'klatch.jpg');

-- --------------------------------------------------------

-- Table structure for table `carts`
--

CREATE TABLE `carts` (
  `userId` int(11) DEFAULT NULL,
  `productId` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `carts`
--

INSERT INTO `carts` (`userId`, `productId`, `quantity`) VALUES
(1, 1, 3),
(1, 5, 1),
(1, 4, 2);

-- --------------------------------------------------------
--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `orderNumber` int(11) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `productId` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`orderNumber`, `status`, `userId`, `productId`, `quantity`) VALUES
(1, 'ordered', 1, 5, 3),
(1, 'ordered', 1, 1, 3),
(2, 'ordered', 1, 2, 2),
(3, 'ordered', 1, 7, 1),
(4, 'ordered', 1, 9, 2),
(4, 'ordered', 1, 1, 1),
(5, 'ordered', 1, 4, 2),
(5, 'ordered', 1, 2, 1),
(6, 'ordered', 1, 1, 1),
(7, 'ordered', 1, 1, 1),
(8, 'ordered', 1, 4, 4),
(9, 'ordered', 1, 1, 1),
(9, 'ordered', 1, 4, 1),
(10, 'ordered', 4, 1, 1),
(11, 'ordered', 4, 2, 3),
(12, 'ordered', 4, 9, 3),
(13, 'ordered', 4, 5, 1),
(13, 'ordered', 4, 4, 2),
(14, 'ordered', 4, 1, 1),
(15, 'ordered', 4, 4, 1),
(15, 'ordered', 4, 2, 1),
(16, 'ordered', 4, 3, 2),
(17, 'ordered', 4, 8, 3),
(18, 'ordered', 9, 5, 1),
(18, 'ordered', 9, 3, 3),
(18, 'ordered', 9, 1, 1),
(19, 'ordered', 4, 3, 1),
(20, 'ordered', 4, 2, 1),
(21, 'ordered', 4, 5, 1),
(21, 'ordered', 4, 9, 2),
(21, 'ordered', 4, 1, 1),
(22, 'ordered', 1, 2, 1),
(23, 'ordered', 1, 7, 3),
(24, 'ordered', 4, 1, 1);

-- --------------------------------------------------------
--
-- Indexes for dumped tables
--

--
-- Indexes for table `carts`
--
ALTER TABLE `carts`
  ADD KEY `userId` (`userId`),
  ADD KEY `productId` (`productId`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD KEY `userId` (`userId`),
  ADD KEY `productId` (`productId`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`),
  ADD KEY `category` (`category`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `login` (`login`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `carts`
--
ALTER TABLE `carts`
  ADD CONSTRAINT `carts_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `carts_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `products` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `products` (`id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category`) REFERENCES `categories` (`id`);
SET FOREIGN_KEY_CHECKS=1;
