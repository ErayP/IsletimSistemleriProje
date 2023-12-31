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
		text = String.format(" %d \t   %d \t  %d \t \t \t \t \t %d \t %d \t   %d \t   %d \t RUNNING",
				item.id, item.varis, item.oncelik, item.KullanilanYazici, item.KullanilanTarayici, item.KullanilanModem, item.KullanilanCd);

		System.out.println(text);

		timer++;
		item.burstTime--;

		item.askiyaAlinma = zaman + timer;// ilgili itemin askıya alınma zamanını güncelliyor. (genel zaman + prosesin
											// işlem için harcadığı zaman)

		if (item.burstTime > 0) { // diğer kuyruğa atama işlemi

			item.oncelik++;

			rr.RR_add(item);// RoundRobin kuyruğuna atama

			text = String.format(" %d \t   %d \t  %d \t \t \t \t \t %d \t %d \t   %d \t   %d \t RUNNING",
					item.id, item.varis, item.oncelik, item.KullanilanYazici, item.KullanilanTarayici, item.KullanilanModem, item.KullanilanCd);

			System.out.println(text);

		} else {
			text = String.format(" %d \t   %d \t  %d \t \t \t \t \t %d \t %d \t   %d \t   %d \t COMPLATED",
					item.id, item.varis, item.oncelik, item.KullanilanYazici, item.KullanilanTarayici, item.KullanilanModem, item.KullanilanCd);

			System.out.println(text);
		}

		dl.TimeOut_Scanner((zaman + timer)); // zaman aşımı kontrolü

		return timer;
	}
}
