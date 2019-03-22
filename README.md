**E-shop webapp based on Spring MVC + Ajax + Hibernate + MySQL(HikariCP):**

Were used:
- Spring Data 1.11.9.RELEASE
- Hibernate 5.0.12 Final
- MySQL Server 5.7.23
- MySQL Connector/J 8.0.13
- HikariCP 3.1.0
- Gson 2.8.5
- Maven
- Java 8

**Functionality description:**
- Not authorized user can see, choose any product but cannot buy them, notification to register or login is shown (screenshots/MainPageWithAllProducts.png);
- User can see products of all categories ("All categories" bookmark) or selected products by specific category (click on picture with desired category on the left panel);
- If user was registered before and try to register again, he/she will be resend to login page for login;
- Registration form has verification on input values;
- If user logged-in and he/she has empty cart, he/she will be resend to product page for shopping;
- If user logged-in and he/she has not empty cart, he/she will be resend to cart page for cart edition or making order;
- Cart can be edited (Ajax, JSON): products can be added/removed (screenshots/UserShoppingCart.png):
    - If some product's count becomes equals to zero, this product disappears from the cart;
    - If all products are removed from the cart, table with cart products list disappears at all;
- Press on 'make order' button creates the new order on the base of user's cart (screenshots/UserOrder.png). Shopping cart becomes empty;
- Access to staff-only info (/admin) is made through the filter. If user has no access, forbidden info is shown (screenshots/ForbiddenResources.png);


  
