package com.gmail.kramarenko104;

import com.gmail.kramarenko104.dao.CartDaoImpl;
import com.gmail.kramarenko104.dao.ProductDaoImpl;
import com.gmail.kramarenko104.dao.UserDaoImpl;
import com.gmail.kramarenko104.model.Cart;
import com.gmail.kramarenko104.model.Product;
import com.gmail.kramarenko104.service.CartServiceImpl;
import com.gmail.kramarenko104.service.ProductServiceImpl;
import com.gmail.kramarenko104.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;

public class RunApp {

    public static void main(String[] args) throws IOException {

        new Mytest().test();


//        DaoFactory daoFactory = DaoFactory.getSpecificDao();
//        ProductDao productDao = daoFactory.getProductDao();
//        String selectedCateg = "3";
//        List<Product> products;
//
//        // when form is opened at the first time (selectedCateg == null)
//        if (selectedCateg != null) {
//            products = productDao.getProductsByCategory(Integer.parseInt(selectedCateg));
//        } else {
//            products = productDao.getAllProducts();
//        }
//
//        products.forEach(e -> System.out.println(e));
//        daoFactory.deleteProductDao(productDao);

//        UserDao userDao = daoFactory.getUserDao();
//        User user = new User();
//        user.setLogin("admin");
//        user.setPassword("admin");
//        user.setName("Alexander");
//        user.setAddress("Odessa");
//        user.setComment("admin");
//        userDao.createUser(user);
//
//        user = new User();
//        user.setLogin("lex@gmail.com");
//        user.setPassword("A2345678");
//        user.setName("Alex");
//        user.setAddress("Kiev");
//        user.setComment("call before delivery");
//        userDao.createUser(user);
//
//        user = new User();
//        user.setLogin("mash198@ukr.net");
//        user.setPassword("1111111");
//        user.setName("Maria");
//        user.setAddress("Lviv");
//        user.setComment("d'ont call");
//        userDao.createUser(user);


//        ProductDao productDao = daoFactory.getProductDao();
////        List<Integer> categoriesList = prodController.getCategoriesList();
////        List<String> categoriesListS = prodController.getCategoriesListS();
//////        categoriesListS.forEach(e -> {
//////            System.out.println("cat from db: " + e);
//////        });
////
////        //String iselectedCategory =  req.getParameter("selectedCat");
////        String iselectedCategory =  "1";
////		int selectedCategory = (iselectedCategory == null? 0: Integer.parseInt(iselectedCategory));
////
//        List<Product> products = null;
////         //filter by category
////		if (selectedCategory != 0){
////            products = prodController.getProductsByCategory(selectedCategory);
////        } else
////        { // show all products
//            products = productDao.getAllProducts();
////        }
//        System.out.println("products = " + products);
//        daoFactory.closeConnection();
    }
}

class Mytest {

    @Autowired
    UserServiceImpl userService;

