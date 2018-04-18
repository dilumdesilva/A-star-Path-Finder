/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author dilumdesilva
 */
public class cell {
    static cell endCell;
    static boolean startCell = true;
    int x, y;           // X and Y coordinates
    int cost;           // Cost to travel into this cell
    int totTravelCost;  // Total travel cost
    double fullCost;
    double h_cost;         // Distance to the end cell
    cell parentCell;        // The cell visited this cell from

    public cell(int x, int y, int cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
    }

    public void totalCost(cell parent) {

        if (parent != null) {
            this.parentCell = parent;
            this.totTravelCost = this.cost + parent.totTravelCost;
        } else {
            this.totTravelCost = 0;
        }
        
        
        this.h_cost = Math.sqrt(Math.pow((this.x - endCell.x), 2) + Math.pow((this.y - endCell.y), 2));
        
        System.out.println(" (" + this.x + " " + this.y + ")" +" (" + endCell.x + " " + endCell.y + ") : " +this.h_cost + " - "+this.totTravelCost);
        this.fullCost = totTravelCost + parent.h_cost;
        
        
        //this.h_cost = Math.abs(endCell.x - this.x) + Math.abs(endCell.y - this.y);
        //System.out.println(this.h_cost);

    }
}
