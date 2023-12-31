package isletimsistemleri;

import java.util.Random;

public class RRList {
	Kuyruk kuyruk = new Kuyruk();
	int sayac = 0;

	void RR_add(Item item) {
		kuyruk.kuyrugaEkle(item);

	}

	boolean RR_isEmpty() {
		return kuyruk.kuyrukBosMu();
	}

	Item cikanEleman = null;

	int RR_execute(int zaman) {// maine gidecek olan timer(ekleme islemi olacak)

		Random rng = new Random();

		// Rastgele RGB renkleri oluşturma
		int r = rng.nextInt(256);
		int g = rng.nextInt(256);
		int b = rng.nextInt(256);

		DispatchList dl = Prosesler.dl;

		String text = "";

		int timer = 0; // prosesin işlem gördüğü toplam zamanı tutan değer

		Item item = kuyruk.Getir(sayac);

		// Rastgele renkleri kullanarak yazıyı biçimlendirme
		text = String.format(
				"\033[38;2;%d;%d;%dm%2d sn proses basladi         (id: %2d   oncelik:%2d  kalan sure:%2d sn)\033[0m", r,
				g, b, (zaman + timer), item.id, item.oncelik, item.burstTime);

		System.out.println(text);

		timer++;
		item.burstTime--;

		item.askiyaAlinma = zaman + timer; // ilgili itemin askıya alınma zamanını güncelliyor. (genel zaman + prosesin
											// işlem için harcadığı zaman)

		if (item.burstTime == 0) { //proses tamamen harcanmışsa yok edilir
			text = String.format(
					"\033[38;2;%d;%d;%dm%2d sn proses sonlandi        (id: %2d   oncelik:%2d  kalan sure:%2d sn)\033[0m",
					r, g, b, (zaman + timer), item.id, item.oncelik, item.burstTime);

			System.out.println(text);

			kuyruk.kuyruktanCikar(sayac);

			if (sayac == kuyruk.kuyrukSize()) //eğer ki gösterge son elemanı gösteriyorsa ve o eleman yok edildiyse sayacı sıfırla
				sayac = 0;							//böylelikle sayac tekrar başa dönebilsin
				
		} else { // kuyrukta round robin mantığına göre sırayla ilerlemeyi ve bir sonraki elemanı almayı sağlıyor
			text = String.format(
					"\033[38;2;%d;%d;%dm%2d sn proses askida          (id: %2d   oncelik:%2d  kalan sure:%2d sn)\033[0m",
					r, g, b, (zaman + timer), item.id, item.oncelik, item.burstTime);

			System.out.println(text);

			dl.TimeOut_Scanner(zaman + timer);

			sayac = (sayac + 1) % kuyruk.kuyrukSize(); //sayacın kendi içinde tekrar etmesini sağlıyor (dairesel)

		}
		return timer;
	}
}
