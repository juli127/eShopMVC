package com.gmail.kramarenko104.factoryDao;

import com.gmail.kramarenko104.dao.CartDao;
import com.gmail.kramarenko104.dao.ProductDao;
import com.gmail.kramarenko104.dao.UserDao;
import java.util.ResourceBundle;

public abstract class DaoFactory {

	public abstract UserDao getUserDao();
	public abstract ProductDao getProductDao();
	public abstract CartDao getCartDao();

	public abstract void deleteUserDao(UserDao userDao);
	public abstract void deleteProductDao(ProductDao productDao);
	public abstract void deleteCartDao(CartDao cartDao);

	public abstract void closeConnection();

	public static DaoFactory getSpecificDao(){
		ResourceBundle config = ResourceBundle.getBundle("config");
		DaoFactory daoFactory = null;
		try {
			daoFactory = (DaoFactory) Class.forName(config.getString("factoryClass")).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return daoFactory;
	}

}
