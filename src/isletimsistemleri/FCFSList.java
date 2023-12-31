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

		text = String.format(" %d \t   %d \t  %d \t \t \t \t \t %d \t %d \t   %d \t   %d \t RUNNING",
				item.id, item.varis, item.oncelik, item.KullanilanYazici, item.KullanilanTarayici, item.KullanilanModem, item.KullanilanCd);

		System.out.println(text);

		while (item.burstTime != 0) {
			item.burstTime--;
			timer++;

			item.askiyaAlinma = zaman + timer;

			if (item.burstTime > 0) {
				text = String.format(" %d \t   %d \t  %d \t \t \t \t \t %d \t %d \t   %d \t   %d \t RUNNING",
						item.id, item.varis, item.oncelik, item.KullanilanYazici, item.KullanilanTarayici, item.KullanilanModem, item.KullanilanCd);

				System.out.println(text);
			} else
				text = String.format(" %d \t   %d \t  %d \t \t \t \t \t %d \t %d \t   %d \t   %d \t COMPLATED",
						item.id, item.varis, item.oncelik, item.KullanilanYazici, item.KullanilanTarayici, item.KullanilanModem, item.KullanilanCd);

			System.out.println(text);

			dl.TimeOut_Scanner(zaman + timer);
		}

		return timer;
	}
}
