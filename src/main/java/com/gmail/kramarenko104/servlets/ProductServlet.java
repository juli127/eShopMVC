package com.gmail.kramarenko104.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gmail.kramarenko104.dao.ProductDao;
import com.gmail.kramarenko104.factoryDao.DaoFactory;
import org.apache.log4j.Logger;
import com.gmail.kramarenko104.models.Product;

@WebServlet(name = "ProductServlet", urlPatterns = {"/", "/products"})
public class ProductServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(ProductServlet.class);
    private DaoFactory daoFactory;

    public ProductServlet() {
        daoFactory = DaoFactory.getSpecificDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDao = daoFactory.getProductDao();
//        req.setAttribute("categories", productDao.getCategoriesList());
        String selectedCateg = req.getParameter("selectedCategory");
        List<Product> products;

        // when form is opened at the first time (selectedCateg == null)
        if (selectedCateg != null) {
            products = productDao.getProductsByCategory(Integer.parseInt(selectedCateg));
        } else {
            products = productDao.getAllProducts();
        }

        req.setAttribute("selectedCateg", selectedCateg);
        req.setAttribute("products", products);
        req.setAttribute("selectedCategIsNull", (selectedCateg==null));

        getServletContext().setAttribute("products", products);

//        products.forEach(e -> System.out.println(e));
        daoFactory.closeConnection();
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/products.jsp");
        rd.forward(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
