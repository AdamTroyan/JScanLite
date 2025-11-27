package troyan.adam.cli;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import troyan.adam.core.RuleEngine;
import troyan.adam.core.RuleResult;
import troyan.adam.rules.EmptyCatchRule;
import troyan.adam.rules.EmptyIfRule;
import troyan.adam.rules.EmptyWhileRule;
import troyan.adam.rules.UnreachableCodeRule;
import troyan.adam.rules.MagicNumberRule;
import troyan.adam.rules.UnusedVariableRule;
import troyan.adam.rules.LongMethodRule;
import troyan.adam.rules.UnusedImportRule;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class JScanLiteCLI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final JavaParser parser = new JavaParser();
    private static RuleEngine engine;

    public static void main(String[] args) throws Exception {
        initializeEngine();

        if (args.length > 0) {
            String path = args[0];
            scanPath(path);
            return;
        }

        showWelcome();
        runInteractiveMode();
    }

    private static void initializeEngine() {
        engine = new RuleEngine();
        engine.addRule(new EmptyCatchRule());
        engine.addRule(new EmptyIfRule());
        engine.addRule(new EmptyWhileRule());
        engine.addRule(new UnreachableCodeRule());
        engine.addRule(new MagicNumberRule());
        engine.addRule(new UnusedVariableRule());
        engine.addRule(new LongMethodRule());
        engine.addRule(new UnusedImportRule());
    }

    private static void showWelcome() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║           JScanLite v1.0             ║");
        System.out.println("║     Java Code Quality Scanner        ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println();
    }

    private static void runInteractiveMode() {
        while (true) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    scanFileInteractive();
                    break;
                case "2":
                    scanDirectoryInteractive();
                    break;
                case "3":
                    showHelp();
                    break;
                case "4":
                    System.out.println("Thank you for using JScanLite! 👋");
                    return;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }

            System.out.println("\n" + "═".repeat(40));
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
        }
    }

    private static void showMenu() {
        System.out.println("\n📋 Main Menu:");
        System.out.println("1. 🔍 Scan a single Java file");
        System.out.println("2. 📁 Scan a directory");
        System.out.println("3. ❓ Help & About");
        System.out.println("4. 🚪 Exit");
        System.out.print("\nChoose an option (1-4): ");
    }

    private static void scanFileInteractive() {
        System.out.print("📄 Enter the path to the Java file: ");
        String filePath = scanner.nextLine().trim();

        if (filePath.isEmpty()) {
            System.out.println("❌ No file path provided.");
            return;
        }

        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            System.out.println("❌ File does not exist: " + filePath);
            return;
        }

        if (!filePath.endsWith(".java")) {
            System.out.println("❌ Please provide a .java file.");
            return;
        }

        System.out.println("🔍 Scanning file: " + filePath);
        scanPath(filePath);
    }

    private static void scanDirectoryInteractive() {
        System.out.print("📁 Enter the path to the directory: ");
        String dirPath = scanner.nextLine().trim();

        if (dirPath.isEmpty()) {
            System.out.println("❌ No directory path provided.");
            return;
        }

        Path path = Paths.get(dirPath);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            System.out.println("❌ Directory does not exist: " + dirPath);
            return;
        }

        System.out.println("🔍 Scanning directory: " + dirPath);
        scanPath(dirPath);
    }

    private static void showHelp() {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("🎯 JScanLite - Java Code Quality Scanner");
        System.out.println("═".repeat(50));
        System.out.println();
        System.out.println("🔍 DETECTS THE FOLLOWING ISSUES:");
        System.out.println("• Empty catch, if, and while blocks");
        System.out.println("• Unreachable code after return statements");
        System.out.println("• Magic numbers (hardcoded values)");
        System.out.println("• Unused local variables");
        System.out.println("• Methods longer than 30 lines");
        System.out.println("• Unused import statements");
        System.out.println();
        System.out.println("📖 USAGE:");
        System.out.println("• Choose option 1 to scan a single .java file");
        System.out.println("• Choose option 2 to scan all .java files in a directory");
        System.out.println("• Command line: jscanlite <file.java or directory>");
        System.out.println();
        System.out.println("✨ FEATURES:");
        System.out.println("• AST-based analysis for accurate detection");
        System.out.println("• Recursive directory scanning");
        System.out.println("• Clear, actionable error messages with line numbers");
        System.out.println("• Interactive console interface");
    }

    private static void scanPath(String path) {
        try {
            Path inputPath = Paths.get(path);
            List<Path> javaFiles;

            if (Files.isDirectory(inputPath)) {
                javaFiles = Files.walk(inputPath)
                        .filter(p -> p.toString().endsWith(".java"))
                        .collect(Collectors.toList());
            } else {
                javaFiles = List.of(inputPath);
            }

            boolean hasIssues = false;
            for (Path javaFile : javaFiles) {
                String code = new String(Files.readAllBytes(javaFile));
                CompilationUnit cu = parser.parse(code).getResult()
                        .orElseThrow(() -> new RuntimeException("Failed to parse " + javaFile));

                List<RuleResult> results = engine.scan(cu);

                if (!results.isEmpty()) {
                    hasIssues = true;
                    System.out.println("\n--- Issues in " + javaFile + " ---");
                    for (RuleResult r : results) {
                        System.out.printf("[Line %d] %s%n", r.getLine(), r.getMessage());
                    }
                }
            }

            if (!hasIssues) {
                System.out.println("No issues found ✔");
            }
        } catch (Exception e) {
            System.out.println("❌ Error during scanning: " + e.getMessage());
        }
    }
}
