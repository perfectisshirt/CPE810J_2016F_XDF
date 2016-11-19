package FlifImage;
/**
 * 
 * @author chenxi
 *
 */


/*
Explanation of lossless YCoCg:

 Y = Luminance (near* weighted average of RGB in 1:2:1).
 C = Chroma.
Co = Amount of [o]range chrome. Max = orange, 0 = gray, min = blue.
Cg = Amount of [g]reen chrome. Max = green, 0 = gray, min = purple.

RGB -> YCoCg
------------
Co = R - B          <1> | This makes sense, if R = B, then we are gray.
                        | If maximally orange, B = 0, R = max.
                        | If maximally blue, B = max, R = 0.

 p = (R + B)/2          | [P]urple, is the average of red/blue, truncated.
 p = (2B + R - B)/2     | All these steps should be obviously
 p = B + (R - B)/2      | equal, losslessly.
 p = B + Co/2       <2>

Cg = G - p          <3> | Again, makes sense, green vs. purple.

 Y = p + Cg/2       <4> | Near* weighted average of RGB in 1:2:1.
 Y = p + (G - p)/2
 Y ~= p + G/2 - p/2     | *These steps are not lossless (they can be off by
 Y ~= p/2 + G/2         | 1 or perhaps 2), but illustrate that Y is a near
 Y ~= (R + B)/4 + G/2   | weighted average of RGB in 1:2:1.

YCoCg -> RGB
------------
p = Y - Cg/2       <4>
G = Cg + p         <3>
B = p - Co/2       <2>
R = Co + B         <1>
*/

public class Transform {
 public int GetMinY(int par){
	 return 0;
 }
 public int GetMaxY(int par){
	 return par*4-1;
 }
 public int GetMinCo(int par, int y){
	 if(y<GetMinY(par) || y>GetMaxY(par)){
		 System.exit(1);
	 }
	 
	 if (y<par-1) {
	      return -3-4*y;
	    } else if (y>=3*par) {
	      return 4*(1+y-4*par);
	    } else {
	      return -4*par+1;
	    }
 }
 
 public int GetMaxCo(int par, int y){
	 if(y<GetMinY(par) || y>GetMaxY(par)){
		 System.exit(1);
	 }
	 if (y<par-1) {
	      return 3+4*y;
	    } else if (y>=3*par) {
	      return 4*par-4*(1+y-3*par);
	    } else {
	      return 4*par-1;
	    }
 }
 public int GetMinCg(int par, int y, int co){
	 if(y<GetMinY(par) || y>GetMaxY(par)){
		 System.exit(1);
	 }
	 if (co < GetMinCo(par,y)) return 8*par; //invalid value
	 if (co > GetMaxCo(par,y)) return 8*par; //invalid value
	 if (y<par-1) {
	      return -(2*y+1);
	    } else if (y>=3*par) {
	      return -(2*(4*par-1-y)-((1+Math.abs(co))/2)*2);
	    } else {
	      return -min(2*par-1+(y-par+1)*2, 2*par+(3*par-1-y)*2-((1+Math.abs(co))/2)*2);
	    }
 }
 
 private int min(int i, int j) {
	// TODO Auto-generated method stub
	 if(i>=j){
		 return j;
     }else{
    	 return i;
     }
}
public int GetMaxCg(int par, int y,int co){
	 if(y<GetMinY(par) || y>GetMaxY(par)){
		 System.exit(1);
	 }
	 if (co < GetMinCo(par,y)) return -8*par; //invalid value
	 if (co > GetMaxCo(par,y)) return -8*par; //invalid value

	 if (y<par-1) {
	      return 1+2*y-(Math.abs(co)/2)*2;
	    } else if (y>=3*par) {
	      return 2*(4*par-1-y);
	    } else {
	      return -max(-4*par + (1+y-2*par)*2, -2*par-(y-par)*2-1+(Math.abs(co)/2)*2);
	    }

 }
private int max(int i, int j) {
	// TODO Auto-generated method stub
	if(i>=j){
		 return i;
    }else{
   	 return j;
    }
}
}
