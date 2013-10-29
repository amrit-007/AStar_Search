Name: Minh La
ID: 3545124759
Email: minht@usc.edu

Project Name: Uniform Cost Search - Stepping A onto B!

1. Brief description:
This project is made for USC AI 460. The program uses A star search and the goal is find the steps needed for A to move onto B in a  2-d n-dimension grid using the three heuristics: Euclidean, Canberra, and Knight. When A moves, B moves as well but different. For instance, A move up, B will move left, etc.

The three heuristics are simple. The Euclidean and Canberra only involves an equation. The Knight actually requires a breadth first search algorithm to find the distance of the node from the root. And since the Knight moves in 8 different ways, the method for the Knight is somewhat lengthy. I also had to use more data structures to find the knight's heuristics. I actually did not know there was a link to how to do the knight's heuristic, so I somehow thought of BFS.

The structure of this program is broken down into Coordinates and State. The State contains where A and B is, the cost of the moves, and the list of previous moves. The States are stored into an 2-D array of size [n][n] of object Grid, which contains the State and the cost. The reason I am using the Grid is to store the previous cost, which I can access easily and through this I do not have to use open and closed queue. I can just have one queue that can see if the object is visted or not in the array.

The reason I am using an 2-D array instead of a hash map is because using a hashmap, I could not access the hash using coordinates, such are objects. Since I am making new coordinates for each child, it was not very successful in getting the hash since I has to attempt to get the compare the two different coordinates, while they are the same x and y. I feel like I could structure this better, but I decided to use a 2-D array and used the x and y to access.

Having the 2-D array, I can get the previous cost, thus I will know if the child or the state in the queue is different or not. The state in the 2-D array is always updated since I add/update the 2-D whenever I add a state to the queue.

In my A2, which has my main function, I have the A star search, expanding, and file writing/reading. It also has the calculation of the three heuristics.


2. How to compile:
using aludra/UNIX:
1. unzip the files
2. Use Filezilla or file transfer to put onto aludra/UNIX
3. go to file and call - 
      javac *.java
4. then enter
      java A1 
5. Open the output.txt to see the results.
Results contain:

         NODE EXPANDED         
      Euclidean  Canberra  Knight   
Input1    the # of nodes expanded         
Input2             
Input3             

Optimal Path Solution:
Input1: The optional node moves
Input2: 
Input3: 

One problem I think with my algorithm is that for the Knight's expanded moves, it can be different than the result that others have. The result might be + 1 because of the nature of the cost for the knights. Since knights count by the number of moves, the moves are int. So their cost are int, and when put in the priority queue, if 2 nodes using Knight's heuristics have the same cost (int), they cannot be compared properly. The other two heuristics are compared with double, yet the knight is int. Thus sometimes if there are 2 nodes of the same cost, one of them might go first randomly thus giving the +1.
I hope this is not factored into the result since the priority queue cannot really compare the ints well if they're the same. But if you do decide to take points off, could you explain why you decided to and how you would fix the priority queue comparator (because I am really interested in knowing how to fix it).
Thanks alot! Good luck on grading!

3. My answers to the questions:
 3-1. According to the inputs and outputs, I found that Euclidean > Knights > Canberra. Euclidean is the the most consistent because it always gives the minimal distance between the two objects. Since heuristic gives an estimate of the path, Euclidean gives the minimal distance while the other two tried complicated algorithm to find the heuristics. Thus, I wouls pick Euclidean as my heuristic of choice. However, all 3 of them seem to output the same path, just different # of nodes expanded.