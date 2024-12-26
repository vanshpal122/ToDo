Never forget your events to do! Schedule your latest events and receive reminders with the ToDo App.</br>

For Fellow Developers:
1) Used RoomDatabase to store event details and timings.
2) Used AlarmManager to schedule alarms, passing a PendingIntent with event details as Intent extras.
3) Used BroadcastReceiver to send notifications based on the Intent received from the AlarmManager.
4) Followed the MVVM pattern and Dependency Injection techniques.
5) Used NavGraph for navigating through different screens in the app.
6) Followed standard code for permission management for allowing Notification.


UI Description:
1) HomeScreen:
      Shows the latest events ToDo retrived from the Database with FloatingActionButton to proceed to AddEvent
      If clicked on one of the events, you will get navigated to event description screen whcih shows all the details about the event clicked.
      If performed long click on one of the event you will get a DropDown List containing two options to delete or edit the event. 

2) AddEventScreen:
   Contains Input Fields:
   1) Event Heading (with word limit of 25 characters)
   2) Event Description (with word limit of 100 characters)
   3) Event Duration (with Numbered KeyBoard input)
   4) Set Timing (which facitilate to add event timings in AM-PM format)
   With Add Button at the bottom to add event succesfully (with a Toast message appearing after additon)

3) EventDetailsScreen:
   Shows all the details about the respective event, provides two buttons at the bottom to edit or delete the event.
4) EditEventScreen:
   With UI same as AddEvent Screen with change that the fields are pre-filled with the details of the respective event to be edited, you can modify the details now.
   The button at the bottom to the save the changes in the event details.
