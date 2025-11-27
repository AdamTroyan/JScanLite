package troyan.adam.rules;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.IfStmt;
import troyan.adam.core.Rule;
import troyan.adam.core.RuleResult;

public class EmptyIfRule implements Rule {
    @Override
    public RuleResult apply(CompilationUnit cu) {
        for (IfStmt ifStmt : cu.findAll(IfStmt.class)) {
            if (ifStmt.getThenStmt().isBlockStmt() && ifStmt.getThenStmt().asBlockStmt().getStatements().isEmpty()) {
                return new RuleResult(true, "Empty if block detected", ifStmt.getBegin().get().line);
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "EmptyIfRule";
    }

    @Override
    public String getDescription() {
        return "Detects empty if blocks.";
    }
}