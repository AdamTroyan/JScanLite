package troyan.adam.core;

/**
 * Represents the result of applying a rule to code.
 * Contains information about whether an issue was found, the message, and the line number.
 */
public class RuleResult {

    private final boolean hasIssue;
    private final String message;
    private final int line;

    /**
     * Constructor for RuleResult.
     * @param hasIssue true if an issue was found
     * @param message the issue message
     * @param line the line number where the issue occurs
     */
    public RuleResult(boolean hasIssue, String message, int line) {
        this.hasIssue = hasIssue;
        this.message = message;
        this.line = line;
    }

    /**
     * Check if this result indicates an issue.
     * @return true if an issue was found
     */
    public boolean hasIssue() {
        return hasIssue;
    }

    /**
     * Get the issue message.
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get the line number of the issue.
     * @return the line number
     */
    public int getLine() {
        return line;
    }
}
