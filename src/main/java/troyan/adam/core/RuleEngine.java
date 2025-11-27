package troyan.adam.core;

import com.github.javaparser.ast.CompilationUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Engine that manages and applies multiple rules to code.
 */
public class RuleEngine {

    private final List<Rule> rules = new ArrayList<>();

    /**
     * Add a rule to the engine.
     * @param rule the rule to add
     */
    public void addRule(Rule rule){
        rules.add(rule);
    }

    /**
     * Scan the compilation unit with all registered rules.
     * @param cu the compilation unit to scan
     * @return list of rule results
     */
    public List<RuleResult> scan(CompilationUnit cu){
        List<RuleResult> results = new ArrayList<>();

        for(Rule rule : rules){
            RuleResult r = rule.apply(cu);
            if(r != null && r.hasIssue()){
                results.add(r);
            }
        }

        return results;
    }
}
