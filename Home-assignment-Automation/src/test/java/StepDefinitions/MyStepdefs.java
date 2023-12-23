package StepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class MyStepdefs {
    WebDriver driver;
    WebDriverWait wait;
    private String day;
    private String month;
    private String year;
    private String firstname;
    private String lastName;
    private String email;
    private String confirmEmail;
    private String password;
    private String confirmPassword;
    private String checkEthics;
    private String checkTerms;

    @Given("I am on the registration page on {string}")
    public void iAmOnTheRegistrationPage(String browser) {

        if ("chrome".equalsIgnoreCase(browser)) {
            driver = new ChromeDriver();
        } else if ("edge".equalsIgnoreCase(browser)) {
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Initialize wait after driver
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }

    @When("I select {string}, {string}, {string} as date of birth")
    public void iSelectAsDateOfBirth(String day, String month, String year) {
        this.day = day;
        this.month = month;
        this.year = year;
        String date = day + "/" + month + "/" + year;
        driver.findElement(By.id("dp")).sendKeys(date);
        if (day.isEmpty() || month.isEmpty() || year.isEmpty()) {
            System.out.println("As of now, date of birth is required");
            throw new IllegalArgumentException("Date of birth is required");
        }
    }

    @And("I enter {string} as first name")
    public void iEnterAsFirstName(String firstname) {
        this.firstname = firstname;
        driver.findElement(By.id("member_firstname")).sendKeys(firstname);
    }

    @And("I enter {string} as last name")
    public void iEnterAsLastName(String lastName) {
        this.lastName = lastName;
        driver.findElement(By.id("member_lastname")).sendKeys(lastName);
    }

    @And("I enter {string} as email")
    public void iEnterAsEmail(String email) {
        this.email = email;
        driver.findElement(By.id("member_emailaddress")).sendKeys(email);
    }

    @And("I enter {string} as confirm email")
    public void iEnterAsConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys(confirmEmail);
    }

    @And("I enter {string} as password")
    public void iEnterAsPassword(String password) {
        this.password = password;
        driver.findElement(By.id("signupunlicenced_password")).sendKeys(password);
    }

    @And("I enter {string} as confirm password")
    public void iEnterAsConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys(confirmPassword);
    }

    @And("I {string} the terms and conditions")
    public void iTheTermsAndConditions(String checkTerms) {
        this.checkTerms = checkTerms;
        if ("Yes".equalsIgnoreCase(checkTerms)) {
            driver.findElement(By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(2) > div:nth-child(1) > label > span.box")).click();
            driver.findElement(By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(2) > div.md-checkbox.margin-top-10 > label > span.box")).click();
        }
    }

    @And("I {string} the code of ethics and conduct")
    public void iTheCodeOfEthicsAndConduct(String checkEthics) {
        this.checkEthics = checkEthics;
        if ("Yes".equalsIgnoreCase(checkEthics)) {
            driver.findElement(By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(7) > label > span.box")).click();
        }
    }

    @And("I submit the registration form")
    public void iSubmitTheRegistrationForm() {
        driver.findElement(By.id("signup_form")).submit();
    }

    @Then("I should see message")
    public void iShouldSee() {
        boolean conditionMet = false;
        try {
            if (firstname.isEmpty()) {
                WebElement errorFirstName = driver.findElement(By.cssSelector("span[for='member_firstname'][generated='true']"));
                String actualErrorMessage = errorFirstName.getText();
                assertEquals("First Name is required", actualErrorMessage);
                System.out.println("Success, First Name is required error message displayed");
                conditionMet = true;
            }
            if (lastName.isEmpty()) {
                WebElement errorLastName = driver.findElement(By.cssSelector("span[for='member_lastname'][generated='true']"));
                String actualErrorMessage = errorLastName.getText();
                assertEquals("Last Name is required", actualErrorMessage);
                System.out.println("Success, Last Name is required error message displayed");
                conditionMet = true;
            }
            if (email.isEmpty() && confirmEmail.isEmpty()) {
                WebElement errorEmail = driver.findElement(By.cssSelector("span[for='member_emailaddress'][generated='true']"));
                String actualErrorMessage = errorEmail.getText();
                assertEquals("Email Address is required", actualErrorMessage);
                System.out.println("Success, Email Address is required displayed");

                WebElement errorConfirmEmail = driver.findElement(By.cssSelector("span[for='member_confirmemailaddress'][generated='true']"));
                String actualConfirmErrorMessage = errorConfirmEmail.getText();
                assertEquals("Confirm Email Address is required", actualConfirmErrorMessage);
                System.out.println("Success, Confirm Email Address is required displayed");
                conditionMet = true;
            }
            if (!email.isEmpty() && confirmEmail.isEmpty()) {
                WebElement errorConfirmEmail = driver.findElement(By.cssSelector("span[for='member_confirmemailaddress'][generated='true']"));
                String actualConfirmErrorMessage = errorConfirmEmail.getText();
                assertEquals("Confirm Email Address does not match", actualConfirmErrorMessage);
                System.out.println("Success, Confirm Email Address does not match displayed");
                conditionMet = true;
            }
            if (email.isEmpty() && !confirmEmail.isEmpty()) {
                WebElement errorEmail = driver.findElement(By.cssSelector("span[for='member_emailaddress'][generated='true']"));
                String actualErrorMessage = errorEmail.getText();
                assertEquals("Email Address is required", actualErrorMessage);
                System.out.println("Success, Email is required error message displayed");
                conditionMet = true;
            }
            if (!email.equalsIgnoreCase(confirmEmail) && !email.isEmpty() && !confirmEmail.isEmpty()) {
                WebElement errorEmail = driver.findElement(By.cssSelector("span[for='member_confirmemailaddress'][generated='true']"));
                String actualErrorMessage = errorEmail.getText();
                assertEquals("Confirm Email Address does not match", actualErrorMessage);
                System.out.println("Success, email did not match error message displayed");
                conditionMet = true;
            }
            if (password.isEmpty() && confirmPassword.isEmpty()) {
                WebElement errorPassword = driver.findElement(By.cssSelector("span[for='signupunlicenced_password'][generated='true']"));
                String actualErrorMessage = errorPassword.getText();
                assertEquals("Password is required", actualErrorMessage);
                System.out.println("Success, Password error message displayed");

                WebElement errorConfirmPassword = driver.findElement(By.cssSelector("span[for='signupunlicenced_confirmpassword'][generated='true']"));
                String actualConfirmErrorMessage = errorConfirmPassword.getText();
                assertEquals("Confirm Password is required", actualConfirmErrorMessage);
                System.out.println("Success, Confirm Password is required error message displayed");
                conditionMet = true;
            }
            if (!password.isEmpty() && confirmPassword.isEmpty()) {
                WebElement errorPassword = driver.findElement(By.cssSelector("span[for='signupunlicenced_confirmpassword'][generated='true']"));
                String actualErrorMessage = errorPassword.getText();
                assertEquals("Confirm Password is required", actualErrorMessage);
                System.out.println("Success, Confirm Password is required error message displayed");
                conditionMet = true;
            }
            if (password.isEmpty() && !confirmPassword.isEmpty()) {
                WebElement errorPassword = driver.findElement(By.cssSelector("span[for='signupunlicenced_password'][generated='true']"));
                String actualErrorMessage = errorPassword.getText();
                assertEquals("Password is required", actualErrorMessage);
                System.out.println("Success, password error message displayed");
                conditionMet = true;
            }
            if (!password.equalsIgnoreCase(confirmPassword) && !password.isEmpty() && !confirmPassword.isEmpty()) {
                WebElement errorPassword = driver.findElement(By.cssSelector("span[for='signupunlicenced_confirmpassword'][generated='true']"));
                String actualErrorMessage = errorPassword.getText();
                assertEquals("Password did not match", actualErrorMessage);
                System.out.println("Success, password did not match error message displayed");
                conditionMet = true;
            }
            if (checkTerms.equalsIgnoreCase("No") || checkTerms.isEmpty()) {
                WebElement errorTerms1 = driver.findElement(By.cssSelector("span[for='TermsAccept'][generated='true']"));
                String actualErrorMessage = errorTerms1.getText();
                assertEquals("You must confirm that you have read and accepted our Terms and Conditions", actualErrorMessage);
                System.out.println("Success, You must confirm that you have read and accepted our Terms and Conditions error message displayed");

                WebElement errorTerms2 = driver.findElement(By.cssSelector("span[for='AgeAccept'][generated='true']"));
                String actualErrorMessage2 = errorTerms2.getText();
                assertEquals("You must confirm that you are over 18 or a person with parental responsibility", actualErrorMessage2);
                System.out.println("Success, You must confirm that you are over 18 or a person with parental responsibility error message displayed");
                conditionMet = true;
            }
            if (checkEthics.equalsIgnoreCase("No") || checkEthics.isEmpty()) {
                WebElement errorEthics = driver.findElement(By.cssSelector("span[for='AgreeToCodeOfEthicsAndConduct'][generated='true']"));
                String actualErrorMessage = errorEthics.getText();
                assertEquals("You must confirm that you have read, understood and agree to the Code of Ethics and Conduct", actualErrorMessage);
                System.out.println("Success, You must confirm that you have read, understood and agree to the Code of Ethics and Conduct error message displayed");
                conditionMet = true;
            }
            if (!conditionMet) {
                WebElement thankYouElement = driver.findElement(By.xpath("//h2[contains(text(), 'THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND')]"));
                String actualThankYouMessage = thankYouElement.getText();
                assertEquals("THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND", actualThankYouMessage);
                System.out.println("Thank you element found, Account created!");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + e.getMessage());
            Assert.fail("Element not found: " + e.getMessage());
        } catch (AssertionError e) {
            System.out.println("Assertion failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        System.out.println("Scenario completed!");
    }
}



