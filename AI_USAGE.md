# AI Usage

AI assistance (Claude) was used during the development of this project to help with:

- Generating initial project structure and boilerplate classes
- Scaffolding repetitive patterns like Page Object skeletons, POJO models, and TestNG setup
- Suggesting how to structure `ThreadLocal` usage in `BaseTest` for parallel safety
- Drafting the README outline

## Manual Review and Refinement

The following areas required manual review, correction, or deliberate decision-making beyond what AI output:

- **Page Object cleanup** — Verified all locators against the live SauceDemo UI; removed incorrect selectors and adjusted a few that the AI guessed wrong
- **No raw selectors in tests** — Manually enforced: every selector lives in a page object; test methods call only high-level page methods
- **Assertion logic** — Reviewed all `assertTrue`/`assertEquals` calls to ensure they actually catch real failures; added meaningful failure messages
- **ReqRes timestamp validation** — The `updatedAt` vs `createdAt` comparison using `OffsetDateTime` was manually written and verified; AI-suggested approach used string comparison which is fragile
- **Checkout total calculation** — Switched to `BigDecimal` arithmetic with `RoundingMode.HALF_UP` after confirming the AI-suggested `double` approach could produce floating-point rounding errors
- **Config-driven credentials** — Ensured no username or password appears in any test class; all read from `config.properties` via `ConfigReader`
- **README cleanup** — Rewrote the AI-generated README for accuracy and removed placeholder text

## Sample Prompt and Output

**Prompt used:**

> "Write a Playwright Java page object for the SauceDemo inventory page. It should sort by price low to high, add the first two products to cart, and return their names. Use constants for selectors."

**Summarized AI output:**

The AI produced a skeleton with `page.selectOption()` for sorting and `page.locator().nth()` for adding items. The selector for the sort dropdown was initially wrong (`#sort-container` instead of `[data-test='product-sort-container']`). The product name retrieval logic was adjusted to trim whitespace and return a proper `List<String>` rather than an array. The `isFirstPriceLowest()` helper was added manually after noticing the AI had not included a way to verify sort order.
