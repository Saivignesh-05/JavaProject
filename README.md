# JavaProject
Server Client Application with server providing two services. 

SERVICE 1 : UNO GAME
A Popular Multiplayer Card Game. Clients can connect to the server and join a Lobby to play this game. Current Limit set to 5 people per game. The cards will be displayed on the terminal. User can choose to play a card or draw from deck. Only valid cards can be played.
 

SERVICE 2:  REMOTE QUIZ CONDUCTOR
The one conducting the quiz can write a file with the questions and store it in the JavaProjects/main/services/quiz/questions folder.
There are some examples of questions provided in the above specified folder, this must be the only way the conductor must provide the question paper.
(NOTE : OTHER WAYS OF SUBMITING WILL RESULTING IN ERROR DURING THE TEST)

THE DEFAULT QUESTION PAPER IS SET TO THE FILENAME "1" WHICH IS THE SAMPLE PAPER.
The conductor can choose to give A VALID question paper in the command line itself (EXAMPLE : java main.server.MultiServer filename)
The filename provided as command line argument must be in the folder JavaProjects/main/services/quiz/questions for it to work.

There is a file named rankings in the JavaProjects/main/services/quiz/questions. It contains the name of the client and his marks on the quiz.
The number of questions can be increased, Negative and Positive marking can also be provided (open the startQuizGame in services folder and make changes)


## TO RUN THE SERVER
    From outside the main folder in the JavaProject.zip/JavaProjects run the command : java main.server.MultiServer.
    The Server will be waiting for clients to connect.
    
## TO RUN THE CLIENT
    From outside the main folder in the JavaProject.zip/JavaProjects run the command : java main.client.Client
    If the Server is running, the client will automatically connect to the server.

