package isletimsistemleri;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		DispatchList dl = new DispatchList();

		Prosesler p1 = new Prosesler(dl);

		System.out.println("Pid \t varış \t öncelik \t cpu \t MBytes \t prn \t scn \t modem \t cd \t status \r\n"
				+ "=================================================================================================");
		p1.process();
	}
}
