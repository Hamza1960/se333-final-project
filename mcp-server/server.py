"""
SE333 - MCP Server for Test Automation
Provides tools for JaCoCo coverage parsing, test analysis,
and development automation.

Author: Hamza
Course: SE333 - Software Testing, Winter 2026
"""

import json
import xml.etree.ElementTree as ET
from pathlib import Path
from fastmcp import FastMCP

mcp = FastMCP("SE333 Testing Agent 🧪")


# ─── Tool 1: Basic arithmetic (Phase 1 demo) ───────────────────────────────

@mcp.tool
def add(a: int, b: int) -> int:
    """Add two numbers together. Simple demo tool for Phase 1."""
    return a + b


# ─── Tool 2: JaCoCo XML Parser ─────────────────────────────────────────────

@mcp.tool
def parse_jacoco_report(xml_path: str) -> str:
    """
    Parse a JaCoCo XML coverage report and return structured coverage data.

    Args:
        xml_path: Absolute path to the jacoco.xml file
                  (usually target/site/jacoco/jacoco.xml)

    Returns:
        JSON string with per-class and overall coverage metrics
    """
    path = Path(xml_path)
    if not path.exists():
        return json.dumps({"error": f"File not found: {xml_path}"})

    try:
        tree = ET.parse(str(path))
        root = tree.getroot()
    except ET.ParseError as e:
        return json.dumps({"error": f"XML parse error: {str(e)}"})

    report_name = root.attrib.get("name", "unknown")
    classes = []
    overall_counters = {}

    # Walk each package / class
    for package in root.findall(".//package"):
        pkg_name = package.attrib.get("name", "")
        for cls in package.findall("class"):
            cls_name = cls.attrib.get("name", "")
            source_file = cls.attrib.get("sourcefilename", "")
            counters = _parse_counters(cls)
            classes.append({
                "package": pkg_name.replace("/", "."),
                "class": cls_name.split("/")[-1],
                "sourceFile": source_file,
                "counters": counters,
            })

    # Overall report counters
    for counter in root.findall("counter"):
        ctype = counter.attrib.get("type", "")
        missed = int(counter.attrib.get("missed", 0))
        covered = int(counter.attrib.get("covered", 0))
        total = missed + covered
        pct = round((covered / total) * 100, 2) if total > 0 else 0.0
        overall_counters[ctype] = {
            "missed": missed,
            "covered": covered,
            "total": total,
            "percentage": pct,
        }

    result = {
        "report": report_name,
        "overall": overall_counters,
        "classes": classes,
    }
    return json.dumps(result, indent=2)


# ─── Tool 3: Coverage Gap Finder ───────────────────────────────────────────

@mcp.tool
def find_coverage_gaps(xml_path: str, threshold: float = 80.0) -> str:
    """
    Analyze JaCoCo XML and find classes/methods below the coverage threshold.

    Args:
        xml_path: Absolute path to the jacoco.xml file
        threshold: Minimum acceptable line coverage percentage (default 80%)

    Returns:
        JSON with a list of under-covered classes and their uncovered methods
    """
    path = Path(xml_path)
    if not path.exists():
        return json.dumps({"error": f"File not found: {xml_path}"})

    try:
        tree = ET.parse(str(path))
        root = tree.getroot()
    except ET.ParseError as e:
        return json.dumps({"error": f"XML parse error: {str(e)}"})

    gaps = []
    for package in root.findall(".//package"):
        pkg_name = package.attrib.get("name", "").replace("/", ".")
        for cls in package.findall("class"):
            cls_name = cls.attrib.get("name", "").split("/")[-1]
            line_counter = cls.find("counter[@type='LINE']")
            if line_counter is None:
                continue

            missed = int(line_counter.attrib.get("missed", 0))
            covered = int(line_counter.attrib.get("covered", 0))
            total = missed + covered
            pct = round((covered / total) * 100, 2) if total > 0 else 0.0

            if pct < threshold:
                # Dig into methods
                uncovered_methods = []
                for method in cls.findall("method"):
                    m_name = method.attrib.get("name", "")
                    m_line = method.find("counter[@type='LINE']")
                    if m_line is not None:
                        m_missed = int(m_line.attrib.get("missed", 0))
                        m_covered = int(m_line.attrib.get("covered", 0))
                        m_total = m_missed + m_covered
                        m_pct = round((m_covered / m_total) * 100, 2) if m_total > 0 else 0.0
                        if m_pct < 100.0:
                            uncovered_methods.append({
                                "method": m_name,
                                "lineCoverage": m_pct,
                                "missedLines": m_missed,
                            })

                gaps.append({
                    "package": pkg_name,
                    "class": cls_name,
                    "lineCoverage": pct,
                    "missedLines": missed,
                    "uncoveredMethods": uncovered_methods,
                })

    gaps.sort(key=lambda g: g["lineCoverage"])
    return json.dumps({
        "threshold": threshold,
        "totalGaps": len(gaps),
        "gaps": gaps,
    }, indent=2)


