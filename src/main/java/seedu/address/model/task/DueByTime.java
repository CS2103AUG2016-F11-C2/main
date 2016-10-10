package seedu.address.model.task;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's due time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueByTime(String)}
 */
public class DueByTime {

    public static final String MESSAGE_DUEBYTIME_CONSTRAINTS = "Due at what time? You should type in a time in format HHMM";
//    public static final String DUEBYTIME_VALIDATION_REGEX = ".*";

    public final LocalTime value;

    /**
     * Validates given dueByTime.
     *
     * @throws IllegalValueException if given dueByTime string is invalid.
     */
    public DueByTime(LocalTime dueByTime) throws IllegalValueException {
        assert dueByTime != null;
        this.value = dueByTime.truncatedTo(ChronoUnit.MINUTES);
    }

    @Override
    public String toString() {
        return value.toString();
    }
    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueTime // instanceof handles nulls
                && this.value.equals(((DueTime) other).value)); // state check
    }
    */

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
