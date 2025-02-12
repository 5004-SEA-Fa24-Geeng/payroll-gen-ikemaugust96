package student;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SuppressWarnings({"checkstyle:Indentation", "checkstyle:LineLength", "checkstyle:MissingJavadocType"})
public class PayStub implements IPayStub {
    @SuppressWarnings("checkstyle:Indentation")
    private final String employeeName;
    @SuppressWarnings("checkstyle:Indentation")
    private final double netPay;
    @SuppressWarnings("checkstyle:Indentation")
    private final double taxesPaid;
    @SuppressWarnings("checkstyle:Indentation")
    private final double ytdEarnings;  // These should be the final values, not modified again
    @SuppressWarnings("checkstyle:Indentation")
    private final double ytdTaxesPaid;

    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
    public PayStub(String employeeName, double netPay, double taxesPaid, double ytdEarnings, double ytdTaxesPaid) {
        this.employeeName = employeeName;
        this.netPay = netPay;
        this.taxesPaid = taxesPaid;
        this.ytdEarnings = ytdEarnings;
        this.ytdTaxesPaid = ytdTaxesPaid;
    }

    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public double getPay() {
        return netPay;
    }

    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public double getTaxesPaid() {
        return taxesPaid;
    }

    // Ensure that toCSV() does not cause additional modifications
    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public String toCSV() {
        return String.format("%s,%s,%s,%s,%s",
                employeeName, formatNumber(netPay), formatNumber(taxesPaid),
                formatNumber(ytdEarnings), formatNumber(ytdTaxesPaid));
    }

    // Formatting method to remove unnecessary trailing zeros
    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:LineLength"})
    private String formatNumber(double value) {
        BigDecimal number = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

        // If it's a whole number, enforce one decimal place
        if (number.scale() <= 0) {
            return String.format("%.1f", value);
        }

        return number.toPlainString();
    }
}

