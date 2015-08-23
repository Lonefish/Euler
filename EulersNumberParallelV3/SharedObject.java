package EulersNumberParallelV3;
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
	private BigDecimal e;
	private int threads;
	private BigDecimal[] nominatorArray;
	private BigDecimal[] denominatorArray;
	
	
	public SharedObject(int iterations, int threads) {
		this.iterations = iterations;	
		e = new BigDecimal(0);
		this.threads = threads;
		this.nominatorArray = new BigDecimal[threads];
		this.denominatorArray = new BigDecimal[threads];
	}
	
	public int getPrecision() {
		return precision;
	}
	
	public synchronized BigDecimal getE() {
		this.precision = denominator.toString().length();
		MathContext mcFinalDivision = new MathContext(
				(int) Math.round(this.precision), RoundingMode.HALF_UP);
		e = nominator.divide(denominator, mcFinalDivision);
		e = e.add(new BigDecimal(1));
		return e;
	}
	
	public synchronized void calculateFraction() {
		System.out.println(Arrays.toString(denominatorArray));
		System.out.println(Arrays.toString(nominatorArray));
/*
		// TODO Auto-generated method stub
		for(int i = 0; i < threads-1; i++) {
			denominator = denominator.multiply(denominatorArray[i]);
			for(int j = 0; j < threads; j++) {
				if(j == i) {
					continue;
				}
				nominatorArray[i] = nominatorArray[i].multiply(denominatorArray[j]);
			}
			nominator = nominator.add(nominatorArray[i]);
			nominator = nominator.subtract(denominatorArray[i+1]);
		}
		denominator = denominator.multiply(denominatorArray[threads-1]);
		nominator = nominator.add(nominatorArray[threads-1]);
		System.out.println(Arrays.toString(denominatorArray));
		System.out.println(Arrays.toString(nominatorArray));
		System.out.println(nominator);
		System.out.println(denominator);*/
		nominator = nominatorArray[nominatorArray.length - 1];
		System.out.println("Nominator first calculation");
		for(int i = denominatorArray.length-1; i > 0; i--) {
			denominator = denominator.multiply(denominatorArray[i]);
			System.out.println("Denominator forloop" + i);
			nominator = nominator.add(nominatorArray[i-1].multiply(denominator));
			System.out.println("Nominator add" + i);
			nominator = nominator.subtract(denominator);
			System.out.println("Nominator subtract" + i);
		}
		denominator = denominator.multiply(denominatorArray[0]);
		System.out.println("Denominator out of for loop");
		System.out.println(Arrays.toString(denominatorArray));
		System.out.println(Arrays.toString(nominatorArray));
		System.out.println(nominator);
		System.out.println(denominator);
	}

	public synchronized void saveDenominator(int thread, BigDecimal denominator) {
		denominatorArray[thread] = denominator;
	}
	
	public synchronized void saveNominator(int thread, BigDecimal nominator) {
		nominatorArray[thread] = nominator;
	}
}
