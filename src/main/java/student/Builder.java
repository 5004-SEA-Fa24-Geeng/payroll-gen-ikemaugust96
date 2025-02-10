package student;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;



/** 
 * This is a static class (essentially functions) that will help you build objects from CSV strings.
 * These objects are then used in the rest of the program. Often these builders are associated
 * with the objects themselves and the concept of a factory, but we placed
 * them here to keep the code clean (and to help guide you).
 */
    public final class Builder {


    private Builder() {
    }

    public static List<IEmployee> buildEmployeesFromCSV(Path employeesFilePath) {
        List<IEmployee> employees = new ArrayList<>();
        // Implement logic to read employees from CSV and add them to the list.
        // For example:
        // Read the file, parse the CSV, and create IEmployee objects.
        return employees;
    }

     /**
     * Builds an employee object from a CSV string.
     * 
     * You may end up checking the type of employee (hourly or salary) by looking at the first
     * element of the CSV string. Then building an object specific to that type.
     * 
     * @param csv the CSV string
     * @return the employee object
     */
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
    public static ITimeCard buildTimeCardFromCSV(String csv) {

        // Split CSV into an array of strings
        String[] parts = csv.split(",");

        // Validate correct number of fields in CSV
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid time card CSV format: " + csv);
        }
        try {
            String employeeID = parts[0].trim();
            double hoursWorked = Double.parseDouble(parts[1]);
            // Create a new TimeCard object
            return new ITimeCard.TimeCard(employeeID, hoursWorked);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in TimeCard CSV: " + csv, e);
        }
    }
}