# ─── Tool 4: Coverage Summary (quick overview) ─────────────────────────────

@mcp.tool
def coverage_summary(xml_path: str) -> str:
    """
    Return a quick one-line coverage summary from a JaCoCo XML report.

    Args:
        xml_path: Absolute path to the jacoco.xml file

    Returns:
        Human-readable summary string
    """
    path = Path(xml_path)
    if not path.exists():
        return f"Error: File not found at {xml_path}"

    try:
        tree = ET.parse(str(path))
        root = tree.getroot()
    except ET.ParseError as e:
        return f"Error: Could not parse XML — {str(e)}"

    results = []
    for counter in root.findall("counter"):
        ctype = counter.attrib.get("type", "")
        missed = int(counter.attrib.get("missed", 0))
        covered = int(counter.attrib.get("covered", 0))
        total = missed + covered
        pct = round((covered / total) * 100, 1) if total > 0 else 0.0
        results.append(f"{ctype}: {pct}%")

    report_name = root.attrib.get("name", "report")
    return f"[{report_name}] " + " | ".join(results)


# ─── Tool 5: Boundary Value Analyzer (Phase 5 extension) ───────────────────

@mcp.tool
def analyze_boundary_values(method_signature: str) -> str:
    """
    Given a method signature with numeric parameters, suggest boundary
    value test cases based on common boundary value analysis techniques.

    Args:
        method_signature: e.g. 'int calculate(int a, int b)'

    Returns:
        JSON with suggested boundary test values
    """
    import re

    # Extract parameter types and names
    params_match = re.search(r'\((.*?)\)', method_signature)
    if not params_match:
        return json.dumps({"error": "Could not parse parameters from signature"})

    params_str = params_match.group(1).strip()
    if not params_str:
        return json.dumps({"suggestions": [], "note": "No parameters found"})

    params = []
    for p in params_str.split(","):
        parts = p.strip().split()
        if len(parts) >= 2:
            params.append({"type": parts[0], "name": parts[1]})

    suggestions = []
    for param in params:
        ptype = param["type"].lower()
        pname = param["name"]

        if ptype in ("int", "integer", "long"):
            suggestions.append({
                "parameter": pname,
                "type": ptype,
                "boundaryValues": [
                    {"value": 0, "reason": "Zero — common boundary"},
                    {"value": 1, "reason": "Just above zero"},
                    {"value": -1, "reason": "Just below zero"},
                    {"value": 2147483647, "reason": "Integer.MAX_VALUE"},
                    {"value": -2147483648, "reason": "Integer.MIN_VALUE"},
                ],
                "equivalenceClasses": [
                    {"class": "negative", "example": -100},
                    {"class": "zero", "example": 0},
                    {"class": "positive", "example": 100},
                ],
            })
        elif ptype in ("double", "float"):
            suggestions.append({
                "parameter": pname,
                "type": ptype,
                "boundaryValues": [
                    {"value": 0.0, "reason": "Zero"},
                    {"value": -0.0, "reason": "Negative zero"},
                    {"value": 0.001, "reason": "Small positive"},
                    {"value": -0.001, "reason": "Small negative"},
                    {"value": "Double.MAX_VALUE", "reason": "Max value"},
                    {"value": "Double.NaN", "reason": "Not a number"},
                    {"value": "Double.POSITIVE_INFINITY", "reason": "Infinity"},
                ],
            })
        elif ptype in ("string", "str"):
            suggestions.append({
                "parameter": pname,
                "type": ptype,
                "boundaryValues": [
                    {"value": "null", "reason": "Null input"},
                    {"value": '""', "reason": "Empty string"},
                    {"value": '" "', "reason": "Single space"},
                    {"value": '"a"', "reason": "Single character"},
                    {"value": '"a" * 10000', "reason": "Very long string"},
                ],
            })

    return json.dumps({"method": method_signature, "suggestions": suggestions}, indent=2)


