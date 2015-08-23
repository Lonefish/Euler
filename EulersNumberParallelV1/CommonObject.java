package EulersNumberParallelV1;
import java.math.BigDecimal;
import java.util.Arrays;


public class CommonObject {
	private BigDecimal[] factorial;
	private int iterations;
	private int counter;
	private BigDecimal e;
	
	
	public CommonObject(int iterations) {
		this.factorial = new BigDecimal[iterations];
		this.iterations = iterations;	
		e = new BigDecimal(0);
		fillFactorial();
	}

	private void fillFactorial() {
		factorial[0] = new BigDecimal(1);
		for(int i = 1; i < iterations; i++) {
			factorial[i] = factorial[i-1].multiply(new BigDecimal(i));
			//System.out.println(factorial[i]);
		}
	}
	
	public int getPrecision() {
		return factorial[factorial.length - 1].toString().length();
	}
	
	public synchronized BigDecimal getE() {
		return e;
	}
	
	public /*synchronized*/ BigDecimal getFactorial(int i) {
		return factorial[i];
	}
	
	public synchronized void addE(BigDecimal eAdd) {
		e = e.add(eAdd);
	}
}
