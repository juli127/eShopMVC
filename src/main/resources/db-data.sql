 -- pasw: admin
   INSERT INTO users_test (login, password, name, address, comment)
   VALUES('admin','1e8949373cf0dc044b49c9dd3ddd1835', 'Alexander', 'Odessa', 'dont call');

-- pasw: A2345678
   INSERT INTO users_test (login, password, name, address, comment)
   VALUES('lex@gmail.com','-5039f9f6cecff39cf359c1de1fbc2b7f', 'Alex', 'Kiev', 'call before delivery');

-- pasw: 1111111
   INSERT INTO users_test (login, password, name, address, comment)
   VALUES('mash198@ukr.net','53df562cd13c57455c2c1bbe1854458f', 'Maria', 'Odessa', 'dont call');

INSERT INTO categories (name) VALUES('dresses');
INSERT INTO categories (name) VALUES('shoes');
INSERT INTO categories (name) VALUES('accessories');

INSERT INTO products_test (name, category, price, description, image)
VALUES('Nora Naviano Imressive dusty blue',1, 3450, 'Blue evening dress with an embroidered bodice and a narrow belt. Model of 2019 year. Main material: atlas. Light shine of a fabric and the romantic muffled shade of blue will submit the most dreamy girls. ', 'dusty-blue-400x650_3400.jpg');

  INSERT INTO products_test (name, category, price, description, image)
  VALUES('Very berry marsala',1, 1654, 'Very berry. Long satin dress with a guipure top, a high waistline and a boat neckline. Color: Marsala.', 'evening_dress_f1_2300.jpg');

  INSERT INTO products_test (name, category, price, description, image)
  VALUES('Dress "Felicia"',1, 3400, 'Silk satin dress. The top of the dress is laced. Silk satin skirt fits perfectly on the hips due to the folds. Dresses of A-shaped cut look amazing in a satin version, especially in pastel colors: ashy rose, peach, coffee colors.', 'evening_dress_felicia_4500.jpg');

  INSERT INTO products_test (name, category, price, description, image)
  VALUES('Shoes ROSE GOLD Rock Glitter Ankle',2, 2100, 'Comfortable stylish shoes decorated with sequins', 'baletki_1255.jpg');

  INSERT INTO products_test (name, category, price, description, image)
  VALUES('Dolce & Gabbana',2, 3500, 'Shoes Dolce & Gabbana, velvet wine shade, decorated with rhinestones', 'Dolce & Gabbana_3500.jpg');

  INSERT INTO products_test (name, category, price, description, image)
  VALUES('Rene Caovilla',2, 3750, 'Evening shoes Rene Caovilla, black velvet with rhinestones.', 'Rene_Caovilla_4300.jpg');

  INSERT INTO products (name, category, price, description, image)
  VALUES('Lady bag Parfois 163918-BU',3, 1429, 'Portugal, size 28 x 29 x 13 см', 'parfois_163918-BU.jpg');

 INSERT INTO products_test (name, category, price, description, image)
 VALUES('Lady bag Furla',3, 1200, 'Italy, size 22 х 4,5 х 15 см', 'furla_1.jpg');

 INSERT INTO products_test (name, category, price, description, image)
 VALUES('Evening clutch with rhinestones', 3, 1200, 'The outer part of the bag is completely covered with rhinestone ornament. The back of the accessory is silver brocade. The case is rigid, the metal frame is silver. The size 22 х 4,5 х 12 см', 'klatch.jpg');



INSERT INTO carts_test (userId, productId, quantity) VALUES(1, 2, 1);
INSERT INTO carts_test (userId, productId, quantity) VALUES(1, 1, 1);
INSERT INTO carts_test (userId, productId, quantity) VALUES(2, 3, 2);

-- from command line:   mysql -u databaseUser -p < db-data.sql