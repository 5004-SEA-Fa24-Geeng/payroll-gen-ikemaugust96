package student;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents an hourly employee in the payroll system.
 * This class calculates payroll, maintains YTD earnings and tax information, and provides
 * formatted data output.
 *
 * <p>It implements the {@code IEmployee} interface and handles payroll calculations
 * based on hourly wages and overtime.</p>
 *
 * @author [Your Name]
 * @version 1.0
 */
@SuppressWarnings("checkstyle:Indentation")
public class HourlyEmployee implements IEmployee, IPayStub {
    /**
     * The employee's name.
     */
    @SuppressWarnings("checkstyle:Indentation")
    private final String name;
    /**
     * The unique identifier for the employee.
     */
    @SuppressWarnings("checkstyle:Indentation")
    private final String id;
    /**
     * The employee's hourly pay rate.
     */
    @SuppressWarnings("checkstyle:Indentation")
    private final double payRate;
    /**
     * The employee's pre-tax deductions.
     */
    @SuppressWarnings("checkstyle:Indentation")
    private final double pretaxDeductions;
    /**
     * The employee's year-to-date earnings.
     */
    @SuppressWarnings("checkstyle:Indentation")
    private double ytdEarnings;
    /**
     * The employee's year-to-date taxes paid.
     */
    @SuppressWarnings("checkstyle:Indentation")
    private double ytdTaxesPaid;
    /**
     * The original year-to-date earnings before payroll calculations.
     */
    @SuppressWarnings("checkstyle:Indentation")
    private double netPay;  // Store the net pay
    /**
     * The original year-to-date taxes paid before payroll calculations.
     */
    @SuppressWarnings("checkstyle:Indentation")
    private final double originalYtdEarnings;
    /**
     * The original year-to-date taxes paid before payroll calculations.
     */
    @SuppressWarnings("checkstyle:Indentation")
    private final double originalYtdTaxesPaid;

    /**
     * Sets the year-to-date earnings for the employee.
     *
     * @param earnings the updated year-to-date earnings
     */
    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:AbbreviationAsWordInName"})
    public void setYTDEarnings(double earnings) {
        this.ytdEarnings = earnings;
    }

    /**
     * Sets the year-to-date taxes paid by the employee.
     *
     * @param taxesPaid the updated year-to-date taxes paid
     */
    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:AbbreviationAsWordInName"})
    public void setYTDTaxesPaid(double taxesPaid) {
        this.ytdTaxesPaid = taxesPaid;
    }

    /**
     * Constructs a new {@code HourlyEmployee} instance.
     *
     * @param name             the name of the employee
     * @param id               the unique identifier for the employee
     * @param payRate          the employee's hourly pay rate
     * @param ytdEarnings      the year-to-date earnings of the employee
     * @param ytdTaxesPaid     the year-to-date taxes paid by the employee
     * @param pretaxDeductions the pre-tax deductions for the employee
     */
    @SuppressWarnings("checkstyle:Indentation")
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

    /**
     * Runs payroll for the hourly employee, calculating net pay, taxes, and updating YTD values.
     *
     * @param hoursWorked the number of hours worked in the pay period
     * @return a new {@code PayStub} representing the payroll data for this pay period
     */
    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public IPayStub runPayroll(double hoursWorked) {
        if (hoursWorked < 0) {
            throw new IllegalStateException("Hours worked cannot be negative.");
        }

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

        //Accumulate YTD values once per payroll cycle.
        //Accumulate YTD earnings and taxex
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

    /**
     * Method to restore original YTD values.
     */
    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
    public void restoreOriginalYtdValues() {
        this.ytdEarnings = this.originalYtdEarnings;
        this.ytdTaxesPaid = this.originalYtdTaxesPaid;
    }


    /**
     * Returns the net pay for the current payroll period.
     *
     * @return the net pay amount
     */
    @Override
    public double getPay() {
        return netPay;  // Return the calculated net pay
    }

    /**
     * Returns the taxes paid for the current payroll period.
     *
     * @return the amount of taxes paid
     */
    @Override
    public double getTaxesPaid() {
        return netPay * 0.2265;  // Returns taxes paid (22.65% of net pay)
    }

    /**
     * Returns a CSV-formatted string representing this pay stub.
     *
     * @return a formatted CSV string with pay stub details
     */
    @Override
    public String toCSV() {
        return String.format("HOURLY,%s,%s,%s,%s,%s,%s",
                name,
                id,
                formatNumber(payRate),
                formatNumber(pretaxDeductions),
                formatNumber(ytdEarnings),
                formatNumber(ytdTaxesPaid)
        );
    }

    /**
     * Formats numerical values for CSV output.
     *
     * <p>Ensures whole numbers retain one decimal
     * place, while other numbers maintain their full precision.</p>
     *
     * @param value the {@code BigDecimal} value to format
     * @return a properly formatted string representation of the number
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



