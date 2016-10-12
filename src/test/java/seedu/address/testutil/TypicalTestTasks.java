package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ToDoList;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask grocery, house, family, car, dog;

    public TypicalTestTasks() {
        try {
            grocery = new TaskBuilder().withDetail("Buy more milk").withDueByDate(LocalDate.of(2012, 07, 13)).withDueByTime(LocalTime.of(13, 20)).withPriority("high").build();
            house = new TaskBuilder().withDetail("Paint the house").withDueByDate(LocalDate.of(2010, 06, 20)).withDueByTime(LocalTime.of(10, 20)).withPriority("high").build();
            family = new TaskBuilder().withDetail("Give Kelly a bath").withDueByDate(LocalDate.of(2012,11,20)).withDueByTime(LocalTime.of(11, 20)).withPriority("low").build();
            car = new TaskBuilder().withDetail("Add gas").withDueByDate(LocalDate.of(2014,11,20)).withDueByTime(LocalTime.of(9, 20)).withPriority("high").build();
            dog = new TaskBuilder().withDetail("Invent automatic dog toilet").withDueByDate(LocalDate.of(2016,10,10)).withDueByTime(LocalTime.of(16, 10)).withPriority("low").withTags("dog").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadToDoListWithSampleData(ToDoList td) {

        try {
            td.addTask(new Task(grocery));
            td.addTask(new Task(house));
            td.addTask(new Task(family));
            td.addTask(new Task(car));
            td.addTask(new Task(dog));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{grocery, house, family, car, dog};
    }

    public ToDoList getTypicalToDoList(){
        ToDoList ab = new ToDoList();
        loadToDoListWithSampleData(ab);
        return ab;
    }
}
