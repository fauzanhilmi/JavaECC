/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tucil3kripto;

import java.math.BigInteger;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author Riady
 */
public class ECC {
    public Point base;
    public long a,b,p;
    public ArrayList<Point> arrP;
    public long k = 10;
    
    public static void main(String[] argv){
        //ECC c = new ECC(1,2,((long)Math.pow(2, 31))-1);
   /*     ECC c = new ECC(9,7,4093);
        c.generate();
        byte b = 4;
        System.out.println(c.decodeKob(c.encodeKob(b)));*/
      /*  Point p1 = new Point(2,4);
        Point p2 = new Point(5,9);
        Point pi = new Point(); pi.makeInfinity();
        Point Pr = c.Add(p1, p2);
        //Point Pr = c.Substract(p1, pi);
        //Point Pr = c.Multiply(p1,2);
        //Point Pr = c.Double(p1);
        //System.out.println(Pr.x+" "+Pr.y);
        for(int i=1; i<14; i++) {
            Pr = c.Multiply(p1,i);
            System.out.println(Pr.x+" "+Pr.y);
        }
        System.out.println(Pr.x+" "+Pr.y);*/
        //c.generate();    
        JFrame a = new JFrame();
        View n = new View();
        n.init();
        a.getContentPane().add(n);
        a.pack();
        a.setVisible(true);
    }
    
    public ECC(){
        arrP = new ArrayList();
    }
    
    public ECC(long _a,long _b,long _p){
        a=_a;
        b=_b;
        p=_p;
        arrP = new ArrayList();
        generate();
    }
    
    private long mod(long l1, long l2) {
        long r=l1%l2;
        if(r<0) r+= l2;
        return r;
    }

    private long gradient (Point P1, Point P2) {
       long gr = -1;
       long atas = (P2.y-P1.y)  ;
       BigInteger bx1 = BigInteger.valueOf(P2.x-P1.x);
       BigInteger bp = BigInteger.valueOf(p);
        //System.out.println(bx1.toString()+" "+bp.toString());
       BigInteger bBawah = bx1.modInverse(bp);
       Long lBawah = bBawah.longValue();
       long bawah = lBawah;
       //System.out.println(bawah);
       gr = atas * bawah;
       //gr = (P2.y-P1.y)/(P2.x-P1.x);
       gr = mod(gr,p);
        //System.out.println(atas+"."+);
       return gr;
    }
    
    public Point Add(Point P1, Point P2) {
        long x,y;
        Point Pr = new Point();
        if(P2.x==P1.x) {
            Pr.makeInfinity();
        }
        else {
            long gr = this.gradient(P1,P2);
            x = mod((((long)Math.pow(gr,2)) - P1.x - P2.x),p);
            y = mod(((gr*(P1.x-x)) - P1.y),p);
            //System.out.println(gr+"("+P1.x+"-"+x+")"+"-"+P1.y+" mod "+p+" = "+y);
            Pr.x = x; Pr.y = y;
        }
        return Pr;
    }
    
    public Point Substract(Point P1, Point Pp2) { //UNTESTED
        Point P2 = new Point(Pp2.x,mod((-1*Pp2.y),p));
        long x,y;
        long gr = this.gradient(P1,P2);
        x = mod((((long)Math.pow(gr,2)) - P1.x - P2.x),p);
        y = mod(((gr*(P1.x-x)) - P1.y),p);
        //System.out.println(gr+"("+P1.x+"-"+x+")"+"-"+P1.y+" mod "+p+" = "+y);
        Point Pr = new Point(x,y);
        return Pr;
    }
    
    public Point Double(Point P1) {
        long gr = -1;
        Point Pr = new Point();
        if(P1.y==0) {
            Pr.makeInfinity();
        }
        else {
            long atas = (3*(long)Math.pow(P1.x,2))+a; //System.out.println(atas);
            long bawah = 2*P1.y; //System.out.println(bawah);
            //BigInteger Batas = BigInteger.valueOf(atas);
            BigInteger Bbawah = BigInteger.valueOf(bawah);
            BigInteger Bp = BigInteger.valueOf(p);
            //System.out.println(bx1.toString()+" "+bp.toString());
            Bbawah = Bbawah.modInverse(Bp);
            bawah = Bbawah.longValue(); //System.out.println(bawah);
            gr = mod((atas * bawah),p);  //System.out.println(gr);
            //System.out.println(gr);
            long x = mod((long)Math.pow(gr,2)-(P1.x*2),p);
            long y = mod((gr*(P1.x-x))-P1.y,p);
            Pr.x = x; Pr.y = y;
            //Long lBawah = Bbawah.longValue();
            //long bawah = lBawah;
        }
        return Pr;
    }   
    
    public Point Multiply(Point P, long k) {
        Point Pr = new Point();
        if(k==1) Pr = P;
        else if(k==2) Pr = Double(P);
        else {
            Pr = Double( Multiply(P,k/2) );
            if(k%2==1) {
                Pr = Add(Pr,P);
            }
        }
        return Pr;
        /*Point Pold = P;
        Point Pr = new Point();
        if(P.y==0) Pr.makeInfinity();
        else {
            for(int i=0; i<k; i++) {
                Pr = Add(Pr,Pold);
                if(Pr.y==0) {
                    Pr.makeInfinity();
                    break;
                }
            }
        }
        return Pr;*/
    }
    
    public Point iterate(long n){
        Point P = new Point();
        return P;
    }
    
    public void generate(){
      
        for(long x = 0 ; x<p;x++){
            for(long y=0;y<p;y++){
                if(mod(y*y,p)==mod((((long) Math.pow(x, 3))+ a*x+b),p)){
                    arrP.add(new Point(x,y));
                    arrP.add(new Point(x,p-y));
                    break;
                
                }
            }
        }
    } 
    
    public Point encodeKob(byte b){
        long x = mod(unsignedToBytes(b)*k,p);
        
        for(int i=0;i<arrP.size();i++){
            if(arrP.get(i).x>=x){
                return arrP.get(i);
            }
        }
        return (new Point(0,0));
    }
    
    public byte decodeKob(Point P){
        return (byte) (P.x/k);
    }
    
    private boolean isPerfectSquare(long n)
    {
      if (n < 0)
        return false;

      long tst = (long)(Math.sqrt(n) + 0.5);
      return tst*tst == n;
    }
    
    
    public int unsignedToBytes(byte b) {
    return b & 0xFF;
  }
}
