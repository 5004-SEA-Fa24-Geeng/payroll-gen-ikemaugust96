package student;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a static class (essentially functions) that will help you build objects from CSV strings.
 * These objects are then used in the rest of the program. Often these builders are associated
 * with the objects themselves and the concept of a factory, but we placed
 * them here to keep the code clean (and to help guide you).
 */
@SuppressWarnings({"checkstyle:Indentation", "checkstyle:CommentsIndentation",
        "checkstyle:LineLength", "checkstyle:JavadocParagraph", "checkstyle:SummaryJavadoc"})
public final class Builder {


    @SuppressWarnings("checkstyle:Indentation")
    private Builder() {
    }

    /**
     * list from cvs
     */
    @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
    public static List<IEmployee>
    buildEmployeesFromCSV(Path employeesFilePath) {
        return new ArrayList<>();
    }

    /**
     * Builds an employee object from a CSV string.
     * You may end up checking the type of employee (hourly or salary) by looking at the first
     * element of the CSV string. Then building an object specific to that type.
     * * @param csv A comma-separated string containing employee data.
     * <p>
     * * @return An IEmployee object representing the employee.
     */
    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:LineLength",
            "checkstyle:AbbreviationAsWordInName",
            "checkstyle:RequireEmptyLineBeforeBlockTagGroup", "checkstyle:CommentsIndentation"})
    public static IEmployee buildEmployeeFromCSV(String csv) {

        // Split CSV by commas
        String[] parts = csv.split(",");

        // Extract the values from the parts
        String type = parts[0].trim();  // Employee type (Hourly or Salary)
        String name = parts[1].trim();  // Employee name
        String id = parts[2].trim();    // Employee ID
        double payRate = Double.parseDouble(parts[3].trim()); // Pay rate
        double pretaxDeductions = Double.parseDouble(parts[4].trim()); // Pretax deductions
        double ytdEarnings = Double.parseDouble(parts[5].trim()); // YTD earnings
        double ytdTaxesPaid = Double.parseDouble(parts[6].trim()); // YTD taxes paid

        // Check employee type and create corresponding object
        if (type.equalsIgnoreCase("HOURLY")) {
            return new HourlyEmployee(name, id, payRate, ytdEarnings, ytdTaxesPaid, pretaxDeductions);
        } else if (type.equalsIgnoreCase("SALARY")) {
            return new SalaryEmployee(name, id, payRate, ytdEarnings, ytdTaxesPaid, pretaxDeductions);
        } else {
            throw new IllegalArgumentException("Unknown employee type: " + type);
        }
    }


    /**
     * Converts a TimeCard from a CSV String.
     *
     * @param csv csv string
     * @return a TimeCard object
     */
    @SuppressWarnings({"checkstyle:Indentation",
            "checkstyle:AbbreviationAsWordInName", "checkstyle:CommentsIndentation"})
    public static ITimeCard buildTimeCardFromCSV(String csv) {
        System.out.println("🔍 Parsing Time Card CSV: " + csv);

        String[] parts = csv.split(",");

        if (parts.length != 2) {
            throw new IllegalArgumentException(" ERROR: Invalid time card CSV format: " + csv);
        }
        try {
            String employeeID = parts[0].trim();
            double hoursWorked = Double.parseDouble(parts[1].trim());

            //  Ignore negative hours instead of throwing an error
            if (hoursWorked < 0) {
                System.out.println("⚠WARNING: Skipping negative hours for Employee ID " + employeeID);
                return null; // Returning null will ensure it’s ignored in PayrollGenerator
            }

            return new ITimeCard.TimeCard(employeeID, hoursWorked);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(" ERROR: Invalid number format in TimeCard CSV: " + csv, e);
        }
    }
}
