package isletimsistemleri;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		DispatchList dl = new DispatchList();

		Prosesler p1 = new Prosesler(dl);

		p1.process();
	}
}
