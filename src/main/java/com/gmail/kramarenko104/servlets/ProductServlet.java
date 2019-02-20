package com.gmail.kramarenko104.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.gmail.kramarenko104.controllers.ProductController;
import com.gmail.kramarenko104.models.Product;

@WebServlet(name = "ProductServlet", urlPatterns = {"/", "/products"})
public class ProductServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(ProductServlet.class);

    public ProductServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductController prodContr = new ProductController();
        req.setAttribute("categories", prodContr.getCategoriesList());

        String selectedCateg = req.getParameter("selectedCateg");

        List<Product> products = null;
        // show all products when 'All categories' was selected or
        // when form is opened at the first time (selectedCateg == null)
        if ("All categories".equals(selectedCateg) || (selectedCateg == null)) {
            products = prodContr.getAllProducts();
        } else {  // filter by category
            products = prodContr.getProductsByCategory(Integer.parseInt(selectedCateg));
        }

        req.setAttribute("products", products);
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/products.jsp");
        rd.forward(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
