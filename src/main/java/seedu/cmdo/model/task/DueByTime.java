package seedu.cmdo.model.task;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import seedu.cmdo.commons.exceptions.IllegalValueException;

//@@author A0141128R
/**
 * Represents a Task's due time in the To Do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueByTime(String)}
 */
public class DueByTime {

    public static final String MESSAGE_DUEBYTIME_CONSTRAINTS = "Due at what time? You should type in a time in format HHMM";
    private final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HHmm");
    private final LocalTime NO_TIME = LocalTime.MAX;
    
    public LocalTime start;
    public LocalTime end;
    public final Boolean isRange;
    private Boolean isFloating = false; // Floating time is found in tasks with no time. 
    
    /**
     * Validates given dueByTime.
     *
     * @throws IllegalValueException if given dueByTime string is invalid.
     */
    public DueByTime(LocalTime dueByTime) throws IllegalValueException {
        assert dueByTime != null;
        // Check for date with time
        if (!dueByTime.equals(NO_TIME)) {
        	this.start = dueByTime.truncatedTo(ChronoUnit.MINUTES);
        } else { 
        	this.start = NO_TIME;
        	this.isFloating = true;
        }
    	this.end = NO_TIME;
    	this.isRange = false;
    }
    
    /**
     * Create a range of dueByTime.
     *
     * @throws IllegalValueException if given dueByTime string is invalid.
     */
    public DueByTime(LocalTime dueByTimeStart, LocalTime dueByTimeEnd) throws IllegalValueException {
        assert dueByTimeStart != null && dueByTimeEnd != null;
        // Enable storage of floating time in date range
        if (dueByTimeStart.equals(NO_TIME) && dueByTimeEnd.equals(NO_TIME)) {
        	this.start = NO_TIME;
        	this.end = start;
        	this.isFloating = true;
        } else {
        	this.start = dueByTimeStart.truncatedTo(ChronoUnit.MINUTES);
        	this.end = dueByTimeEnd.truncatedTo(ChronoUnit.MINUTES);
        }
        this.isRange = true;
    }

    @Override
    public String toString() {
        if (isRange)
        	return new StringBuilder(start.toString() + "/to/" + end.toString()).toString();
        else 
        	return start.toString();
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueByTime // instanceof handles nulls
                && this.equals((DueByTime) other)); 
    }
    
    //to set it to a floating task for edit command purpose
    public void setFloating(){
    	this.start = LocalTime.MAX;
    	this.end = start;
    }
    //check if single time
    public boolean isSingleTime(){
    	return isFloating;
    }
    //check if time is entered
    public boolean timeNotEntered(){
    	return (end.equals(LocalTime.MAX) && start.equals(LocalTime.MAX));
    }

    @Override
    public int hashCode() {
    	return Objects.hash(start, end);
    }

    public boolean isRange() {
		return isRange;
	}
    
    public boolean isFloating() {
    	return isFloating;
    }
    
    /*
     * Produces a friendly string of values in the format HH:MM
     */
    public String getFriendlyString() {
		// If floating date, return do not print anything
		if (start.equals(NO_TIME) && end.equals(NO_TIME)) {
			return "";
		}
    	if (!isRange) {
    		return new StringBuilder(start.format(TIME_FORMAT)).toString();
    	}
		return new StringBuilder(start.format(TIME_FORMAT) 
				+ " - " 
				+ end.format(TIME_FORMAT))
				.toString();
	}
    
	public String getFriendlyStartString() {
		if (!isRange && isFloating)
			return "";
		return start.format(TIME_FORMAT).toString(); 
	}
	
	public String getFriendlyEndString() {
		if (!isRange) {
			return "";
		}
		if (end.equals(NO_TIME)) {
			return "";
		} else return end.format(TIME_FORMAT).toString();
	}
}