package student;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SalaryEmployee implements IEmployee {
    private final String name;
    private final String id;
    private final double annualSalary;
    private final double pretaxDeductions;
    private double ytdEarnings;
    private double ytdTaxesPaid;
    private final double originalYtdEarnings;
    private final double originalYtdTaxesPaid;
    private double netPay; // Store net pay for toCSV()

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
        this.netPay = 0;
    }

    @Override public String getName() { return name; }
    @Override public String getID() { return id; }
    @Override public double getPayRate() { return annualSalary; }
    @Override public String getEmployeeType() { return "SALARY"; }
    @Override public double getYTDEarnings() { return ytdEarnings; }
    @Override public double getYTDTaxesPaid() { return ytdTaxesPaid; }
    @Override public double getPretaxDeductions() { return pretaxDeductions; }

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
    public void restoreOriginalYtdValues() {
        this.ytdEarnings = this.originalYtdEarnings;
        this.ytdTaxesPaid = this.originalYtdTaxesPaid;
    }

    /**
     * Formats numbers to match output requirements.
     */
    private String formatNumber(double value) {
        BigDecimal number = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

        // If it has one decimal place, ensure it keeps exactly one
        if (number.scale() == 0) {
            return String.format("%.1f", value);
        }

        return number.toPlainString();
    }

}