# ─── Tool 6: Code Smell Detector (Phase 5 extension) ───────────────────────

@mcp.tool
def detect_code_smells(java_source: str) -> str:
    """
    Perform lightweight static analysis on Java source code to detect
    common code smells and potential issues.

    Args:
        java_source: The Java source code as a string

    Returns:
        JSON with detected code smells and suggestions
    """
    import re
    findings = []

    lines = java_source.split("\n")
    for i, line in enumerate(lines, 1):
        stripped = line.strip()

        # Empty catch blocks
        if stripped == "} catch" or "catch" in stripped:
            # Look ahead for empty catch
            if i < len(lines) and lines[i].strip() == "}":
                findings.append({
                    "line": i,
                    "type": "EMPTY_CATCH",
                    "severity": "WARNING",
                    "message": "Empty catch block — exceptions are silently swallowed",
                    "suggestion": "Log the exception or handle it properly",
                })

        # String comparison with ==
        if "==" in stripped and '"' in stripped and ".equals" not in stripped:
            findings.append({
                "line": i,
                "type": "STRING_EQUALITY",
                "severity": "ERROR",
                "message": "Comparing strings with == instead of .equals()",
                "suggestion": "Use .equals() for string content comparison",
            })

        # Division by zero risk
        if re.search(r'/\s*0[^.]', stripped):
            findings.append({
                "line": i,
                "type": "DIVISION_BY_ZERO",
                "severity": "ERROR",
                "message": "Potential division by zero detected",
                "suggestion": "Add a zero-check before division",
            })

        # Magic numbers (numbers > 1 not assigned to constants)
        magic_nums = re.findall(r'(?<!=)\b(\d{2,})\b', stripped)
        for num in magic_nums:
            if num not in ("10", "100", "1000"):  # common acceptable ones
                findings.append({
                    "line": i,
                    "type": "MAGIC_NUMBER",
                    "severity": "INFO",
                    "message": f"Magic number {num} — consider using a named constant",
                    "suggestion": f"private static final int SOME_NAME = {num};",
                })

        # Long method heuristic (> 30 lines between braces)
        if stripped.startswith("public") or stripped.startswith("private"):
            if "(" in stripped and "{" in stripped:
                # Count lines until closing brace
                brace_count = stripped.count("{") - stripped.count("}")
                method_lines = 0
                for j in range(i, min(i + 100, len(lines))):
                    method_lines += 1
                    brace_count += lines[j].count("{") - lines[j].count("}")
                    if brace_count <= 0:
                        break
                if method_lines > 30:
                    findings.append({
                        "line": i,
                        "type": "LONG_METHOD",
                        "severity": "WARNING",
                        "message": f"Method is {method_lines} lines long — consider refactoring",
                        "suggestion": "Break into smaller, focused methods",
                    })

        # Unused variable heuristic
        var_match = re.match(r'\s*(int|String|double|float|long|boolean)\s+(\w+)\s*=', stripped)
        if var_match:
            var_name = var_match.group(2)
            # Count occurrences in the rest of the method scope
            rest_of_code = "\n".join(lines[i:min(i + 50, len(lines))])
            if rest_of_code.count(var_name) <= 0:
                findings.append({
                    "line": i,
                    "type": "UNUSED_VARIABLE",
                    "severity": "WARNING",
                    "message": f"Variable '{var_name}' may be unused after assignment",
                    "suggestion": "Remove unused variables to improve readability",
                })

    return json.dumps({
        "totalFindings": len(findings),
        "findings": findings,
    }, indent=2)


# ─── Helpers ────────────────────────────────────────────────────────────────

def _parse_counters(element):
    """Extract all counter types from a JaCoCo XML element."""
    counters = {}
    for counter in element.findall("counter"):
        ctype = counter.attrib.get("type", "")
        missed = int(counter.attrib.get("missed", 0))
        covered = int(counter.attrib.get("covered", 0))
        total = missed + covered
        pct = round((covered / total) * 100, 2) if total > 0 else 0.0
        counters[ctype] = {
            "missed": missed,
            "covered": covered,
            "total": total,
            "percentage": pct,
        }
    return counters


# ─── Entry Point ────────────────────────────────────────────────────────────

if __name__ == "__main__":
    print("Starting SE333 MCP Server...")
    print("Tools available: add, parse_jacoco_report, find_coverage_gaps,")
    print("  coverage_summary, analyze_boundary_values, detect_code_smells")
    # Use SSE transport so VS Code can connect via URL
    mcp.run(transport="sse")
