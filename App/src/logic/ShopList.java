package logic;

import java.util.ArrayList;

public class ShopList {
	public String name;
	public ArrayList<Product> list;
	SortHandler sortHandler;
	
	public ShopList(String name) {
		this.name = name;
		list = new ArrayList<>();
		sortHandler = new AlphabeticSorter();
	}
	
	public void addProduct(Product p) {
		list.add(p);
	}
	
	public void remove(Product p) {
		list.remove(p);
	}
	
	public void sort() {
		sortHandler.sort(list);
	}
	
	public void printList() {
		for(Product p : list) {
			System.out.println(p.name);
		}
	}
}
