package student;


/**
 * An interface for the concept of the employee.
 * <p>
 * DO NOT MODIFY THIS FILE! (unless you are cleaning up comments/style changes)
 * <p>
 * This file is provided, and we will be grading every function implemented in as a unit test for
 * both SalaryEmployee and HourlyEmployee.
 */
@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:JavadocParagraph"})
public interface IEmployee {

    /**
     * Gets the employee's name.
     *
     * <p>@return the name of the employee
     */
    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:RequireEmptyLineBeforeBlockTagGroup"})
    String getName();

    /**
     * <p>Gets the employee's ID.
     *
     * <p>@return the ID of the employee
     */

    String getID();

    /**
     * Gets the employee's pay rate.
     *
     * <p>@return the pay rate of the employee
     */
    double getPayRate();


    /**
     * Gets the employee's Type as a string.
     * <p>
     * Either "HOURLY" or "SALARY" depending on the type of employee.
     * <p>
     * You may want to consider using an enum to store
     * the type, and using .name() to get the string representation.
     *
     * @return the type of the employee as a string
     */
    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:RequireEmptyLineBeforeBlockTagGroup", "checkstyle:JavadocParagraph"})
    String getEmployeeType();

    /**
     * Gets the YTD earnings of the employee.
     *
     * @return the YTD earnings of the employee
     */
    @SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:Indentation", "checkstyle:RequireEmptyLineBeforeBlockTagGroup"})
    double getYTDEarnings();

    /**
     * Gets the YTD taxes paid by the employee.
     *
     * @return the YTD taxes paid by the employee
     */
    @SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:Indentation", "checkstyle:RequireEmptyLineBeforeBlockTagGroup"})
    double getYTDTaxesPaid();

    /**
     * Gets pretax deductions for the employee. Yes, on a normal paycheck this varies as either set
     * amounts or percents, and often more than one type of deduction.
     * <p>
     * For now, you can just assume a single pretax deduction as a whole dollar amount.
     *
     * @return the pretax deductions for the employee
     */
    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:RequireEmptyLineBeforeBlockTagGroup", "checkstyle:JavadocParagraph"})
    double getPretaxDeductions();


    /**
     * Runs the employee's payroll.
     * <p>
     * This will calculate the pay for the current pay, update the YTD earnings, and update the
     * taxes paid YTD.
     * taxes are calculated as 1.45% for medicare, 6.2% for social security, and 15% for
     * withholding. or 22.65% total. They are calculated on the net pay (after pretax deductions).
     * For hourly employees, the pay is calculated as payRate * hoursWorked for the first 40 hours,
     * then payRate * 1.5 * (hoursWorked - 40) for overtime.
     * For salary employees, it is pay rate divided by 24 for two payments every month.
     * If either type of employee has < 0 hours, they are skipped this payroll period.
     * (suggestion return null, and skip adding nulls to your paystub list)
     * Final net pay is calculated as pay - pretaxDeductions - taxes.
     * All numbers (across all methods) are rounded to the nearest cent. (2 decimal places)
     * SUGGESTION: You may want to use BigDecimal for the calculations to avoid floating point errors.
     * SUGGESTION: You may want to create an protected abstract calculateGrossPay(double hoursWorked)
     * method to calculate the gross pay for the pay period, as runPayroll is exactly
     * the same for both SalaryEmployee and HourlyEmployee, but calculateGrossPay is different.
     *
     * @param hoursWorked the hours worked for the pay period
     * @return the pay stub for the current pay period
     */
    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:RequireEmptyLineBeforeBlockTagGroup", "checkstyle:LineLength", "checkstyle:JavadocParagraph"})
    IPayStub runPayroll(double hoursWorked);


    /**
     * Converts the employee to a CSV string.
     * <p>
     * Format of the String s as follows:
     * <p>
     * employee_type,name,ID,payRate,pretaxDeductions,YTDEarnings,YTDTaxesPaid
     * <p>
     * employee_type has the options for HOURLY or SALARY.
     * <p>
     * You do not have to worry about commas in the name or any other field.
     *
     * @return the employee as a CSV string
     */
    @SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:Indentation", "checkstyle:RequireEmptyLineBeforeBlockTagGroup", "checkstyle:JavadocParagraph"})
    String toCSV();


}
