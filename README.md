# JScanLite

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-21+-blue.svg)](https://openjdk.java.net/)

A lightweight, AST-based Java code quality scanner that detects common programming issues.

## Features

JScanLite analyzes Java source code for:

- **Empty Blocks**: Detects empty catch, if, and while blocks
- **Unreachable Code**: Identifies code after return statements
- **Magic Numbers**: Finds hardcoded numbers that should be constants
- **Unused Variables**: Locates local variables that are declared but never used
- **Long Methods**: Flags methods exceeding 30 lines
- **Unused Imports**: Detects import statements that are not used

### ✨ Additional Features

- **Interactive Console Interface**: User-friendly menu-driven experience
- **Command Line Support**: Backward compatible with scripts and automation
- **AST-based Analysis**: Leverages JavaParser for accurate code parsing
- **Recursive Directory Scanning**: Automatically finds all .java files
- **Clear Error Messages**: Actionable feedback with line numbers
- **Cross-platform**: Works on Windows, Linux, and macOS

## Installation

### Prerequisites

- Java 21 or higher
- Maven 3.6+

### Build from Source

```bash
git clone https://github.com/yourusername/JScanLite.git
cd JScanLite
mvn clean compile package
```

## Usage

JScanLite offers two modes of operation:

### Interactive Console Mode

Run without arguments for an interactive experience:

```bash
# Windows
run.bat

# Linux/Mac
./run.sh
```

The interactive mode provides:
- 📋 **Main Menu** with clear options
- 🔍 **Single File Scanning** - Choose specific Java files
- 📁 **Directory Scanning** - Scan entire projects
- ❓ **Help & About** - Detailed information
- 🚪 **Clean Exit** - Proper termination

### Command Line Mode

For automation and scripts, use command line arguments:

```bash
# Windows
run.bat path/to/file.java
run.bat src/

# Linux/Mac
./run.sh path/to/file.java
./run.sh src/
```

### Example Interactive Session

```
╔══════════════════════════════════════╗
║           JScanLite v1.0             ║
║     Java Code Quality Scanner        ║
╚══════════════════════════════════════╝

📋 Main Menu:
1. 🔍 Scan a single Java file
2. 📁 Scan a directory
3. ❓ Help & About
4. 🚪 Exit

Choose an option (1-4): 1
📄 Enter the path to the Java file: MyClass.java
🔍 Scanning file: MyClass.java
No issues found ✔
```

### Example Output

```
--- Issues in src/main/java/Example.java ---
[Line 15] Empty catch block detected
[Line 20] Magic number '42' detected. Consider using a named constant.
[Line 25] Unused variable 'temp' detected
```

## Architecture

JScanLite uses an extensible rule-based architecture:

- **AST Analysis**: Leverages JavaParser for accurate Abstract Syntax Tree parsing
- **Modular Rules**: Each rule implements the `Rule` interface
- **Easy Extension**: Add new rules by implementing `Rule.apply(CompilationUnit)`

### Core Components

- `Rule`: Interface for all code quality rules
- `RuleEngine`: Manages rule execution and aggregation
- `RuleResult`: Contains issue details (message, line number)
- **Interactive CLI**: User-friendly console interface with menu system
- **Command Line Support**: Backward compatible for automation
- Rules package: Contains all implemented rules (8 quality checks)

### Design Patterns

- **Strategy Pattern**: Each rule is a strategy for detecting specific issues
- **Composite Pattern**: RuleEngine aggregates multiple rules
- **Command Pattern**: CLI handles different user actions
- **Factory Pattern**: Engine initialization creates rule instances

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/new-rule`
3. Implement your rule in the `rules` package
4. Add tests in `src/test/java`
5. Run tests: `mvn test`
6. Submit a pull request

### Adding a New Rule

1. Create a class implementing `Rule`
2. Use JavaParser's AST nodes for analysis
3. Return `RuleResult` with issue details or `null`
4. Register the rule in `JScanLiteCLI.main()`

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [JavaParser](https://javaparser.org/) for AST parsing
- Inspired by static analysis tools like SonarQube and SpotBugs