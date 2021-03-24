package logic;

public class Main {
	public static void main(String[] args) {
		/*
		 * ShopList sp = new ShopList("Wocheneinkauf"); sp.addProduct(new
		 * Product("Zuchini", null, 0, 1, 0)); sp.addProduct(new Product("Schinken",
		 * null, 0, 1, 0)); sp.addProduct(new Product("Apfel", null, 0, 1, 0));
		 * 
		 * sp.sort(); sp.printList();
		 */

		SQLHandler.fileName = "db";
        SQLHandler dbc = SQLHandler.getInstance();
        dbc.buildConnection();
        dbc.createEmptyFile();
		
        QueryBuilder.insertProduct();
        QueryBuilder.updateProduct(1, "Schinken", "Maika", 0, 0);
        QueryBuilder.insertProduct();
        QueryBuilder.updateProduct(2, "Käse", "Edammer", 0, 3);
        QueryBuilder.insertProduct();
        QueryBuilder.updateProduct(3, "Milch", "Hoffmann", 0, 2);
        
        QueryBuilder.insertList();
        QueryBuilder.updateList(1, "Wocheneinkauf");

        QueryBuilder.insertListProduct(1, 1, 500);
        QueryBuilder.insertListProduct(1, 2, 200);
        QueryBuilder.insertListProduct(1, 3, 10);
        
        dbc.printAllTables();
        dbc.closeConnection();
        dbc.deleteFile();
	}
}
