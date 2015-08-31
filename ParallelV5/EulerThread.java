package ParallelV5;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;

public class EulerThread extends Thread {
	SharedObject common;
	BigDecimal sum = new BigDecimal(0);
	BigDecimal nominator = new BigDecimal(1);
	BigDecimal denominator = new BigDecimal(1);
	int iterations;
	int numberOfThreads;
	int numberOfThread;

	//constructor
	public EulerThread(SharedObject common, int iterations,
			int numberOfThreads, int numberOfThread) {
		this.common = common;
		this.iterations = iterations;
		this.numberOfThreads = numberOfThreads;
		this.numberOfThread = numberOfThread;
	}

	// runmethod of the thread
	public void run() {
		// calculate the nominator
		calculate();
		// add the nominator to the common nominator
		common.saveDenominator(numberOfThread, denominator);
		common.saveNominator(numberOfThread, nominator);
		// print when done
		System.out.println(numberOfThread + " done.");
	}

	public void calculate() {
		BigDecimal lastTerm = new BigDecimal(1);
		//int lastI = 0;
		
		//Lowerbound for for loop
		int lowerBoundFor = (numberOfThread == 0 ? 0 : (iterations * numberOfThread)-1);
		
		for (int i = (iterations * (numberOfThread + 1)) - 2; i >= lowerBoundFor ; i--) {
			//System.out.println(numberOfThread + "thread : " + i);
			lastTerm = lastTerm.multiply(new BigDecimal(i + 2));
			nominator = nominator.add(lastTerm);
			//lastI = i;
		}
		denominator = lastTerm;
	}

}
