# **API Testing Assignment: Integration with Swagger Petstore API**

Welcome to the **API Testing Assignment** for practicing **API Testing** with **Rest Assured**. This project covers testing various endpoints from the **Swagger Petstore API**. The goal is to automate tests for the core functionalities and build an integration flow simulating a real-world scenario.

## **Project Overview**
This project involves automating API tests for **Swagger Petstore**, which includes validating user creation, product management (pets), and order processing. The project is divided into two parts: **Basic API Test Cases** and an **Integration Project** that connects multiple APIs in a cohesive workflow.

---

## **🧪 End-to-End Scenarios**

The following **end-to-end test cases** are covered:

1. **Create a new User (POST /user)**  
   Verify that the user is successfully created.

2. **Get User by Username (GET /user/{username})**  
   Verify that the user details are retrieved correctly.

3. **Update User Details (PUT /user/{username})**  
   Ensure that the updates are applied successfully.

4. **Delete User (DELETE /user/{username})**  
   Verify that the user is deleted from the system.

5. **Add a New Pet (POST /pet)**  
   Ensure the pet is successfully added and appears in the pet store.

6. **Get Pet by ID (GET /pet/{petId})**  
   Validate that the pet details are returned correctly by the ID.

7. **Update Pet (PUT /pet)**  
   Verify that the pet details are updated successfully.

8. **Delete Pet (DELETE /pet/{petId})**  
   Confirm that the pet is deleted as expected.

9. **Place an Order (POST /store/order)**  
   Verify that the order ID is generated.

10. **Get Order by ID (GET /store/order/{orderId})**  
    Ensure that the correct order details are retrieved by ID.

11. **Delete Order (DELETE /store/order/{orderId})**  
    Validate that the order is deleted correctly.

12. **Check Inventory (GET /store/inventory)**  
    Verify that the inventory response structure is valid.

---

## **🛠️ Tech Stack**
- **Java 17**
- **Rest Assured 5.0.0** (for API testing)
- **TestNG 7.10.2**
- **Allure Report** (for rich and interactive test reports)
- **Maven** (for Build & Dependency Management)

---

## **🧩 Project Design**
This project follows a **modular design approach** using the **Page Object Model (POM)** to separate the test logic from the actual API logic. The test cases are written in **TestNG**, and **Rest Assured** is used for sending requests and validating responses.

---

## **🧰 Key Features**
- **TestNG Integration** for test execution and configuration management.
- **Modular Design** using **Page Object Model (POM)** for maintaining cleaner code.
- **Allure Reporting** for rich, detailed, interactive test reports, including screenshots for failed tests.
- **Dynamic Data Handling** using variables like **petId**, **orderId** between test steps.
- **Custom Utilities** for managing configurations, requests, and responses.

---

## **⚙️ Build Configuration (pom.xml)**
The project uses **Maven** for dependency management and build configuration. Some important configurations include:
- **Rest Assured** for sending API requests.
- **TestNG** for defining test suites and running tests.
- **Allure** for generating detailed, interactive test reports.

---

## **🚀 Getting Started**
1. Clone the repository.
2. Open the project in your preferred IDE (e.g., IntelliJ IDEA or Eclipse).
3. Install dependencies using **Maven**:
    ```bash
    mvn clean install
    ```
4. Run the test classes using **Maven** or **IDE**:
    ```bash
    mvn clean test
    ```
5. After running tests, generate the Allure report:
    ```bash
    mvn allure:serve
    ```

---

## **🧠 Lessons Learned**
A separate file, `Learned_Lessons.md`, includes a summary of key concepts gained during this project.

### **Key Takeaways**:
- **TestNG** for managing test execution, retries, and parallel execution.
- **Page Object Model (POM)** for cleaner and maintainable test code.
- **Data-driven testing** using dynamic data (e.g., petId, orderId).
- **Interactive Reporting** with **Allure**.
- Hands-on experience with **Rest Assured** for API testing.

---

## **👨‍💻 Author**
**Fatma Essa**  
Junior Software QA Engineer  
📧 **fatma.essa566@gmail.com**  
🔗 [LinkedIn Profile](https://www.linkedin.com/in/fatma-essa-63a658197)  
💻 [GitHub Repository](https://github.com/fatmaessa5)

---

This **README** will guide you through setting up, running tests, and understanding the project structure. If you need any help or adjustments, feel free to reach out!
