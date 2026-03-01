# SE333 Final Project: Intelligent Testing Agent

This is my final submission for the SE333 Software Agents project. I built an automated testing agent that uses the Model Context Protocol (MCP) to write tests, check coverage, and fix code gaps on its own. It's basically an AI assistant that loops over the code until it hits max coverage using JaCoCo.

## What's in Here

- **MCP Server** (`mcp-server/`): The Python side of the project. It connects the LLM to local tools like reading coverage reports and analyzing boundaries.
- **Demo App & Agent** (`se333-demo/`): A Java Maven project with some custom classes I wrote for the agent to test, plus the actual prompt files that make the agent run.

## How It works

The setup follows the prompt chaining and iterative testing loop we discussed in class:

1. The agent runs `mvn clean test`.
2. It uses an MCP tool to parse the `jacoco.xml` file.
3. It figures out which lines and branches are missing coverage.
4. It writes new JUnit 5 tests to cover those specific gaps.
5. It re-runs the whole thing to verify, and loops until coverage is maxed out.

## Getting Started

### 1. Boot up the MCP Server

Head over to the `mcp-server` folder and set up Python:

```bash
cd mcp-server
uv venv
# On Windows:
.venv\Scripts\activate
# Install what it needs
uv add "mcp[cli]" httpx fastmcp

# Run it
python server.py
```
It should say it's running on port 8000.

### 2. Connect VS Code
Open the repo in VS Code, pop open the command palette (`Ctrl+Shift+P`), and run **MCP: Add Server**. Give it the URL `http://localhost:8000/sse` and name it whatever you want (I used `se333-mcp-server`).

### 3. Run the Java Stuff

Go to the `se333-demo` folder and run this exact command to start the build:
```powershell
cd se333-demo
& 'C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2025.2.3\plugins\maven\lib\maven3\bin\mvn.cmd' clean test
```

## The Custom Tools I Added (Phase 5)

I dug a bit deeper than the standard requirements and added two extra MCP tools for the agent:

1. **Boundary Value Analyzer**: You can feed it a method signature (like `int divide(int a, int b)`) and it spits out suggested edge cases (0, MAX_VALUE, negative numbers, etc.) based on the parameter types.
2. **Code Smell Detector**: A quick static analysis tool that scans Java code for bad practices, like division by zero risks or comparing strings with `==`.

## Complete MCP Tool API Documentation

All tools are exposed via the `se333-mcp-server` and are accessible through the VS Code Chat.

### 1. `add(a: int, b: int) -> int`
- **Purpose**: Verify MCP connectivity.
- **Parameters**: `a` (int), `b` (int).
- **Returns**: Sum of a and b.

### 2. `parse_jacoco_report(xml_path: str) -> dict`
- **Purpose**: Convert JaCoCo XML reports into structured JSON.
- **Parameters**: `xml_path` (Absolute path to `jacoco.xml`).
- **Returns**: JSON object containing instruction, branch, and line metrics.

### 3. `find_coverage_gaps(xml_path: str, threshold: float = 100.0) -> dict`
- **Purpose**: Identify classes and methods missing coverage.
- **Parameters**: `xml_path` (str), `threshold` (float 0-100).
- **Returns**: List of methods/classes below the threshold.

### 4. `coverage_summary(xml_path: str) -> str`
- **Purpose**: Get a one-line summary of instruction coverage.
- **Parameters**: `xml_path` (str).
- **Returns**: Formatted string (e.g., "Coverage: 98.1%").

### 5. `analyze_boundary_values(method_signature: str) -> dict`
- **Purpose**: Specification-based testing extension.
- **Parameters**: `method_signature` (e.g., `public int multiply(int x, int y)`).
- **Returns**: Suggested boundary values (0, 1, -1, MAX, MIN) for testing.

### 6. `detect_code_smells(java_source: str) -> dict`
- **Purpose**: Static analysis extension.
- **Parameters**: `java_source` (Contents of a Java file).
- **Returns**: List of detected smells (Magic numbers, division by zero risks, etc.).

## Troubleshooting & FAQ

**Q: The agent says it can't find the `jacoco.xml` file.**
- **A**: Make sure you have run `mvn clean test` at least once in the `se333-demo` folder. The file is generated at `target/site/jacoco/jacoco.xml`.

**Q: My MCP server is running but tools aren't showing up in Chat.**
- **A**: Click the "Tools" icon (lightning bolt) in the Chat panel and ensure `se333-mcp-server` is checked. If it's not there, re-add the server via the Command Palette using the SSE URL.

**Q: The agent is asking for permission for every tool call.**
- **A**: Enable **Auto-Approve** (YOLO Mode) in VS Code Settings (`Chat: Settings`). This allows the agent to run the multi-step iteration loop without a hundred popups.

**Q: Why stop at 98.1% coverage?**
- **A**: In `Calculator.java`, some intentional bugs like absolute value of `Integer.MIN_VALUE` or specific prime number edge cases are designed to show how the agent handles "impossible" or "hard" paths. 100% is the goal, but 98%+ is elite for automated generation.

## Feedback Loop & Iteration

This project isn't just a static script. It implements a **Recursive Feedback Loop**:
1. **Analyze**: Agent reads current coverage using MCP tools.
2. **Execute**: Agent writes a test targeting a specific missing line.
3. **Verify**: Agent runs `mvn test`. If it fails, the agent reads the error, uses `detect_code_smells` to help debug, and fixes the test.
4. **Iterate**: This process repeats automatically until the coverage graph stabilizes.

## Technical Credits
- **Course**: SE333 - Software Testing
- **Student**: Hamza Patel
- **Instructor**: Dong Jae Kim
