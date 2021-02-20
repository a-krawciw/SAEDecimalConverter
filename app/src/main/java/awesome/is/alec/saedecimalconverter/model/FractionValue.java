package awesome.is.alec.saedecimalconverter.model;

import android.telecom.StatusHints;

import java.math.BigInteger;

public class FractionValue {
    private final double value;
    private final int denominator;
    private final Units units;
    private final int numerator;
    private final int largestDenominator;


    public FractionValue(double value, int maxDenominator, Units units){
        this.value = value;
        this.largestDenominator = maxDenominator;
        int numerator = (int)Math.round(value*maxDenominator);

        int max_div = max_divisor(numerator, maxDenominator);
        this.numerator = numerator /max_div;
        this.denominator = maxDenominator / max_div;
        this.units = units;
    }

    public static int max_divisor(int i1, int i2) {
        BigInteger b1 = BigInteger.valueOf(i1);
        BigInteger b2 = BigInteger.valueOf(i2);
        return b1.gcd(b2).intValue();
    }

    public FractionValue toUnit(Units newUnit){
        return new FractionValue(this.units.convertToUnit(newUnit, value), largestDenominator, newUnit);
    }

    public FractionValue withMaxDenominator(int newMax){
        return new FractionValue(value, newMax, units);
    }

    public int getNumerator(){
        return numerator;
    }

    public int getDenominator(){
        return denominator;
    }

    public Units getUnits() {
        return units;
    }

    public double getValue() {
        return value;
    }
}
