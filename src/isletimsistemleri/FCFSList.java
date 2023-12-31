package isletimsistemleri;

import java.util.Random;

public class FCFSList {

	Kuyruk kuyruk = new Kuyruk();

	void FCFS_add(Item item) {
		kuyruk.kuyrugaEkle(item);

	}

	boolean FCFS_isEmpty() {
		return kuyruk.kuyrukBosMu();
	}

	int FCFS_execute(int zaman) {// maine gidecek olan timer(ekleme islemi olacak)

		Random rng = new Random();

		// Rastgele RGB renkleri oluşturma
		int r = rng.nextInt(256);
		int g = rng.nextInt(256);
		int b = rng.nextInt(256);

		// Rastgele renkleri kullanarak yazıyı biçimlendirme

		DispatchList dl = Prosesler.dl;

		// System.out.println();
		// String text = String.format("\033[38;2;%d;%d;%dmFCFS\033[0m", r, g, b);
		// System.out.println(text);
		int timer = 0;
		String text = "";
		Item item = kuyruk.kuyruktanCikar();

		text = String.format(
				"\033[38;2;%d;%d;%dm%2d sn proses basladi         (id: %2d   oncelik:%2d  kalan sure:%2d sn)\033[0m", r,
				g, b, (zaman + timer), item.id, item.oncelik, item.burstTime);

		System.out.println(text);

		while (item.burstTime != 0) {
			item.burstTime--;
			timer++;

			item.askiyaAlinma = zaman + timer;

			if (item.burstTime > 0) {
				text = String.format(
						"\033[38;2;%d;%d;%dm%2d sn proses yurutuluyor     (id: %2d   oncelik:%2d  kalan sure:%2d sn)\033[0m",
						r, g, b, (zaman + timer), item.id, item.oncelik, item.burstTime);

				System.out.println(text);
			} else
				text = String.format(
						"\033[38;2;%d;%d;%dm%2d sn proses sonlandi        (id: %2d   oncelik:%2d  kalan sure:%2d sn)\033[0m",
						r, g, b, (zaman + timer), item.id, item.oncelik, item.burstTime);

			System.out.println(text);

			dl.TimeOut_Scanner(zaman + timer);
		}

		return timer;
	}
}
