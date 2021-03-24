package logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Creates and opens the connection to the SQL file.
 */
public class SQLHandler {
    
    private static final SQLHandler SQLHandler = new SQLHandler();
    private static Connection connection;
    public static String fileName = "";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Fehler beim Laden des JDBC-Treibers");
            e.printStackTrace();
        }
    }
    
    private SQLHandler(){
    }
    
    public static SQLHandler getInstance(){
        return SQLHandler;
    }

    /**
     * Builds the Connection to SQLite
     */
    public void buildConnection() {
        try {
            if (connection != null)
                return;
            System.out.println("Creating Connection to Database...");
            connection = DriverManager.getConnection("jdbc:sqlite:" 
            		+ fileName + ".sqlite");
            if (!connection.isClosed())
                System.out.println("...Connection established");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    if (!connection.isClosed() && connection != null) {
                    	closeConnection();
                        if (connection.isClosed())
                            System.out.println("Connection to "
                            		+ "Database closed");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * creates all Tables
     */
    public void createEmptyFile() {
        try {
            Statement stmt = connection.createStatement();
            
            //attribute table
            stmt.executeUpdate("PRAGMA foreign_keys = ON");
            
            //list table
            stmt.executeUpdate(
            		"CREATE TABLE list ("
            		+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            		+ "name TEXT)");
            
            //product table
            stmt.executeUpdate(
            		"CREATE TABLE product ("
            		+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            		+ "name TEXT, "
            		+ "brand TEXT,"
            		+ "icon INTEGER,"
            		+ "prio INTEGER)");
            
            //list_product table
            stmt.executeUpdate(
            		"CREATE TABLE list_product ("
                    + "list_id INTEGER, "
            		+ "product_id INTEGER, "
            		+ "amount INTEGER CHECK (amount > 0), "
            		+ "FOREIGN KEY(list_id) REFERENCES list(id),"
            		+ "FOREIGN KEY(product_id) REFERENCES product(id),"
            		+ "PRIMARY KEY (list_id, product_id))");
            
        } catch (SQLException e) {
            System.err.println("Mistake in script");
            e.printStackTrace();
        }
    }
    
    /**
     * prints the table on the console
     * @param table table name
     */
    public void printTable(String table) {
    	try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
            ResultSetMetaData rsmd = rs.getMetaData();
            
            int count = rsmd.getColumnCount();
            String s = "";
            
            System.out.println("/*"+table+"*/");
            
            while (rs.next()) {
                //print one row
            	for(int i = 1; i <= count; i++) {
            		//print one element of a row 
            		System.out.print(rs.getString(i));
            		
            		s = (i+1) <= count ? " | " : "";
            		System.out.print(s);
            	}
            	System.out.println();
            }
            System.out.println();
            
            rs.close();
    	} catch (SQLException e) {
            System.err.println(table + " possibly does not exist");
            e.printStackTrace();
        }
    }
    
    /**
     * prints all tables
     */
    public void printAllTables() {
    	try {
    		ResultSet rs = connection.getMetaData().
    				getTables(null, null, null, null);
    		
    		while (rs.next()) {
    			printTable(rs.getString("TABLE_NAME"));
    	    }
            
            rs.close();
    	} catch (SQLException e) {
            System.err.println("Could not print all tables");
            e.printStackTrace();
        }
    }

    /**
     * Handles execute statements
     * @param sql execute statement
     */
    public void makeStatement(String sql) {
    	try {
    		Statement stmt = connection.createStatement();
    		stmt.executeUpdate(sql);
    	} catch (SQLException e) {
            System.err.println("Couldn't execute Update");
            e.printStackTrace();
        }
    }

    /**
     * Handles Queries
     * @param sql Query Statement
     * @return ResultSet Result from the Query
     */
    public ResultSet makeQuery(String sql) {
    	try {
    		Statement stmt = connection.createStatement();
    		return stmt.executeQuery(sql);
    	} catch (SQLException e) {
            System.err.println("Couldn't receive ResultSet from Query");
            e.printStackTrace();
        }
    	return null;
    }

    /**
     * Closes Connection to SQLite
     */
    public void closeConnection() {
    	try {
			connection.close();
            System.out.println("Connection to Database closed");
        	//deleteFile();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    /**
     * Deletes the SQLite File
     */
    public void deleteFile() {
        try {
			Files.deleteIfExists(Paths.get(fileName + ".sqlite"));
            System.out.println("File deleted");
		} catch (IOException e) {
			System.out.println("Couldn't delete file");
			e.printStackTrace();
		} 
    }
}
