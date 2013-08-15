SELP
====

Software Engineering Large Practical - Voting App

Voting App Documentation

Instruction for running the app:
- Before running the app, make sure to set up an email client in the Android Emulator with a Google mail account (in order to send email).
- To setup the email client in the Android Emulator: From the Home screen, open all applications. Then select the application ‘Email’. Enter your Google mail address and password to set up the email client.
- After the email client is set up, just open up an Android Virtual Device (Emulator) and click ‘Run as Android Application’ to install the app on the Emulator.
- The app runs on both Android 2.3.3 default emulator and the Google API [API Level 10] emulator (as tested on my laptop), you can use either one to run the app.
- When clicking on the ‘Vote (Email)’ button on the candidate information screen, the app will open up the email client which already has the relevant messages and email addresses set up, simply click ‘Send’ on the email client to vote for a candidate.
- After voting for a candidate (sent email), the app will go back to the candidate information screen and now you may click on the ‘Tweet’ button to send a private tweet. When clicking on the ‘Tweet’ button, it will prompt you to sign in with your Twitter account. After that, simply click ‘Sign in and Tweet’ and the message will be automatically posted. (This is shown in one of the screenshots)
- If you wish to view or vote for other candidates after sending a tweet, press ‘Home’ button on the Emulator and open up the app again.


Documentation of App design and features:
- MainMenuActivity.java, activity_main_menu.xml – Downloads the xml document of candidates from a server, parses the xml document and stores the data in a local database in the app. When the app is first started; the user will see a logo along with three buttons: ‘Register’, ‘Candidates’, ‘About’. Initially, when the app is first started, the ‘Candidates’ button is disabled as the user has to first register their student number before being able to view the list of candidates. The ‘Register’ button simply allows the user to register their student number and the ‘About’ button gives a brief description of the app.
- RegisterActivity.java, activity_register.xml – Allows the user to register their student number by entering the student number in an EditText widget which stores their student number for use later when sending an email.
- AboutActivity.java, activity_about.xml – Lets the user view a brief description of the app.
- Candidate.java – A class which provides getter and setter methods for storing and retrieving candidate information.
- DatabaseHandler.java – A SQLite database helper class which manages all database operations such as: Create, Read, Update, Delete.
- XMLParser.java – An xml parser class which downloads an xml document from the server and parses the xml document.
- UserSessionManager.java – A class used to handle user interactions with the app such as storing the student number, maintaining the star ratings given to a candidate etc.
- DisplayActivity.java, activity_display.xml – Uses a ListView widget to display a list of candidates from the local database.
- CandidateInfoActivity.java, activity_candidate_info.xml – Displays all information for a particular candidate such as their name, office, image of candidate and statement (corresponding to the candidate clicked in the ListView). Also allows user to give a rating to the candidate; permanently delete the candidate from the local database; vote for the candidate by sending an email to the user’s university email address confirming the vote and send a tweet after voting. The ‘Tweet’ button is initially disabled as the app requires the user to vote for a candidate first (sending an email) before allowing the user to send a private tweet.





