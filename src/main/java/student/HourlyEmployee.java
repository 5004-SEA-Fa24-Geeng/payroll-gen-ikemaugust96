package student;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HourlyEmployee implements IEmployee, IPayStub {
    private final String name;
    private final String id;
    private final double payRate;
    private final double pretaxDeductions;
    private double ytdEarnings;
    private double ytdTaxesPaid;
    private double netPay;  // Store the net pay
    private final double originalYtdEarnings;
    private final double originalYtdTaxesPaid;

    public void setYTDEarnings(double earnings) {
        this.ytdEarnings = earnings;
    }

    public void setYTDTaxesPaid(double taxesPaid) {
        this.ytdTaxesPaid = taxesPaid;
    }
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public double getPayRate() {
        return payRate;
    }

    @Override
    public String getEmployeeType() {
        return "HOURLY";
    }

    @Override
    public double getYTDEarnings() {
        return ytdEarnings;
    }

    @Override
    public double getYTDTaxesPaid() {
        return ytdTaxesPaid;
    }

    @Override
    public double getPretaxDeductions() {
        return pretaxDeductions;
    }

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
    public void restoreOriginalYtdValues() {
        this.ytdEarnings = this.originalYtdEarnings;
        this.ytdTaxesPaid = this.originalYtdTaxesPaid;
    }


    @Override
    public double getPay() {
        return netPay;  // Return the calculated net pay
    }

    @Override
    public double getTaxesPaid() {
        return netPay * 0.2265;  // Returns taxes paid (22.65% of net pay)
    }

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
    private String formatNumber(double value) {
        BigDecimal number = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

        // If it has one decimal place, ensure it keeps exactly one
        if (number.scale() == 0) {
            return String.format("%.1f", value);
        }

        return number.toPlainString();
    }

}



