package EulersNumberParallelV2;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;


public class CommonObject {
	//private BigDecimal[] factorialArray;
	private BigDecimal[] nominatorArray;
	private int iterations;
	private int precision;
	private BigDecimal denominator;
	private int counter;
	private BigDecimal nominator = new BigDecimal(0);
	private BigDecimal e;
	
	
	public CommonObject(int iterations) {
		this.iterations = iterations;	
		e = new BigDecimal(0);
		fillFactorial(iterations);
		this.precision = (denominator +"").length();
	}

	private void fillFactorial(int iterations) {
		//factorialArray = new BigDecimal[iterations];
		nominatorArray = new BigDecimal[iterations];
		for (int i = 0; i < iterations; i++) {
			nominatorArray[i] = new BigDecimal(1);
		}
		/*factorialArray[0] = new BigDecimal(1);
		for (int i = 1; i < iterations; i++) {
			factorialArray[i] = factorialArray[i - 1].multiply(new BigDecimal(
					i + 1));
			// System.out.println(factorialArray[i]);
			/*for (int j = 0; j < i; j++) {
				nominatorArray[j] = nominatorArray[j].multiply(new BigDecimal(
						i + 1));
			}*/
		//}
		//System.out.println(Arrays.toString(nominatorArray));
		for (int i = iterations - 2; i >= 0; i--) {
			// System.out.println(i);
			nominatorArray[i] = nominatorArray[i + 1].multiply(new BigDecimal(i+2));
		}
		denominator = nominatorArray[0];
		//System.out.println(Arrays.toString(nominatorArray));
	}
	
	public int getPrecision() {
		return precision;
	}
	
	public synchronized BigDecimal getE() {
		MathContext mcFinalDivision = new MathContext(
				(int) Math.round(this.precision), RoundingMode.HALF_UP);
		e = nominator.divide(denominator, mcFinalDivision);
		System.out.println("Length nominator:" + nominator.toString().length());
		e = e.add(new BigDecimal(1));
		return e;
	}
	
	public synchronized void addE(BigDecimal eAdd) {
		e = e.add(eAdd);
	}
	
	public /*synchronized*/ BigDecimal getNominator(int i) {
		return nominatorArray[i];
	}
	
	public synchronized void addNominator(BigDecimal nomAdd) {
		nominator = nominator.add(nomAdd);
	}

	public synchronized BigDecimal getDenominator() {
		// TODO Auto-generated method stub
		return denominator;
	}
}
