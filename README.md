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

## Design

### Design Notes

- Since sorting is not needed, a collection of task can be an arraylist
- A Category contains an arraylist of tasks
  - Alternatively, Category and tasks can be a 2D array
    - Assume that there will be more extensions made to Category in the future, having them as classes is more
      convenient
- Serialization is an interface, so future serializers can be implemented easily

### Diagrams
