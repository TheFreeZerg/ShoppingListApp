package logic;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Builds various Queries to insert, update and delete data from the SQLite File.
 * The Query itself is used in SQLHandler.
 */
public class QueryBuilder {
	/**
	 * inserts a product
	 */
	public static void insertProduct() {
        SQLHandler dbc = SQLHandler.getInstance();
        dbc.makeStatement("INSERT INTO product(name,brand,icon,prio) "
        		+ "VALUES('new_product','NA',0,0)");
	}

	/**
	 * Updates the product with the corresponding id with the passed params
	 * @param id ID from the product which is to be updated
	 * @param name new Name
	 * @param brand new Brand
	 * @param icon new icon number
	 * @param prio new Priority
	 */
	public static void updateProduct(int id, String name, String brand, int icon, int prio) {
        SQLHandler dbc = SQLHandler.getInstance();
        dbc.makeStatement("UPDATE product "
        		+ "SET name = '"+name+"', "
        		+ "brand = '"+brand+"', "
                + "icon = "+icon+", "
                + "prio = "+prio+" "
        		+ "WHERE id = "+id);
	}

	/**
	 * Deletes the Product with the corresponding id
	 * @param id ID from the Product which is to be deleted
	 */
	public static void deleteProduct(int id) {
        SQLHandler dbc = SQLHandler.getInstance();
        dbc.makeStatement("DELETE FROM product "
        		+ "WHERE id = "+id);
	}

	/**
	 * creates new shopping list
	 */
	public static void insertList() {
        SQLHandler dbc = SQLHandler.getInstance();
        dbc.makeStatement("INSERT INTO list(name) "
        		+ "VALUES('new_list')");
	}

	/**
	 * Updates the list with the corresponding id with the passed name and contraction
	 * @param id ID from the list which is to be updated
	 * @param name new Name
	 */
	public static void updateList(int id, String name) {
        SQLHandler dbc = SQLHandler.getInstance();
        dbc.makeStatement("UPDATE list "
        		+ "SET name = '"+name+"' "
        		+ "WHERE id = "+id);
	}

	/**
	 * Deletes the list with the corresponding id
	 * @param id ID from the list which is to be deleted
	 */
	public static void deleteList(int id) {
        SQLHandler dbc = SQLHandler.getInstance();
        dbc.makeStatement("DELETE FROM list "
        		+ "WHERE id = "+id);
	}

	/**
	 * adds a product on a list
	 * @param listID
	 * @param productID
	 * @param amount
	 */
	public static void insertListProduct(int listID, int productID, int amount) {
		SQLHandler dbc = SQLHandler.getInstance();
		
        dbc.makeStatement("INSERT INTO list_product(list_id,product_id,amount) "
        		+ "VALUES("+listID+","+productID+","+amount+")");
	}

	/**
	 * Updates the list
	 * @param listID
	 * @param productID
	 * @param amount
	 */
	public static void updateListProduct(int listID, int productID, int amount) {
		SQLHandler dbc = SQLHandler.getInstance();
		
		dbc.makeStatement("UPDATE list_product "
				+ "SET amount = "+amount+" "
				+ "WHERE list_id = "+listID+" "
				+ "AND product_id = "+productID);
	}

	/**
	 * Removes a product of a list
	 * @param listID
	 * @param productID
	 */
	public static void deletePlayerAttribute(int listID, int productID) {
        SQLHandler dbc = SQLHandler.getInstance();
        dbc.makeStatement("DELETE FROM list_product "
        		+ "WHERE list_id = "+listID+" "
        		+ "AND product_id = "+productID);
	}
}
