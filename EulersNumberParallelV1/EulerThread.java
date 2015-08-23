package EulersNumberParallelV1;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;

public class EulerThread extends Thread {
	CommonObject common;
	BigDecimal sum = new BigDecimal(0);
	int precision;
	int iterations;
	int numberOfThreads;
	int numberOfThread;

	public EulerThread(CommonObject common, int precision,int iterations,
			int numberOfThreads, int numberOfThread) {
		this.common = common;
		this.precision = precision;
		this.iterations = iterations;
		this.numberOfThreads = numberOfThreads;
		this.numberOfThread = numberOfThread;
	}

	public void run() {
		search();
		common.addE(sum);
		System.out.println(numberOfThread + " done.");
	}

	public void search() {
		MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
		BigDecimal term = new BigDecimal(1);
		BigDecimal one = new BigDecimal(1);
		for (int i = numberOfThread; i < iterations; i += numberOfThreads) {
			// BigDecimal term = one.divide(factorial(new BigDecimal(i)), mc);
			// Calculate 1/n!
			/*
			 * factorial = factorial.multiply(new BigDecimal(i)); term =
			 * one.divide(factorial, mc);
			 */
			System.out.println(i);
			if (i >= iterations) {
				//System.out.println("Bigger");
			} else {
				// use previous term and divide by n
				term = one.divide(common.getFactorial(i), mc);

				sum = sum.add(term);
			}
			//System.out.println(term);
		}
	}

}
