package isletimsistemleri;

import java.util.Random;

public class SecondPriList {

//	public SecondPriList(DispatchList dl) {
//		this.dl = dl;
//	}

	Kuyruk kuyruk = new Kuyruk();
	RRList rr = new RRList();

	void SPL_add(Item item) {
		kuyruk.kuyrugaEkle(item);

	}

	boolean SPL_isEmpty() {
		return kuyruk.kuyrukBosMu();
	}

//System.out.println("varis: " + item.varis + "  burst: " + item.burstTime);
	int SPL_execute(int zaman) {// maine gidecek olan timer(ekleme islemi olacak)

		Random rng = new Random();

		// Rastgele RGB renkleri oluşturma
		int r = rng.nextInt(256);
		int g = rng.nextInt(256);
		int b = rng.nextInt(256);

		DispatchList dl = Prosesler.dl;

		String text = "";

		int timer = 0;// prosesin işlem gördüğü toplam zamanı tutan değer

		Item item = kuyruk.kuyruktanCikar();

		// Rastgele renkleri kullanarak yazıyı biçimlendirme
		text = String.format(
				"\033[38;2;%d;%d;%dm%2d sn proses basladi         (id: %2d   oncelik:%2d  kalan sure:%2d sn)\033[0m", r,
				g, b, (zaman + timer), item.id, item.oncelik, item.burstTime);

		System.out.println(text);

		timer++;
		item.burstTime--;

		item.askiyaAlinma = zaman + timer;// ilgili itemin askıya alınma zamanını güncelliyor. (genel zaman + prosesin
											// işlem için harcadığı zaman)

		if (item.burstTime > 0) { // diğer kuyruğa atama işlemi

			item.oncelik++;

			rr.RR_add(item);// RoundRobin kuyruğuna atama

			text = String.format(
					"\033[38;2;%d;%d;%dm%2d sn proses askida          (id: %2d   oncelik:%2d  kalan sure:%2d sn)\033[0m",
					r, g, b, (zaman + timer), item.id, item.oncelik, item.burstTime);

			System.out.println(text);

		} else {
			text = String.format(
					"\033[38;2;%d;%d;%dm%2d sn proses sonlandi        (id: %2d   oncelik:%2d  kalan sure:%2d sn)\033[0m",
					r, g, b, (zaman + timer), item.id, item.oncelik, item.burstTime);

			System.out.println(text);
		}

		dl.TimeOut_Scanner((zaman + timer)); // zaman aşımı kontrolü

		return timer;
	}
}
