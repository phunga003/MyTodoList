# MyTodoList

A todo list application in java 17

## Deliverables

- [ ] Store tasks in a file
- [ ] supports
  - [ ] adding tasks
  - [ ] removing tasks
  - [ ] listing tasks
  - [ ] classify tasks into different arbitrary categories

## What is out of scope

- Complex Querying (select all tasks where ...)
  - Simple query to query for specific task should suffice for the deliverables
- Sorting tasks
- complex UI outside of CLI
- Hosing a version of the app
- Atomicity of serialization
- Descriptions in tasks (it is not strictly required)

## Application Limitations

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

### Diagrams
