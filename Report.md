# Report for Payroll Generator

This report helps you demonstrate your understanding of the concepts. You should write this report after you have completed the project. 

## Technical Questions

1. What does CSV stand for? 
   CSV stands for Comma-Separated Values. It is a simple file format used to store tabular data, where each row represents a record, and fields are separated by commas.

2. Why would you declare `List<IEmployee>` instead of `ArrayList<HourlyEmployee>`?
   This allows us to store different types of employees  in the same list while maintaining code that is independent of the specific implementation of the list.

3. When you have one class referencing another object, such as storing that object as one of the attributes of the first class - what type of relationship is that called (between has-a and is-a)?

This is called a has-a relationship, also known as composition. 

4. Can you provide an example of a has-a relationship in your code (if one exists)?
in the PayStub class there are attributes like employeeName, netPay, ytdEarnings, and ytdTaxesPaid.

5. Can you provide an example of an is-a relationship in your code (if one exists)?
the HourlyEmployee and SalaryEmployee classes implementing IEmployee. Since they implement the IEmployee interface, they follow an is-a relationship, meaning “HourlyEmployee is an IEmployee” and “SalaryEmployee is an IEmployee”.

6. What is the difference between an interface and an abstract class?
• Interfaces define a contract that a class must follow without providing any default implementation (before Java 8). A class can implement multiple interfaces.
 • Abstract classes provide a base class that may include implemented methods as well as abstract methods. A class can extend only one abstract class.

7. What is the advantage of using an interface over an abstract class?
Interfaces promote loose coupling and multiple inheritance, allowing a class to implement multiple behaviors. This is useful when different classes share behavior but are not logically related in an inheritance hierarchy.

8. Is the following code valid or not? `List<int> numbers = new ArrayList<int>();`, explain why or why not. If not, explain how you can fix it.
   
No, it is not valid. Java generics do not support primitive types (e.g., int, double). Instead, it should be written using wrapper classes:

List<Integer> numbers = new ArrayList<>();

Java will use autoboxing to convert int values into Integer objects automatically.

9. Which class/method is described as the "driver" for your application? 
The main method in the main application class (usually in PayrollGenerator or similar) serves as the driver. It initializes the payroll system, processes employee data, and handles payroll calculations.


10. How do you create a temporary folder for JUnit Testing? 
In JUnit 5, you can use the @TempDir annotation to create a temporary directory for testing:

@TempDir
Path tempDir;

## Deeper Thinking 

Salary Inequality is a major issue in the United States. Even in STEM fields, women are often paid less for [entry level positions](https://www.gsb.stanford.edu/insights/whats-behind-pay-gap-stem-jobs). However, not paying equal salary can hurt representation in the field, and looking from a business perspective, can hurt the company's bottom line has diversity improves innovation and innovation drives profits. 

Having heard these facts, your employer would like data about their salaries to ensure that they are paying their employees fairly. While this is often done 'after pay' by employee surveys and feedback, they have the idea that maybe the payroll system can help them ensure that they are paying their employees fairly. They have given you free reign to explore this idea.

Think through the issue / making sure to cite any resources you use to help you better understand the topic. Then write a paragraph on what changes you would need to make to the system. For example, would there be any additional data points you would need to store in the employee file? Why? Consider what point in the payroll process you may want to look at the data, as different people could have different pretax benefits and highlight that. 

The answer to this is mostly open. We ask that you cite at least two sources to show your understanding of the issue. The TAs will also give feedback on your answer, though will be liberal in grading as long as you show a good faith effort to understand the issue and making an effort to think about how your design to could help meet your employer's goals of salary equity. 

***
Salary inequality remains a persistent issue in many industries, including STEM fields, where women and minorities often receive lower wages compared to their counterparts. Ensuring pay equity is not only ethically responsible but also beneficial for businesses, as diversity fosters innovation and increases profitability (Hunt et al., 2018). To tackle this issue, integrating salary analysis features within the payroll system could help organizations monitor and address disparities before they become systemic.

To implement this, the payroll system could store additional demographic data such as gender, race, and years of experience. This would allow for statistical analyses on salary distributions across different employee groups. The system could include a pay equity audit feature, which would flag discrepancies where employees with similar roles and experience receive significantly different salaries. By comparing salaries at the payroll processing stage, HR could be alerted to potential inequities before issuing payments.

Additionally, integrating trend analysis could help track salary changes over time, identifying whether pay gaps widen or close based on promotions and raises. One challenge with this implementation is ensuring data privacy and legal compliance with labor laws like the Equal Pay Act (EEOC, 2021). By designing the payroll system with anonymized reporting and ensuring transparency in pay structures, companies can take proactive steps to maintain fairness while safeguarding sensitive employee data.

Implementing such changes would require adding demographic fields to the employee records, modifying payroll reports to include equity analysis, and designing an algorithm to highlight discrepancies. While these modifications may require significant effort, they could help create a more equitable workplace and ensure that companies remain competitive by attracting and retaining diverse talent.
