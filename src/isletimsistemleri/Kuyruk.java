package isletimsistemleri;

import java.util.LinkedList;

public class Kuyruk {

	private LinkedList<Item> kuyruk = new LinkedList<Item>();

	public void kuyrugaEkle(Item item) {
		kuyruk.addLast(item);
		
	}

	public Item kuyruktanCikar() {
		return kuyruk.remove();
	}
	
	public Item kuyruktanCikar(int id) {
		return kuyruk.remove(id);
	}

	public Item Getir(int index)
	{
		return kuyruk.get(index);
	}
	
	public boolean kuyrukBosMu() {
		return kuyruk.isEmpty();
	}
	
	public int kuyrukSize()
	{
		return kuyruk.size();
	}

}
