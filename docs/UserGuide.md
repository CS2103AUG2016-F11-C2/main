# User Guide

<br><img src="images/Logo.jpeg" width="600"><br>

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough.
   >
   > This app will not work with earlier versions of Java 8.
   
1. Download the latest `CMDo.jar` from the 'releases' tab.
2. Copy the file to the folder you want to use as the home folder for your CMDo.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   <img src="images/Ui.jpeg" width="600">
   <img src="images/Main.jpeg" width="600">


4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
   
5. Some example commands you can try:
   * **`list`** : lists all task
   * **`add`**` email prof damith to feedback on the module on wednesday` : 
     adds a task named `email prof damith to feedback on module` to the To Do List.
   * **`delete`**` 3` : deletes the task 3 “email prof damith” in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

<br><img src="images/Help.jpeg" width="600"><br>

#### Adding a task in CMDo: `add`
Adds a task to CMDo <br>
Format: `add <details> <day> <time> /<priority> -<tag>` 
 
> You can type anything in details. It must not end with `by`, `on`, `before` or `at`.
> 
> For time, typing `tml`, `tmr`, `tomorrow` will still be recognised as tomorrow.
>
> For time, typing `1300`, `1pm`, will be recognised as 1300.
> 
> For priority, use `/low`, `/medium` or `/high`. Tasks added without specific priority will default to low.
<br><img src="images/Add1.jpeg" width="600"><br>
<br><img src="images/Add1a.jpeg" width="600"><br>
> You can also add a tag to the task by using `-`.
<br><img src="images/Add2.jpeg" width="600"><br>
<br><img src="images/Add2a.jpeg" width="600"><br>

#### Listing task in CMDo : `list <day>` or `param`
Shows a list of all task in the CMDo on that day. It also acts as a filter.<br>
Format: `list <today>`

> Key in <damith> and all task with 'damith' will appear
> <br><img src="images/ListTmr.jpeg" width="600"><br>
> Key in <done> and all done task will appear
> <br><img src="images/ListDone.jpeg" width="600"><br>
> Key in any search parameter and list command will add a filter for you
<br><img src="images/ListTake.jpeg" width="600"><br>
> Key in <with ___ priority> to show all tasks with specified priority
<br><img src="images/ListHP.jpeg" width="600"><br>

#### Deleting a task in CMDo : `delete`
Deletes the specified task from the to do list.
Format: `delete INDEX`

> Deletes the person at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`
  `delete 2`
  Deletes the 2nd task in the to do list.
* `list email` 
  `delete 1`
  Deletes the 1st task in the results of the `find` command.

#### Update task : `update`
Update existing tasks in the CMDo list, as you would entering a new task.<br>
Format: `edit INDEX details`  

> Examples: 
> * `edit 2 eat candy tomorrow`
>  Edit the 2nd task in the to do list to eat candy with date due as tomorrow 
>  
> * `edit 1 Bear`
> Task 2 changes to Bear


####  Mark a task as done : `done <index>`
Done a task so tick it off the list

Format: `done <index>`  
<br><img src="images/Done.jpeg" width="600"><br>


#### Undo the last action : `undo`
Undo the earlier action
Format: `undo`  

<br><img src="images/Undo.jpeg" width="600"><br>


#### Redo earlier action : `redo`
Redo the earlier action.
Format: `redo`  

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
To do list data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Address Book.
       
**Q**: Is there a click function? 
**A**: No

**Q**: How do i customize the functions and commands to suit my style?
**A**: We will have in the next version :)

**Q**: Can i change the layout?
**A**: Yes

**Q**: How do i set priority to a task?
**A**: add priority under tags eg.(H for high, M for medium, L for low)

**Q**: Is there a cheat sheet for the commands?
**A**: `/help`

**Q**: Do I have to check my to-do list regularly, is there something to remind me?
**A**: You have to check your to-do list, isnt that the point?

**Q**: How do i block out slots for unconfirmed tasks?
**A**: just key in the timming after the task description.

**Q**: How do i see upcoming tasks?
**A**: The list will be sorted according to date and time

**Q**: How to scroll?
**A**: Use the page up and page down commands
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add <task> <day> <time> <priority>`
Delete | `delete <INDEX>`
Done | `done <INDEX>`
List | `list <keyword>`
List All | `la`
Help | `help`
Undo | `undo`
Redo | `redo`
Update | `update <index> <task details>`
Page Up | <kbd>PgUp</kbd> (Windows) / <kbd>Fn</kbd>+<kbd>Shift</kbd>+<kbd>Up</kbd> (Mac)
Page Down | <kbd>PgDown</kbd> (Windows) / <kbd>Fn</kbd>+<kbd>Shift</kbd>+<kbd>Down</kbd> (Mac)
Exit | `exit`

Last updated 7 Oct 2016.