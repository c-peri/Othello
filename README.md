# OTHELLO

This program is an Othello implementation, faithfully following its rules, its objective as well as its intended game flow.
The main focus of this project is for the user to be able to play against an A.I. that uses the MiniMax algorithm to decide the best possible move each round.

The game:
* Is played on an 8x8 board, represented by the Board class of this program.
* Is played by two players, the White and Black player, one of which is the A.I. represented by the Player class of this program.
* Starts off with four discs already in the center of the board, two white and two black, in the traditional opening positions.
* Ends either when the entirety of the board is filled, or both players have no valid moves. Each move is represented by the Move class of this program.

## Running the program

This program is made to run in cmd. To run it with its intended visual design you must first run the following command in your cmd:
```bash
chcp 65001
```
After that comes the compiling and executing of the program, made by the following commands:
```bash
javac Main.java
java Main.java
```
Afterwards, the program is made simple by prompts that guide the user throughout the game