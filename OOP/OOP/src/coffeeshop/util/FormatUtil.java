package coffeeshop.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormatUtil {
    private static final Locale VI_VN = new Locale("vi", "VN");
    private static final DecimalFormatSymbols SYMBOLS = DecimalFormatSymbols.getInstance(VI_VN);
    private static final DecimalFormat DEC = new DecimalFormat("#,###");
    static {
        DEC.setDecimalFormatSymbols(SYMBOLS);
        DEC.setMaximumFractionDigits(0);
        DEC.setMinimumFractionDigits(0);
    }

    public static String formatCurrency(double amount) {
        long rounded = Math.round(amount);
        return DEC.format(rounded) + " VND";
    }
}
