package troyan.adam.rules;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.WhileStmt;
import troyan.adam.core.Rule;
import troyan.adam.core.RuleResult;

public class EmptyWhileRule implements Rule {
    @Override
    public RuleResult apply(CompilationUnit cu) {
        for (WhileStmt whileStmt : cu.findAll(WhileStmt.class)) {
            if (whileStmt.getBody().isBlockStmt() && whileStmt.getBody().asBlockStmt().getStatements().isEmpty()) {
                return new RuleResult(true, "Empty while block detected", whileStmt.getBegin().get().line);
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "EmptyWhileRule";
    }

    @Override
    public String getDescription() {
        return "Detects empty while blocks.";
    }
}