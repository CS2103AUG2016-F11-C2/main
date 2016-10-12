package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withDetail(String detail) throws IllegalValueException {
        this.task.setDetail(new Detail(detail));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }
    
    
    public TaskBuilder withDueByDate(LocalDate dbd) throws IllegalValueException {
        this.task.setDueByDate(new DueByDate(dbd));
        return this;
    }

    public TaskBuilder withDueByTime(LocalTime dbt) throws IllegalValueException {
        this.task.setDueByTime(new DueByTime(dbt));
        return this;
    }

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
