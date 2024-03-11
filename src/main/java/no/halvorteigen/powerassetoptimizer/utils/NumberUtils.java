package no.halvorteigen.powerassetoptimizer.utils;

public class NumberUtils {

    private NumberUtils() {
        // Hide default constructor
    }

    public static String fillLeadingZeros(int number, int length) {
        if (String.format("%d", number).length() > length) {
            throw new IllegalArgumentException("Number is longer than length");
        }
        return String.format("%0" + length + "d", number);
    }
}
