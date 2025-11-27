package troyan.adam.core;

import com.github.javaparser.ast.CompilationUnit;

public interface Rule {
    /**
     * Apply the rule on the given compilation unit
     * @param cu the parsed compilation unit
     * @return RuleResult object, or null if no issue found.
     */
    RuleResult apply(CompilationUnit cu);

    /**
     * Get the name of the rule.
     * @return the rule name
     */
    String getName();

    /**
     * Get a description of what the rule checks.
     * @return the rule description
     */
    String getDescription();
}
