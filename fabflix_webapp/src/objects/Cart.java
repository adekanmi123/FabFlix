package objects;

import java.util.*;
//partially a java bean

public class Cart {
    private HashMap<Integer, Item>items;
	
	public Cart() {
		this.items = new HashMap<Integer, Item>();	
	}
	
	public ArrayList<Item> getItems()
	{
	    return new ArrayList<Item>(items.values()); 	
	}
	
	
	public void addItemtoCart(Movie mov, int num)
	{
		if(!items.containsKey(mov.getId()))
		{
		   Item newItem = new Item(mov,num);
		   items.put(mov.getId(), newItem);
		}
		else
		{
		   items.get(mov.getId()).addQuantity(num);
		}
	}

	public void updateQua(Movie mov, int num)
	{
		if(items.containsKey(mov.getId()))
      		items.get(mov.getId()).setQuantity(num);			
	}
	
	
	public void removeMov(Movie movie)
	{
		if (items.containsKey(movie.getId()))
			items.remove(movie.getId());
	}
	
	public void removeALL()
	{
	    items.clear();	
	}
	
	public boolean iscontain(Movie mov)
	{
	    return (items.containsKey(mov.getId()));
	}
	
	public boolean isempty()
	{
		return items.isEmpty();
	}

}

