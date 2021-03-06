package seedu.cmdo.model.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import seedu.cmdo.commons.util.CollectionUtil;
import seedu.cmdo.model.tag.UniqueTagList;

/**
 * Represents a Task in the To Do List.
 * Guarantees: details are present and not null, field values are validated.
 **/
public class Task implements ReadOnlyTask, Comparable {
	private Detail detail;
    private Done done = new Done();
    private DueByDate dueByDate;
    private DueByTime dueByTime; 
    private Priority priority;
    private UniqueTagList tags;
    private Boolean block = false;
    private LocalDateTime startLdt;
    public UUID id = UUID.randomUUID();

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
        this.startLdt = LocalDateTime.of(dueByDate.start, dueByTime.start);
    }
    
    //@@author A0141128R
    /**
     * This constructor is for the reloading of saved states in XmlAdaptedTask, where done may not be false by default.
     * 
     * Every field must be present and not null.
     */
    public Task(Detail detail, Done done, DueByDate dueByDate, DueByTime dueByTime, Priority priority, boolean block, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(detail, done, dueByDate, dueByTime, priority, tags);
        
        this.detail = detail;
        this.done = done;
        this.dueByDate = dueByDate;
        this.dueByTime = dueByTime;
        this.priority = priority;
        this.block = block;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.startLdt = LocalDateTime.of(dueByDate.start, dueByTime.start);
    }
    
    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDetail(), source.checkDone(), source.getDueByDate(), source.getDueByTime(), source.getPriority(), source.getBlock(), source.getTags());
    }
    
    //@@author A0139661Y
    /**
     * Copy constructor with dereferencing Done value. This ensures that source's value does not get modified along with the new task's.
     */
    public Task(ReadOnlyTask source, Done done) {
        this(source.getDetail(), done, source.getDueByDate(), source.getDueByTime(), source.getPriority(), source.getBlock(), source.getTags());
    }
    
    //@@author A0141128R
    //to edit it to a floating task
    public void setFloating(){
    	dueByTime.setFloating();
    	dueByDate.setFloating();
	}
    
    //@@author A0141128R
    //change detail used in edit command
    public void setDetail(Detail d) {
        detail = d;
    }
    
    //@@author A0141128R
    //setter to edit due by date for edit command
    public void setDueByDate(DueByDate dbd){
    	dueByDate = dbd;
    }
    
    //@@author A0141128R
    //setter to edit due by time for edit command
    public void setDueByTime(DueByTime dbt){
    	dueByTime = dbt;
    }
    
    //@@author A0141128R
    //setter to edit start due by date time for edit command
    public void setStartLdt(LocalDateTime ldt) {
    	startLdt = ldt;
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
    
    @Override
    public LocalDateTime getStartLdt() {
    	return startLdt;
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
    
    //@@author A0141006B
    /**
     * Marks a floating task via boolean
     */
    
    public boolean isFloatingTask() {
    	if(getDueByDate().start.equals(LocalDate.MAX)) {
    		return true;
    	}else
    		return false;
    }
    
    //@@author A0139661Y
    /**
     * Returns a proper parser understandable date string for testing purposes.
     * @return friendly string of date object.
     */
    public String getFriendlyDate() {
    	return dueByDate.getFriendlyString();  
    }
    
    //@@author A0139661Y
    /**
     * Returns a proper parser understandable date string for testing purposes.
     * @return friendly string of time object.
     */
    public String getFriendlyTime() {
    	return dueByTime.getFriendlyString();
    }
    
    //@@author A0141128R
    /**
     * To set task to blocked time slot
     */
    public void setBlock(){
    	block = true;
    }
    
    @Override
    public boolean getBlock(){
    	return block;
    }

    /**
     * Determines if task is occupies a range.
     */
	@Override
	public boolean isRange() {
		return dueByDate.isRange() || dueByTime.isRange();
	}

	//@@author A0139661Y
	@Override
	public int compareTo(Object o) {
		// Ensure done tasks are always last
		if (this.checkDone().value) {
			return 1;
		} if (((Task)o).checkDone().value) {
			return -1;
		}
		int i = this.getStartLdt().compareTo(((Task) o).getStartLdt());
    	if (i != 0) 
    		return i;
    	return this.getDetail().toString().compareToIgnoreCase(((Task)o).getDetail().toString());
	}
}