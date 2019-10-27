# Introduction

This project aims to offer an automated solution for a hypothetical situation. Basically, for this situation we have a file with some information related to Salesmen, Customers and Sales. The file is in the project root directory and is called "DataFile.txt".

As an output, the automation generates a new file with the test results called "TestResult.txt" and it is located in the same directory.

# Solution

It was determined that the automation should cover 5 requirements:
* Display the number of successfully imported lines
* Display the number of customers with invalid CPFs
* Display the number of oldest customer
* Display the name of the worst salesman
* Display the ID of the most expensive sale

For this purpose, I created a Behavior Driven Development structure with the following files:
* Sales.feature - file that contains the feature "Sales file verification" and the scenario: "File is accessed, read and verified". This feature refers to the 5 requirements mentioned previously.
* TestRunner.java - file that contains the JUnit Runner and that executes the step definitions.
* SalesFileTest.java - file that contains the Cucumber step definitions (@Given, @When, @Then) and that presents the automated solution.

The following step definitions are contained in this last file:
 * @Given("^I access the test data file$") - this step is for the automation to access the test data file in the root directory  
 * @When("^The file information are read$") - this step is for the automation to read the test data file and load it into memory
 * @Then("^It should verify the number of lines successfully imported$") - this step is for the automation to verify the number of lines successfully imported 
 * @Then("^It should verify the number of customers with invalid CPF$") - this step is for the automation to verify the number of customers with invalid CPF 
 * @Then("^It should verify the name of the oldest customer$") - this step is for the automation to verify the name of the oldest customer 
 * @Then("^It should verify the name of the worst salesman$") - this step is for the automation to verify the name of the worst salesman 
 * @Then("^It should verify the id of the most expensive sale$") - this step is for the automation to verify the id of the most expensive sale 
 * @Then("^It should generate a flat file with the test result$") - this step is for the automation to generate the flat file with the test result 

It is important to mention that for this project it was also used the Maven tool (apache-maven-3.6.2). The POM.xml file has the following information:

		<!-- https://mvnrepository.com/artifact/io.cucumber/cucumber-junit -->
		<dependency>
			<groupId>io.cucumber</groupId>
			<artifactId>cucumber-junit</artifactId>
			<version>5.0.0-RC1</version>
			<scope>test</scope>
		</dependency>

		<!-- https://mvnrepository.com/artifact/io.cucumber/cucumber-java -->
		<dependency>
		    <groupId>io.cucumber</groupId>
		    <artifactId>cucumber-java</artifactId>
		    <version>5.0.0-RC1</version>
		</dependency>
 
		<!-- https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-surefire-plugin -->
		<dependency>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-surefire-plugin</artifactId>
			<version>3.0.0-M3</version>
		</dependency>
		
# Test Execution
To execute the tests mentioned here, just go to the project root directory and run the command "mvn test". Below is a demonstration of the automated test execution console information:

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running br.com.test.automacao.TestRunner
- NUMBER OF IMPORTED LINES: 20
- NUMBER OF INVALID CPFs: 1
- NAME OF THE OLDEST CUSTOMER: Fulano da Silva
- NAME OF THE WORST SALESMAN: Jair
- ID OF MOST EXPENSIVE SALE: 13

Test Result File generaged: /home/marcio/eclipse-workspace/automacao/TestResult.txt

1 Scenarios (1 passed)

8 Steps (8 passed)

0m0.102s

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.465 sec

Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------

[INFO] BUILD SUCCESS

[INFO] ------------------------------------------------------------------------

[INFO] Total time:  3.833 s

[INFO] Finished at: 2019-10-27T12:59:21-03:00

[INFO] ------------------------------------------------------------------------
