package logic;

public class Product implements Comparable<Product>{
	public String name;
	public String brand;
	public int icon;
	public int amount;
	public int prio;
	
	public Product(String name, String brand, int icon, int amount, int prio) {
		this.name = name;
		this.brand = brand;
		this.icon = icon;
		this.amount = amount;
		this.prio = prio;
	}
	
	@Override
	public int compareTo(Product product) {
		return this.name.compareTo(product.name);
	}
}
