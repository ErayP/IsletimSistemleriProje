package isletimsistemleri;

import java.util.LinkedList;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

public class DispatchList {
	public Map<String,Resource> resources;
	LinkedList<Item> dispatchList;

	KullanıcıIsıKuyruk uj = new KullanıcıIsıKuyruk();
	
	GercekZamanliKuyruk gzk = new GercekZamanliKuyruk();
	FirstPriList fpl = new FirstPriList();
	SecondPriList spl = fpl.spl;
	RRList rr = spl.rr;

	int damn_timer = 0;// Genel zaman
	//resourceları tutan bir yapı oluşturacağız ve constructuerın içinde resourceları tek tek atayacğız ondan önce resource classı olusturmalıyız
	public DispatchList() {
		resources = new HashMap<>();
		dispatchList = new LinkedList<Item>();
		
		 resources.put("printer", new Resource(2,2));
	     resources.put("scanner", new Resource(1,1));
	     resources.put("modem", new Resource(1,1));
	     resources.put("cd-drive", new Resource(2,2));
	}

	public void ListeyeEkle(Item item) {
		dispatchList.add(item);
	}

	public void General_Dispatcher() { // listedeki prosesleri gerekli kuyruklara atama
		// dispatchList is full.
		int size = dispatchList.size();

		int used_items_count = 0;// kullanılan item sayısı

		while (used_items_count != size || !fpl.FPL_isEmpty() || !spl.SPL_isEmpty() || !rr.RR_isEmpty()) { // tüm
																											// itemler
																											// kullanıldığında
																											// ve tüm
																										// listeler
																											// boşaldığında
																											// duracak
			for (int i = 0; i < size; i++) {// tüm listeyi dolaşıp zamana göre atama yapılmasını sağlıyor

				if (dispatchList.size() == 0)
					break;

				if (dispatchList.get(0).varis > damn_timer) {
					break;
				} else if (dispatchList.get(0).oncelik == 0) {// eğer ki ilgili proses gelmişse (zamana bağlı olarak)
																// önceliğine göre atama işlemleri yapılıyor
					gzk.FCFS_add(dispatchList.remove());
					used_items_count++;
				} else {
					if (dispatchList.get(0).oncelik != 0) {// eğer ki ilgili proses gelmişse (zamana bağlı olarak)
															// önceliğine göre atama işlemleri yapılıyor
						uj.UJ_add(dispatchList.remove()); // kullanıcı işi kuyruk'a ekleme işlemi yapılıyor
						used_items_count++;
					}
				}
			} // for bitis //time'a gore degerler atanıyor.

			if (!(uj.kuyruk.kuyrukBosMu()))// eğer ki kullanıcı işi kuyruk boş değil ise çalışması sağlanıyor ve düşük
				uj.UJ_Dispatch(); // öncelikli kuyruklara içindeki prosesleri öncelik değerlerine göre dağıtıyor

			Executer(); // listeleri çalıştırmaya yarıyor
		}
	}

	void Executer() {
		if (!(gzk.FCFS_isEmpty())) { // fcfs kuyruğu boş değil ise ilk olarak bu kuyruk işlem görüyor eğer ki boş ise
										// diğer kuyruklar aynı mantığa göre işlem görüyor
			int gzk_ExecTime = gzk.FCFS_execute(damn_timer);
			damn_timer += gzk_ExecTime;// prosesin işlem zamanı genel zamana ekleniyor.
		} else if (!(fpl.FPL_isEmpty())) {
			int fpl_ExecTime = fpl.FPL_execute(damn_timer);
			damn_timer += fpl_ExecTime;
		} else if (!(spl.SPL_isEmpty())) {
			int spl_ExecTime = spl.SPL_execute(damn_timer);
			damn_timer += spl_ExecTime;
		} else if (!(rr.RR_isEmpty())) {
			int rr_ExecTime = rr.RR_execute(damn_timer);
			damn_timer += rr_ExecTime;
		} else
			damn_timer++;
	}

	public void TimeOut_Scanner(int gecenZaman) { // tüm listeleri tarıyor ve zaman aşımına uğrayan herhangi bir proses
													// varsa gerekli işlemleri yapıyor

		String text = "";

		for (int i = 0; i < gzk.kuyruk.kuyrukSize(); i++) {// GercekZamanliKuyruk checking

			Item item = gzk.kuyruk.Getir(i);

			if (gecenZaman - item.askiyaAlinma >= 20)// zaman aşımı oldu
			{
				Random rng = new Random();

				// Rastgele RGB renkleri oluşturma
				int r = rng.nextInt(256);
				int g = rng.nextInt(256);
				int b = rng.nextInt(256);

				text = String.format(
						" %d HATA - Proses zaman aşımı (20 sn de tamamlanamadı)",item.id);

				System.out.println(text);

				gzk.kuyruk.kuyruktanCikar(i);
				i--; // İşlem gören eleman kaldırıldığı için o elemandan sonraki elemanlar bir birim
						// sola kayıyor ve eğer ki i değerini
						// bir birim azaltmazsak kaldırılan elemanın yerine gelen eleman atlanmış oluyor
			}
		}
		// alt tarafta bulunan yapılar üst kısımdaki ile aynı işlevi yapmaktadır.
		// Yalnızca kontrol edilen kuyruklar değişmiştir
		
		for (int i = 0; i < fpl.kuyruk.kuyrukSize(); i++) {// firstPriorityList checking
			Item item = fpl.kuyruk.Getir(i);
			if (gecenZaman - item.askiyaAlinma >= 20)// zaman aşımı oldu
			{
				Random rng = new Random();
				// Rastgele RGB renkleri oluşturma
				int r = rng.nextInt(256);
				int g = rng.nextInt(256);
				int b = rng.nextInt(256);

				text = String.format(
						" %d HATA - Proses zaman aşımı (20 sn de tamamlanamadı)",item.id);

				System.out.println(text);

				fpl.kuyruk.kuyruktanCikar(i);
				i--;
			}
		}
		
		for (int i = 0; i < spl.kuyruk.kuyrukSize(); i++) {// secondPriorityList checking
			Item item = spl.kuyruk.Getir(i);
			if (gecenZaman - item.askiyaAlinma >= 20)// zaman aşımı oldu
			{
				Random rng = new Random();

				// Rastgele RGB renkleri oluşturma
				int r = rng.nextInt(256);
				int g = rng.nextInt(256);
				int b = rng.nextInt(256);

				text = String.format(
						" %d HATA - Proses zaman aşımı (20 sn de tamamlanamadı)",item.id);

				System.out.println(text);

				spl.kuyruk.kuyruktanCikar(i);
				i--;
			}
		}
		for (int i = 0; i < rr.kuyruk.kuyrukSize(); i++) {// Round-Robin checking
			Item item = rr.kuyruk.Getir(i);
			if (gecenZaman - item.askiyaAlinma >= 20)// zaman aşımı oldu
			{
				Random rng = new Random();

				// Rastgele RGB renkleri oluşturma
				int r = rng.nextInt(256);
				int g = rng.nextInt(256);
				int b = rng.nextInt(256);

				text = String.format(
						" %d HATA - Proses zaman aşımı (20 sn de tamamlanamadı)",item.id);

				System.out.println(text);

				rr.kuyruk.kuyruktanCikar(i);
				i--;
			}
		}

	}
}
