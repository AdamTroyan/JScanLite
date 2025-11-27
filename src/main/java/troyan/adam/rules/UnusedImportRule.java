package troyan.adam.rules;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import troyan.adam.core.Rule;
import troyan.adam.core.RuleResult;

import java.util.HashSet;
import java.util.Set;

public class UnusedImportRule implements Rule {
    @Override
    public RuleResult apply(CompilationUnit cu) {
        Set<String> usedTypes = new HashSet<>();
        for (NameExpr nameExpr : cu.findAll(NameExpr.class)) {
            usedTypes.add(nameExpr.getNameAsString());
        }
        for (ClassOrInterfaceType type : cu.findAll(ClassOrInterfaceType.class)) {
            usedTypes.add(type.getNameAsString());
        }

        // Collect annotation names
        for (AnnotationExpr annotation : cu.findAll(AnnotationExpr.class)) {
            usedTypes.add(annotation.getNameAsString());
        }

        for (ImportDeclaration importDecl : cu.getImports()) {
            if (!importDecl.isAsterisk()) {
                String importedClass = importDecl.getName().getIdentifier();
                if (!usedTypes.contains(importedClass)) {
                    return new RuleResult(true, "Unused import: " + importDecl.getNameAsString(), importDecl.getBegin().get().line);
                }
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "UnusedImportRule";
    }

    @Override
    public String getDescription() {
        return "Detects unused import statements.";
    }
}