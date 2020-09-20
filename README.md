# FetchRewardsExercise_Android
## About this Exercise
To write an Android/IOS app that retrieves the data from "https://fetch-hiring.s3.amazonaws.com/" and display this list of items to the user based on the following requirements:

- Display all the items grouped by "listId"
- Sort the results first by "listId" then by "name" when displaying.
- Filter out any items where "name" is blank or null.

The final result should be displayed to the user in an easy-to-read list.

## About the this Application
### GettingStarted
These are things you need to know or have before you start working with is project
#### Prerequisites
- Android SDK v29
- Android Build Tools v29.0.3
- Android Stuidio 

#### Installation
1. Clone this repository using git or download the zipped files using the option provided by Github on to your local machince
2. Open Android Studio, click "File -> Open ->" . Navigate the folder location and click open.
3. You are all set here, click on run button and wait for thr emulator to launch.

### Architecture
I have implemented the application using a MVVM architechute which helps me separate the data repository layer, business logic layer and UI.
There are various benifits of using such an architecture as it helps build clean, maintable and resulable code.
The below diagram depicts the flow of data and gives insights on the architecture.

<p align="center">
  <img width="500" height="200" src="https://github.com/anuj1995/FetchRewardsExercise_Android/blob/master/images/ArchitectureDiagram.png">
</p>

You can get the code snipets along with its usage guide in the wiki link provided below.
[Wiki Link](https://github.com/anuj1995/FetchRewardsExercise_Android/wiki)