    public void test(){
//        ClassLoader classLoader = getClass().getClassLoader();
//        String configFile = new File(classLoader.getResource("hikari.properties").getFile()).getAbsolutePath();
//        System.out.println(configFile);

//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();

        System.out.println("--------start---------");

//        User user = new User();
//        user.setLogin("julia@gmail.com");
//        user.setPassword("1111111");
//        user.setName("julia");
//        user.setAddress("Kiev");
//        user.setComment("d'ont call");
        UserDaoImpl userDao = new UserDaoImpl();
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao);
//        userService.createUser(user);

//        user = new User();
//        user.setLogin("lex@gmail.com");
//        user.setPassword("A2345678");
//        user.setName("Alex");
//        user.setAddress("Kiev");
//        user.setComment("call before delivery");
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
//        System.out.println("---------------------------------");
//        Product product = new Product();
//        product.setPrice(3450);
//        product.setCategory(1);
//        product.setName("Nora Naviano Imressive dusty blue");
//        product.setDescription("Blue evening dress with an embroidered bodice and a narrow belt. Model of 2019 year. Main material: atlas. Light shine of a fabric and  the romantic muffled shade of blue will submit the most dreamy girls.");
//        product.setImage("dusty-blue-400x650_3400.jpg");
        ProductDaoImpl productDao = new ProductDaoImpl();
        ProductServiceImpl productService = new ProductServiceImpl();
        productService.setProductDao(productDao);
//        productService.addProduct(product);
//
//        product = new Product();
//        product.setPrice(1654);
//        product.setCategory(1);
//        product.setName("Very berry marsala");
//        product.setDescription("Very berry. Long satin dress with a guipure top, a high waistline and a boat neckline. Color: Marsala.");
//        product.setImage("evening_dress_f1_2300.jpg");
//        productService.addProduct(product);
//
//        product = new Product();
//        product.setPrice(3400);
//        product.setCategory(1);
//        product.setName("Dress Felicia");
//        product.setDescription("Silk satin dress. The top of the dress is laced. Silk satin skirt fits perfectly on the hips due to the folds. Dresses of A-shaped cut look amazing in a satin version, especially in pastel colors: ashy rose, peach, coffee colors.");
//        product.setImage("evening_dress_felicia_4500.jpg");
//        productService.addProduct(product);
//
//        product = new Product();
//        product.setPrice(2100);
//        product.setCategory(2);
//        product.setName("Shoes ROSE GOLD Rock Glitter Ankle");
//        product.setDescription("Comfortable stylish shoes decorated with sequins");
//        product.setImage("baletki_1255.jpg");
//        productService.addProduct(product);
//
//        product = new Product();
//        product.setPrice(3500);
//        product.setCategory(2);
//        product.setName("Dolce & Gabbana");
//        product.setDescription("Shoes Dolce & Gabbana, velvet wine shade, decorated with rhinestones");
//        product.setImage("Dolce & Gabbana_3500.jpg");
//        productService.addProduct(product);
//
//        product = new Product();
//        product.setPrice(3750);
//        product.setCategory(2);
//        product.setName("Rene Caovilla");
//        product.setDescription("Evening shoes Rene Caovilla, black velvet with rhinestones");
//        product.setImage("Rene_Caovilla_4300.jpg");
//        productService.addProduct(product);
//
//        product = new Product();
//        product.setPrice(1429);
//        product.setCategory(3);
//        product.setName("Lady bag Parfois 163918-BU");
//        product.setDescription("Portugal, size 28 x 29 x 13 см");
//        product.setImage("parfois_163918-BU.jpg");
//        productService.addProduct(product);
//
//        product = new Product();
//        product.setPrice(1200);
//        product.setCategory(3);
//        product.setName("Lady bag Furla");
//        product.setDescription("Italy, size 22 х 4,5 х 15 см");
//        product.setImage("furla_1.jpg");
//        productService.addProduct(product);
//
//        product = new Product();
//        product.setPrice(1200);
//        product.setCategory(3);
//        product.setName("Evening clutch with rhinestones");
//        product.setDescription("The outer part of the bag is completely covered with rhinestone ornament. The back of the accessory is silver brocade. The case is rigid, the metal frame is silver. The size 22 х 4,5 х 12 см");
//        product.setImage("klatch.jpg");
//        productService.addProduct(product);


        System.out.println("---------------------------------");

        Cart cart = new Cart(4);
        HashMap<Product, Integer> products = new HashMap<>();
        products.put(productDao.getProduct(2), 10);
        cart.setProducts(products);

        CartDaoImpl cartDao = new CartDaoImpl();
        CartServiceImpl cartService = new CartServiceImpl();
        cartService.setCartDao(cartDao);
        cartService.addCart(cart);
//        cartService.addProduct(3, 1, 1);
//        cartService.addProduct(1, 4, 3);

        System.out.println("--------end---------");
//        session.close();
    }

}
