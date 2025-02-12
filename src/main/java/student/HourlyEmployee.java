package student;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocType"})
public class HourlyEmployee implements IEmployee, IPayStub {
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
    private double netPay;  // Store the net pay
    @SuppressWarnings("checkstyle:Indentation")
    private final double originalYtdEarnings;
    @SuppressWarnings("checkstyle:Indentation")
    private final double originalYtdTaxesPaid;

    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:AbbreviationAsWordInName"})
    public void setYTDEarnings(double earnings) {
        this.ytdEarnings = earnings;
    }

    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:AbbreviationAsWordInName"})
    public void setYTDTaxesPaid(double taxesPaid) {
        this.ytdTaxesPaid = taxesPaid;
    }
    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:EmptyLineSeparator", "checkstyle:MissingJavadocMethod"})
    public HourlyEmployee(String name, String id, double payRate,
                          double ytdEarnings, double ytdTaxesPaid,
                          double pretaxDeductions) {
        this.name = name;
        this.id = id;
        this.payRate = payRate;
        this.ytdEarnings = ytdEarnings;
        this.ytdTaxesPaid = ytdTaxesPaid;
        this.pretaxDeductions = pretaxDeductions;
        this.originalYtdEarnings = this.ytdEarnings;
        this.originalYtdTaxesPaid = this.ytdTaxesPaid;
    }

    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public String getName() {
        return name;
    }

    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public String getID() {
        return id;
    }

    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public double getPayRate() {
        return payRate;
    }

    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public String getEmployeeType() {
        return "HOURLY";
    }

    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public double getYTDEarnings() {
        return ytdEarnings;
    }

    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public double getYTDTaxesPaid() {
        return ytdTaxesPaid;
    }

    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public double getPretaxDeductions() {
        return pretaxDeductions;
    }

    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:NeedBraces"})
    @Override
    public IPayStub runPayroll(double hoursWorked) {
        if (hoursWorked < 0) return null;

        BigDecimal hours = BigDecimal.valueOf(hoursWorked);
        BigDecimal regularHours = hours.min(BigDecimal.valueOf(40));
        BigDecimal overtimeHours = hours.subtract(regularHours).max(BigDecimal.ZERO);

        BigDecimal gross = BigDecimal.valueOf(payRate)
                .multiply(regularHours)
                .add(BigDecimal.valueOf(payRate * 1.5).multiply(overtimeHours))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal taxable = gross.subtract(BigDecimal.valueOf(pretaxDeductions))
                .max(BigDecimal.ZERO);
        BigDecimal taxes = taxable.multiply(BigDecimal.valueOf(0.2265))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal netPay = taxable.subtract(taxes);


        // Accumulate YTD values once per payroll cycle
        // Accumulate YTD earnings and taxes
        this.ytdEarnings = originalYtdEarnings + netPay.doubleValue();
        this.ytdTaxesPaid = originalYtdTaxesPaid + taxes.doubleValue();



        return new PayStub(
                this.name,              // Employee's name
                netPay.doubleValue(),   // Net pay for the current period
                taxes.doubleValue(),    // Taxes paid for the current period
                this.ytdEarnings,       // Updated Year-to-date earnings
                this.ytdTaxesPaid       // Updated Year-to-date taxes paid
        );
    }

    // Method to restore original YTD values
    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
    public void restoreOriginalYtdValues() {
        this.ytdEarnings = this.originalYtdEarnings;
        this.ytdTaxesPaid = this.originalYtdTaxesPaid;
    }


    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public double getPay() {
        return netPay;  // Return the calculated net pay
    }

    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public double getTaxesPaid() {
        return netPay * 0.2265;  // Returns taxes paid (22.65% of net pay)
    }

    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public String toCSV() {
        return String.format("HOURLY,%s,%s,%s,%s,%s,%s",
                name,
                id,
                formatNumber(netPay),
                formatNumber(pretaxDeductions),
                formatNumber(ytdEarnings),
                formatNumber(ytdTaxesPaid)
        );
    }

    /**
     * Formats the number as follows:
     * 1. Whole numbers (e.g., 1661.0) get 1 decimal.
     * 2. Non-whole numbers with 1 decimal (e.g., 2491.5) remain as they are.
     * 3. Numbers with 2 decimals (e.g., 4802.38) stay with 2 decimals.
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



