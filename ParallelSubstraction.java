
public class ParallelSubstraction implements Runnable {

	private Elementary elm;
	private int index;
	private int startIndex,endIndex;
	public ParallelSubstraction(int start,int end)
	{
		this.startIndex = start;
		this.endIndex = end;
	}
	@Override
	public void run() {
		
		int index = elm.sharedIndex.decrementAndGet();
		
		long localBorrow;
		if(elm.T==1)
		{
			localBorrow = 0;
			for(int i=endIndex;i>=startIndex;i--)
			{
				if(elm.digit1[i]>=(elm.digit2[i]+localBorrow))
				{
					elm.result[i] = elm.digit1[i] - elm.digit2[i] - localBorrow;
					localBorrow = 0;
				}
				else
				{
					int pos;
					pos = String.valueOf(elm.digit1[i]).length();
					elm.result[i] = (long)(Math.pow(10, pos+1)) + elm.digit1[i]  - elm.digit2[i] - localBorrow;
					localBorrow  =1 ;
					
					
				}
				
				
				
			}
			
		}
		else
		{
			localBorrow =0;
			if(elm.digit1[endIndex]>= (elm.digit2[endIndex]+ elm.borrow[index]))
			{
					elm.result[endIndex] = elm.digit1[endIndex] = elm.digit2[endIndex] - elm.borrow[index];
					localBorrow = 0;

				
			}
			for(int i=endIndex;i>=startIndex;i--)
			{
				if(elm.digit1[i]>=(elm.digit2[i]+localBorrow))
				{
					elm.result[i] = elm.digit1[i] - elm.digit2[i] - localBorrow;
					localBorrow = 0;
				}
				else
				{
					int pos;
					pos = String.valueOf(elm.digit1[i]).length();
					elm.result[i] = (long)(Math.pow(10, pos+1)) + elm.digit1[i]  - elm.digit2[i] - localBorrow;
					localBorrow  =1 ;
				}
				

				
			}
			
			
		}//end of if condition
		
		if(index!=elm.noOfThreads-1)
		{
			if(localBorrow==1)
			{
				elm.borrow[index+1] = 1;
			}
			else
			{
				elm.borrow[index+1] = 0;
			}
		}
		else
		{
			if(localBorrow==1)
			{
				elm.isEndBorrow = true;
			}
		}
		
		elm.threadsExecuting--;
		elm.isProcessingDoneSubstraction();
		
	}
		
		
		// TODO Auto-generated method stub

	
	public Elementary getElm() {
		return elm;
	}
	public void setElm(Elementary elm) {
		this.elm = elm;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
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
