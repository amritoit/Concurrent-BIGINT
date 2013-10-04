import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

public class Elementary {

	long[] digit1;
	long[] digit2;
	long[] result;
	long[] carry;
	long[] borrow;
	int numOfDigits;
	int tokenSize;
	public boolean isEndBorrow = false;
	public int T = 1;
	public boolean isEndCarry = false;
	// int numOfThreads;
	int noOfThreads;
	public int threadsExecuting;
	AtomicInteger sharedIndex;
	private boolean subdone = false;
	private boolean adddone = false;
	Thread mainThread;
	Thread tjoin;

	protected Elementary() {
		sharedIndex = new AtomicInteger(0);
	}

	Thread thread[];

	private void displayResult() {
		System.out.println("result is:");
		for (int i = 0; i < result.length; i++) {

			if (String.valueOf(result[i]).length() >= 18) {

				System.out.print(result[i]);
			} else if (i != 0) {
				String str = String.valueOf(result[i]);
				for (int j = 0; j < 18 - String.valueOf(result[i]).length(); j++) {
					str = "0" + str;
				}

				System.out.print(str);

			} else {

				System.out.print(result[i]);
			}

		}
		System.out.println();

	}

	// ///////////////////////////////////////////////////////////////////////////
	private void displayResultSub() {
		for (int i = 0; i < result.length; i++) {

			if (String.valueOf(result[i]).length() >= 18) {

				System.out.print(result[i]);
			} else if (i != 0) {
				String str = String.valueOf(result[i]);
				for (int j = 0; j < 18 - String.valueOf(result[i]).length(); j++) {
					str = "0" + str;
				}

				System.out.print(str);

			} else {

				System.out.print(result[i]);
			}

		}
		System.out.println();

	}

	// ////////////////////////////////////////////////////////
	/*
	 * private void fixResult() { for(int i=0;i<result.length;i++) {
	 * if(String.valueOf(result[i]).length()< 18) { //int no_of_zero = 18 -
	 * String.valueOf(result[i]).length(); for(int
	 * i=0;i<18-String.valueOf(result[i]).length();i++) {
	 * 
	 * }r
	 * 
	 * } } }
	 */
	public boolean isProcessingDone() {
		// int tokenSize;
		// int numOfDigits = Math.max(digit1.length,digit2.length);
		// tokenSize = (int)(numOfDigits/noOfThreads);
		// synchronized (thread) {
		//
		// for (Thread t : thread) {
		// if (t != null && t.isAlive()) {
		// return;
		// }
		// }
		// }

		// if (threadsExecuting == 0) {
		// for(Thread t:thread){
		// System.out.println(t.isAlive());
		// }
		if (isMoreCarry()) {
			return true;
		} else {
			adddone = true;
			// displayResult();
			// /System.out.println();
		}

		// }
		return false;

	}// ////////////////////

	public void isProcessingDoneSubstraction() {
		// int tokenSize;
		// int numOfDigits = Math.max(digit1.length,digit2.length);
		// tokenSize = (int)(numOfDigits/noOfThreads);
		if (threadsExecuting == 0) {
			if (isMoreBorrow()) {
				T++;
				createThreadsSub(tokenSize);
			} else {
				subdone = true;
				// displayResult();
				// displayResultSub();
			}

		}

	}// end of isProcessingDoneSubstraction

	private boolean isMoreCarry() {
		for (int i = 0; i < carry.length; i++) {
			if (carry[i] == 1)
				return true;
		}
		return false;

	}

	private boolean isMoreBorrow() {
		for (int i = 0; i < borrow.length; i++) {
			if (borrow[i] == 1)
				return true;
		}
		return false;

	}

	// /////////////////////////////////////////////////////////////
	// code for substraction

	public LargeInteger sub(LargeInteger val1, LargeInteger val2) {


		subdone = false;
		noOfThreads = LargeInteger.noOfProcessor();
		boolean sign = false;
		if (val1.compareTo(val2) == 1) {
			digit1 = val1.getDigit();
			digit2 = val2.getDigit(); //
		} else {
			digit2 = val1.getDigit();
			digit1 = val2.getDigit(); //
			sign = true;
		}
		// System.out.println(); // for(int i=0;i<digit1.length;i++) // { //
		// System.out.print(digit1[i]); // } //
		// System.out.println("digit 2 is "); // for(int
		// for(int i=0;i<digit2.length;i++) // { // System.out.print(digit2[i]);
		// // } //
		// set size of both the digits to be same and also size of the result.
		result = new long[digit1.length];
		borrow = new long[noOfThreads]; //
		threadsExecuting = noOfThreads;

		thread = new Thread[noOfThreads];
		numOfDigits = Math.max(digit1.length, digit2.length); // int tokenSize;

		tokenSize = (int) (numOfDigits / noOfThreads); //
		//System.out.println("token size " + tokenSize);

		createThreadsSub(tokenSize); // while (!subdone) { // // }
		LargeInteger ret = new LargeInteger("");
		ret.setDigit(result);
		if (ret.toString().startsWith("-")) {
			ret = new LargeInteger(ret.toString().substring(1));
			ret.setSign(true);
		}else{
			ret = new LargeInteger(ret.toString());
		}

		if (sign)
			ret.setSign(!ret.isSign());

		return ret;

		// BigInteger b = new BigInteger(val1.toString());
		// BigInteger b1 = new BigInteger(val2.toString());
		//
		// b = b.subtract(b1);
		// LargeInteger re;
		// if (b.toString().startsWith("-")) {
		// re = new LargeInteger(b.toString().substring(1));
		// re.setSign(true);
		// } else {
		// re = new LargeInteger(b.toString());
		// re.setSign(false);
		// }
		// return re;
		//
		
	}

