package com.gmail.kramarenko104;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class RunApp {

    private static Logger logger = LoggerFactory.getLogger(RunApp.class);
    private final static String SALT = "34Ru9k";

    public static void main(String[] args) throws IOException {

        logger.debug("[eshop] --------start---------");
//        List<User> usersList = new ArrayList<>();

//        UserDaoImpl userDao = new UserDaoImpl();
//        UserServiceImpl userService = new UserServiceImpl();
//        userService.setUserDao(userDao);
//
//        User user = new User();
//        user.setLogin("admin");
//        user.setPassword("admin");
//        user.setName("Alex");
//        user.setAddress("Kiev");
//        user.setComment("-");
//        userService.createUser(user);
//
//        user = new User();
//        user.setLogin("alex@gmail.com");
//        user.setPassword("A2345678");
//        user.setName("Alexander");
//        user.setAddress("Kiev");
//        user.setComment("call before delivery");
//        userService.createUser(user);
//
//        user = new User();
//        user.setLogin("julia@gmail.com");
//        user.setPassword("1111111");
//        user.setName("julia");
//        user.setAddress("Kiev");
//        user.setComment("d'ont call");
//        userService.createUser(user);
//
//        user = new User();
//        user.setLogin("mash198@ukr.net");
//        user.setPassword("1111111");
//        user.setName("Maria");
//        user.setAddress("Lviv");
//        user.setComment("d'ont call");
//        userService.createUser(user);
//
//        logger.debug("[eshop] ---------------------------------");
//        ProductDaoImpl productDao = new ProductDaoImpl();
//        ProductServiceImpl productService = new ProductServiceImpl();
//        productService.setProductDao(productDao);
//
//        Product product = new Product();
//        product.setPrice(3450);
//        product.setCategory(1);
//        product.setName("Nora Naviano Imressive dusty blue");
//        product.setDescription("Blue evening dress with an embroidered bodice and a narrow belt. Model of 2019 year. Main material: atlas. Light shine of a fabric and  the romantic muffled shade of blue will submit the most dreamy girls.");
//        product.setImage("dusty-blue-400x650_3400.jpg");
//        productService.createProduct(product);
//
//        product = new Product();
//        product.setPrice(1654);
//        product.setCategory(1);
//        product.setName("Very berry marsala");
//        product.setDescription("Very berry. Long satin dress with a guipure top, a high waistline and a boat neckline. Color: Marsala.");
//        product.setImage("evening_dress_f1_2300.jpg");
//        productService.createProduct(product);
//
//        product = new Product();
//        product.setPrice(3400);
//        product.setCategory(1);
//        product.setName("Dress Felicia");
//        product.setDescription("Silk satin dress. The top of the dress is laced. Silk satin skirt fits perfectly on the hips due to the folds. Dresses of A-shaped cut look amazing in a satin version, especially in pastel colors: ashy rose, peach, coffee colors.");
//        product.setImage("evening_dress_felicia_4500.jpg");
//        productService.createProduct(product);
//
//        product = new Product();
//        product.setPrice(2100);
//        product.setCategory(2);
//        product.setName("Shoes ROSE GOLD Rock Glitter Ankle");
//        product.setDescription("Comfortable stylish shoes decorated with sequins");
//        product.setImage("baletki_1255.jpg");
//        productService.createProduct(product);
//
//        product = new Product();
//        product.setPrice(3500);
//        product.setCategory(2);
//        product.setName("Dolce & Gabbana");
//        product.setDescription("Shoes Dolce & Gabbana, velvet wine shade, decorated with rhinestones");
//        product.setImage("Dolce & Gabbana_3500.jpg");
//        productService.createProduct(product);
//
//        product = new Product();
//        product.setPrice(3750);
//        product.setCategory(2);
//        product.setName("Rene Caovilla");
//        product.setDescription("Evening shoes Rene Caovilla, black velvet with rhinestones");
//        product.setImage("Rene_Caovilla_4300.jpg");
//        productService.createProduct(product);
//
//        product = new Product();
//        product.setPrice(1429);
//        product.setCategory(3);
//        product.setName("Lady bag Parfois 163918-BU");
//        product.setDescription("Portugal, size 28 x 29 x 13 см");
//        product.setImage("parfois_163918-BU.jpg");
//        productService.createProduct(product);
//
//        product = new Product();
//        product.setPrice(1200);
//        product.setCategory(3);
//        product.setName("Lady bag Furla");
//        product.setDescription("Italy, size 22 х 4,5 х 15 см");
//        product.setImage("furla_1.jpg");
//        productService.createProduct(product);
//
//        product = new Product();
//        product.setPrice(1200);
//        product.setCategory(3);
//        product.setName("Evening clutch with rhinestones");
//        product.setDescription("The outer part of the bag is completely covered with rhinestone ornament. The back of the accessory is silver brocade. The case is rigid, the metal frame is silver. The size 22 х 4,5 х 12 см");
//        product.setImage("klatch.jpg");
//        productService.createProduct(product);

//        logger.debug("[eshop] ---------cart functionality testing------------------------");

//        CartDaoImpl cartDao = new CartDaoImpl();
//        CartServiceImpl cartService = new CartServiceImpl();
//        cartService.setDaos(cartDao, productDao);

//        Cart cart = new Cart();
//        User user = userService.getUser(5);
//        cart.setUser(user);
//        cartService.createCart(cart);
//        logger.debug("[eshop] main: Julia: " + cartService.addProduct(5, 2, 22));
//        cartService.removeProduct(1, 3, 12);
//        System.out.println(cartService.getCartByUserId(1));
//        cartService.clearCartByUserId(10);
//

//        Cart cart = new Cart();
//        cart.setUser(userService.getUser(1));
//        cart.getProducts().put(productDao.getProduct(1), 1);
//        cart.getProducts().put(productDao.getProduct(2), 1);
//        cartService.addCart(cart);
//
//        cart = new Cart();
//        cart.setUser(userService.getUser(2));
//        cart.getProducts().put(productDao.getProduct(5), 1);
//        cart.getProducts().put(productDao.getProduct(3), 2);
//        cartService.addCart(cart);


//        -------------------order functionality testing------------------------------
//        Order order = new Order();
//         User user = userService.getUser(4);
//        order.setUser(user);

//        OrderDaoImpl orderDao = new OrderDaoImpl();
//        OrderServiceImpl orderService = new OrderServiceImpl();
//        orderService.setOrderDao(orderDao, userDao, cartDao);

//        orderService.createOrder(user.getUserId(), cartDao.getCartByUserId(user.getUserId()).getProducts());
//        orderService.getAll().stream().forEach(p -> System.out.println(p));

//        List<Product> products = orderService.;
//        for (Product p: products) {
//            System.out.println(p.getName());
//        }

//        List<User> users = userService.getAllUsers();
//        for (User u: users) {
//            System.out.println(u.getName());
//        }


//        System.out.println(cartDao.getAll());
//        dbUser.setComment("do nothing");
//        userService.update(dbUser);

//        userService.deleteUser(5);
        logger.debug("[eshop] --------end---------");
    }
}

