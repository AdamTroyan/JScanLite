package troyan.adam.rules;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import troyan.adam.core.Rule;
import troyan.adam.core.RuleResult;

import java.util.List;

public class UnusedVariableRule implements Rule {
    @Override
    public RuleResult apply(CompilationUnit cu) {
        for (MethodDeclaration method : cu.findAll(MethodDeclaration.class)) {
            if (method.getBody().isPresent()) {
                List<com.github.javaparser.ast.stmt.Statement> statements = method.getBody().get().getStatements();
                for (com.github.javaparser.ast.stmt.Statement stmt : statements) {
                    for (VariableDeclarationExpr varDecl : stmt.findAll(VariableDeclarationExpr.class)) {
                        for (com.github.javaparser.ast.body.VariableDeclarator var : varDecl.getVariables()) {
                            String varName = var.getNameAsString();
                            boolean used = false;
                            for (com.github.javaparser.ast.stmt.Statement s : statements) {
                                for (NameExpr nameExpr : s.findAll(NameExpr.class)) {
                                    if (nameExpr.getNameAsString().equals(varName)) {
                                        used = true;
                                        break;
                                    }
                                }
                                if (used) break;
                            }
                            if (!used) {
                                return new RuleResult(true, "Unused variable '" + varName + "' detected", var.getBegin().get().line);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "UnusedVariableRule";
    }

    @Override
    public String getDescription() {
        return "Detects unused local variables.";
    }
}