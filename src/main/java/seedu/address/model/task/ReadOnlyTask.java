package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {
	
    Detail getDetail();
    Done checkDone();
    DueByDate getDueByDate();
    DueByTime getDueByTime();
    Priority getPriority();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
           
                && other.getDetail().equals(this.getDetail()) // state checks here onwards
                && other.checkDone().equals(this.checkDone())
                && other.getDueByDate().equals(this.getDueByDate())
                && other.getDueByTime().equals(this.getDueByTime())
                && other.getPriority().equals(this.getPriority()));
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDetail())
        		.append(" Done: ")
        		.append(checkDone())
                .append(" DueDate: ")
                .append(getDueByDate())
                .append(" DueTime: ")
                .append(getDueByTime())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a string representation of this Task's tags
     */
    default String tagsString() {
        final StringBuffer buffer = new StringBuffer();
        final String separator = ", ";
        getTags().forEach(tag -> buffer.append(tag).append(separator));
        if (buffer.length() == 0) {
            return "";
        } else {
            return buffer.substring(0, buffer.length() - separator.length());
        }
    }

}
