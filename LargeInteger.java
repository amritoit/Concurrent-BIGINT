import java.io.Serializable;
import java.math.BigInteger;

/**
 * 
 * @author Amritendu Mondal, Anant nag @ IITM.
 * 
 */

public class LargeInteger extends Number implements Comparable<LargeInteger>,
		Serializable {

	private static final long serialVersionUID = 1L;
	private boolean sign = false;// true indicates negative number
	private long[] digit;

	public LargeInteger(LargeInteger l) {
		this.digit = new long[(int) l.length()];
		int i = 0;
		for (long ll : l.getDigit()) {
			digit[i++] = ll;
		}
		sign = l.isSign();
	}

	public static int noOfProcessor() {
		return 4;
	}

	@Override
	public double doubleValue() {
		return 0;
	}

	@Override
	public float floatValue() {
		return 0;
	}

	@Override
	public int intValue() {
		return 0;
	}

	@Override
	public long longValue() {
		return 0;
	}

	@Override
	public int compareTo(LargeInteger o) {
		long len1, len2;
		len1 = this.length();
		len2 = o.length();
		if (len1 > len2) {
			return 1;
		} else if (len1 < len2) {
			return -1;
		}
		if (len1 == len2) {

			long[] digit1 = this.getDigit();
			long[] digit2 = o.getDigit();
			for (int i = 0; i < len1; i++) {
				if (digit1[i] > digit2[i]) {
					return 1;
				} else if (digit1[i] < digit2[i]) {
					return -1;
				}
			}

		}

		return 0;
	}

	/**
	 * 
	 * @param sign
	 * @param value
	 * @Description:Constructor with parameter as int value
	 */
	public LargeInteger(boolean sign, int value) {
		this.sign = sign;
		store((long) value);
	}

	public LargeInteger(boolean sign, long value) {
		store(value);
	}

	public LargeInteger(String val) {
		this(val, 10);
	}

	public LargeInteger(String val, int radix) {
		if (val == null) {
			throw new NullPointerException();
		}
		if ((radix < Character.MIN_RADIX) || (radix > Character.MAX_RADIX)) {
			// math.11=Radix out of range
			throw new NumberFormatException("Radix out of range"); //$NON-NLS-1$
		}
		if (val.length() == 0) {
			// math.12=Zero length BigInteger
			digit = new long[1];
			digit[0] = 0;
			return;
		}
		setFromString(this, val, radix);
	}

	public void printDigit() {
		System.out.println("digit is:");
		for (int i = 0; i < digit.length; i++)
			System.out.print(digit[i]);
		System.out.println();
	}

	/**
	 * @see BigInteger#BigInteger(String, int)
	 */
	private void setFromString(LargeInteger li, String val, int radix) {

		int startChar = 0;
		int stringLength = val.length();
		if (val.charAt(0) == '-') {
			sign = true;
			startChar = 1;
		} else if (val.charAt(0) == '+') {
			sign = false;
			startChar = 1;
		} else {
			sign = false;
		}

		val = val.substring(startChar, stringLength);
		digit = chopAndConvert(val);
	}

	/**
	 * 
	 * @param str
	 * @return
	 * @description This function takes in a string and chops the string into
	 *              toeks of 18 digits. It returns the array of those tokens.
	 */
	private static long[] chopAndConvert(String str) {
		long temp;
		int len, beg, left;
		long[] arr;
		if (str.isEmpty()) {
			arr = new long[1];
			arr[0] = 0;
			return arr;
		}
		len = (int) (Math.ceil((double) ((double) str.length() / 18)));
		arr = new long[len];
		left = str.length() % 18;
		if (left != 0) {
			arr[0] = Long.parseLong(str.substring(0, left));
			beg = left;
		} else {
			arr[0] = Long.parseLong(str.substring(0, 18));
			beg = 18;
		}
		for (int i = 1; i < len; i++) {

			temp = Long.parseLong(str.substring(beg, beg + 18));
			arr[i] = temp;
			beg = beg + 18;
		}

		return arr;
	}

	/**
	 * 
	 * @param s
	 * @param partLength
	 * @return
	 * @Description This method splits the string s into specified size of block
	 */
	private String[] splitInParts(String s, int partLength) {
		int len = s.length();

		int nparts = (len + partLength - 1) / partLength;/* Number of parts */
		String parts[] = new String[nparts];

		/* Break into parts */
		int offset = 0;
		int i = 0;
		while (i < nparts) {
			parts[i] = s.substring(offset, Math.min(offset + partLength, len));
			offset += partLength;
			i++;
		}

		return parts;
	}

	/**
	 * 
	 * @param str
	 * @return
	 * @Description Remove all the leading zeros
	 */
	public static String cutOffLeadingZeroes(String str) {

		if (str == null || str.trim().isEmpty()) {
			return Constants.EMPTY;
		}
		str = str.trim();
		str = str.replaceFirst(" ", "");
		int i = 0;
		for (i = 0; i < str.length(); i++) {
			if (str.charAt(i) != '0') {
				break;
			}
		}
		if (i == str.length()) {
			return Constants.EMPTY;
		}
		return str.substring(i);

	}

	/**
	 * 
	 * @param value
	 * @Description: store the value into the digit array
	 */
	private void store(long value) {

		digit = new long[1];
		digit[0] = value;

	}

	public long[] getDigit() {

		return digit;
	}

	public LargeInteger divide(LargeInteger val) {
		DivideClass div = new DivideClass();
		return div.divide(this, val);

	}

	public LargeInteger add(LargeInteger val) {

		preprocess(val);
		Elementary elm = new Elementary();

		String s = this.toString();
		String t = val.toString();
		if (digit == null) {
			digit = new long[1];
			digit[0] = 0;
		}

		if (val.getDigit() == null) {
			long[] d = new long[1];
			d[0] = 0;
			val.setDigit(d);
		}
		LargeInteger re = null;
		if ((String.valueOf(digit[0]).startsWith("-") || sign)
				&& (String.valueOf(val.getDigit()).startsWith("-") || val
						.isSign())) {

			if (String.valueOf(digit[0]).startsWith("-")) {
				s = String.valueOf(digit[0]).substring(1);
				digit[0] = Long.parseLong(s);
			}

			if (String.valueOf(val.getDigit()).startsWith("-")) {
				s = String.valueOf(val.getDigit()[0]).substring(1);
				val.getDigit()[0] = Long.parseLong(s);
			}

			re = elm.add(this, val);
			re.setSign(true);

		} else if (!(String.valueOf(digit[0]).startsWith("-") || sign)
				&& (String.valueOf(val.getDigit()).startsWith("-") || val
						.isSign())) {
			if (String.valueOf(val.getDigit()).startsWith("-")) {
				s = String.valueOf(val.getDigit()[0]).substring(1);
				val.getDigit()[0] = Long.parseLong(s);

			}

			re = elm.sub(this, val);

		} else if ((String.valueOf(digit[0]).startsWith("-") || sign)
				&& !(String.valueOf(val.getDigit()).startsWith("-") || val
						.isSign())) {
			if (String.valueOf(digit[0]).startsWith("-")) {
				s = String.valueOf(digit[0]).substring(1);
				digit[0] = Long.parseLong(s);
			}

			re = elm.sub(this, val);

		} else {
			re = elm.add(this, val);
		}
		if (re.toString().startsWith("-")) {
			re = new LargeInteger(re.toString().substring(1));
			re.setSign(true);

		}

		LargeInteger l = new LargeInteger(re.toString());
		l.setSign(re.isSign());
		return l;
	}

	public LargeInteger sub(LargeInteger val) {

		preprocess(val);
		Elementary elm = new Elementary();

		String s;
		if (digit == null) {
			digit = new long[1];
			digit[0] = 0;
		}

		if (val.getDigit() == null) {
			long[] d = new long[1];
			d[0] = 0;
			val.setDigit(d);
		}
		LargeInteger re = null;
		if ((String.valueOf(digit[0]).startsWith("-") || sign)
				&& (String.valueOf(val.getDigit()).startsWith("-") || val
						.isSign())) {

			if (String.valueOf(digit[0]).startsWith("-")) {
				s = String.valueOf(digit[0]).substring(1);
				digit[0] = Long.parseLong(s);
			}

			if (String.valueOf(val.getDigit()).startsWith("-")) {
				s = String.valueOf(val.getDigit()[0]).substring(1);
				val.getDigit()[0] = Long.parseLong(s);
			}

			re = elm.sub(val, this);

		} else if (!(String.valueOf(digit[0]).startsWith("-") || sign)
				&& (String.valueOf(val.getDigit()).startsWith("-") || val
						.isSign())) {
			if (String.valueOf(val.getDigit()).startsWith("-")) {
				s = String.valueOf(val.getDigit()[0]).substring(1);
				val.getDigit()[0] = Long.parseLong(s);

			}

			re = elm.add(this, val);
			re.setSign(false);

		} else if ((String.valueOf(digit[0]).startsWith("-") || sign)
				&& !(String.valueOf(val.getDigit()).startsWith("-") || val
						.isSign())) {
			if (String.valueOf(digit[0]).startsWith("-")) {
				s = String.valueOf(digit[0]).substring(1);
				digit[0] = Long.parseLong(s);
			}

			re = elm.add(this, val);
			re.setSign(true);

		} else {
			re = elm.sub(this, val);
		}

		LargeInteger l = new LargeInteger(re.toString());
		l.setSign(re.isSign());
		return l;
	}

	/**
	 * @Description: Multiply two large integer sequentially, created for
	 *               testing performance
	 * @param l
	 * @return
	 */
	public LargeInteger multiplySeq(LargeInteger l) {
		return Multipication.multiplySeq(this, l);
	}
	
	/**
	 * @Description: Multiply two large integer sequentially, created for
	 *               testing performance
	 * @param l
	 * @return
	 */
	public LargeInteger multiply(LargeInteger l) {
		return Multipication.multiply(this, l);
	}

	/**
	 * @Description: The method preprocess the data , eg: removing leading zero,
	 *               convert sign to a unsigned number.
	 * @param val
	 */
	private void preprocess(LargeInteger val) {
		signFixed(val);
		signFixed(this);
		lengthFix(val);

	}

	/**
	 * @Description: Making same length integers.
	 * @param val
	 */
	private void lengthFix(LargeInteger val) {

		if (val.getDigit().length == this.digit.length) {
			return;
		} else if (val.getDigit().length > this.digit.length) {
			String append = "";
			int i = 0;
			for (i = 0; i < (val.getDigit().length * 18)
					- (this.digit.length * 18); i++) {
				append += "0";
			}

			this.digit = chopAndConvert(append + toString());

		} else {
			String append = "";
			for (int i = 0; i < (this.digit.length * 18)
					- (val.getDigit().length * 18); i++) {
				append += "0";
			}
			val.digit = chopAndConvert(append + val.toString());
		}

	}

	/**
	 * @Description: Make string of digits , digit array contains
	 * @param val
	 * @return
	 */
	private String getStringOfDigit(LargeInteger val) {

		String str = "";
		int p = 0;
		for (;;) {
			try {
				str += String.valueOf(digit[p++]);
			} catch (Exception e) {
				return str;
			}
		}
	}

	@Override
	public String toString() {
		String str = "";

		for (int i = 0; i < digit.length; i++) {

			if (String.valueOf(digit[i]).length() >= 18) {
				str += String.valueOf(digit[i]);
			} else if (i != 0) {
				String st = String.valueOf(digit[i]);
				for (int j = 0; j < 18 - String.valueOf(digit[i]).length(); j++) {
					st = "0" + st;
				}

				str += st;
			} else {
				str += String.valueOf(digit[i]);
			}

		}
		str = cutOffLeadingZeroes(str);
		if (str.isEmpty()) {
			str = "0";
		}
		return str;
	}

	/**
	 * @Description: Shifting large integer by len position bitwise left.
	 * @param l
	 * @return
	 */
	public LargeInteger bitwiseLeftShift(long l) {
		LargeInteger li = new LargeInteger(this);
		for (int i = 0; i < l; i++) {
			li = li.bitwiseLeftShiftHelper();
		}
		return li;
	}

	/**
	 * @Description: Helper to the bitwiseLeftShift method.
	 * @return
	 */
	public LargeInteger bitwiseLeftShiftHelper() {
		long arr[];
		long result[];
		boolean stop;
		arr = this.getDigit();
		int len;
		len = this.getDigit().length;
		long carry = 0;
		long temp = arr[len - 1] * 2;
		carry = (int) (temp / 1000000000000000000l);
		arr[len - 1] = temp % 1000000000000000000l;
		for (int i = len - 2; i >= 0; i--) {
			temp = arr[i] * 2;
			arr[i] = temp % 1000000000000000000l + carry;
			carry = (int) (temp / 1000000000000000000l);
		}
		if (carry != 0) {
			result = new long[len + 1];
			result[0] = carry;
			for (int i = 0; i < len; i++) {
				result[i + 1] = arr[i];
			}
			this.setDigit(result);
			return this;
		}
		this.setDigit(arr);
		return this;
	}

	/**
	 * @Description: Rotate the small integer
	 * @param l
	 * @param carry
	 * @param i
	 * @param resultArr
	 * @return
	 */
	private int leftRotateSmall(LargeInteger l, long carry, int i,
			long[] resultArr) {
		long result = 0;
		int left;
		result = l.getDigit()[i];

		left = (int) (l.getDigit()[i] / 100000000000000000l);
		result = result % 100000000000000000l;
		result = result * 10;
		result = result + carry;
		resultArr[i] = result;
		return left;
	}

	/**
	 * @Description: Rotate the large integers
	 * @param l
	 * @param carry
	 * @param i
	 * @param resultArr
	 * @return
	 */
	private int leftRotateLarge(LargeInteger l, long carry, int i,
			long[] resultArr) {
		long result;
		int left;
		result = l.getDigit()[i];
		left = (int) (l.getDigit()[i] / 100000000000000000l);
		result = result % 100000000000000000l;
		result = result * 10;
		result = result + carry;
		resultArr[i + 1] = result;
		return left;
	}

	/**
	 * @Desciption:Providing sign to the integers.
	 * @param lr
	 */
	private void signFixed(LargeInteger lr) {
		if (lr.getDigit() != null) {
			if (String.valueOf(lr.getDigit()[0]).startsWith("-")) {
				lr.getDigit()[0] = Long.parseLong(String.valueOf(
						lr.getDigit()[0]).substring(1));
				lr.setSign(!lr.isSign());
			}
		}
	}

	/**
	 * @Description: Shifting large integer by len position left.
	 * @param pmul
	 * @param lint1
	 * @param len
	 * @return
	 */
	public LargeInteger leftShift(long l) {
		LargeInteger li = new LargeInteger(this);
		signFixed(li);

		for (int i = 0; i < l; i++) {
			li = li.leftShiftHelper();
		}
		return li;
	}

	private LargeInteger leftShiftHelper() {
		int len = 0;
		len = this.getDigit().length;
		long[] resultArr;
		LargeInteger li = new LargeInteger(this);
		if (String.valueOf(this.getDigit()[0]).length() >= 18) {
			resultArr = new long[len + 1];

			int carry = 0;

			len = li.getDigit().length;
			carry = 0;
			for (int i = len - 1; i >= 0; i--) {
				carry = leftRotateLarge(li, carry, i, resultArr);

			}
			resultArr[0] = carry;
			li.digit = resultArr;
			return li;

		} else {
			resultArr = new long[len];

			int carry = 0;

			len = li.getDigit().length;
			carry = 0;
			for (int i = len - 1; i >= 0; i--) {
				carry = leftRotateSmall(li, carry, i, resultArr);

			}
			li.digit = resultArr;
		}

		return li;
	}

	/**
	 * 
	 * @Description: Shifting large integer by len position right.
	 * @param pmul
	 * @param lint1
	 * @param len
	 * @return
	 */
	public LargeInteger rightShift(long l) {
		LargeInteger li = new LargeInteger(this);
		signFixed(li);
		for (int i = 0; i < l; i++) {
			li = li.rightShiftHelper();
		}

		return li;
	}

	private LargeInteger rightShiftHelper() {
		long[] temp;
		LargeInteger li = new LargeInteger(this);
		int borrow = 0;
		temp = this.getDigit();
		int len = temp.length;
		long resultArr[];
		if (len != 0 && len != 1 && String.valueOf(temp[0]).length() == 1
				&& temp[0] != 0) {
			borrow = (int) temp[0];
			resultArr = new long[len - 1];
			for (int i = 1; i < len; i++) {
				borrow = rightRotateLarge(li, borrow, i, resultArr);
			}
			li.digit = resultArr;
			return li;
		} else {

			for (int i = 0; i < len; i++) {
				borrow = rightRotateSmall(li, borrow, i);
			}

			return li;

		}
	}

	/**
	 * @Description: Rotate the integer clockwise
	 * @param l
	 * @param borrow
	 * @param i
	 * @param resultArr
	 * @return
	 */
	private int rightRotateLarge(LargeInteger l, long borrow, int i,
			long[] resultArr) {
		long result;
		int left;
		result = l.getDigit()[i] / 10;
		result = result + borrow * 100000000000000000l;
		left = (int) (l.getDigit()[i] % 10);
		resultArr[i - 1] = result;
		return left;
	}

	/**
	 * 
	 * @param l
	 * @param borrow
	 * @param i
	 * @return
	 */
	private int rightRotateSmall(LargeInteger l, long borrow, int i) {
		long result;
		int left;
		result = l.getDigit()[i] / 10;
		result = result + borrow * 100000000000000000l;
		left = (int) (l.getDigit()[i] % 10);
		l.getDigit()[i] = result;
		return left;
	}

	public long length() {
		return digit.length;
	}

	public boolean isSign() {
		return sign;
	}

	public void setSign(boolean sign) {
		this.sign = sign;
	}

	public void setDigit(long[] digit) {
		this.digit = digit;
	}

}
