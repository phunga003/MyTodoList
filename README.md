# MyTodoList

A todo list application in java 17

## To Run the Project

Prerequisites:

- Maven 3.9.16
- Java 17

From Repo root:

```bash
mvn compile

# If you are on cmd or bash
mvn exec:java -Dexec.mainClass="MyTodoList.Main" -Dexec.args="src/main/resources/seedData2.json"

# If you are on Powershell
mvn exec:java "-Dexec.mainClass=MyTodoList.Main" "-Dexec.args=src/main/resources/seedData2.json"
```

### Seed Data

Seed data are two files. They can be found in

- `src/main/resources/seedData2.json`
- `src/test/resources/seedData.json`

The seed can be applied to the application via command line arguments by filling in `-Dexec.args`
> Note: The application only can take one file as seed. Multiple data files are not supported
>

## Deliverables

- [X] Store tasks in a file
- [X] supports
  - [X] adding tasks
  - [X] removing tasks
  - [X] listing tasks
  - [X] classify tasks into different arbitrary categories

## What is out of scope

- Complex Querying (select all tasks where ...)
  - Simple query to query for specific task should suffice for the deliverables
- Sorting tasks
- complex UI outside of CLI
- Hosing a version of the app
- Atomicity of serialization
  - Tests for this is hard to set up
- Descriptions in tasks (it is not strictly required)
- Writing data to multiple files
- Cleaning up bad seed data
  - The system will assume that provided data seed is in the correct format
  - 'default' must exist as a category in the seed data

## Application Limitations

- Does not support atomic writing to files, a crash mid-writing will potentially corrupt data
- Does not support cleaning of bad data inside the seed.
- The application may produce undefined behavior if the following syntax rules are violated in the data file:
  - Tasks with the same name living in the same category
  - Does not have a 'default' category, even if it is empty
  - Numeric data for instead of strings
  - Overall json structure (see seed data)
  - categories with the same name
- Does not protect user's data privacy. All data files are in human-readable format

## Design

### Design Notes

- Business operations should fail loudly and handled by Main, easier to maintain.
  - Disclaimer: All exceptions thrown are unchecked, caller verify
- Since sorting is not needed, a collection of task can be an arraylist
- A Category contains an arraylist of tasks
  - Alternatively, Category and tasks can be a 2D array
    - Assume that there will be more extensions made to Category and Task in the future, having them as classes is more
      convenient
    - Task implement comparable to check for existing duplicate
      - Using Sets to ensure this produce quiet fails, and hard to implement sort-related features
      - O(n) where n is the number of tasks in a category -> not too bad that warrant optimization

- Serialization is an interface, so future serializers can be implemented easily
- task will be queried by index within a category
  - Since after a mutation the index will shift, the system _MUST_ display the current state of the todo list after all
    mutation operations
  - Category lookup will be in O(n) time, acceptable since user won't need millions of categories stored. If so, a
    database server is recommended

- the database will always have a default category for safe category deletion (special case object)
  - system will support moving task between category for ease of use

## Slide

[slide link](https://docs.google.com/presentation/d/157fT0tYSpsc8lxB7zB0SIKau0x85sXoNQJulCJEJ-Hg/edit?usp=sharing)



