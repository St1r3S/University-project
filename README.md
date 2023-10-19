# University_project
Task 9 Decompose university
Task - building a CRM for university schedule management.

General
- The university has 6 classes per day Mon-Sat, Sunday is usually a day off
- Students are divided into groups.
- Classes are linked to groups.
- For simplicity we assume that there are no combined lectures for several groups.
- Auditoriums have no specialisation and are all the same.
- Teachers are specialised.


Schedule type
- Simple schedule

Simple schedule
- The schedule is set once at the beginning of the academic period (semester), any additional changes are private and have no relation to our system.

Users
- Any visitor can view a common for all groups (or by choice) schedule (template) for a week.
- Using the link received from the dean's office a user can be registered in the system and get a set of roles. Then these roles can be edited by a user with Admin role.
- A user can have only one role.


Roles

1. Employee(stuff)
- Anyone not related to  ed. process - cleaner, janitor, etc.
- Can view the class schedule.

2. Student
- By default sees the schedule for his/her group, but can see the general schedule.

2. Teacher
- Can see his/her schedule.

4. Administrator/Dean (admin)
- Can edit the schedule.
- Can edit user roles.

