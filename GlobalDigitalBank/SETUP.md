# 🏦 Global Digital Bank (GDB) — Complete Setup, Run & Test Guide

This guide contains everything you need to set up the prerequisites, configure the environment, compile, run, and test the `GlobalDigitalBank` console application.

> [!NOTE]
> All folder paths and usernames (e.g., `DELL`, `GlobalDigitalBank`, `GDB`) used in this guide are illustrative examples. These directory structures, paths, and usernames **will vary for different users** based on their system username and local workspace directories.

---

## 💻 1. Required Software & Prerequisites

To run this application, you need the following software installed:

1. **Java Development Kit (JDK) 21 or higher**
2. **Apache Maven 3.9+** (Build tool)
3. **IDE / Text Editor** (Recommended: **VS Code** with *Extension Pack for Java* or **IntelliJ IDEA**)

---

## 📁 2. Codebase Setup

1. **Extract or Clone the Repository:**
   Ensure the codebase is located in a persistent directory, for example:
   `C:\Users\DELL\Downloads\GlobalDigitalBank\GDB\GlobalDigitalBank` (Note: exact path will vary per user system)

2. **Open in IDE (VS Code):**
   * Launch VS Code.
   * Go to `File` ➔ `Open Folder` and select the directory above.
   * Ensure VS Code prompts you to import the project as a Maven project.

---

## 🔧 3. Installing & Configuring Missing Software

If any of the required software is missing or not configured in your system `PATH`, follow these instructions:

### A. If Java JDK is Missing or Incorrect
1. **Check Version:** Open terminal/command prompt and run:
   ```cmd
   java -version
   ```
