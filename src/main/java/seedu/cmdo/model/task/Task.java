package seedu.cmdo.model.task;

import java.util.Objects;

import seedu.cmdo.commons.util.CollectionUtil;
import seedu.cmdo.model.tag.UniqueTagList;

/**
 * Represents a Task in the To Do List.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

	
    private Detail detail;
    private Done done = new Done();
    private DueByDate dueByDate;
    private DueByTime dueByTime; 
    private Priority priority;
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Detail detail, DueByDate dueByDate, DueByTime dueByTime, Priority priority, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(detail, dueByDate, dueByTime, priority, tags);
        
        this.detail = detail;
        this.dueByDate = dueByDate;
        this.dueByTime = dueByTime;
        this.priority = priority;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    /**
     * This constructor is for the reloading of saved states in XmlAdaptedTask, where done may not be false by default.
     * 
     * Every field must be present and not null.
     * 
     * @author A0141128R
     */
    public Task(Detail detail, Done done, DueByDate dueByDate, DueByTime dueByTime, Priority priority, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(detail, done, dueByDate, dueByTime, priority, tags);
        
        this.detail = detail;
        this.done = done;
        this.dueByDate = dueByDate;
        this.dueByTime = dueByTime;
        this.priority = priority;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDetail(), source.checkDone(), source.getDueByDate(), source.getDueByTime(), source.getPriority(), source.getTags());
    }

    @Override
    public Detail getDetail() {
        return detail;
    }

    @Override
    public Done checkDone() {
        return done;
    }
    
    @Override
    public DueByDate getDueByDate() {
        return dueByDate;
    }

    @Override
    public DueByTime getDueByTime() {
        return dueByTime;
    }
    
    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(detail, done, dueByDate, dueByTime, priority, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    /**
     * Returns a proper parser understandable date string for testing purposes.
     * @return friendly string of date object.
     * 
     * @author A0139661Y
     */
    public String getFriendlyDate() {
    	return dueByDate.getFriendlyString();  
    }
    
    /**
     * Returns a proper parser understandable date string for testing purposes.
     * @return friendly string of time object.
     * 
     * @author A0139661Y
     */
    public String getFriendlyTime() {
    	return dueByTime.getFriendlyString();
    }
}