# MCP Server

Quick script to run the Python server tools. 

## How to run it

```bash
uv venv
# Windows:
.venv\Scripts\activate
# Mac/Linux:
# source .venv/bin/activate

uv add "mcp[cli]" httpx fastmcp
python server.py
```

It exposes tools like `parse_jacoco_report` and `detect_code_smells` using SSE transport on port 8000. Give that URL to VS Code to connect it.
