package troyan.adam;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.jupiter.api.Test;
import troyan.adam.core.RuleResult;
import troyan.adam.rules.EmptyCatchRule;
import troyan.adam.rules.MagicNumberRule;
import troyan.adam.rules.UnusedVariableRule;

import static org.junit.jupiter.api.Assertions.*;

public class JScanLiteTest {

    private final JavaParser parser = new JavaParser();

    @Test
    public void testEmptyCatchRule() {
        String code = """
            public class TestClass {
                public void method() {
                    try {
                        System.out.println("test");
                    } catch (Exception e) {
                        // Empty catch block
                    }
                }
            }
            """;

        ParseResult<CompilationUnit> result = parser.parse(code);
        assertTrue(result.isSuccessful());
        CompilationUnit cu = result.getResult().get();

        EmptyCatchRule rule = new EmptyCatchRule();
        RuleResult ruleResult = rule.apply(cu);

        assertNotNull(ruleResult);
        assertTrue(ruleResult.getMessage().contains("Empty catch block"));
    }

    @Test
    public void testMagicNumberRule() {
        String code = """
            public class TestClass {
                public void method() {
                    int x = 42; // Magic number
                    int y = 0; // Not a magic number
                    int z = 1; // Not a magic number
                }
            }
            """;

        ParseResult<CompilationUnit> result = parser.parse(code);
        assertTrue(result.isSuccessful());
        CompilationUnit cu = result.getResult().get();

        MagicNumberRule rule = new MagicNumberRule();
        RuleResult ruleResult = rule.apply(cu);

        assertNotNull(ruleResult);
        assertTrue(ruleResult.getMessage().contains("Magic number"));
    }

    @Test
    public void testUnusedVariableRule() {
        String code = """
            public class TestClass {
                public void method() {
                    int unused = 10; // Unused variable
                    int used = 20;
                    System.out.println(used);
                }
            }
            """;

        ParseResult<CompilationUnit> result = parser.parse(code);
        assertTrue(result.isSuccessful());
        CompilationUnit cu = result.getResult().get();

        UnusedVariableRule rule = new UnusedVariableRule();
        RuleResult ruleResult = rule.apply(cu);

        assertNotNull(ruleResult);
        assertTrue(ruleResult.getMessage().contains("Unused variable"));
    }

    @Test
    public void testNoIssues() {
        String code = """
            public class TestClass {
                public void method() {
                    try {
                        System.out.println("test");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    int x = 10;
                    System.out.println(x);
                }
            }
            """;

        ParseResult<CompilationUnit> result = parser.parse(code);
        assertTrue(result.isSuccessful());
        CompilationUnit cu = result.getResult().get();

        EmptyCatchRule emptyCatchRule = new EmptyCatchRule();
        MagicNumberRule magicNumberRule = new MagicNumberRule();
        UnusedVariableRule unusedVariableRule = new UnusedVariableRule();

        assertNull(emptyCatchRule.apply(cu));
        assertNull(magicNumberRule.apply(cu));
        assertNull(unusedVariableRule.apply(cu));
    }
}
