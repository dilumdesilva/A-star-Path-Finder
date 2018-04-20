
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.objects.NativeArray.map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and cellCostQueue the template in the editor.
 */


/**
 *
 * @author dilumdesilva 2016142 || w1626637
 */
public class PathFinder {

    //to set the canvas gridSize to the stdDaraw 
    private static final int canvasSize = 700;
    static int N;
    private static Cell startingCell,endingCell;
    private static int[][] map;
    
    private static char dMetricsType = 'N';
    
    
    
    
    /**
     * @param x - x_coordinates of the user pressed cell
     * @param y - y_coordinates of the user pressed cell
     */
    public static void mouseClicked(double x, double y) {
        //once user pressed on the grid to mark start and end points this method will fire to capture those points.
        
        //since coordinates have decimal points here I am casting them to integers for further steps
        int x_coordinates = (int) x;
        int y_coordinates = (int) y;
        
        //to display a warning msg, if user clicks on a invlid area 
        if (map[y_coordinates][x_coordinates] == 5) {
            Component frame = null;
            
            JOptionPane.showMessageDialog(frame,"Sorry invalid area poniter\n"
                    + "You can't start or end in this area");
            
        } else {
            //starting Cell related stuff
            if (startingCell == null) {
                //setting the starting Cell coordinates by creating a new Cell
                startingCell = new Cell(x_coordinates, y_coordinates, 0);
                
                //here I am setting the colour of the starting pointer to green
                StdDraw.setPenColor(StdDraw.GREEN);
                StdDraw.filledCircle(x_coordinates + 0.5, N - y_coordinates - 0.5, .5);
                
                //here I am setting the colour of the starting pointer border to black
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.circle(x_coordinates + 0.5, N - y_coordinates - 0.5, .45);
                
                System.out.println("");
                System.out.println(CustomColors.custom_GREEN+"Captured x coordinates of the startingcell = " + x +" --> "+x_coordinates + CustomColors.custom_RESET);
                System.out.println(CustomColors.custom_GREEN+"Captured y coordinates of the startingcell= " + y +" --> "+y_coordinates + CustomColors.custom_RESET);
                
                System.out.println(CustomColors.custom_GREEN+"Travel cost of the starting cell : "+startingCell.totTravelCost + CustomColors.custom_RESET);

            } else if (endingCell == null) {
                //ending Cell related stuff
                
                //setting the ending Cell coordinates by creating a new Cell
                endingCell = new Cell(x_coordinates, y_coordinates, map[y_coordinates][x_coordinates]);
                
                //here I am setting the colour of the ending pointer to red
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.filledCircle(x_coordinates + 0.5, N - y_coordinates - 0.5, .5);
                
                //here I am setting the colour of the ending pointer border to black
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.circle(x_coordinates + 0.5, N - y_coordinates - 0.5, .45);
                
                System.out.println("");
                System.out.println(CustomColors.custom_RED+"Captured x coordinates of the endingcell= " + x +" --> "+x_coordinates + CustomColors.custom_RESET);
                System.out.println(CustomColors.custom_RED+"Captured y coordinates of the endingcell= " + y +" --> "+y_coordinates + CustomColors.custom_RESET);
                System.out.println(CustomColors.custom_RED+"Travel cost of the ending cell : "+endingCell.totTravelCost + CustomColors.custom_RESET);
                
                System.out.println("");
                
                
                alogorithmGenerator();
            }
        }
    }

