
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
 * @author dilumdesilva
 */
public class pathFinder {
   
    //to set the canvas gridSize to the stdDaraw 
    private static final int canvasSize = 700;
    static int N;
    private static Cell startingCell,endingCell;
    private static int[][] map;

    /**
     * Finds and draws the easiest pathHolder (Path with lowest cost) using the given heuristic and the mapCostHolder
     */
    private static void alogorithmGenerator() {
        
        AstarBuilder aStar = new AstarBuilder(map);
        ArrayList<Cell> path = aStar.findPath(startingCell, endingCell, DistanceMetricsType.MANHATTEN);
        Cell pathCell;
        pathCell = path.get(0);
        double prea = pathCell.x + 0.5;
        double preb = N - pathCell.y - 0.5;
        for (int i = 1; i < path.size(); i++) {
            pathCell = path.get(i);
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
                System.out.println(" (" + pathCell.x + " " + pathCell.y + ")" + " (" + endingCell.x + " " + endingCell.y + ") : " + pathCell.h_cost + " - " + pathCell.totTravelCost);
            }
        }
    }


    public static void main(String[] args) {
        map = MapGenerator.getMap(2);
        
        N = map.length;
        StdDraw.setCanvasSize(canvasSize, canvasSize);
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(0, N);
        drawMap();
        Thread t = new Thread(new mouseInteraction(N));
        t.setDaemon(true);
        t.start();
    }

    public static void drawMap() {


        //StdDraw.picture(N/2, N/2, "mapCostHolder.png", N, N);
        //*
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                switch (map[i][j]) {
                    case 1:
                        StdDraw.setPenColor(StdDraw.WHITE);
                        break;
                    case 2:
                        StdDraw.setPenColor(200, 200, 200);

                        break;
                    case 3:
                        StdDraw.setPenColor(150, 150, 150);

                        break;
                    case 4:
                        StdDraw.setPenColor(100, 100, 100);

                        break;
                    case 5:
                        StdDraw.setPenColor(StdDraw.BLACK);

                        break;
                }
                StdDraw.filledSquare(j + 0.5, N - i - 0.5, .50);
                StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                StdDraw.square(j + 0.5, N - i - 0.5, .50);

            }
        }
        //*/
    }

    public static void mouseClicked(double x, double y) {
        int j = (int) x;
        int i = (int) y;
        if (map[i][j] == 5) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Can't start or end here.");
        } else {
            if (startingCell == null) {
                startingCell = new Cell(j, i, 0);
                StdDraw.setPenColor(StdDraw.GREEN);
                StdDraw.filledCircle(j + 0.5, N - i - 0.5, .25);
                StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                StdDraw.circle(j + 0.5, N - i - 0.5, .25);
                System.out.println("Cell : (" + startingCell.x + ":" + startingCell.y + ")" + startingCell.totTravelCost);

            } else if (endingCell == null) {
                endingCell = new Cell(j, i, map[i][j]);
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.filledCircle(j + 0.5, N - i - 0.5, .25);
                StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                StdDraw.circle(j + 0.5, N - i - 0.5, .25);
                System.out.println("Cell : (" + endingCell.x + ":" + endingCell.y + ")" + endingCell.totTravelCost);
                alogorithmGenerator();
            }
        }
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
    
    
   
    /**
     * implemented a priority queue with the compare method from its super class.
     * using this queue's compare method, i am taking the cells ordered by the cost (lowest to highest).
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
        int gridSize = map.length;
        
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
    public ArrayList<Cell> findPath( Cell startingCell, Cell endingCell, DistanceMetricsType heuristic){
        this.endingCell = endingCell;
        this.heuristic = heuristic;
        ArrayList<Cell> pathHolder = new ArrayList<>();
        cellCostQueue.add(startingCell);
        Cell currentCell = (Cell) cellCostQueue.remove();
        
        while (!(currentCell.x == endingCell.x && currentCell.y == endingCell.y)) {
            visitedCells[currentCell.x][currentCell.y] = true;
            int x = currentCell.x;
            int y = currentCell.y;
            
            int left = x-1;
            int right = x+1;
            int up = y-1;
            int down = y+1;
            
            addCell(left,up,     currentCell , TravelType.D);
            addCell(left,y,            currentCell , TravelType.S);
            addCell(left,down,     currentCell , TravelType.D);
            
            addCell(x,up,            currentCell , TravelType.S);
            addCell(x,down,            currentCell , TravelType.S);
            
            addCell(right,up,     currentCell , TravelType.D);
            addCell(right,y,            currentCell , TravelType.S);
            addCell(right,down,     currentCell , TravelType.D);
            
            
            boolean proceed = false;
            while (!proceed) {
                try {
                    currentCell = (Cell) cellCostQueue.remove();
                } catch (NoSuchElementException e) {
                    // TODO : Handel the exception
                }
                if (!visitedCells[currentCell.x][currentCell.y]) {
                    proceed = true;
                }
            }
        }
        System.out.println(currentCell.totTravelCost);
        Cell pathCell = currentCell;
        while (pathCell != null) {
            pathHolder.add(pathCell);
            pathCell = pathCell.parent;
        }
        return pathHolder;
    }

    private void addCell(int x, int y, Cell parent,TravelType d) {

        try {
            if (mapCostHolder[y][x] != 5) {
                Cell c = new Cell(x, y, mapCostHolder[y][x]);
                c.totalCost(parent,heuristic,endingCell,d);
                cellCostQueue.add(c);
            }
        } catch (IndexOutOfBoundsException e) {
            //TODO : Handel the exception
        }
    }
}
