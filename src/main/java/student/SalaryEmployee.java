package student;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a salaried employee in the payroll system.
 * This class calculates payroll, maintains YTD earnings and tax information, and provides
 * formatted data output.
 *
 * <p>It implements the {@code IEmployee} interface and handles payroll calculations
 * on a biweekly basis.</p>
 *
 * @author [Your Name]
 * @version 1.0
 */
@SuppressWarnings({"checkstyle:Indentation", "checkstyle:LineLength", "checkstyle:SummaryJavadoc"})
public class SalaryEmployee implements IEmployee {
    @SuppressWarnings("checkstyle:Indentation")
    private final String name;
    @SuppressWarnings("checkstyle:Indentation")
    private final String id;
    @SuppressWarnings("checkstyle:Indentation")
    private final double payRate;
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

    /**
     * Constructs a new {@code SalaryEmployee} instance.
     *
     * @param name             the name of the employee
     * @param id               the unique identifier for the employee
     * @param payRate          the employee's annual salary
     * @param ytdEarnings      the year-to-date earnings of the employee
     * @param ytdTaxesPaid     the year-to-date taxes paid by the employee
     * @param pretaxDeductions the pre-tax deductions for the employee
     */
    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
    public SalaryEmployee(String name, String id, double payRate,
                          double ytdEarnings, double ytdTaxesPaid,
                          double pretaxDeductions) {
        this.name = name;
        this.id = id;
        this.payRate = payRate;
        this.ytdEarnings = ytdEarnings;
        this.ytdTaxesPaid = ytdTaxesPaid;
        this.pretaxDeductions = pretaxDeductions;
        this.originalYtdEarnings = ytdEarnings;
        this.originalYtdTaxesPaid = ytdTaxesPaid;
        System.out.println(this.payRate);
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
        return payRate;
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
        BigDecimal grossPay = BigDecimal.valueOf(payRate)
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
                formatNumber(payRate), // Use stored netPay
                formatNumber(pretaxDeductions),
                formatNumber(ytdEarnings),
                formatNumber(ytdTaxesPaid)
        );
    }

    /**
     * Restore original YTD values
     */
    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
    public void restoreOriginalYtdValues() {
        this.ytdEarnings = this.originalYtdEarnings;
        this.ytdTaxesPaid = this.originalYtdTaxesPaid;
    }

    /**
     * Formats numerical values for CSV output.
     *
     * <p>Ensures whole numbers retain one decimal place, while other numbers maintain their full precision.</p>
     *
     * @param value the {@code BigDecimal} value to format
     * @return a properly formatted string representation of the number
     */

    @SuppressWarnings({"checkstyle:CommentsIndentation", "checkstyle:Indentation", "checkstyle:LineLength"})
    private String formatNumber(double value) {
        BigDecimal number = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
        if (number.scale() == 0) {
            return String.format("%.1f", value);
        }

        return number.toPlainString();
    }

}
