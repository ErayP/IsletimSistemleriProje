package isletimsistemleri;

public class KullanıcıIsıKuyruk {
	Kuyruk kuyruk = new Kuyruk();

	void UJ_add(Item item) {
		kuyruk.kuyrugaEkle(item);
	}
	
	void UJ_Dispatch() //dispatchList'ten gelen prosesleri önceliklerine göre ilgili kuyruklara gönderiyor
	{
		DispatchList dl = Prosesler.dl;
		
		while(!kuyruk.kuyrukBosMu())
		{
			if (kuyruk.Getir(0).oncelik == 1) {
				dl.fpl.FPL_add(kuyruk.kuyruktanCikar());
			} else if (kuyruk.Getir(0).oncelik == 2) {
				dl.spl.SPL_add(kuyruk.kuyruktanCikar());
			} else {
				dl.rr.RR_add(kuyruk.kuyruktanCikar());
			}
		}
		
	}
}