	// //////////////////////////////////////////////////////////
	public LargeInteger add(LargeInteger val1, LargeInteger val2) {

		mainThread = Thread.currentThread();
		adddone = false;
		noOfThreads = LargeInteger.noOfProcessor();
		// System.out.println("Adding"+val1+"with"+val2);
		digit1 = val1.getDigit();
		digit2 = val2.getDigit();
		// System.out.println();
		// for(int i=0;i<digit1.length;i++)
		// {
		// System.out.print(digit1[i]);
		// }
		// System.out.println("digit 2 is ");
		// for(int i=0;i<digit2.length;i++)
		// {
		// System.out.print(digit2[i]);
		// }
		// set size of both the digits to be same and also size of the result.
		result = new long[digit1.length];
		carry = new long[noOfThreads];
		numOfDigits = Math.max(digit1.length, digit2.length);
		// threadsExecuting = noOfThreads;

		thread = new Thread[noOfThreads];
		// int numOfDigits = Math.max(digit1.length,digit2.length);
		// int tokenSize;

		tokenSize = (int) (numOfDigits / noOfThreads);
		// System.out.println("token size " + tokenSize);
		try {
			createThreads(tokenSize);
			// while (!adddone) {
			// //System.out.println("looping");
			// }
			// System.out.println("after loop");
			LargeInteger ret = new LargeInteger("");
			ret.setDigit(result);

			if (ret.toString().startsWith("-")) {
				ret = new LargeInteger(ret.toString().substring(1));
				ret.setSign(true);
			}
			return ret;

		} catch (Exception ex) {
			return null;
		}

	}

	public void createThreads(int tokenSize) {

		try {
			int start, end;
			start = 0;
			end = tokenSize - 1;
			// int numOfDigits;

			threadsExecuting = 0;
			if (tokenSize != 0) {
				threadsExecuting = LargeInteger.noOfProcessor();
				sharedIndex.set(threadsExecuting);
				// System.out.println("token size not zero");
				for (int i = 0; i < noOfThreads; i++) {

					if (i != noOfThreads - 1) {
						start = tokenSize * (i);
						end = tokenSize * (i + 1) - 1;

					} else {
						start = tokenSize * i;
						end = Math
								.max(tokenSize * (i + 1) - 1, numOfDigits - 1);

					}
					// System.out.println("Thread "+ i+ "takes start " + start +
					// "end "+ end);
					ParallelAddition pAdd = new ParallelAddition(start, end);
					pAdd.setElm(this);
					thread[i] = new Thread(pAdd);
					thread[i].start();
					thread[i].join();

				}
				// for (int i = 0; i < noOfThreads; i++) {
				//
				// try {
				// thread[i].join();
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// }
			} else {

				threadsExecuting = 1;
				sharedIndex.set(threadsExecuting);
				ParallelAddition pAdd = new ParallelAddition(0, numOfDigits - 1);
				pAdd.setElm(this);
				thread[0] = new Thread(pAdd);
				thread[0].start();
				// try {
				thread[0].join();
				// } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				// }

				//
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	public void createThreadsSub(int tokenSize) {
		int start, end;
		start = 0;
		end = tokenSize - 1;
		// int numOfDigits;

		threadsExecuting = 0;
		;
		if (tokenSize != 0) {
			threadsExecuting = LargeInteger.noOfProcessor();
			sharedIndex.set(threadsExecuting);
			//System.out.println("token size not zero");
			for (int i = 0; i < noOfThreads; i++) {

				if (i != noOfThreads - 1) {
					start = tokenSize * (i);
					end = tokenSize * (i + 1) - 1;

				} else {
					start = tokenSize * i;
					end = Math.max(tokenSize * (i + 1) - 1, numOfDigits - 1);

				}
				// System.out.println("Thread "+ i+ "takes start " + start +
				// "end "+ end);
				ParallelSubstraction pAdd = new ParallelSubstraction(start, end);
				pAdd.setElm(this);
				thread[i] = new Thread(pAdd);
				thread[i].start();

			}
			for (int i = 0; i < noOfThreads; i++) {

				try {
					thread[i].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {

			threadsExecuting = 1;
			sharedIndex.set(threadsExecuting);
			ParallelSubstraction pAdd = new ParallelSubstraction(0,
					numOfDigits - 1);
			pAdd.setElm(this);
			thread[0] = new Thread(pAdd);
			thread[0].start();
			try {
				thread[0].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//
		}

	}
}
