package seedu.cmdo.model.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import seedu.cmdo.commons.exceptions.IllegalValueException;

//@@author A0141128R
/**
 * Represents a Task's Due date in the To Do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueByDate(String)}
 */
public class DueByDate {

    public static final String MESSAGE_DUEBYDATE_CONSTRAINTS = "Due by? You should enter a day, or a date.";
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/uuuu");
    private final LocalDate NO_DATE = LocalDate.MAX;

    public final LocalDate start;
    public LocalDate end;
    public final Boolean isRange;
    private Boolean isFloating = false; // Floating date is found in task with no date.

    /**
     * Takes in a single date.
     *
     * @throws IllegalValueException if given due date string is invalid.
     */
    public DueByDate(LocalDate dueByDate) throws IllegalValueException {
        assert dueByDate != null;
        this.end = NO_DATE;
        this.start = dueByDate;
        this.isRange = false;
        if (dueByDate.equals(NO_DATE))
        	this.isFloating = true;
    }
    
    /**
     * Takes in a start date and end date.
     *
     * @throws IllegalValueException if given due date string is invalid.
     */
    public DueByDate(LocalDate dueByDateStart, LocalDate dueByDateEnd) {
        assert dueByDateStart != null && dueByDateEnd != null;
        this.start = dueByDateStart;
        this.end = dueByDateEnd;
        this.isRange = true;
    }

    @Override
    public String toString() {
        if (isRange)
        	return new StringBuilder(start.toString() + "/to/" + end.toString()).toString();
        else 
        	return start.toString();
    }
    
    public boolean isRange() {
        return isRange;
    }
    
    public boolean isFloating() {
    	return isFloating;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueByDate // instanceof handles nulls
                && this.equals((DueByDate) other));
    }

    @Override
    public int hashCode() {
    	return Objects.hash(start, end);
    }
    
    /*
     * Produces a friendly string of values in the format MM/DD/YYYY
     */
	public String getFriendlyString() {		
		// If floating date, return do not print anything
		if (start.equals(NO_DATE) && end.equals(NO_DATE))
			return "";
		if (!isRange) {
			return new StringBuilder(start.format(DATE_FORMAT)).toString();
		}
		return new StringBuilder(start.format(DATE_FORMAT) 
								+ " - " 
								+ end.format(DATE_FORMAT))
								.toString();
	}
	
	//setter to set it a floating for edit command purpose
	public void setFloating(){
		end = NO_DATE;
	}
	//to check if date was entered
	public boolean dateNotEntered(){
		if (end.equals(NO_DATE) & start.equals(NO_DATE))
			return true;
		else
			return false;
	}
	
	// Operates on the premise that the start date is always specified.
	public String getFriendlyStartString() {
		if (isFloating) return "";
		if (!isRange) return start.format(DATE_FORMAT).toString();
		return start.format(DATE_FORMAT).toString(); 
	}
	
	public String getFriendlyEndString() {
		if (!isRange || isFloating || end.equals(NO_DATE)) return "";
		return end.format(DATE_FORMAT).toString();
	}
}