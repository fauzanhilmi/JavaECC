/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tucil3kripto;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        ECC c = new ECC(9,7,4093);
        //ECC c = new ECC(1,6,11);
        
        String text = "a d;fjdf qoiuo 804402389 ";
        Point p = new Point(0,0);
        long b = 2;
        byte[] Arrbyte = text.getBytes(Charset.forName("UTF-8"));
        ArrayList<PairPoint> ListPoint = new ArrayList<>(c.Encipher(Arrbyte, 10, b));
        
        //decipher
        byte[] Listbyte = c.Decipher(ListPoint, b);
        String str;
        try {
            str = new String(Listbyte,"UTF-8");
            System.out.println(str);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ECC.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        /*long k = 3;
        Point B = new Point(5,10);
        Point pm = new Point(10,10);
        Point pb = new Point(5,5);
        System.out.println(c.EncipherByte(k, B, pm, pb).p1.x+" "+c.EncipherByte(k, B, pm, pb).p1.y);
        System.out.println(c.EncipherByte(k, B, pm, pb).p2.x+" "+c.EncipherByte(k, B, pm, pb).p2.y);*/
        
        
        
        
        //c.generate();
        //byte b = 4;
        //System.out.println(c.decodeKob(c.encodeKob(b)));
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
        /*JFrame a = new JFrame();
        View n = new View();
        n.init();
        a.getContentPane().add(n);
        a.pack();
        a.setVisible(true);*/
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
        else if(P1.isInfinity() || P2.isInfinity()) {
            if(P1.isInfinity() && P2.isInfinity()) {
                Pr.makeInfinity();
            }
            else if(P1.isInfinity()) {
                Pr.x = P2.x ; Pr.y = P2.y;
            }
            else {
                Pr.x = P1.x; Pr.y = P1.y;
            }
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
        Point Pr = new Point();
        Pr = this.Add(P1, P2);
        /*if(P2.x==P1.x || P1.isInfinity() || P2.isInfinity()) {
            Pr.makeInfinity();
        }
        if(P1.isInfinity()||P2.isInfinity()) {
            if(P1.isInfinity() && P2.isInfinity()) {
                Pr.makeInfinity();
            }
            else if(P1.isInfinity()) {
                Pr.x = P2.x; Pr.y = P2.y;
            }
            else {
                Pr.x
            }
        }
        else {
            long gr = this.gradient(P1,P2);
            x = mod((((long)Math.pow(gr,2)) - P1.x - P2.x),p);
            y = mod(((gr*(P1.x-x)) - P1.y),p);
            //System.out.println(gr+"("+P1.x+"-"+x+")"+"-"+P1.y+" mod "+p+" = "+y);
            Pr.x = x; Pr.y = y;
        }*/
        return Pr;
    }
    
    public Point Double(Point P1) {
        long gr = -1;
        Point Pr = new Point();
        if(P1.y==0 || P1.isInfinity()) {
            Pr.makeInfinity();
        }
        else {
            long atas = (3*(long)Math.pow(P1.x,2))+a; 
            long bawah = 2*P1.y;
            BigInteger Bbawah = BigInteger.valueOf(bawah);
            BigInteger Bp = BigInteger.valueOf(p);
            Bbawah = Bbawah.modInverse(Bp);
            bawah = Bbawah.longValue(); 
            gr = mod((atas * bawah),p); 
            long x = mod((long)Math.pow(gr,2)-(P1.x*2),p);
            long y = mod((gr*(P1.x-x))-P1.y,p);
            Pr.x = x; Pr.y = y;
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
    }
    
    //public ArrayList<Point> EncipherByte(long k, Point B, Point Pm, Point Pb) {
    public PairPoint EncipherByte(long k, Point B, Point Pm, long b) {
        Point Pb = new Point();
        Pb = this.Multiply(B,b);
        //Point Pb = new Point(this.Multiply(B.x,b),this.Multiply( B.y,b));
        //ArrayList<Point> pp = new ArrayList<>();
        PairPoint pp = new PairPoint();
        Point P1 = new Point();
        Point P2 = new Point();
        //P1.x = B.x * k; 
        //P1.y=B.y * k;
        P1 = this.Multiply(B, k);
        //P2.x = (Pm.x+(k*Pb.x));
        //P2.y = (Pm.y+(k*Pb.y));
        P2 = this.Add(Pm, this.Multiply(Pb,k));
        pp.p1 = P1;
        pp.p2 = P2;
        return pp;
    }
    
    //public void Encipher(String text, long k, Point Pb) {
    public ArrayList<PairPoint> Encipher(byte[] Arrbyte, long k, long b) {
        //byte[] Arrbyte = text.getBytes();
        this.generate();
        ArrayList<Point> ListPoint = new ArrayList<>();
        for(int i=0; i<Arrbyte.length; i++) {
            ListPoint.add(this.encodeKob(Arrbyte[i])); //only (2,4)
        }
        /*for(int i=0; i<ListPoint.size(); i++) {
            System.out.println(ListPoint.get(i).x+" "+ListPoint.get(i).y);
        }*/
        //System.out.println(ListPoint.get(0).x+" "+ListPoint.get(0).y);
        Point B = arrP.get(0); //(2,4)
        //Point Pb = new Point(B.x*b, B.y*b);
        ArrayList<PairPoint> ListPair = new ArrayList<>();
        for(int i=0; i<ListPoint.size(); i++) {
            ListPair.add(this.EncipherByte(k, B, ListPoint.get(i), b));
        }
        //System.out.println(ListPair.size());
        System.out.println("");
        //nyoba decipher
        ArrayList<Point> ListSolP = new ArrayList<>();
        for(int i=0; i<ListPair.size(); i++) {
            Point pkanan = new Point();
            pkanan = this.Multiply(ListPair.get(i).p1,b);
            ListSolP.add( this.Substract(ListPair.get(i).p2, pkanan) );
            if(this.Substract(ListPair.get(i).p2, pkanan).isInfinity()) {
                System.out.println("("+ListPair.get(i).p2.x+","+ListPair.get(i).p2.y+") - ("+pkanan.x+","+pkanan.y+") = ("+this.Substract(ListPair.get(i).p2, pkanan).x+","+this.Substract(ListPair.get(i).p2, pkanan).y);
            }
        }
        /*for(int i=0; i<ListSolP.size(); i++) {
            System.out.print(ListPoint.get(i).x+" "+ListPoint.get(i).y+" | ");
            System.out.println(ListSolP.get(i).x+" "+ListSolP.get(i).y);
        }*/
        return ListPair;
        
    }
    
    public byte[] Decipher(ArrayList<PairPoint> ListPair, long b) {
        ArrayList<Point> ListSolP = new ArrayList<>();
        for(int i=0; i<ListPair.size(); i++) {
            Point pkanan = new Point();
            pkanan = this.Multiply(ListPair.get(i).p1,b);
            ListSolP.add( this.Substract(ListPair.get(i).p2, pkanan) );
            /*if(this.Substract(ListPair.get(i).p2, pkanan).isInfinity()) {
                System.out.println("("+ListPair.get(i).p2.x+","+ListPair.get(i).p2.y+") - ("+pkanan.x+","+pkanan.y+") = ("+this.Substract(ListPair.get(i).p2, pkanan).x+","+this.Substract(ListPair.get(i).p2, pkanan).y);
            }*/
        }
        byte[] ListByte = new byte[ListSolP.size()];
        for(int i=0; i<ListSolP.size(); i++) {
            Point p = ListSolP.get(i);
            ListByte[i] = decodeKob(p);
        }
        return ListByte;
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
