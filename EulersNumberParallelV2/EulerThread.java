package EulersNumberParallelV2;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;

public class EulerThread extends Thread {
	CommonObject common;
	BigDecimal sum = new BigDecimal(0);
	BigDecimal nominator = new BigDecimal(1);
	int precision;
	int iterations;
	int numberOfThreads;
	int numberOfThread;

	public EulerThread(CommonObject common, int precision, int iterations,
			int numberOfThreads, int numberOfThread) {
		this.common = common;
		this.precision = precision;
		this.iterations = iterations;
		this.numberOfThreads = numberOfThreads;
		this.numberOfThread = numberOfThread;
	}

	//runmethod of the thread
	public void run() {
		//calculate the nominator
		calculate();
		//add the nominator to the common nominator
		common.addNominator(nominator);
		//print when done
		System.out.println(numberOfThread + " done.");
	}

	public void calculate() {
		/*
		 * Calculate the nominator for the current term. Add it to the nominator in memory.
		 * 
		 * The idea is that the terms are put on a common denominator, after all the terms are summed they're divided by the denominator.
		 * (This happens in the commonobject)
		 */
		
		for (int i = numberOfThread; i < iterations; i += numberOfThreads) {
			nominator = nominator.add(common.getNominator(i));
		}
	}

}
