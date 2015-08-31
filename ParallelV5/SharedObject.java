package ParallelV5;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;


public class SharedObject {
	//private BigDecimal[] factorialArray;
	private int iterations;
	private int precision;
	private BigDecimal nominator = new BigDecimal(0);
	private BigDecimal denominator = new BigDecimal(1);
	private BigDecimal e = new BigDecimal(1);
	private int threads;
	
	/*
	 * Arrays containing nominator and denominator from the threads with array[0] containing 1/1! 
	 * and array[last] containing 1/n!
	 */
	private BigDecimal[] nominatorArray;
	private BigDecimal[] denominatorArray;
	
	//Constructor
	public SharedObject(int iterations, int threads) {
		this.iterations = iterations;	
		e = new BigDecimal(0);
		this.threads = threads;
 		this.nominatorArray = new BigDecimal[threads];
		this.denominatorArray = new BigDecimal[threads];
	}
	
	public int getPrecision() {
		this.precision = denominator.toString().length();
		return precision;
	}
	
	/*
	 * Calculate e by dividing the nominator by the denominator.
	 * Precision is the length of the denominator, as this is the most precise the answer could ever be.
	 * 
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * (Shouldn't this be smaller? Since the nominator is >> than 1, so the precision is most likely to big.)
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 
	 */
	public synchronized BigDecimal getE() {
		//Get precision
		this.precision = denominator.toString().length();
		
		//Create mathcontext for division
		MathContext mcFinalDivision = new MathContext(
				(int) Math.round(this.precision), RoundingMode.HALF_UP);
		
		//divide
		e = nominator.divide(denominator, mcFinalDivision);
		//add 1 to account for 1/0!
		e = e.add(new BigDecimal(1));
		return e;
	}
	
	//Calculate the fraction
	public synchronized void calculateFraction() {
		//Start with the denominator set to the denominator of the last fraction
		nominator = nominatorArray[nominatorArray.length - 1];
		
		System.out.println("Nominator first calculation");
		
		//Calculate denominator and nominator
		for(int i = denominatorArray.length-1; i > 0; i--) {
			//Multiply the denominator with the next denominator (starting from right to left)
			denominator = denominator.multiply(denominatorArray[i]);
			System.out.println("Denominator forloop" + i);
			//Add the nominator to the previous one, multiplied with the denominator
			nominator = nominator.add(nominatorArray[i-1].multiply(denominator));
			System.out.println("Nominator add" + i);
			//Account for 1 multiplication too much (Multiply with denominator - 1? Then again, it's a subtraction as well..)
			nominator = nominator.subtract(denominator);
			System.out.println("Nominator subtract" + i);
		}
		//Last multiplication of the denominator
		denominator = denominator.multiply(denominatorArray[0]);
		System.out.println("Denominator out of for loop");
	}

	/*
	 * Save the denominator of a specific thread in an array.
	 * Synchronized to prevent corruption
	 */
	public synchronized void saveDenominator(int thread, BigDecimal denominator) {
		denominatorArray[thread] = denominator;
	}
	
	/*
	 * Save the nominator of a specific thread in an array.
	 * Synchronized to prevent corruption
	 */
	public synchronized void saveNominator(int thread, BigDecimal nominator) {
		nominatorArray[thread] = nominator;
	}
	
	/*
	 * Get the denominator of a specific thread in an array.
	 * Synchronized to prevent corruption
	 */
	public synchronized BigDecimal getDenominator(int thread) {
		return denominatorArray[thread];
	}
	
	/*
	 * Get the nominator of a specific thread in an array.
	 * Synchronized to prevent corruption
	 */
	public synchronized BigDecimal getNominator(int thread) {
		return nominatorArray[thread];
	}
	
	public synchronized void addE(BigDecimal e) {
		this.e.add(e);
	}

	public BigDecimal getEParallelDivided() {
		return e;
	}
}