    /**
     * Finds and draws the easiest pathHolder (Path with lowest cost) using the given heuristic and the mapCostHolder
     */
    private static void alogorithmGenerator() {
        //sendiing the selected pathHolder as a parameter
        AstarBuilder astarBuilder = new AstarBuilder(map);
        
        ArrayList<Cell> pathHolder = new ArrayList<>();
        
        switch(getdMetricsType()){
            case 'M':
                pathHolder = astarBuilder.generatePath(startingCell, endingCell, DistanceMetricsType.MANHATTEN);
                System.out.println("User has selcted MANHATTEN distance metrics");
                break;
            case 'E':
                pathHolder = astarBuilder.generatePath(startingCell, endingCell, DistanceMetricsType.EUCLIDEAN);
                System.out.println("User has selcted EUCLIDEAN distance metrics");
                break;
            case 'C':
                pathHolder = astarBuilder.generatePath(startingCell, endingCell, DistanceMetricsType.CHEBYSHEV);
                System.out.println("User has selcted CHEBYSHEV distance metrics");
                break;
            case 'N':
                pathHolder = astarBuilder.generatePath(startingCell, endingCell, DistanceMetricsType.NONE);
                System.out.println("User has selcted no distance metrics");
                break;
                
        }
        
        //ArrayList<Cell> pathHolder = astarBuilder.generatePath(startingCell, endingCell, DistanceMetricsType.MANHATTEN);
        
        
        Cell pathCell;
        pathCell = pathHolder.get(0);
        double prea = pathCell.x + 0.5;
        double preb = N - pathCell.y - 0.5;
        
        System.out.println("");
        System.out.println(CustomColors.custom_BLUE + "Summary of the path related cells"+ CustomColors.custom_RESET);
        System.out.println(CustomColors.custom_BLUE +"---------------------------------"+ CustomColors.custom_RESET);
        
        for (int i = 1; i < pathHolder.size(); i++) {
            pathCell = pathHolder.get(i);
            int a = pathCell.x;
            int b = pathCell.y;
            StdDraw.setPenRadius(0.008);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(prea, preb, a + 0.5, N - b - 0.5);
            StdDraw.setPenRadius(0.002);
            pathCell = pathCell.parent;
            prea = a + 0.5;
            preb = N - b - 0.5;
            if (pathCell != null) {
                System.out.println(" Current Cell: (" + pathCell.x + " " + pathCell.y + ")" 
                        +"\n"
                        +" End Cell: (" + endingCell.x + " " + endingCell.y + ")"
                        +"\n"
                        + CustomColors.custom_PURPLE+" Heuristic Cost: " + pathCell.h_cost + CustomColors.custom_RESET
                        +"\n"
                        + CustomColors.custom_PURPLE+" Travel Cost: " + pathCell.totTravelCost + CustomColors.custom_RESET);
                
                System.out.println("");
            }
        }
    }

     
//    public static void main(String[] args) {
//          pathFindertMain(2,1,'M');
//        
//    }
    
    
    public void pathFindertMain(int userSelectedMapType, int userSelectedMapSize, char distanceMetricsType ){
        
        //mapSize 20 - twenty by twenty map
        //mapSize 40 - fourty by fourty map
        //mapSize 80 - eighty by eighty map
        
            
        //to capture the size of the mapGrid array according to what user has selected
        map = MapGenerator.getMap(userSelectedMapSize);
        N = map.length;
        
        //to generate the mapGrid here I am setting the y and x axis of the grid
        StdDraw.setCanvasSize(canvasSize, canvasSize);
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(0, N);
        
        
        //the method which generates the mapGrid 
        switch(userSelectedMapType){
            
            //mapType 1 - pictureMap
            //mapType 2 - gridMap

            case 1:
                generateMap();
                System.out.println("Picture Map Generated");
                break;
            case 2:
                generateMapGrid();
                System.out.println("Grid Map Generated");
                break;
        }
        
        switch(distanceMetricsType){
            case 'M':
                setdMetricsType('M');
                //System.out.println("User has selcted MANHATTEN distance metrics");
                break;
            case 'E':
                setdMetricsType('E');
                //System.out.println("User has selcted EUCLIDEAN distance metrics");
                break;
            case 'C':
                setdMetricsType('C');
                //System.out.println("User has selcted CHEBYSHEV distance metrics");
                break;
                
            case 'N':
                setdMetricsType('N');
                break;
            
        }
        
        try{
            Thread mouseInteractionThread = new Thread(new MouseInteraction(N));
            mouseInteractionThread.setDaemon(true);
            mouseInteractionThread.start();
        }catch (IllegalThreadStateException e) {
            System.out.println(e);
        }
        
    
    }
    
