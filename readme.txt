Name: Minh La
Date: 10/9/13
Email: minht@usc.edu

Project Name: A Star Search - Stepping A onto B!

1. Brief description:
This project is made for USC AI 460. The program uses A star search and the goal is find the steps needed for A to move onto B in a  2-d n-dimension grid using the three heuristics: Euclidean, Canberra, and Knight. When A moves, B moves as well but different. For instance, A move up, B will move left, etc.

The three heuristics are simple. The Euclidean and Canberra only involves an equation. The Knight actually requires a breadth first search algorithm to find the distance of the node from the root. And since the Knight moves in 8 different ways, the method for the Knight is somewhat lengthy. I also had to use more data structures to find the knight's heuristics. I actually did not know there was a link to how to do the knight's heuristic, so I somehow thought of BFS.

The structure of this program is broken down into Coordinates and State. The State contains where A and B is, the cost of the moves, and the list of previous moves. The States are stored into an 2-D array of size [n][n] of object Grid, which contains the State and the cost. The reason I am using the Grid is to store the previous cost, which I can access easily and through this I do not have to use open and closed queue. I can just have one queue that can see if the object is visted or not in the array.

The reason I am using an 2-D array instead of a hash map is because using a hashmap, I could not access the hash using coordinates, such are objects. Since I am making new coordinates for each child, it was not very successful in getting the hash since I has to attempt to get the compare the two different coordinates, while they are the same x and y. I feel like I could structure this better, but I decided to use a 2-D array and used the x and y to access.

Having the 2-D array, I can get the previous cost, thus I will know if the child or the state in the queue is different or not. The state in the 2-D array is always updated since I add/update the 2-D whenever I add a state to the queue.

In my A2, which has my main function, I have the A star search, expanding, and file writing/reading. It also has the calculation of the three heuristics.

         NODE EXPANDED         
      Euclidean  Canberra  Knight   
Input1    the # of nodes expanded         
Input2             
Input3             

Optimal Path Solution:
Input1: The optional node moves
Input2: 
Input3: 
