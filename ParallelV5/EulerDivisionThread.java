package ParallelV5;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class EulerDivisionThread  extends Thread {
	SharedObject common;
	BigDecimal sum = new BigDecimal(0);
	BigDecimal nominator = new BigDecimal(1);
	BigDecimal denominator = new BigDecimal(1);
	BigDecimal e = new BigDecimal(0);
	int numberOfThreads;
	int numberOfThread;
	
	//constructor
	public EulerDivisionThread(SharedObject common,
			int numberOfThreads, int numberOfThread) {
		this.common = common;
		this.numberOfThreads = numberOfThreads;
		this.numberOfThread = numberOfThread;
	}
	
	// runmethod of the thread
	public void run() {
		// calculate the division
		calculate();
		
		// print when done
		System.out.println(numberOfThread + " done.");
	}

	private void calculate() {
		//Get precision
		int precision = common.getPrecision();
		System.out.println("Precision" + precision);
		//Create mathcontext for division
		MathContext mcFinalDivision = new MathContext(
				(int) Math.round(precision), RoundingMode.HALF_UP);
		
		//divide
		e = common.getNominator(numberOfThread).divide(common.getDenominator(numberOfThread), mcFinalDivision);
		//add 1 to account for 1/0!
		common.addE(e);
	}
}
