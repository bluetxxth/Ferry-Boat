



import java.util.Random;


/**
 * This class is responsible for starting and initializing the gates
 * Control the number of passengers that go in and out and outputting totals.
 * Parts of this class from lesson 5 p.
 *
 *  @author gabriel
 *
 *
 */
public class Simulator {

    /*instance fields*/
 
    public Counter mCounter = new Counter();/*counter*/
    static Gate[] gate = new Gate[6]; /*an array of gate objects*/
    private Random r = new Random();/*random generator*/
    int g, n; /*control for gate*/
    private boolean enter;/*entering or leaving*/


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {

        Simulator sim = new Simulator();
        sim.initGate();/*initializes the gates*/
        sim.checkMaxValues();/*check maximum values*/
        sim.totals();/*print totals*/


    }

    /**
     * intialize and start the gates
     *
     */
    private void initGate() {

        /*spawn 5 gates*/
        for (g = 0; g < 6; g++) {
            gate[g] = new Gate(g + 1);
        }/*end for*/
    }/*end function*/


    /**
     * Method - checks to see if counter has max value
     */
    private void checkMaxValues() {

        while (mCounter.getCurrentValue() < mCounter.getMaxValue()) {

            /*will enter and exit at random*/
            enter = r.nextBoolean();
            /*randomly generated */
            g = r.nextInt(6);

            /*enter*/
            if (enter == true) {

                /*persons gates*/
                if (g < 3) {
                   n = 1;
                }
                /*cars gates*/
                else {
                    n = r.nextInt(5) + 1;
                    }
            }/*end if*/
            while ((g < 3) && (n > 1)) {
                /*exit*/
               if (enter == false) {
                    /*cars*/
                    n = r.nextInt(5)+1;
                    
               }/*end if*/

            }/*end while*/
            gate[g].addToBuffer(enter, n);

        }/*end while*/
    }/*end function*/

   /**
     * Method close gates and write the total ammount of
     * persons on the boat (from the counter).
     */
    private void totals() {

        // checkMaxValues();
        System.out.printf("\t\tEnter      Exit" + "\n");
        for (g = 0; g < 6; g++) {
            gate[g].endThis();
            System.out.printf("Gate " + (g + 1) + ":");
            System.out.printf("\t" + gate[g].mCounter.in + "\t" + gate[g].mCounter.out + "\n");
        }
        System.out.printf("\n" + "Total onboard: " + mCounter.getCurrentValue() + "\n");


    }/*end function*/
}/*end class*/
