import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestAdd {

	public static void main(String[] args) throws IOException {
		String str1 = "";
		String str2 = "";
		LargeInteger l1;
		LargeInteger l2;
		BigInteger b1;
		BigInteger b2;
		double t1, t2;

		BufferedWriter br = new BufferedWriter(new FileWriter(
				"Mulvaluelarge.txt"));
		BufferedWriter brb = new BufferedWriter(new FileWriter(
				"Mulvaluebig.txt"));

		for (int j = 0; j < 200; j++) {
			str1 = str1 + "123459875346546474645454545454";
			str2 = str2 + "231398132918273913919312989545454";
		}
		l1 = new LargeInteger(str1);
		l2 = new LargeInteger(str2);

		b1 = new BigInteger(str1);
		b2 = new BigInteger(str2);
		str1 = "";
		str2 = "";
		SimpleDateFormat sdf = new SimpleDateFormat("HH.MM.ss.SSS");

		Calendar cal = Calendar.getInstance();
		cal.getTime();
		t1 = System.nanoTime();

		cal.getTime();
		br.write("6000\t");
		br.write((System.currentTimeMillis() - t1) / 1000 + "\n");

		t1 = System.nanoTime();
		b1.multiply(b2);
		brb.write("6000\t");
		brb.write((System.currentTimeMillis() - t1) / 1000 + "\n");

		for (int j = 0; j < 2000; j++) {
			str1 = str1 + "323131313212313131313213131312";
			str2 = str2 + "424345354634363252525252525253";
		}

		l1 = new LargeInteger(str1);
		l2 = new LargeInteger(str2);
		b1 = new BigInteger(str1);
		b2 = new BigInteger(str2);
		str1 = "";
		str2 = "";
		t1 = System.nanoTime();
		br.write("60000\t");
		br.write((System.currentTimeMillis() - t1) / 1000 + "\n");

		t1 = System.nanoTime();
		b1.multiply(b2);
		brb.write("6000000000\t");
		brb.write((System.currentTimeMillis() - t1) / 1000 + "\n");

		for (int j = 0; j < 2000; j++) {
			str1 = str1 + "424345354634363252525252525253";
			str2 = str2 + "323131313212313131313213131312";
		}
		l1 = new LargeInteger(str1);
		l2 = new LargeInteger(str2);
		b1 = new BigInteger(str1);
		b2 = new BigInteger(str2);
		str1 = "";
		str2 = "";

		t1 = System.currentTimeMillis();
		br.write("600000\t");
		br.write(System.currentTimeMillis() - t1 + "\n");

		t1 = System.currentTimeMillis();
		b1.subtract(b2);
		brb.write("600000\t");
		brb.write(System.currentTimeMillis() - t1 + "\n");

		for (int j = 0; j < 20000; j++) {
			str1 = str1 + "424345354634363252525252525253";
			str2 = str2 + "323131313212313131313213131312";
		}
		l1 = new LargeInteger(str1);
		l2 = new LargeInteger(str2);
		b1 = new BigInteger(str1);
		b2 = new BigInteger(str2);
		str1 = "";
		str2 = "";

		t1 = System.currentTimeMillis();
		br.write("60000000\t");
		br.write(System.currentTimeMillis() - t1 + "\n");

		t1 = System.nanoTime();
		b1.subtract(b2);
		brb.write("60000000\t");
		brb.write((System.currentTimeMillis() - t1) / 1000 + "\n");

		br.close();
		brb.close();

	}
}
