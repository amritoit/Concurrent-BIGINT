public class DivideClass {

	long[] quotient;
	long[] remainder;
	long currentLength;
	LargeInteger Partial;

	int index = 0;

	public DivideClass() {
		quotient = new long[100];
	}

	public LargeInteger divide(LargeInteger Dividend, LargeInteger Divisor) {

		long[] dividend;
		long[] divisor;
		dividend = Dividend.getDigit();
		divisor = Divisor.getDigit();
		int lenOfDivisor, lenOfDividend;

		lenOfDivisor = divisor.length;
		lenOfDividend = dividend.length;
		currentLength = lenOfDividend;
		if (lenOfDivisor > lenOfDividend) {
			quotient[index] = 0;
			LargeInteger quot = new LargeInteger("");
			quot.setDigit(quotient);
			return quot;
		} else {
			int firstLenDivisor, firstLenDividend, inTheEndDivideBy = 0;
			firstLenDivisor = String.valueOf(Divisor.getDigit()[0]).length();
			firstLenDividend = String.valueOf(Dividend.getDigit()[0]).length();

			Dividend.leftShift((long) (18 - firstLenDividend));
			Divisor.leftShift((long) (18 - firstLenDivisor));
			inTheEndDivideBy = inTheEndDivideBy + firstLenDivisor
					- firstLenDividend;
			LargeInteger temp = new LargeInteger("");
			int factor = findFactor(Dividend, Divisor);
			System.out.print("factor " + factor);
			quotient[index] = factor;
			index++;
			temp = temp.sub(Partial);/* we've got partial too. */
			temp = subtractFix(Dividend, temp);
			currentLength = temp.length();
			return divide(temp, Divisor);

		}

	}

	private LargeInteger subtractFix(LargeInteger Dividend, LargeInteger l) {
		long len;
		long[] temp;
		len = l.length();
		temp = new long[(int) (len + currentLength)];
		for (int i = 0; i < len; i++) {
			temp[i] = l.getDigit()[i];
		}
		for (int i = 0; i < currentLength; i++) {
			temp[(int) len + i] = Dividend.getDigit()[i];
		}
		LargeInteger toReturn = new LargeInteger("");
		toReturn.setDigit(temp);
		return toReturn;
	}

	private int findFactor(LargeInteger Dividend, LargeInteger Divisor) {
		Dividend.setDigit(getFromIndex(Dividend.getDigit(), 0l,
				Divisor.length() - 1));
		boolean cond = true;
		int count = 0;
		LargeInteger prev, curr;
		prev = Divisor;
		while (cond) {
			curr = Divisor.add(prev);
			count++;
			if (curr.compareTo(Dividend) == -1) {
				cond = false;
			} else {
				prev = curr;
			}
		}
		Partial = prev;
		return count;
	}

	private long[] getFromIndex(long[] arr, long i, long j) {
		long[] temp;
		temp = new long[(int) (j - i + 1)];
		for (int k = (int) i; k <= j; k++) {
			temp[(int) (k - i)] = arr[k];
		}
		return temp;

	}

}// End of DivideClass
