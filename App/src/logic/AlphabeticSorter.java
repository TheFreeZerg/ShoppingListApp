package logic;

import java.util.Collections;
import java.util.List;

public class AlphabeticSorter extends SortHandler{

	@Override
	public void sort(List<Product> list) {
		Collections.sort(list);
	}
}
