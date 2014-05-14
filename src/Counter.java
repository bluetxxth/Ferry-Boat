

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/*
 * This class comes from lection 5 p. 23
 *
 */

public class Counter {

    static int mCurrentValue = 0; /*the carrying value of the counter*/
    static final int mMaxValue = 1500; /*max value allowed for the counter*/
    static Semaphore mutex = new Semaphore(1);
//    private Semaphore empty;
//    private Semaphore full;
//    static final int BUFFER_SIZE = 2;

    ArrayList<Unit> buffer = new ArrayList<Unit>();/*array list of elements*/
    private int cVal = 0;
    private Unit unit;
    
    int in = 0;
    int out = 0;


    /**
     * Constructor
     *
    public Counter() {
   //Buffer is initially empty
        this.in = 0;
        this.out = 0;
       // buffer = new ArrayList<Unit>();
        mutex = new Semaphore(1);
        unit = new Unit(true, cVal);
//        full = new Semaphore(0);
//        empty = new Semaphore(BUFFER_SIZE);
    }/*end constructor*/

    /**
     * producer calls this method
     * @param item
     */
   public synchronized void increase(Object item) {
        try {
            mutex.acquire();
            if(buffer.isEmpty()){
            buffer.add((Unit) item);
            }
        } catch (InterruptedException ex) {

        }
        try {
            mutex.release();
        }catch(Exception ex){
        }
    }


/**
 * consumer calls this method
 * @return
 */
 public synchronized  Object decrease() {
        try {
            mutex.acquire();
            if(!buffer.isEmpty()){
                unit = buffer.remove(0);
            }
        } catch (InterruptedException ex) {

        }
        try{
            mutex.release();
        }catch(Exception ex){

        }
// decrease an item from buffer
        Object item = unit;
        return item;
    }



    /**
     * Method - Gets the current number
     * @return current value
     */
  public int getCurrentValue() {

        try {
            mutex.acquire();
            cVal = mCurrentValue;
        } catch (InterruptedException ex) {
        } try {
            mutex.release();
        }catch(Exception ex){
        }

        return cVal;
    }

    /**
     * Method - gets the max value
     * @return
     */
     public int getMaxValue() {
        return mMaxValue;
    }

         /**
     * Method gives the number of units
     * @return the number of units
     */
    public int numberOfUnits() {
        int unitNum = unit.number;
        return unitNum;
    }
}

