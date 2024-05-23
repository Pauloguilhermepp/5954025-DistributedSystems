# Multiplayer NxN Tic Tac Toe
The project presented here consists of a multiplayer version of the NxN Tic Tac Toe game. You can play with your friends following the instruction below.

<p align="center">
  <img src="https://raw.githubusercontent.com/Pauloguilhermepp/5954025-DistributedSystems/main/images/game.png" alt="Game Image">
</p>

## Author
This project was developed by:
* Paulo Guilherme

## How To Run It?
After open the directory of the project, you must follow the following steps:
1. Go to the *src* directory
```
cd src
```
2. Compile all the files
```
javac *.java
```
3. Run the local server
```
java GameServer
```
4. In another terminal, run the local client
```
java GameClient 1 localhost
```
5. Each of the others players must run its own client 
```
java GameClient <PlayerId> <Server IPv6>
```

## Making My Own Changes

If you want to make your own changes in the code, you also can apply the code formatter in the end! To do it, you need to install *clang* and run the following command:
```
bash style.sh
```
It will automatically format all the files of the project.

## Observations
* **Discovering the Server IPv6**: There are a variety of ways to discover your own IP. Probably, the easiest way is just to access an web site such as [https://www.whatismyip.com/](https://www.whatismyip.com/).
* **Local Client**: In the instructions above it is considered that the server is running the same machine as the first client. If it is not the case, the *localhost* parameter must be replaced by the server IP.