package troyan.adam.rules;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import troyan.adam.core.Rule;
import troyan.adam.core.RuleResult;

public class MagicNumberRule implements Rule {
    @Override
    public RuleResult apply(CompilationUnit cu) {
        for (IntegerLiteralExpr intLit : cu.findAll(IntegerLiteralExpr.class)) {
            FieldDeclaration field = intLit.findAncestor(FieldDeclaration.class).orElse(null);
            if (field != null && field.getModifiers().stream().anyMatch(m -> m.getKeyword() == com.github.javaparser.ast.Modifier.Keyword.FINAL)) {
                continue;
            }
            int value = intLit.asInt();

            if (value > 1 && value != 10 && value != 100 && value != 1000) {
                return new RuleResult(true, "Magic number '" + value + "' detected. Consider using a named constant.", intLit.getBegin().get().line);
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "MagicNumberRule";
    }

    @Override
    public String getDescription() {
        return "Detects magic numbers in code.";
    }
}