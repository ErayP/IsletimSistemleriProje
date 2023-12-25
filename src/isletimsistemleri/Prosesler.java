package isletimsistemleri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Prosesler {

	public static DispatchList dl = null;

	public Prosesler(DispatchList dl) // her class'ın main içinde oluşturulan dispatchlist'e erişimini sağlamak için
	{
		Prosesler.dl = dl;
	}

	public void process() throws IOException {

		List<String> allLines = Files.readAllLines(Paths.get("./txtfolder/giris.txt"));// dosyadaki satirlari listede
																						// tutacak
		int idSayac = 0; // itemlere id atamak için tutulan sayaç

		for (int i = 0; i < allLines.size(); i++) { // tüm satırları gezmemizi sağlıyor

			String satir = allLines.get(i);// ilk satir alindi

			Item item = new Item(); // her satır için bir item

			int sayac = -1;// satirdaki verileri varis, oncelik ve burst time'a gore ayiriyor.

			String veri = "";// satirdaki verileri almaya yariyor

			for (int j = 0; j < satir.length(); j++) {
				char ch = satir.charAt(j);// satiri tek tek gezmeyi sagliyor

				if (ch == ' ')
					continue;

				else if (j == satir.length() - 1) {// son satirdaki veriyi de almayi sagliyor ve ilgili Item degerine
													// atamayi sagliyor
					veri += ch;
					item.burstTime = Integer.parseInt(veri);

				} else if (ch != ',') {// verileri veri nesnesine atamayi sagliyor ve verileri ayirmayi sagliyor
					veri += ch;

				} else {// alinan verileri ilgili Item degerlerine atiyor

					sayac++;
					switch (sayac) {
					case 0:
						item.varis = Integer.parseInt(veri);
						break;
					case 1:
						item.oncelik = Integer.parseInt(veri);
						break;
					}

					veri = ""; //veri stringini sıfırlıyor
				}
			}// bir satirdaki verileri ayırdık ve Item classına atadık
			
			item.id = idSayac; //id ataması
			
			idSayac++;
			
			item.askiyaAlinma = item.varis; //itemlere default askıya alınma zamanı atıyor (varış zamanı)
			
			
			dl.ListeyeEkle(item);
		}//tüm atama işlemleri bitti. dispatchList dolu
		dl.General_Dispatcher();
	}
}