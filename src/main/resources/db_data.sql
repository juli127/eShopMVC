INSERT INTO `users` (`userId`, `address`, `comment`, `login`, `name`, `password`) VALUES
(1, 'Kiev', '-', 'admin', 'Alex', '1e8949373cf0dc044b49c9dd3ddd1835'),
(2, 'Kiev', 'call before delivery', 'alex@gmail.com', 'Alexander', '-5039f9f6cecff39cf359c1de1fbc2b7f'),
(3, 'Kiev', 'd''ont call', 'julia@gmail.com', 'julia', '53df562cd13c57455c2c1bbe1854458f'),
(4, 'Lviv', 'd''ont call', 'mash198@ukr.net', 'Maria', '53df562cd13c57455c2c1bbe1854458f');


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

INSERT INTO carts (userId, productId, quantity) VALUES(1, 2, 1);
INSERT INTO carts (userId, productId, quantity) VALUES(1, 1, 1);
INSERT INTO carts (userId, productId, quantity) VALUES(2, 3, 2);
