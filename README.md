# Cyberint Ops XPath Challenge

This repository contains a complete solution to the Cyberint-Ops XPath Challenge, where the main task is to write reliable and generic XPath expressions to locate specific elements on the Firefox support forum. The provided Java program with Selenium is an additional tool to verify and highlight the correctness of those expressions automatically.

---

##  Challenge Summary
The goal of this challenge is to:

- Write generic XPath expressions to select specific elements on the Firefox support forum.

- Avoid absolute or brittle XPath selectors like div[1]/div[2]/div.

- Use Chrome Inspector and the search field (Ctrl+F) to test the XPath expressions.

- Verify XPath correctness programmatically using Selenium WebDriver.

- Support both regular and headless browser modes.
---

##  Repository Contents

| File           | Description                                                   |
|----------------|---------------------------------------------------------------|
| `TrackXpath.java` | Main Java program that opens the browser, applies each XPath, highlights matches, and logs results. |
| `xpaths.txt`      | List of XPath expressions solving each challenge in the assignment. |
| `xpath_results.txt` | Auto-generated log file storing the outcome of each XPath (created at runtime). |

---

## Challenge Tasks Solved

This project successfully finds and highlights the following using XPath:

1. **Top bar “Ask a Question” button**


`.//a[contains(@class,'sumo-nav--link') and contains(text(), 'Ask a Question')]`

2. **Top-right search input box**

`.//form[@id='support-search-sidebar']/input[@id='search-q']`

3. **First post on the page**

`.//article[@class='forum--question-item'][1]`

4. **First 5 posts**

`.//article[@class='forum--question-item' and position()<=5]`

5. **Publishing time of the first post**

`.//article[@class='forum--question-item'][1]//p[@class='user-meta-asked-by']/text()[contains(.,'ago')]`

6. **All responded posts (posts with replies)**

`.//article[@class='forum--question-item'] [ .//li[ span[@class='forum--meta-key' and contains(text(), 'repl')] and number(span[@class='forum--meta-val']) > 0 ]]`

7. **Posts that say “1 hour ago”**

`.//article[@class='forum--question-item'][.//p[@class='user-meta-asked-by']/text()[contains(.,'1 hour ago')]]`

8. **Posts with `?` in the title**

`.//article[@class='forum--question-item'][.//h2[@class='forum--question-item-heading']/a[contains((text()), '?')]]`

9. **Posts where the author's name starts with D or d**

`.//article[@class='forum--question-item'][.//p[@class='user-meta-asked-by']/strong/a[starts-with(text(), 'D') or starts-with(text(), 'd')]]`

10. **Posts with more than 2 replies**

`.//article[@class='forum--question-item'][.//li[span[@class='forum--meta-key' and contains(text(), 'replies')] and number(span[@class='forum--meta-val']) > 2]]`

11. **Posts whose title starts with “P” or “p”**

`.//article[@class='forum--question-item'][.//h2[@class='forum--question-item-heading']/a[starts-with(.,'P') or starts-with(., 'p')]]`

12. **Posts whose title length is 14 characters or more**

`.//article[@class='forum--question-item'][.//h2[@class='forum--question-item-heading']/a[string-length(text()) >=14]]`

13. **Posts tagged with “Windows 11”**

`.//article[@class='forum--question-item'][.//li/a[@class='tag-name'][contains(text(), 'Windows 11')]]`

14. **Posts with 0 replies**

`.//article[@class='forum--question-item'][.//li[number(span[@class='forum--meta-val'])=0 and span[@class='forum--meta-key' and contains(., 'replies')]]]`


All XPath expressions are stored in `xpaths.txt`.

---

##  How to Run the Project

This project is a Maven-based Java application and can be run using **IntelliJ IDEA** (or any Java IDE) **or** directly from the command line.

---

###  Step-by-Step from the Beginning

#### 1. Clone the Repository

```bash
git clone https://github.com/ahedkhatib/cyberint-ops-xpath-challenge.git
cd cyberint-ops-xpath-challenge
```

---

###  Option 1: Run Using IntelliJ IDEA (or any Java IDE)

1. Open **IntelliJ IDEA**.
2. Click **Open** and select the project folder `cyberint-ops-xpath-challenge`.
3. IntelliJ will detect it's a **Maven project** and automatically import dependencies.
4. Open the file:  
   `src/main/java/com/assignment/TrackXpath.java`
5. Click the green **Run** button next to the `main()` method, or right-click and choose **Run 'TrackXpath.main()'**.
6. To run in **headless mode**:
    - Go to `Run > Edit Configurations...`
    - In **Program Arguments**, add: `--headless`
    - Click **Apply**, then **Run**

---

### ️ Option 2: Run from Terminal (with Maven)

Make sure you have **Java 17+** and **Maven** installed.

####  Run in normal (visible browser) mode

```bash
mvn clean compile exec:java
```

#### ️ Run in headless mode (no browser window)

```bash
mvn clean compile exec:java "-Dexec.args=--headless"
```

---

###  Requirements

- Java 17 or higher
- Maven
---
