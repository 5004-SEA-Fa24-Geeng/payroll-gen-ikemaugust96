# Payroll Generator Design Document


This document is meant to provide a tool for you to demonstrate the design process. You need to work on this before you code, and after have a finished product. That way you can compare the changes, and changes in design are normal as you work through a project. It is contrary to popular belief, but we are not perfect our first attempt. We need to iterate on our designs to make them better. This document is a tool to help you do that.


## (INITIAL DESIGN): Class Diagram

Place your class diagram below. Make sure you check the fil in the browser on github.com to make sure it is rendering correctly. If it is not, you will need to fix it. As a reminder, here is a link to tools that can help you create a class diagram: [Class Resources: Class Design Tools](https://github.com/CS5004-khoury-lionelle/Resources?tab=readme-ov-file#uml-design-tools)

![package](https://github.com/user-attachments/assets/927b09b8-2c12-4853-8940-b7151db61945)


![UML Diagram](diagram/package.png)
I forgot to record down the first diagram but the most important change is to create a PayStub class outside IPayStud.
## (INITIAL DESIGN): Tests to Write - Brainstorm

Write a test (in english) that you can picture for the class diagram you have created. This is the brainstorming stage in the TDD process. 

> [!TIP]
> As a reminder, this is the TDD process we are following:
> 1. Figure out a number of tests by brainstorming (this step)
> 2. Write **one** test
> 3. Write **just enough** code to make that test pass
> 4. Refactor/update  as you go along
> 5. Repeat steps 2-4 until you have all the tests passing/fully built program

You should feel free to number your brainstorm. 

1. Test that the `Employee` class properly returns `name` from `getName()`
2. Test that the `Employee` class properly returns `id` from `getId()`
3. Test that an `HourlyEmployee` correctly calculates pay based on hoursWorked.
4. Test that payroll processing updates `ytdEarnings` correctly.
5. Test that net pay is correctly computed as grossPay - taxes - deductions.


## (FINAL DESIGN): Class Diagram

Go through your completed code, and update your class diagram to reflect the final design. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. It is normal that the two diagrams don't match! Rarely (though possible) is your initial design perfect. 

> [!WARNING]
> If you resubmit your assignment for manual grading, this is a section that often needs updating. You should double check with every resubmit to make sure it is up to date.

![final](https://github.com/user-attachments/assets/e6fbbfe7-90cd-4bda-94cf-2421d0769189)


![UML Diagram](diagram/package.png)
## (FINAL DESIGN): Reflection/Retrospective

> [!IMPORTANT]
> The value of reflective writing has been highly researched and documented within computer science, from learning new information to showing higher salaries in the workplace. For this next part, we encourage you to take time, and truly focus on your retrospective.

Take time to reflect on how your design has changed. Write in *prose* (i.e. do not bullet point your answers - it matters in how our brain processes the information). Make sure to include what were some major changes, and why you made them. What did you learn from this process? What would you do differently next time? What was the most challenging part of this process? For most students, it will be a paragraph or two. 

Throughout the development of the payroll generator, the design evolved significantly as I encountered various challenges and refined my approach. Initially, the pay stub was closely tied to the employee classes, which made it difficult to manage independently. To improve modularity and maintainability, I decided to create an independent PayStub class, allowing better separation of concerns and reducing unnecessary dependencies between components. This change improved the clarity of the design and made it easier to test and modify the payroll logic without affecting the core employee structures.

Another major challenge was handling precision issues in salary calculations. Since financial computations require high accuracy, I experimented with different methods, including using BigDecimal for calculations instead of primitive data types like double. This transition helped eliminate floating-point inaccuracies, ensuring that values such as net pay, taxes, and year-to-date earnings were consistently rounded correctly. However, implementing this change across different parts of the system required careful attention to detail, especially in formatting output to meet expected test results.


The most challenging part of this process was debugging repeated errors related to YTD earnings and tax accumulation. Ensuring that values were reset correctly and preventing duplicate additions required thorough testing and logical adjustments. Despite these difficulties, refining the payroll system improved my understanding of financial computations, object-oriented design, and precision handling in Java.
