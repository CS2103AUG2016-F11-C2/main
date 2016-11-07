<!-- @@author A0139661Y -->

# Test Instructions

Welcome, tester. We are happy to have you on board the CMDo team. Here are some instructions to get you started.

## Setup
1. Download `[F11-C2][CMDo-v0.5].jar` from the Releases page.
2. On first run, the folder `data` is created where the JAR file is run. It contains storage file `cmdo.xml`. This is the default storage file name. All storage files must have this name. You are free to rename the folder `data` to whatever you want, see step 7 for more)
3. Quit the application by typing in `exit` and delete `cmdo.xml`.
4. Download `SampleData.xml` from the directory `\src\test\data\ManualTesting\` into `data`.
5. Rename `SampleData.xml` to `cmdo.xml`.
6. Run the application. You are now ready to begin testing!
7. We recommend that you perform the tests in the order listed so that we fit in our assumptions well. (i.e. `delete 1` when there is nothing at index 1 returns a different outcome) If you do so, set your system time and date to **Nov 5, 2016, 9pm.**
8. _Optional:_ If you decide to change the name of the folder `data`, to, say `DropBox`, you must also let CMDo know. In `CMDo`, type in 
    ```
    storage DropBox
    ```
    You would have tested our [storage change functionality](###Change-Storage) already. Well done!

## Sample Codes

CMDo hosts a couple of interesting features that we need you to help test.

We have provided some sample test cases which you can start with. The input and expected output at the result display box below the command box are provided for your reference.

#### _**!! Please note that CMDo runs on date input format MM/DD/YYYY !!**_

### Add
Adds a new task. Tasks have 
- 'details'
- date and time (both, either, or none; start and/or end)
- priority levels `HIGH, MEDIUM, LOW, or NONE`
- multiple [tags]
```
add new task                         # "Encapsulate your details in ' '"
add ''                               # "Blank task? Did you mean to block out a date? Type help to see usage."
add 'hershey's hersh'                # Adds a task 'hershey's hersh'
add 'date' 10/31/2016                # Adds a task 'date' due on 10/31/2016
add 'time' 1300                      # Adds a task 'time' due today at 1pm
add 'range' tmr 1300 to 12/31 1400   # Adds a task 'range' from tomorrow 1pm to 12/31 2pm
add 1300 '5pm' /high                 # Adds a task '5pm' due today at 1pm with priority HIGH
add 'tag' -tag1 -tag2                # Adds a task 'tag' with tags tag1 and tag2
add 'invalid tag' -/high             # "Tags names should be alphanumeric"
```

### Block
Blocks are also tasks. Just that they don't allow other tasks to be added in that time slot they occupy. You can add a block with a time already taken up by a task, but not the other way round. Blocks **must** have
- 'details'
- date and time (both, either, or none; start and/or end)

They may have
- multiple [tags]


They do not have 
- priority levels `HIGH, MEDIUM, LOW, or NONE`
```
block new                                # "Encapsulate your details in ' '"
block ``                                 # "Blank task? Did you mean to block out a date? Type help to see usage."
block 'hershey's hersh'                  # "Specify a time/date range for the block."
block 'wedding' tmr 1300 to 12/31 1400   # Adds a task 'wedding' occupying tomorrow 1pm to 12/31 2pm
block 'exam' 1300                        # Adds a task 'exam' occupying today 1pm to 2pm 
block 2300 '12pm'                        # Adds a task '12pm' occupying today 11pm to tomorrow 12am
add 'tag' tomorrow -tag1 -tag2           # "Time slot blocked! Here is a list of all your blocked slots."
la                                       # Shows all tasks in the list
```

We do an `la` to reset the list to show all tasks. It is elaborated upon [here](###List). 

### Do
Doing a task relegates it to the done list forever (unless you undo). Note that blocked tasks can't be done. They must be [deleted](###Delete).

The command takes in an index and only an index.

```
done 1 lol                           # "Invalid command format!"
done 1                               # "Done task: date"
done 2                               # "You can't do a blocked timeslot... Right?"
ld                                   # Shows all done tasks in the list.
done 1                               # "Already done!"
la                                   # Shows all tasks in the list
```

We do an `ld` to reset the list to show all done tasks. It is elaborated upon [here](###List). 


### Delete
Deleting a task. Who knew it was that simple? Note that deleted tasks can be retrieved by `undo`.

The command takes in an index and only and index.

```
delete 1                             # "Deleted task: day"
delete 999                           # "The task index provided is invalid"
```

### Find
The search built in to CMDo is both fuzzy and powerful. Meaning that you may search for tasks and blocks with details up to 60% similar to any of your keywords. Search includes all terms you that you have entered. Narrow it down yourself if you have to.

There are no qualifiers or flags to add (unless you are searching for done tasks, use `find --done`). Just type everything out.

Before we test this, let's quickly add some more tasks to improve our coverage.
```
add 'tag1' 5pm
add 'ranges'
```

Ok, lets go!

```
find 5pm                             # '5pm' and 'tag1' found
find tag1                            # 'tag1' and 'tag' found
find wdding                          # 'wedding' found
find ranges                          # 'range', 'ranges' found
find 1300                            # '5pm', 'exam', 'time', 'range', 'wedding' found
find 1300 tag1                       # '5pm', 'exam', 'time', 'tag1', 'range', 'wedding', 'tag' found.
find --done                          # 'date' found.
la
```

### Edit
Edit changes the parameters of the desired task in the task list. Again, you do not have to use flags. Just type it all out and we will handle it for you.

To edit
- Details
    - Encapsultate details in ' '
- Date and time
    - Do it as normal, but note that if you enter
        - **Start time only**: Only start time will be changed
            ```
            edit 6 6pm               # 'range' has date 11/06/2016 1800 to 12/31/2016
            ```
            
        - **Time only**: Time will be changed and date reset to today's date
            ```
            edit 7 6pm to 7pm        # 'range' has date 11/05/2016 1800 to 11/05/2016 1900
            ```
            
        - **Start date only**: Only start date will be changed
            ```
            edit 1 11/7/2016         # '6pm' has date 11/7/2016 1300
            ```
            
        - **Date only**: Date will be changed and time remains the same
            ```
            edit 6 11/20 to 11/21    # 'wedding' has date 11/20/2016 1pm to 11/21/2016 2pm
            ```
            
        - **Start date and time only**: Only start date and time will be changed.
            ```
            edit 5 11/4 2301        # '12pm' has date 11/4/2016 2301 to 11/6/2016
            ```
            
        - **Date and time**: Exactly as is
            ```
            edit 5 11/4 2301 to 11/5 2pm       # '12pm' has date 11/4/2016 2301 to 11/5/2016 1400
            ```

        - To completely remove all date/time parameters and make the task floating, type in `floating`

- Priority
    - Use a `/` before the desired priority
    - If no priority, use `no priority`
- Tags
    - Use a `-` before the desired tag.
    - All other tags get overwritten. Be careful!

The following tests test for the other kinds of non-date/time parameters. You may give it a try.
```
edit 1                               # "'Invalid command format!" 
edit 6 '6pm'                         # '5pm' becomes '6pm'
edit 7 -love /high                   # 'wedding' gets tag -love and HIGH priority
```

### List
All tests for lists have inadvertently been done by you. Congratulations! You may run through them here again if you want

```
list all
la
done 1
list done
ld
list block
lb
```

### Undo and Redo
You may undo and redo any action, except undo and redo themselves.

```
la
add 'to be undone'
undo                                 # "Undone!"
redo                                 # "Redone!"
redo                                 # "Nothing to redo."
```

### Keyboard Navigation
CMDo is also built for the keyboard junkie. You may use your keyboard to navigate.

```
select 1                             # Preset the list selection to index 1.
down
down
down
up
up
up
d
d
d
u
u
u
u                                    # "You can't go up no more."
bottom                               # "Right at the bottom."
d                                    # "You can't go down no more!"
top                                  # "I can see my house from here."
u                                    # "You can't go up no more."
```

### Change Storage
You have also already performed the storage test in the [setup](##Setup) instructions. Note that the storage file is named `CMDo.xml` and this cannot be changed.

```
storage test                         # Changes the storage file container to test. Watch the status bar at the bottom right.
add 'blah'
```

At this point when you check the `CMDo.xml` file in the new directory `/test/` you will see the latest updated version of the storage file, with task 'blah' at the end. In the old directory `/data/`, the old version without 'blah' is found. Now let's move it back.

```
storage data                         # Changes the storage file container to data. Watch the status bar at the bottom right.
```
Go to `/data/` and check `CMDo.xml`. You should see that task 'blah' has been added.

---


## Conclusion
Thank you for being a part of the testing for CMDo. The current version you are testing is v0.5. If you have any suggestions or feedback, please email us at justin@catharsys.co.

===
_Last updated Nov 7, 2016_

