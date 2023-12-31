package isletimsistemleri;

import java.util.Random;

public class FirstPriList {

	SecondPriList spl = new SecondPriList();
	Kuyruk kuyruk = new Kuyruk();

	void FPL_add(Item item) {
		kuyruk.kuyrugaEkle(item);

	}

	boolean FPL_isEmpty() {
		return kuyruk.kuyrukBosMu();
	}

	int FPL_execute(int zaman) {// maine gidecek olan timer(ekleme islemi olacak)

		Random rng = new Random();

		// Rastgele RGB renkleri oluşturma
		int r = rng.nextInt(256);
		int g = rng.nextInt(256);
		int b = rng.nextInt(256);

		DispatchList dl = Prosesler.dl;

		String text = "";

		int timer = 0; // prosesin işlem gördüğü toplam zamanı tutan değer

		Item item = kuyruk.kuyruktanCikar();

		// Rastgele renkleri kullanarak yazıyı biçimlendirme
		text = String.format(
				"\033[38;2;%d;%d;%dm%2d sn proses basladi         (id: %2d   oncelik:%2d  kalan sure:%2d sn)\033[0m", r,
				g, b, (zaman + timer), item.id, item.oncelik, item.burstTime);

		System.out.println(text);

		timer++;
		item.burstTime--;

		item.askiyaAlinma = zaman + timer; // ilgili itemin askıya alınma zamanını güncelliyor. (genel zaman + prosesin
											// işlem için harcadığı zaman)

		if (item.burstTime > 0) { // diğer kuyruğa atama işlemi

			item.oncelik++;

			text = String.format(
					"\033[38;2;%d;%d;%dm%2d sn proses askida          (id: %2d   oncelik:%2d  kalan sure:%2d sn)\033[0m",
					r, g, b, (zaman + timer), item.id, item.oncelik, item.burstTime);

			System.out.println(text);

			spl.SPL_add(item); // 2. öncelikli kuyruğa atama

		} else {
			text = String.format(
					"\033[38;2;%d;%d;%dm%2d sn proses sonlandi        (id: %2d   oncelik:%2d  kalan sure:%2d sn)\033[0m",
					r, g, b, (zaman + timer), item.id, item.oncelik, item.burstTime);

			System.out.println(text);
		}

		dl.TimeOut_Scanner(zaman + timer); // zaman aşımı kontrolü

		return timer;
	}
}
