package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {
    
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should /low, /medium or /high";
    public static final String PRIORITY_VALIDATION_REGEX = "";

    public final String value;

    /**
     * Validates given priority.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        assert priority != null;
        if (!isValidPriority(priority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.value = priority;
    }

    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }
    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.value.equals(((Priority) other).value)); // state check
    }
    */

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}