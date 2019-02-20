package com.gmail.kramarenko104.servlets;

import org.apache.log4j.Logger;
import com.gmail.kramarenko104.models.Cart;
import com.gmail.kramarenko104.models.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(CartServlet.class);

    public CartServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        int idSelectedProdust = Integer.valueOf(req.getParameter("category"));
        if (cart == null){
            cart = new Cart();
        }
        Product product = new Product();
        product.setId(Integer.valueOf(req.getParameter("productID")));
        cart.addProduct(product);
        session.setAttribute("cart", cart);

 //       resp.sendRedirect(req.getAttribute());
//        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/cart.jsp");
//        rd.forward(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
