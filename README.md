# RPA & AI Automation Engineer Coding Challenge

## Tech Stack

- Java 17
- Maven
- TestNG 7
- Playwright Java (UI automation)
- REST Assured (API automation)

## What Is Automated

**UI (SauceDemo - https://www.saucedemo.com)**
- Full end-to-end purchase flow: login → sort products → add to cart → checkout → order confirmation → logout
- Login validation across multiple user types (standard, locked, problem, performance glitch)

**API (ReqRes - https://reqres.in/api)**
- Create user (POST /users) — asserts 201 and returned id/createdAt
- Get user (GET /users/2) — asserts 200 and email domain
- Update user (PUT /users/2) — asserts 200 and that updatedAt is not older than request time
- Negative test: register without password — asserts 400 and error message

## Folder Structure

```
src/
├── main/java/com/automation/
│   ├── config/         ConfigReader.java
│   ├── listeners/      TestListener.java
│   ├── models/         UserRequest.java, UserResponse.java
│   ├── pages/          LoginPage, InventoryPage, CartPage, CheckoutPage, CheckoutOverviewPage, OrderConfirmationPage
│   ├── retry/          RetryAnalyzer.java
│   └── utils/          ApiConfig.java, PageHolder.java
└── test/
    ├── java/com/automation/
    │   ├── base/       BaseTest.java
    │   └── tests/
    │       ├── api/    UserLifecycleTest.java
    │       └── ui/     PurchaseFlowTest.java, LoginDataProviderTest.java
    └── resources/
        ├── config.properties
        └── testng.xml
```

## How to Run

### Install Playwright browsers (first time only)

```bash
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
```

### Run all tests

```bash
mvn clean test
```

## Configuration

All settings are in `src/test/resources/config.properties`. No credentials are hardcoded in test classes.

| Property | Description |
|---|---|
| `ui.base.url` | SauceDemo base URL |
| `api.base.url` | ReqRes API base URL |
| `browser` | Browser to use: `chromium`, `firefox`, or `webkit` |
| `headless` | Run browser headless: `true` or `false` |
| `sauce.standard.user` | Standard SauceDemo username |
| `sauce.password` | Shared SauceDemo password |

## Screenshots

On test failure, screenshots are saved to `target/screenshots/` with the test method name in the filename.

## Parallel Execution

Tests run in parallel at method level with 2 threads (configured in `testng.xml`). `BaseTest` uses `ThreadLocal` for Playwright instances to ensure thread safety.
