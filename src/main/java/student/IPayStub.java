package student;

/**
 * An interface for the concept of the pay stub.
 *
 * <p>DO NOT MODIFY THIS FILE! (unless you are cleaning up comments/style changes)
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public interface IPayStub {

    /**
     * Gets the pay for the current pay period.
     *
     * @return the pay for the current pay period
     */
    @SuppressWarnings("checkstyle:Indentation")
    double getPay();

    /**
     * Gets the taxes paid for the current pay period.
     *
     * @return the taxes paid for the current pay period
     */
    @SuppressWarnings("checkstyle:Indentation")
    double getTaxesPaid();

    /**
     * Converts the PayStub object to a CSV string.
     *
     * <p>Format of the CSV string is: "employee_name,net_pay,taxes,ytd_earnings,ytd_taxes_paid"
     *
     * @return the CSV string
     */
    @SuppressWarnings({"checkstyle:AbbreviationAsWordInName",
            "checkstyle:Indentation", "checkstyle:JavadocParagraph"})
    String toCSV();

}
