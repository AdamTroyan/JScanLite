package troyan.adam.rules;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.CatchClause;
import troyan.adam.core.Rule;
import troyan.adam.core.RuleResult;

/**
 * Rule that detects empty catch blocks.
 * Empty catch blocks can swallow exceptions silently, which is often a bug.
 */
public class EmptyCatchRule implements Rule {
    @Override
    public RuleResult apply(CompilationUnit cu) {
        for (CatchClause catchClause : cu.findAll(CatchClause.class)) {
            if (catchClause.getBody().getStatements().isEmpty()) {
                return new RuleResult(true, "Empty catch block detected", catchClause.getBegin().get().line);
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "EmptyCatchRule";
    }

    @Override
    public String getDescription() {
        return "Detects empty catch blocks which swallow exceptions.";
    }
}