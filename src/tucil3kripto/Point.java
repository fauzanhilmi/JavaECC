/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tucil3kripto;

/**
 *
 * @author Riady
 */
public class Point {
    public long x,y;
    Point(){
    
    }
    Point(long _x,long _y){
        x=_x;
        y=_y;
    }
    
    public boolean isInfinity(){
        return (x==Long.MAX_VALUE && y==Long.MAX_VALUE);
    }
    
    public void makeInfinity(){
        x = Long.MAX_VALUE;
        y = Long.MAX_VALUE;
    }
}
