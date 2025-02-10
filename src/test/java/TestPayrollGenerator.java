/*
 * Students, build off this class. We are providing one sample test case as file reading is new to
 * you.
 * 
 * NOTE: you may end up changing this completely depending on how you setup your project.
 * 
 * we are just using .main() as we know that is an entry point that we specified.
 * 
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import student.PayrollGenerator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertTrue;
import student.Builder;              // Ensure you import the Builder class
import student.IEmployee;             // Make sure IEmployee is imported
import student.HourlyEmployee;        // Import HourlyEmployee
import student.SalaryEmployee;        // Import SalaryEmployee


public class TestPayrollGenerator {

    @TempDir
    static Path tempDir;


    @Test
    public void testFinalPayStub() throws IOException {
        // copy employees.csv into tempDir
        Path employees = tempDir.resolve("employees.csv");
        Files.copy(Paths.get("resources/employees.csv"), employees);

        // get the path of the paystubs.csv
        Path payStubs = tempDir.resolve("paystubs.csv");



        String[] args = {"-e", employees.toString(), "-t", "resources/time_cards.csv", // allowed,
                                                                                       // this isn't
                                                                                       // modified -
                                                                                       // so safe
                "-o", payStubs.toString()};

        // run main method
        PayrollGenerator.main(args);



        String expectedPayStubs = Files
                .readString(Paths.get("resources/original/pay_stubs_solution_to_original.csv"));

        String actualPayStubs = Files.readString(payStubs);

        // Assert that the normalized output matches
        assertEquals(expectedPayStubs, actualPayStubs);


        // you could also read lines and compared the lists


    }
    /**
     * **Test 2: Payroll Processing with Negative Hours**
     * - Ensures employees with negative hours are skipped.
     */
    @Test
    public void testPayrollWithNegativeHours() throws IOException {
        // Copy the employees file into the temp directory
        Path employees = tempDir.resolve("employees_original_solution.csv");
        Files.copy(Paths.get("resources/original/employees_original_solution.csv"), employees);

        // Copy the time cards file into the temp directory
        Path timeCards = tempDir.resolve("time_cards_original.csv");
        Files.copy(Paths.get("resources/original/time_cards_original.csv"), timeCards);

        // Path for generated pay stubs
        Path payStubs = tempDir.resolve("paystubs.csv");

        // Define the arguments to pass to the PayrollGenerator
        String[] args = {
                "-e", employees.toString(),          // Employee file path
                "-t", timeCards.toString(),          // Time cards file path
                "-o", payStubs.toString()            // Output pay stub file path
        };

        // Run the payroll generator
        PayrollGenerator.main(args);

        // Read the actual generated pay stubs
        String actualPayStubs = Files.readString(payStubs);

        // Ensure the pay stubs file does NOT contain negative-hour employees
        assertTrue(!actualPayStubs.contains("EmployeeWithNegativeHours"),
                "Pay stubs should not contain employees with negative hours");
    }

}
