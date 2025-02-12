package student;

import java.math.BigDecimal;
import java.math.RoundingMode;


@SuppressWarnings({"checkstyle:RightCurly", "checkstyle:LineLength", "checkstyle:MissingJavadocType"})
public class SalaryEmployee implements IEmployee {
    @SuppressWarnings("checkstyle:Indentation")
    private final String name;
    @SuppressWarnings("checkstyle:Indentation")
    private final String id;
    @SuppressWarnings("checkstyle:Indentation")
    private final double annualSalary;
    @SuppressWarnings("checkstyle:Indentation")
    private final double pretaxDeductions;
    @SuppressWarnings("checkstyle:Indentation")
    private double ytdEarnings;
    @SuppressWarnings("checkstyle:Indentation")
    private double ytdTaxesPaid;
    @SuppressWarnings("checkstyle:Indentation")
    private final double originalYtdEarnings;
    @SuppressWarnings("checkstyle:Indentation")
    private final double originalYtdTaxesPaid;
    @SuppressWarnings("checkstyle:Indentation")
    private double netPay; // Store net pay for toCSV()

    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
    public SalaryEmployee(String name, String id, double annualSalary,
                          double ytdEarnings, double ytdTaxesPaid,
                          double pretaxDeductions) {
        this.name = name;
        this.id = id;
        this.annualSalary = annualSalary;
        this.ytdEarnings = ytdEarnings;
        this.ytdTaxesPaid = ytdTaxesPaid;
        this.pretaxDeductions = pretaxDeductions;
        this.originalYtdEarnings = ytdEarnings;
        this.originalYtdTaxesPaid = ytdTaxesPaid;
    }

    @SuppressWarnings({"checkstyle:LeftCurly", "checkstyle:Indentation"})
    @Override
    public String getName() {
        return name;
    }

    @SuppressWarnings({"checkstyle:LeftCurly", "checkstyle:EmptyLineSeparator", "checkstyle:Indentation"})
    @Override
    public String getID() {
        return id;
    }

    @SuppressWarnings({"checkstyle:LeftCurly", "checkstyle:EmptyLineSeparator", "checkstyle:Indentation"})
    @Override
    public double getPayRate() {
        return annualSalary;
    }

    @SuppressWarnings({"checkstyle:LeftCurly", "checkstyle:EmptyLineSeparator", "checkstyle:Indentation"})
    @Override
    public String getEmployeeType() {
        return "SALARY";
    }

    @SuppressWarnings({"checkstyle:LeftCurly", "checkstyle:EmptyLineSeparator", "checkstyle:Indentation"})
    @Override
    public double getYTDEarnings() {
        return ytdEarnings;
    }

    @SuppressWarnings({"checkstyle:LeftCurly", "checkstyle:EmptyLineSeparator", "checkstyle:Indentation"})
    @Override
    public double getYTDTaxesPaid() {
        return ytdTaxesPaid;
    }

    @SuppressWarnings({"checkstyle:LeftCurly", "checkstyle:EmptyLineSeparator", "checkstyle:Indentation"})
    @Override
    public double getPretaxDeductions() {
        return pretaxDeductions;
    }

    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:LineLength"})
    @Override
    public IPayStub runPayroll(double hoursWorked) {
        // Biweekly salary calculation
        BigDecimal grossPay = BigDecimal.valueOf(annualSalary)
                .divide(BigDecimal.valueOf(24), 2, RoundingMode.HALF_UP);

        // Deduct pretax contributions
        BigDecimal taxablePay = grossPay.subtract(BigDecimal.valueOf(pretaxDeductions))
                .max(BigDecimal.ZERO);

        // Tax calculation (22.65%)
        BigDecimal taxes = taxablePay.multiply(BigDecimal.valueOf(0.2265))
                .setScale(2, RoundingMode.HALF_UP);

        // Calculate net pay and store it
        this.netPay = taxablePay.subtract(taxes).doubleValue();

        // Update YTD earnings and taxes
        this.ytdEarnings = originalYtdEarnings + this.netPay;
        this.ytdTaxesPaid = originalYtdTaxesPaid + taxes.doubleValue();

        // Return the pay stub
        return new PayStub(this.name, this.netPay, taxes.doubleValue(), this.ytdEarnings, this.ytdTaxesPaid);
    }

    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public String toCSV() {
        return String.format("SALARY,%s,%s,%s,%s,%s,%s",
                name,
                id,
                formatNumber(netPay), // Use stored netPay
                formatNumber(pretaxDeductions),
                formatNumber(ytdEarnings),
                formatNumber(ytdTaxesPaid)
        );
    }

    // Restore original YTD values
    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
    public void restoreOriginalYtdValues() {
        this.ytdEarnings = this.originalYtdEarnings;
        this.ytdTaxesPaid = this.originalYtdTaxesPaid;
    }

    /**
     * Formats numbers to match output requirements.
     */
    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:LineLength"})
    private String formatNumber(double value) {
        BigDecimal number = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

        // If it has one decimal place, ensure it keeps exactly one
        if (number.scale() == 0) {
            return String.format("%.1f", value);
        }

        return number.toPlainString();
    }

}
