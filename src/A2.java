import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;


public class A2 {

	//enum
	private static enum AStar {EUCLIDEAN, CANBERRA, KNIGHT};

	//input and output
	private static Scanner sc = null;
	private static PrintStream ps = null;
	private static String input = "input.txt";
	private static String output = "output.txt";


	//used instead of hash since I am using Coordinate to access, need x and y rather than object
	private static ArrayList<Grid[][]> grid = new ArrayList<Grid[][]>();
	private static int aX[] = new int[3];
	private static int aY[] = new int[3];
	private static int bX[] = new int[3];
	private static int bY[] = new int[3];
	private static int size[] = new int[3];
	private static int nodeExpanded[] = new int[9];

	public A2(){
	}

	public static void main(String[] args) throws Exception{
		//Read in a file and set the size and initial positions of pieces
		sc = new Scanner(new BufferedReader(new FileReader(input)));

		try{
			size[0] = sc.nextInt();
			aX[0] = sc.nextInt();
			aY[0]= sc.nextInt();
			bX[0]= sc.nextInt();
			bY[0]= sc.nextInt();

			size[1] = sc.nextInt();
			aX[1] = sc.nextInt();
			aY[1]= sc.nextInt();
			bX[1]= sc.nextInt();
			bY[1]= sc.nextInt();

			size[2] = sc.nextInt();
			aX[2] = sc.nextInt();
			aY[2]= sc.nextInt();
			bX[2]= sc.nextInt();
			bY[2]= sc.nextInt();

			sc.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}

		//set up file writing
		try{
			File file = new File(output);
			if (!file.exists()) {
				file.createNewFile();
			}
			ps = new PrintStream(new FileOutputStream(output));
		}
		catch(Exception e){
			e.printStackTrace();
		}


		//Start the search
		for(int i = 0; i < 3; i++){
			grid.add(new Grid[size[i] + 1][size[i] + 1]);
		}
		State end[] = new State[9];

		for(int i = 0; i < 9; i++)
			end[i] = AStarSearch(new State(new Coordinate(aX[i/3],aY[i/3]), new Coordinate(bX[i/3],bY[i/3]), 0, new ArrayList<Coordinate>(), 0), (i/3), i);

		ps.println("         NODE EXPANDED         ");
		ps.println("      Euclidean  Canberra  Knight   ");
		for(int i = 0; i < 3; i++){
			ps.print("Input" + (i+1) + "    ");
			for(int j = 0; j < 3; j++){
				if(end[j+i*3] == null){
					try{ // there's no solution
						if(j == 2){
							ps.println("-         ");	
						}
						else
							ps.print("-         ");
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
				else{
					try{
						if(j == 2){
							ps.println(nodeExpanded[j+i*3] + "         ");	
						}
						else
							ps.print(nodeExpanded[j+i*3] + "         ");
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}

			}	
		}
		ps.println("\nOptimal Path Solution:");
		for(int i = 0; i < 3; i++){
			int opt = 0;
			ps.print("Input" + (i+1) + ": ");
			if(nodeExpanded[i * 3] < nodeExpanded[i * 3 + 1])
				opt = i * 3;
			else
				opt = i * 3 + 1;
			if(nodeExpanded[i * 3] < nodeExpanded[i * 3 + 2])
				opt = i * 3;
			else
				opt = i * 3 + 2;
			if(nodeExpanded[opt] == -1){
				ps.print("-1\n");
				continue;
			}
			else{
				if(i == 1){
				for(int q = 0; q < end[5].getSteps().size(); q++){
					System.out.println("(" + end[5].getSteps().get(q).getX() + ", " + end[5].getSteps().get(q).getY() + ")");
				}}
				for(int j = 1;j < end[opt].getSteps().size();j++){
					ps.print("(" + end[opt].getSteps().get(j).getX() + ", " + end[opt].getSteps().get(j).getY() + ")");
					if(j == end[opt].getSteps().size() - 1)
						ps.print("\n");
				}
			}
		}

	}

	public static State AStarSearch(State root, int inputNum, int methodNum){
		for(int i = 0; i < size[inputNum] + 1; i++){
			for(int j = 0; j < size[inputNum] + 1; j++){ 
				if(grid.get(inputNum)[i][j] != null)
					grid.get(inputNum)[i][j] = null;
			}
		}
		grid.get(inputNum)[root.getA().getX()][root.getA().getY()] = new Grid(root,0);
		Comparator<State> comparator = new Comparator<State>(){
			public int compare(State a, State b){
				if(a.getCost() > b.getCost())
					return 1;
				else if(a.getCost() == b.getCost())
					return 0;
				else
					return -1;
			}

		};
		PriorityQueue <State> open = new PriorityQueue<State>(10, comparator);
		open.add(root);
		AStar selection = AStar.EUCLIDEAN;
		if(methodNum%3 == 0)
			selection = AStar.EUCLIDEAN;
		else if(methodNum%3 == 1)
			selection = AStar.CANBERRA;
		else if(methodNum%3 == 2)
			selection = AStar.KNIGHT;
		else
			selection = AStar.EUCLIDEAN;
		int nodeEx = 0;
		do{
			if(open.isEmpty()){
				nodeExpanded[methodNum] = -1;
				return null;
			}
			State current = open.poll();
			if(current.goalCheck()){
				return current;
			}
			ArrayList<State> children = expand(current, selection, inputNum, methodNum,nodeEx);
			for(State child:children){
				if(grid.get(inputNum)[child.getA().getX()][child.getA().getY()] == null){
					open.add(child);
					grid.get(inputNum)[child.getA().getX()][child.getA().getY()] = new Grid(child,child.getCost());
				}
				else if(child.getCost() < grid.get(inputNum)[child.getA().getX()][child.getA().getY()].getCost()){
					open.remove(grid.get(inputNum)[child.getA().getX()][child.getA().getY()].getState());
					open.add(child);

					grid.get(inputNum)[child.getA().getX()][child.getA().getY()] = new Grid(child,child.getCost());
				}				
			}	
		}
		while(true);
	}


	public static ArrayList<State> expand(State s, AStar method,int inputNum, int methodNum, int nodeEx){
		ArrayList<State> children = new ArrayList<State>();
		if(s.getA().getX() > 1 && s.getB().getY() < size[inputNum]){ // A move up, B move right
			State temp = new State(s);
			temp.getA().setX(temp.getA().getX() - 1);
			temp.getB().setY(temp.getB().getY() + 1);
			if(method == AStar.EUCLIDEAN)
				temp.setCost(s.getSteps().size() + calculateEuclidean(temp));
			else if(method == AStar.CANBERRA)
				temp.setCost(s.getSteps().size() + calculateCanberra(temp));
			else
				temp.setCost(s.getSteps().size() + calculateKnight(temp, inputNum));
			temp.getSteps().add(new Coordinate(temp.getA().getX(),temp.getA().getY()));
			children.add(temp);
		}
		if(s.getA().getY() > 1 && s.getB().getX() > 1){ // A move left, B move up
			State temp = new State(s);
			temp.getA().setY(temp.getA().getY() - 1);
			temp.getB().setX(temp.getB().getX() - 1);
			if(method == AStar.EUCLIDEAN)
				temp.setCost(s.getSteps().size() + calculateEuclidean(temp));
			else if(method == AStar.CANBERRA)
				temp.setCost(s.getSteps().size() + calculateCanberra(temp));
			else
				temp.setCost(s.getSteps().size() + calculateKnight(temp, inputNum));
			temp.getSteps().add(new Coordinate(temp.getA().getX(),temp.getA().getY()));
			children.add(temp);	
		}
		if(s.getA().getY() < size[inputNum] && s.getB().getX() < size[inputNum] ){ // A move right, B move down
			State temp = new State(s);
			temp.getA().setY(temp.getA().getY() + 1);
			temp.getB().setX(temp.getB().getX() + 1);
			if(method == AStar.EUCLIDEAN)
				temp.setCost(s.getSteps().size() + calculateEuclidean(temp));
			else if(method == AStar.CANBERRA)
				temp.setCost(s.getSteps().size() + calculateCanberra(temp));
			else
				temp.setCost(s.getSteps().size() + calculateKnight(temp, inputNum));
			temp.getSteps().add(new Coordinate(temp.getA().getX(),temp.getA().getY()));
			children.add(temp);
		}
		if(s.getA().getX() < size[inputNum] && s.getB().getY() > 1){ // A move down, B move left
			State temp = new State(s);
			temp.getA().setX(temp.getA().getX() + 1);
			temp.getB().setY(temp.getB().getY() - 1);
			if(method == AStar.EUCLIDEAN)
				temp.setCost(s.getSteps().size() + calculateEuclidean(temp));
			else if(method == AStar.CANBERRA)
				temp.setCost(s.getSteps().size() + calculateCanberra(temp));
			else
				temp.setCost(s.getSteps().size() + calculateKnight(temp, inputNum));
			temp.getSteps().add(new Coordinate(temp.getA().getX(),temp.getA().getY()));
			children.add(temp);
		}
		if(children.size() > 0){
			nodeExpanded[methodNum]++;
		}
		return children;
	}

	public static double calculateEuclidean(State s){

		//System.out.println(Math.sqrt(Math.pow(s.getA().getX() - s.getB().getX(),2) + Math.pow(s.getA().getY() - s.getB().getY(), 2)));
		return Math.sqrt((double)Math.pow(s.getA().getX() - s.getB().getX(),2) + (double)Math.pow(s.getA().getY() - s.getB().getY(), 2));
	}

	public static double calculateCanberra(State s){
		return (((double)Math.abs(s.getA().getX() - s.getB().getX()) / ((double)Math.abs(s.getA().getX()) + (double)Math.abs(s.getB().getX())))
				+ ((double)Math.abs(s.getA().getY() - s.getB().getY()) / ((double)Math.abs(s.getA().getY()) + (double)Math.abs(s.getB().getY()))));
	}

	public static double calculateKnight(State knight_s, int inputNum){
		State s= new State(knight_s);
		s.setCost(0);
		boolean[][] visited = new boolean[size[inputNum]+1][size[inputNum]+1];
		ArrayList<State> knights = new ArrayList<State>();
		knights.add(s);
		visited[s.getA().getX()][s.getA().getY()] = true;

		while(!knights.isEmpty()){
			State current = knights.remove(0);
			if(current.goalCheck())
				return current.getCost();
			for(int i = 0; i < 8; i++){
				State temp = new State(current);
				switch(i){
				case 0: 
					temp.getA().setX(temp.getA().getX() - 1);
					temp.getA().setY(temp.getA().getY() - 2);
					break;
				case 1:
					temp.getA().setX(temp.getA().getX() - 2);
					temp.getA().setY(temp.getA().getY() - 1);
					break;
				case 2:
					temp.getA().setX(temp.getA().getX() - 1);
					temp.getA().setY(temp.getA().getY() + 2);
					break;
				case 3:
					temp.getA().setX(temp.getA().getX() - 2);
					temp.getA().setY(temp.getA().getY() + 1);
					break;
				case 4:
					temp.getA().setX(temp.getA().getX() + 1);
					temp.getA().setY(temp.getA().getY() - 2);
					break;
				case 5:
					temp.getA().setX(temp.getA().getX() + 2);
					temp.getA().setY(temp.getA().getY() - 1);
					break;
				case 6:
					temp.getA().setX(temp.getA().getX() + 1);
					temp.getA().setY(temp.getA().getY() + 2);
					break;
				case 7:
					temp.getA().setX(temp.getA().getX() + 2);
					temp.getA().setY(temp.getA().getY() + 1);
					break;
				}
				temp.setCost(current.getCost() + 1);
				if(temp.getA().getX() > 0 && temp.getA().getX() <= size[inputNum] && temp.getA().getY() > 0 && temp.getA().getY() <= size[inputNum]){
					if(visited[temp.getA().getX()][temp.getA().getY()])
						continue;
					visited[temp.getA().getX()][temp.getA().getY()] = true;
					knights.add(temp);
				}
			}
		}
		return -1;
	}
}


