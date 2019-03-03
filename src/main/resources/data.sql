   INSERT INTO users (login, password, name, address, comment)
   VALUES('admin','admin', 'Alexander', 'Odessa', 'dont call');

   INSERT INTO users (login, password, name, address, comment)
   VALUES('lex@gmail.com','A2345678', 'Alex', 'Kiev', 'call before delivery');

   INSERT INTO users (login, password, name, address, comment)
   VALUES('mash198@ukr.net','1111111', 'Maria', 'Odessa', 'dont call');

INSERT INTO categories (name) VALUES('одежда');
INSERT INTO categories (name) VALUES('обувь');
INSERT INTO categories (name) VALUES('аксессуары');

INSERT INTO products (name, category, price, description, image)
VALUES('Nora Naviano Imressive dusty blue',1, 3450, 'Голубое вечернее платье с вышитым лифом и узким поясом. Модель 2019 г. Основной материал: атлас. Легкое сияние ткани и романтичный приглушенный оттенок голубого покорят самых мечтательных девушек. ', 'dusty-blue-400x650_3400.jpg');

  INSERT INTO products (name, category, price, description, image)
  VALUES('Very berry marsala',1, 1654, 'Very berry. Длинное атласное платье с гипюровым верхом, завышенной линией талии и вырезом-лодочка. Цвет: марсала.', 'evening_dress_f1_2300.jpg');

  INSERT INTO products (name, category, price, description, image)
  VALUES('Платье "Фелиция"',1, 3400, 'Платье из шелкового сатина. Топ платья кружевной. Юбка из шелкового сатина идеально ложится на бедрах за счет складок. Восхитительно смотрятся платья А-образного кроя в атласном варианте, особенно в пастельных цветах: пепельной розе, персиковом, кофейном.', 'evening_dress_felicia_4500.jpg');

  INSERT INTO products (name, category, price, description, image)
  VALUES('Балетки ROSE GOLD Rock Glitter Ankle',2, 2100, 'Удобные стильные балетки, украшенные блестками', 'baletki_1255.jpg');

  INSERT INTO products (name, category, price, description, image)
  VALUES('Dolce & Gabbana',2, 3500, 'Туфли Dolce & Gabbana, бархат винного оттенка, украшены стразами', 'Dolce & Gabbana_3500.jpg');

  INSERT INTO products (name, category, price, description, image)
  VALUES('Rene Caovilla',2, 3750, 'Вечерние туфли Rene Caovilla, черный бархат со стразами.', 'Rene_Caovilla_4300.jpg');

  INSERT INTO products (name, category, price, description, image)
  VALUES('Женская сумка Parfois 163918-BU',3, 1429, 'Португалия, размер 28 x 29 x 13 см', 'parfois_163918-BU.jpg');

 INSERT INTO products (name, category, price, description, image)
 VALUES('Женская сумка Furla',3, 1200, 'Италия. Размер 22 х 4,5 х 15 см', 'furla_1.jpg');

 INSERT INTO products (name, category, price, description, image)
 VALUES('Вечерний клатч со стразами',3, 1200, 'Наружная часть сумочки полностью покрыта орнаментом из страз. Задняя часть аксессуара – серебристая парчовая ткань. Корпус жесткий, металлический каркас серебристого цвета. Размер 22 х 4,5 х 12 см', 'klatch.jpg');



INSERT INTO carts (userId, productId, quantity) VALUES(1, 2, 1);
INSERT INTO carts (userId, productId, quantity) VALUES(1, 1, 1);
INSERT INTO carts (userId, productId, quantity) VALUES(1, 3, 2);