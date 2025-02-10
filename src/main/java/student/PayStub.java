package student;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PayStub implements IPayStub {
    private final String employeeName;
    private final double netPay;
    private final double taxesPaid;
    private final double ytdEarnings;  // These should be the final values, not modified again
    private final double ytdTaxesPaid;

    public PayStub(String employeeName, double netPay, double taxesPaid, double ytdEarnings, double ytdTaxesPaid) {
        this.employeeName = employeeName;
        this.netPay = netPay;
        this.taxesPaid = taxesPaid;
        this.ytdEarnings = ytdEarnings;
        this.ytdTaxesPaid = ytdTaxesPaid;
    }

    @Override
    public double getPay() {
        return netPay;
    }

    @Override
    public double getTaxesPaid() {
        return taxesPaid;
    }

    // Ensure that toCSV() does not cause additional modifications
    @Override
    public String toCSV() {
        return String.format("%s,%s,%s,%s,%s",
                employeeName, formatNumber(netPay), formatNumber(taxesPaid),
                formatNumber(ytdEarnings), formatNumber(ytdTaxesPaid));
    }

    // Formatting method to remove unnecessary trailing zeros
    private String formatNumber(double value) {
        BigDecimal number = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

        // If it's a whole number, enforce one decimal place
        if (number.scale() <= 0) {
            return String.format("%.1f", value);
        }

        return number.toPlainString();
    }
}

