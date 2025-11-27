package troyan.adam.rules;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import troyan.adam.core.Rule;
import troyan.adam.core.RuleResult;

public class LongMethodRule implements Rule {
    private static final int MAX_METHOD_LENGTH = 30;

    @Override
    public RuleResult apply(CompilationUnit cu) {
        for (MethodDeclaration method : cu.findAll(MethodDeclaration.class)) {
            if (method.getBody().isPresent()) {
                int lineCount = method.getEnd().get().line - method.getBegin().get().line + 1;
                if (lineCount > MAX_METHOD_LENGTH) {
                    return new RuleResult(true, "Method '" + method.getNameAsString() + "' is too long (" + lineCount + " lines). Consider breaking it into smaller methods.", method.getBegin().get().line);
                }
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "LongMethodRule";
    }

    @Override
    public String getDescription() {
        return "Detects methods that are too long.";
    }
}