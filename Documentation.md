# Intelligent Testing Agent Documentation

This document outlines the technical design, features, and iteration strategy of the MCP-powered testing agent built for SE333.

## 1. System Architecture

The project is split into two components to mimic real-world toolchain separation:
1. **MCP Server (`mcp-server/`)**: A Python application exposing local tools via Server-Sent Events (SSE). 
2. **Client Workspace (`se333-demo/`)**: The Java codebase being analyzed and tested by the LLM.

### The Feedback Loop
The agent relies on JaCoCo coverage reports to identify missing test coverage. The core loop is:
1. Agent generates tests and successfully compiles them.
2. Agent runs `mvn clean test`.
3. JaCoCo generates `target/site/jacoco/jacoco.xml`.
4. Agent uses the `#parse_jacoco_report` MCP tool to read coverage percentages.
5. Agent uses the `#find_coverage_gaps` MCP tool to locate exactly which classes, methods, and lines are uncovered.
6. Agent writes new targeted `@Test` methods to cover those gaps.

## 2. Model Context Protocol Tools

The Python backend exposes the following tools:

- **parse_jacoco_report**: Reads the XML output from JaCoCo and returns a structured JSON summary. This prevents the LLM from having to process raw XML blocks.
- **find_coverage_gaps**: Filters the coverage data against a threshold (e.g., `< 100%`) and returns a precise list of methods that need testing.
- **coverage_summary**: A quick command to grab the high-level percentage without the full payload.
- **analyze_boundary_values** (Phase 5 Extension): Takes a method signature and programmatically suggests boundary test cases (like `Integer.MAX_VALUE`, `0`, `-1`).
- **detect_code_smells** (Phase 5 Extension): Performs lightweight static analysis to warn the agent of potential logic flaws before writing tests.

## 3. The Target Application

To adequately test the agent, a suite of utility classes was built in `src/main/java/`:
- `Calculator.java`: Basic arithmetic, including an intentional division-by-zero risk requiring the agent or user to catch it.
- `StringProcessor.java`: Includes logic for string reversal, palindrome checking, and vowel counting.
- `ArrayUtils.java`: Search, sort, and duplicate removal for integer arrays.
- `InventoryManager.java`: A simple stock management system to test state and exception paths.

## 4. Git Automation & Trunk-Based Development

Version control isn't an afterthought. A secondary agent prompt (`git-workflow.prompt.md`) dictates how code changes are saved:
- The main branch (`main`) is protected. The agent never commits directly to it.
- Feature branches (`feature/<name>`) are created for all test generation.
- Changes are grouped, committed with standardized messages (`test: improve coverage on ArrayUtils`), and pushed.
- Merge pull requests serve as the quality gate before trunk integration.

## 5. Usage Prompting

To trigger the agent from the Chat UI:
- Open the `.github/prompts/tester.prompt.md` file or use `@workspace` to point to it.
- Ask the agent: *"Follow the instructions in the tester prompt to reach 100% coverage."*
- Ensure "Auto-Approve" is checked if you want the agent to rapidly iterate on building and testing without manual confirmation prompts.

## 6. Coverage Metrics Achieved

Over multiple recursive attempts, the agent produced 129 test cases across the 4 classes.
- **Instruction Coverage**: 98.1%
- **Class Coverage**: 100%
- **Tests Passing**: 129 / 129. 
