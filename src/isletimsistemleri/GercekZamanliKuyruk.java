package isletimsistemleri;

import java.util.Random;

public class GercekZamanliKuyruk {

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

		DispatchList dl = Prosesler.dl;

		int timer = 0;// prosesin işlem gördüğü toplam zamanı tutan değer

		String text = "";
		Item item = kuyruk.kuyruktanCikar();
		Resource printer = dl.resources.get("printer");
		Resource scanner  = dl.resources.get("scanner");
		Resource modem = dl.resources.get("modem");
		Resource cd_drive = dl.resources.get("cd-drive");
		for(int i = 0; i<item.KullanilanYazici;i++) {
			try {
				printer.lock();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int i = 0; i<item.KullanilanTarayici;i++) {
			try {
				scanner.lock();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int i = 0; i<item.KullanilanModem;i++) {
			try {
				modem.lock();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int i = 0; i<item.KullanilanCd;i++) {
			try {
				cd_drive.lock();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// Rastgele renkleri kullanarak yazıyı biçimlendirme
		text = String.format(
				"\033[38;2;%d;%d;%dm%2d sn proses basladi         (id: %2d   oncelik:%2d  kalan sure:%2d sn)\033[0m", r,
				g, b, (zaman + timer), item.id, item.oncelik, item.burstTime);

		System.out.println(text);

		while (item.burstTime != 0) {// prosesin tamamen harcanana kadar döngünün devam etmesini sağlıyor
			item.burstTime--;
			timer++;

			item.askiyaAlinma = zaman + timer;// ilgili itemin askıya alınma zamanını güncelliyor. (genel zaman +
												// prosesin
												// işlem için harcadığı zaman)

			if (item.burstTime > 0) { // proses yürütülüyor

				text = String.format(
						"\033[38;2;%d;%d;%dm%2d sn proses yurutuluyor     (id: %2d   oncelik:%2d  kalan sure:%2d sn)\033[0m",
						r, g, b, (zaman + timer), item.id, item.oncelik, item.burstTime);

				System.out.println(text);
			} else {// propses yürütülüyor
				
				for(int i = 0; i<item.KullanilanYazici;i++) {
						printer.unlock();
				}
				for(int i = 0; i<item.KullanilanYazici;i++) {
					scanner.unlock();
				}
				for(int i = 0; i<item.KullanilanYazici;i++) {
					modem.unlock();
				}
				for(int i = 0; i<item.KullanilanYazici;i++) {
					cd_drive.unlock();
				}
				
				text = String.format(
						"\033[38;2;%d;%d;%dm%2d sn proses sonlandi        (id: %2d   oncelik:%2d  kalan sure:%2d sn)\033[0m",
						r, g, b, (zaman + timer), item.id, item.oncelik, item.burstTime);

				System.out.println(text);
			}
			dl.TimeOut_Scanner(zaman + timer); //zaman aşımı kontrolü

		}

		return timer;
	}
}
