package com.gmail.kramarenko104.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gmail.kramarenko104.dao.ProductDao;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import org.apache.log4j.Logger;
import com.gmail.kramarenko104.model.Product;

@WebServlet(name = "ProductServlet", urlPatterns = {"/", "/products"})
public class ProductServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(ProductServlet.class);
    private DaoFactory daoFactory;

    public ProductServlet() {
        daoFactory = DaoFactory.getSpecificDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        ProductDao productDao = daoFactory.getProductDao();

        String selectedCateg = req.getParameter("selectedCategory");
        List<Product> allProducts;

        // when form is opened at the first time, selectedCateg == null
        if (selectedCateg != null) {
            allProducts = productDao.getProductsByCategory(Integer.parseInt(selectedCateg));
        } else {
            allProducts = productDao.getAllProducts();
        }

        session.setAttribute("selectedCateg", selectedCateg);
//        session.setAttribute("allProducts", allProducts);
//        session.setAttribute("productsIds", allProducts.stream().map(product -> product.getId()).collect(Collectors.toList()));
//        session.setAttribute("selectedCategIsNull", (selectedCateg==null));

//        getServletContext().setAttribute("products", products);
//        products.forEach(e -> System.out.println(e));
        daoFactory.deleteProductDao(productDao);

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/products.jsp");
        rd.forward(req, resp);
    }

    @Override
    public void destroy() {
        daoFactory.closeConnection();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
