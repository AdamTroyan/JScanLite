package troyan.adam.rules;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.ReturnStmt;
import troyan.adam.core.Rule;
import troyan.adam.core.RuleResult;

import java.util.List;

public class UnreachableCodeRule implements Rule {
    @Override
    public RuleResult apply(CompilationUnit cu) {
        for (MethodDeclaration method : cu.findAll(MethodDeclaration.class)) {
            if (method.getBody().isPresent()) {
                List<com.github.javaparser.ast.stmt.Statement> statements = method.getBody().get().getStatements();
                for (int i = 0; i < statements.size(); i++) {
                    if (statements.get(i) instanceof ReturnStmt) {
                        for (int j = i + 1; j < statements.size(); j++) {
                            return new RuleResult(true, "Unreachable code after return statement", statements.get(j).getBegin().get().line);
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "UnreachableCodeRule";
    }

    @Override
    public String getDescription() {
        return "Detects unreachable code after return statements.";
    }
}