    private static void generateMap() {
        
        String mapPath = "src/images/map.png";
          int middlePoint = N/2;
          int mapWidth = N;
          
        StdDraw.picture(middlePoint, middlePoint, mapPath, mapWidth, mapWidth);
    }

    private static void generateMapGrid() {


        //StdDraw.picture(N/2, N/2, "mapCostHolder.png", N, N);
        //*
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                switch (map[i][j]) {
                    case 1://to draw white shade of gray cells which has weight of 1
                        StdDraw.setPenColor(StdDraw.WHITE);
                        break;
                    case 2://to draw lightest shade of gray cells which has weight of 2
                        StdDraw.setPenColor(200, 200, 200);

                        break;
                    case 3://to draw middle shade of gray cells which has weight of 3
                        StdDraw.setPenColor(150, 150, 150);

                        break;
                    case 4://to draw darkest shade of gray cells which has weight of 4
                        StdDraw.setPenColor(100, 100, 100);

                        break;
                    case 5://to draw blacky cells which represent the sea area in the map
                        StdDraw.setPenColor(StdDraw.BLACK);

                        break;
                }
                StdDraw.filledSquare(j + 0.5, N - i - 0.5, .50);
                
                // #C3AED6 applying the purple color to the mapGrip Cell borders
                StdDraw.setPenColor(195,174,214);
                StdDraw.square(j + 0.5, N - i - 0.5, .50);

            }
        }
    }

  
    /**
     * @return the dMetricsType
     */
    public static char getdMetricsType() {
        return dMetricsType;
    }
    
    /**
     * @param dMetricsType the dMetricsType to set
     */
    public void setdMetricsType(char dMetricsType) {
        this.dMetricsType = dMetricsType;
    }
    
}

/**
 * 
 * @author dilumdesilva 2016142 || w1626637
 
 this class holds the astar algorithm logic related functions
 so if using this class we can find the pathHolder according to astar algorithm
 in the same way we can check the other algorithms by implementing classes like this.
 */


class AstarBuilder{
    
    
    
    //to dimensional array to hold the costs 
    private int[][] mapCostHolder;  
    //to track the ending cell
    private Cell endingCell;
    //to track the heuristic type
    private DistanceMetricsType heuristic;
    //to keep track of visited cells
    private static boolean [][] visitedCells; 
    
    int gridSize;
    
    
   
    /**
     * implemented a priority queue with the compare method from its super class.
     * using this queue's compare method, y_coordinates am taking the cells ordered by the cost (lowest to highest).
     */
    static PriorityQueue cellCostQueue= new PriorityQueue(new Comparator() {
            @Override
            public int compare(Object obj1, Object obj2) {
                
                //here I am assigning the two cell objs which was passed to the the queue's compare method while visiting the nodes.
                Cell cell1 = (Cell) obj1;
                Cell cell2 = (Cell) obj2;
                
                //using the captured cell objs, I am getting the cost and store it variables for further comparision 
                double cell1Cost = cell1.totTravelCost + cell1.h_cost;
                double cell2Cost = cell2.totTravelCost + cell2.h_cost;
                
                /**
                 * Here I am checking for 
                 * Whether 
                 *  cell1 cost is less than cell2 cost : will return -1
                 *  or 
                 *  cell2 cost is greater than cell2 cos : will return 1
                 *  
                 * if the both cell cost are equal will return 0
                 */
                if (cell1Cost<cell2Cost)     
                    return -1;
                else if (cell1Cost>cell2Cost) 
                    return 1;
                else                                       
                    return 0;
                
            }
        });

    //constrctor of the astar class but !default
    public AstarBuilder(int[][] map){
        // to get the grid size of user selected mapCostHolder (20*20 || 40*40) 
        gridSize = map.length;
        
        //initialize a visted cell array 
        visitedCells = new boolean[gridSize][gridSize];
        this.mapCostHolder = map;
        
    }

