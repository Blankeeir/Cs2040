import java.util.*; // or import java.util.Scanner;

class FractionOOPV2 {
  public int num, denom;

  public FractionOOPV2(int iNum, int iDenom) {
    num = iNum;
    denom = iDenom;
  }

  public FractionOOPV2() {
    num = 1;
    denom = 1;
  }

  public int getNum() { return num; }
  public int getDenom() { return denom; }
  public void setNum(int iNum) {num = iNum;}
  public void setDenom(int iDenom) {denom = iDenom;}
  public static int gcd(int e, int f) {
    int rem;

    while (f > 0) {
      rem = e%f;
      e = f;
      f = rem;
    }
    return e;
  }

  // instance method add -> takes in another fraction add to this fraction and modify it
  public void add(FractionOOPV1 iFrac) {
    num = num*iFrac.getDenom()+denom*iFrac.getNum();
    denom = denom*iFrac.getDenom();
    int divisor = gcd(num,denom);
    num /= divisor;
    denom /= divisor;
  }

  // overloaded instance method add
  public void add(int iNum, int iDenom) {
    num = num*iDenom+denom*iNum;
    denom = denom*iDenom;
    int divisor = gcd(num,denom);
    num /= divisor;
    denom /= divisor;
  }

  // class method add
  public static FractionOOPV2 add(FractionOOPV1 frac1, FractionOOPV1 frac2) {
    FractionOOPV2 newFrac = new FractionOOPV2();
    int tempNum = frac1.getNum()*frac2.getDenom()+frac1.getDenom()*frac2.getNum();
    int tempDenom = frac1.getDenom()*frac2.getDenom();
    int divisor = gcd(tempNum,tempDenom);
    newFrac.setNum(tempNum/divisor);
    newFrac.setDenom(tempDenom/divisor);
    return newFrac;
  }

  @Override
  public String toString() {
    return num+"/"+denom;
  }
}

public class TestFractionOOPV2 {

  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    int numAdd = sc.nextInt();

    for (int i=0; i < numAdd; i++) {
      FractionOOPV1 f1 = new FractionOOPV1(sc.nextInt(),sc.nextInt());
      FractionOOPV1 f2 = new FractionOOPV1(sc.nextInt(),sc.nextInt());
      f1.add(f2);
      System.out.println(f1);
      sc.close();
    }
  }
}