2. **Installation:** If missing, download and run the installer for **JDK 21** or higher from [Adoptium (Temurin)](https://adoptium.net/) or [Oracle JDK](https://www.oracle.com/java/technologies/downloads/).
3. **System Environment Variables Setup:**
   * Open Windows Search ➔ Type **"Edit the system environment variables"** and press Enter.
   * Click **Environment Variables...** at the bottom.
   * Under **System variables**, click **New...**:
     * **Variable name:** `JAVA_HOME`
     * **Variable value:** Path to your JDK installation (e.g., `C:\Program Files\Eclipse Adoptium\jdk-21.x.x`)
   * Locate the variable named **Path** under System variables, select it, and click **Edit...**.
   * Click **New** and add: `%JAVA_HOME%\bin`
   * Click **OK** to save and apply all changes.

---

### B. If Maven is Missing or Not Recognized
If running `mvn -version` displays *"'mvn' is not recognized as an internal or external command"*, use one of these solutions:

#### Solution 1: Add Existing Maven wrapper/dists to PATH (Temporary/Quick)
During project run, if you already have Maven downloaded via VS Code or another tool, you can search for and use it. 
* To search for existing Maven paths, run this in PowerShell:
  ```powershell
  Get-ChildItem -Path "C:\Users\DELL" -Filter "mvn.cmd" -Recurse -ErrorAction SilentlyContinue | Select-Object -First 3 FullName
  ```
* Once you find the path (e.g., `C:\Users\DELL\.m2\wrapper\dists\apache-maven-3.9.6-bin\3311e1d4\apache-maven-3.9.6\bin`), append it to your path temporarily in your active terminal:
  ```powershell
  $env:PATH = $env:PATH + ";C:\Users\DELL\.m2\wrapper\dists\apache-maven-3.9.6-bin\3311e1d4\apache-maven-3.9.6\bin"
  ```

#### Solution 2: Clean Install & Permanent PATH Setup
1. **Download:** Go to [Apache Maven Downloads](https://maven.apache.org/download.cgi) and download the **Binary zip archive** (e.g., `apache-maven-3.9.16-bin.zip`).
2. **Extract:** Extract the zip file contents to a folder of your choice (e.g., `C:\Program Files\Maven`).
3. **Environment Setup:**
   * Go to **Environment Variables...** (like in the Java step).
   * Under **System variables**, click **New...**:
     * **Variable name:** `MAVEN_HOME`
     * **Variable value:** `C:\Program Files\Maven\apache-maven-3.9.16` (your extracted folder path)
   * Edit the system **Path** variable, click **New**, and add: `%MAVEN_HOME%\bin`
   * Save all dialogs. Restart any open terminals or VS Code to apply.

---



## 🚀 4. Compiling, Running & Testing the App

### A. How to Compile the App
In your terminal, navigate to the root directory of the project and execute:
```powershell
mvn compile
```
*(If `mvn` is not globally recognized, use your found full path instead):*
```powershell
& "C:\Users\DELL\.m2\wrapper\dists\apache-maven-3.9.6-bin\3311e1d4\apache-maven-3.9.6\bin\mvn.cmd" compile
```

---

### B. How to Run the App
To start the console interface, run:
* **In PowerShell / VS Code Terminal:**
  ```powershell
  mvn exec:java "-Dexec.mainClass=Main"
  ```
* **In standard Command Prompt (cmd.exe):**
  ```cmd
  mvn exec:java -Dexec.mainClass="Main"
  ```

---

### C. How to Test the App (Automated & Manual)

#### 1. Running Automated Unit/Integration Tests
Run all 176 JUnit tests to verify codebase integrity:
```powershell
mvn test
```

#### 2. Manual Testing Guide (Testing All Features)
Run the application and execute these test scenarios sequentially to test all features:

##### Test 1: Open Account (Savings & Current)
1. Choose option `2) Open Account`.
2. Select `Savings`.
3. Provide the following details:
   * **Name:** `Sala Anil Kumar`
   * **Tier:** `1` (SILVER - requires minimum balance of `10,000`)
   * **Initial Amount:** `30000`
   * **Phone:** `1234567890`
   * **Gender:** `1` (Male)
   * **DOB:** `24/02/2003`
   * **Aadhar:** `123456789012`
   * **PIN:** `8374`
4. **Expected Result:** App prints "Account created successfully" with a new 10-digit account number (e.g., `1000000001`). Note it down.
5. Create a second account (type `Current`) to test transfers:
   * **Name:** `Corp Inc`
   * **Tier:** `2` (GOLD)
   * **Initial Amount:** `25000`
   * **Company Name:** `Gavin Tech`
   * **Website:** `gavin.com`
   * **Registration No:** `REG123`
   * **PIN:** `1111`
6. **Expected Result:** Generates Account Number `1000000002`.

##### Test 2: Deposit Cash
1. Choose option `6) Deposit`.
2. Input:
   * **Account Number:** `1000000001`
   * **PIN:** `8374`
   * **Amount to deposit:** `50000`
3. **Expected Result:** Previous Balance is displayed as `30,000`, Amount Deposited: `50,000`, New Balance: `80,000`. Message: Success.

##### Test 3: Withdraw Cash
1. Choose option `5) Withdraw`.
2. Input:
   * **Account Number:** `1000000001`
   * **PIN:** `8374`
   * **Amount to withdraw:** `75000`
3. **Expected Result:** Previous Balance: `80,000`, Amount Withdrawn: `75,000`, New Balance: `5,000`.
4. **Low Balance Alert:** Because the balance (`5,000`) dropped below the Silver Tier minimum balance requirement of `10,000`, the terminal will display:
   *"Warning: Your balance has dropped below the minimum balance for your privilege tier."*

##### Test 4: Transfer Funds
1. Choose option `7) Transfer`.
2. Input:
   * **From Account:** `1000000002`
   * **Destination Account:** `1000000001`
   * **Amount:** `10000`
   * **Transfer Mode:** `2` (IMPS)
   * **PIN:** `1111`
3. **Expected Result:** Deducts `10,000` from Account 2. New Balance for Account 2: `15,000`. Account 1's balance increases to `15,000`.

##### Test 5: Verify Transactions & Logs
Since the in-memory data resets on application restart, you can verify transaction history and business operations in real-time by checking the **`logs/app.log`** file in the project directory. All operations (e.g., account creations, deposits, withdrawals, transfers, and low-balance warnings) are logged here automatically.

