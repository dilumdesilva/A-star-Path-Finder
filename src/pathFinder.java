
import java.awt.Component;
import java.io.File;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.objects.NativeArray.map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author dilumdesilva
 */
public class pathFinder {
    
    
    private static final int windowSize = 700;
    static int N;
    static cell[][] grid = new cell[20][20];
    static boolean[][] closed = new boolean[20][20];
    static PriorityQueue open;
    static cell startingCell;
    static cell endingCell;
    static boolean startSelected = false;
    static boolean endSelected = false;

    private static int[][] mapGrid = {
        // {1,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20}
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1},//01
        {4, 4, 1, 4, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1},//02
        {4, 4, 4, 4, 4, 4, 1, 1, 2, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1},//03
        {4, 4, 4, 4, 4, 4, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1},//04
        {1, 1, 4, 1, 1, 1, 1, 1, 2, 2, 3, 3, 3, 2, 1, 1, 2, 2, 1, 1},//05
        {1, 4, 4, 1, 2, 2, 1, 1, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 1, 1},//06
        {4, 2, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1},//07
        {1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4},//08
        {1, 1, 2, 3, 3, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4},//09
        {1, 2, 3, 3, 3, 3, 2, 2, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1},//10
        {1, 2, 3, 2, 2, 2, 3, 2, 4, 1, 1, 1, 4, 4, 4, 4, 2, 1, 1, 1},//11
        {1, 2, 2, 1, 1, 1, 4, 4, 4, 4, 1, 1, 4, 4, 4, 1, 1, 1, 1, 1},//12
        {1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},//13
        {4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 5},//14
        {1, 1, 4, 4, 4, 4, 1, 1, 1, 2, 2, 5, 5, 1, 1, 1, 1, 1, 1, 5},//15
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 5, 5, 5, 1, 1, 5, 5, 5},//16
        {1, 2, 2, 2, 2, 2, 1, 1, 1, 2, 1, 1, 1, 5, 5, 5, 5, 5, 5, 5},//17
        {2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 5},//18
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5},//19
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5},//20 
    };

    private static void run() {

        boolean[][] visited = new boolean[20][20];
        
        // Proirity queue to get the cell with least weight
        open = new PriorityQueue(new Comparator() {
            @Override
            public int compare(Object object1, Object object2) {
                cell cell1 = (cell) object1;
                cell cell2 = (cell) object2;
                
                double cost1 = cell1.fullCost;
                double cost2 = cell2.fullCost;
                
                if (cost1<cost2) {        // Cell 1 is less than Cell 2
                    return -1;
                } else if (cost1>cost2) { // Cell 2 is less than Cell 1
                    return 1;
                } else {                                        // Both cells are equal so the order doesn'mouseInteractionThread matter
                    return 0;
                }
            }
        });

        //------------------------------------------------------------
        //add the starting cell onto the priority queue
        open.add(startingCell);
        cell currentCell = (cell) open.remove();
        //Check if we r in the end cell
        boolean pathNotFound = false;
        while (!(currentCell.x == endingCell.x && currentCell.y == endingCell.y)) {
            visited[currentCell.x][currentCell.y] = true;

            int x = currentCell.x;
            int y = currentCell.y;

            addCell(x - 1, y - 1, currentCell);
            addCell(x - 1, y, currentCell);
            addCell(x - 1, y + 1, currentCell);
            
            addCell(x, y - 1, currentCell);
            addCell(x, y + 1, currentCell);
            
            addCell(x + 1, y - 1, currentCell);
            addCell(x + 1, y, currentCell);
            addCell(x + 1, y + 1, currentCell);

            //visited[currentCell.y][currentCell.x] = true; // current cell is now visited
            boolean proceed = false;
            while (!proceed) {
                try {
                    currentCell = (cell) open.remove();
                } catch (NoSuchElementException e) {
                    pathNotFound = true;
                }
                if (!visited[currentCell.x][currentCell.y]) {
                    proceed = true;
                }

            }

        }
        cell pathCell = currentCell;
        System.out.println("Total Cost : "+ pathCell.totTravelCost);
        double prea = pathCell.x + 0.5;
        double preb = N - pathCell.y - 0.5;
        
                        System.out.println("Path Started");

        
        while (pathCell != null) {
            int a = pathCell.x;
            int b = pathCell.y;

            /*
            StdDraw.setPenColor(StdDraw.ORANGE);
            StdDraw.filledSquare(a + 0.5, N - b - 0.5, .5);
            StdDraw.setPenColor(StdDraw.BOOK_BLUE);
            StdDraw.square(a + 0.5, N - b - 0.5, .5);
            //*/
            StdDraw.setPenRadius(0.008);
            
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(prea, preb, a + 0.5, N - b - 0.5);
            StdDraw.setPenRadius(0.002);
            pathCell = pathCell.parentCell;
            prea = a + 0.5;
            preb = N - b - 0.5;
            if (pathCell != null) {
                System.out.println(" (" + pathCell.x + " " + pathCell.y + ")" +" (" + endingCell.x + " " + endingCell.y + ") : " +pathCell.h_cost + " - "+pathCell.totTravelCost);
                //System.out.println("Visited Cell : ("+ pathCell.x +":"+ pathCell.y+")" + pathCell.totTravelCost);
            }
        }
        /*
        Cell c1 = new Cell(2, 3, 16);
        Cell c2 = new Cell(3, 20, 13);
        Cell c3 = new Cell(3, 20, 15);
        c3.totalCost(null);
        c1.totalCost(c2);
        c2.totalCost(c3);
        open.add(c1);

        open.add(c2);
        System.out.println("Cost of C1 : " + c1.totTravelCost);
        System.out.println("Cost of C2 : " + c2.totTravelCost);
        Cell cr1 = (Cell) open.remove();
        System.out.println(cr1.totTravelCost);
        cr1 = (Cell) open.remove();
        System.out.println(cr1.totTravelCost);
         */
    }

    public static void addCell(int x, int y, cell parent) {

        try {
            if (mapGrid[y][x] != 5) {
                cell newCell = new cell(x, y, mapGrid[y][x]);
                newCell.totalCost(parent);
                //System.out.println("Cell : ("+ newCell.x +":"+ newCell.y+")" + newCell.totTravelCost);
                open.add(newCell);
                
                StdDraw.setPenColor(StdDraw.CYAN);
                StdDraw.filledSquare(x + 0.5, N - y - 0.5, .25);
                StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                StdDraw.square(x + 0.5, N - y - 0.5, .5);
                
            }
        } catch (IndexOutOfBoundsException e) {

        }
    }
    
    /**
     * 
     * @param userSelectedMapType 
     */
    public  static void pathFinderMain(int userSelectedMapType){

        //to capture the size of the mapGrid array
        //N = 20 
        N = mapGrid.length;
        
        //to set the size of the mapGrid
        StdDraw.setCanvasSize(windowSize, windowSize); 
        
        //to generate 20 by 20 mapGrid here I am setting the y and x axis of the grid
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
        
        try {
            
            Thread mouseInteractionThread = new Thread(new mouseInteraction());
            mouseInteractionThread.setDaemon(true);
            mouseInteractionThread.start();
            
        } catch (IllegalThreadStateException e) {
            System.out.println(e);
        }
    }

    public static void generateMapGrid() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                switch (mapGrid[i][j]) {
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
                StdDraw.filledSquare(j + 0.5, N - i - 0.5, .5);
                
                // #C3AED6 applying the purple color to the mapGrip cell borders
                StdDraw.setPenColor(195,174,214);
                StdDraw.square(j + 0.5, N - i - 0.5, .5);

            }
        }          
    }
    
    
    public  static void generateMap(){
        String mapPath = "src/images/map.png";
          int middlePoint = N/2;
          int mapWidth = N;
          
        StdDraw.picture(middlePoint, middlePoint, mapPath, mapWidth, mapWidth);
    }

    /**
     * 
     * @param x
     * @param y 
     */  
    public static void captureMouseClick(double x, double y) {
       
        //once user pressed on the grid to mark start and end points this method will fire to capture those points.
        
        //since coordinates have decimal points here I am casting them to integers for further steps
        int x_coordinates = (int) x;
        int y_coordinates = (int) y;
        
        System.out.println("");
        System.out.println(customColors.custom_GREEN+"Captured x coordinates = " + x +" --> "+x_coordinates + customColors.custom_RESET);
        System.out.println(customColors.custom_GREEN+"Captured y coordinates = " + y +" --> "+y_coordinates + customColors.custom_RESET);

         //to display a warning msg, if user clicks on a invlid area 
        if (mapGrid[y_coordinates][x_coordinates] == 5) {
            Component frame = null;
     
            JOptionPane.showMessageDialog(frame,"Sorry invalid area poniter\n"
                    + "You can't start or end in this area");
        } else {
            
            //starting cell related stuff
            if (startingCell == null) {
                //setting the starting cell coordinates by creating a new cell
                startingCell = new cell(x_coordinates, y_coordinates, 0);
                
                //here I am setting the colour of the starting pointer square to green
                StdDraw.setPenColor(StdDraw.GREEN);
                StdDraw.filledSquare(x_coordinates + 0.5, N - y_coordinates - 0.5, .5);
                //here I am setting the colour of the starting pointer square border to black
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.square(x_coordinates + 0.5, N - y_coordinates - 0.5, .5);
                
                System.out.println("Cell : (" + startingCell.x + ":" + startingCell.y + ")" + startingCell.totTravelCost);
                System.out.println("");

            } else if (endingCell == null) {
              //ending cell related 
                
                //setting the ending cell coordinates by creating a new cell
                endingCell = new cell(x_coordinates, y_coordinates, mapGrid[y_coordinates][x_coordinates]);
                
                //to track the ending cell for further calculations
                cell.endCell = endingCell;
                
                //here I am setting the colour of the ending pointer square to red
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.filledSquare(x_coordinates + 0.5, N - y_coordinates - 0.5, .5);
                
                //here I am setting the colour of the ending pointer square border to black
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.square(x_coordinates + 0.5, N - y_coordinates - 0.5, .5);
                
                System.out.println("Cell : (" + endingCell.x + ":" + endingCell.y + ")" + endingCell.totTravelCost);
                System.out.println("");
                
                //System.out.println("Ending cell cost : " + endingCell.cost);
                
                run();
            }
        }
    }
}
