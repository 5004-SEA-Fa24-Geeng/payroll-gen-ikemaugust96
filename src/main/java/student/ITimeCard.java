package student;

/**
 * An interface for the concept of the time card.
 * 
 * While you are free to modify this, we use this in the Builder to help convert the CSV file to a
 * list of time cards.
 */
public interface ITimeCard {

    /**
     * Gets the employee ID.
     * 
     * @return the employee ID
     */
    String getEmployeeID();

    /**
     * Gets the hours worked by the employee.
     * 
     * @return the hours worked by the employee
     */
    double getHoursWorked();
    /**
     * A static nested class implementing the ITimeCard interface.
     *
     * This class provides a concrete implementation of a time card, which includes:
     * - Employee ID
     * - Hours worked
     *
     * By nesting this class inside the interface, we ensure that an implementation
     * is always available without needing a separate file.
     */
    public static class TimeCard implements ITimeCard { // Implements ITimeCard
        private final String employeeID;
        private final double hoursWorked;

        /**
         * Constructs a TimeCard object.
         *
         * @param employeeID  The unique ID of the employee.
         * @param hoursWorked The number of hours the employee worked in this period.
         * @throws IllegalArgumentException if hoursWorked is negative.
         */
        public TimeCard(String employeeID, double hoursWorked) {
            if (hoursWorked < 0) {
                throw new IllegalArgumentException("Hours worked cannot be negative.");
            }
            this.employeeID = employeeID;
            this.hoursWorked = hoursWorked;
        }

        /**
         * Gets the employee ID associated with this time card.
         *
         * @return the employee ID.
         */
        @Override
        public String getEmployeeID() {
            return employeeID;
        }

        /**
         * Gets the number of hours worked by the employee in this time period.
         *
         * @return the total hours worked.
         */
        @Override
        public double getHoursWorked() {
            return hoursWorked;
        }

        /**
         * Converts the TimeCard object into a formatted CSV string.
         *
         * The format follows: "employeeID,hoursWorked"
         *
         * @return a string representing the time card details in CSV format.
         */
        @Override
        public String toString() {
            return String.format("%s,%.2f", employeeID, hoursWorked);
        }
    }

}
