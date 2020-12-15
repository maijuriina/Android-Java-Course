## Android-Java-Course
Android Java Programming

# Test Application
The Test Application has been made using Java and XML on Android Studio.  
Tested on Android Studio's Virtual Machine: Pixel 3, API 28.  
Specific build data can be found in the gradle-file.

#### The app consists of four main fragments:
1. Home
1. Location
1. Timer
1. Company search

## Home
In the home fragment there are two puzzle games. 
### Card game
In the card game the user has to choose one of four cards, and if they choose the right one with a hidden value, the user gets one point. After each right guess the cards are shuffled. If the user clicks the wrong card, the points reset and the cards are shuffled again. On the first try at 0 points clicking on a wrong card takes the card away from the table until the right one is found and one point has been accumulated, after which one wrong pick shuffles the cards and resets the points.  
The game counts the current score of the user, and saves the highest obtained score as a high score.  
### Guessing game
In the guessing game the user has to choose one of five options to match the logo of a University of Applied Sciences on top. Points are given by how many clicks it takes for the user to know the logo, and they are displayed in the form of a SeekBar. When the user gets the right answer, they may go to another view to see their total score.

## Location
In the location fragment (authorized) location data is used to display the coordinates and the Google Maps address of the device. The user may open their location on Google Maps.

## Timer
The timer fragment displays the current time. There is a timer for minutes and seconds which uses NumberPicker. The set and running time is displayed for the user and there are play/pause/stop-ToggleButtons with which the user can control the timer. A notification is sounded when the timer runs out.

## Company search
The company search fragment utilises open JSON data from the Finnish Patent and Registration Office (PRH) using an HTTP request. More of the database can be read here: http://avoindata.prh.fi/index_en.html  
The search is done by user submitted text, and the maximum amount of results displayed in the app is set to 30, starting from companies founded on the 28th February 1900. The search returns JSON Object data into an array, which is displayed using RecyclerView. When an item in the list is tapped, more information is shown, and tapping it again hides the extra information.  
The displayed list of search results may be filtered by entering text into the filter search field, with only those items that match the filter criteria displayed. The filter updates the result list dynamically, and if all filter text is deleted, the original result list is displayed in full.
