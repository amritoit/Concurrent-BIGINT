public class ParallelAddition implements Runnable {

	private Elementary elm;
	int index;

	private int startIndex, endIndex;

	public ParallelAddition(int startIndex, int endIndex) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	@Override
	public void run() {
		try {
			index = elm.sharedIndex.decrementAndGet();
			// System.out.println("adding " + elm.digit1[index]
			// +"with "+elm.digit2[index]);
			/*
			 * String str; str = String.valueOf(index+ "------>"); for(int
			 * i=startIndex;i<=endIndex;i++) { str= str +
			 * (String.valueOf(elm.digit1[i])); } System.out.println(str);
			 * System.out.println("got index : "+index);
			 */

			long localCarry;
			if (elm.T == 1) {
				localCarry = 0;

				for (int i = endIndex; i >= startIndex; i--) {
					elm.result[i] = elm.digit1[i] + elm.digit2[i] + localCarry;
					if (String.valueOf(elm.result[i]).length() > 18) {
						localCarry = 1;
						elm.result[i] = elm.result[i] % 1000000000000000000L;
					} else {
						localCarry = 0;
					}

				}
				// System.out.println(index+"-->"+elm.result[index]);

			} else {
				localCarry = 0;
				elm.result[endIndex] = elm.result[endIndex] + elm.carry[index]
						+ localCarry;

				if (String.valueOf(elm.result[endIndex]).length() > 18) {
					localCarry = 1;
					elm.result[endIndex] = elm.result[endIndex] % 1000000000000000000L;
				}

				for (int i = endIndex - 1; i >= startIndex; i--) {
					elm.result[i] = elm.result[i] + localCarry;
					if (String.valueOf(elm.result[i]).length() > 18) {
						localCarry = 1;
						elm.result[i] = elm.result[i] % 1000000000000000000L;
					} else {
						localCarry = 0;
					}

				}

			}
			// System.out.println(index+"-->"+elm.result[index]);
			if (index != elm.noOfThreads - 1) {
				if (localCarry == 1) {
					// System.out.println("for index "+ temp +"geneated carry");
					elm.carry[index + 1] = 1;
				} else {
					elm.carry[index + 1] = 0;
				}
			} else {

				if (localCarry == 1) {
					// System.out.println("isendcarry");
					elm.isEndCarry = true;
				}

			}
			boolean flag = false;
			Thread tid = null;
			synchronized (elm) {

				elm.threadsExecuting--;
				if (elm.threadsExecuting == 0)
					flag = elm.isProcessingDone();
				if (flag) {
					elm.T++;
					tid = Thread.currentThread();
				}

			}
			if (flag && tid == Thread.currentThread()) {
				elm.createThreads(elm.tokenSize);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public Elementary getElm() {
		return elm;
	}

	public void setElm(Elementary elm) {
		this.elm = elm;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

}