    /**
     * 
     * @param startingCell - to track of the starting cell
     * @param endingCell - to track of the ending cell
     * @param heuristic - to track of the distance metrics type for the heuristic calculations
     * @return arraylist which holds the pathHolder
     */
    
    long startTime = System.nanoTime();
    public ArrayList<Cell> generatePath( Cell startingCell, Cell endingCell, DistanceMetricsType heuristic){
        this.endingCell = endingCell;
        this.heuristic = heuristic;
        
        //an arraylist to hold the pathHolder related cells
        ArrayList<Cell> pathHolder = new ArrayList<>();
        //adding the current cell tot the priority queue
        cellCostQueue.add(startingCell);
        
        //will store in the recent cell variable retrieved and removed the head/cell of this queue
        Cell recentCell = (Cell) cellCostQueue.remove();
        
        
        //this loop will run til it finds the last cell 
        while (!(recentCell.x == endingCell.x && recentCell.y == endingCell.y)) {
            visitedCells[recentCell.x][recentCell.y] = true;
            int x_coordinates = recentCell.x;
            int y_coordinates = recentCell.y;
            
            int left = x_coordinates-1;
            int right = x_coordinates+1;
            int up = y_coordinates-1;
            int down = y_coordinates+1;
            
            addCell(left,up,     recentCell , TravelType.D);
            addCell(left,y_coordinates,      recentCell , TravelType.S);
            addCell(left,down,   recentCell , TravelType.D);
            
            addCell(x_coordinates,up,        recentCell , TravelType.S);
            addCell(x_coordinates,down,      recentCell , TravelType.S);
            
            addCell(right,up,    recentCell , TravelType.D);
            addCell(right,y_coordinates,     recentCell , TravelType.S);
            addCell(right,down,  recentCell , TravelType.D);
            
            /**
             * Flag will become true when it hasn't visited the recent cell 
             * Until it visits the it will retrieved and removed the head/cell
             * If it couldn't find a head/cell or if the queue is empty it will throws a NoSuchElement Exception 
             */
            
            boolean flag = false;
            
            while (!flag) {
                try {
                    recentCell = (Cell) cellCostQueue.remove();
                } catch (NoSuchElementException e) {
                    System.out.println(e + "cellCostQueue is empty");
                }
                
                //to keep track whether this recent is visted before or not
                if (!visitedCells[recentCell.x][recentCell.y]) {
                    flag = true;
                }
            }
        }
        
        long finalTime = System.nanoTime();
        System.out.println("Final cost: "+recentCell.totTravelCost);
        
        
        //keep track of every valid recent cell as a path cell
        Cell pathCell = recentCell;
        
        //adding path cells to the array list to draw the path 
        while (pathCell != null) {
            pathHolder.add(pathCell);
            
            pathCell = pathCell.parent;
        }
        
        long elapsedTime = (finalTime-startTime);
        long elapsedTimeInMiliS = elapsedTime/1000000;
        
        System.out.println("Elapsed Time: "+elapsedTimeInMiliS+"ms");
        
        //once all the path cells are stored this will return a list of path cells
        return pathHolder;
    }

    private void addCell(int x, int y, Cell parent,TravelType d) {
        int N= gridSize;
        try {
            if (mapCostHolder[y][x] != 5) {
                Cell c = new Cell(x, y, mapCostHolder[y][x]);
                c.totalCost(parent,heuristic,endingCell,d);
                cellCostQueue.add(c);
                
                //uncomment this to draw the visited cell 
//                StdDraw.setPenColor(StdDraw.CYAN);
//                StdDraw.filledSquare(x + 0.5, N - y - 0.5, .25);
//                StdDraw.setPenColor(StdDraw.BOOK_BLUE);
//                StdDraw.square(x + 0.5, N - y - 0.5, .5);
            }
        } catch (IndexOutOfBoundsException e) {
            //System.out.println(e);
        }
    }
}
