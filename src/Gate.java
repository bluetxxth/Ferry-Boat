/*
 * Project: GotlandsbÃ¥ten
 * File name: Gate
 *

 */


import java.util.*;


/**
 * This class represents a gate through which passangers can pass. With
 * some help Class it updates the number of passengers entering on foot
 * and on car. The class segregates gates for persons and gates for cars.
 * 3 gates for persons and 3 for cars. Persons are generated randomly, and
 * Cars can bear upto 5 passangers which are also generated randomly.
 *
 *  @author gabriel
 *
 *
 */
public class Gate implements Runnable {

    Counter mCounter;
    private int mNoOfPassengers;
     Unit unit = null;
    Thread t;
    Random r = new Random();

    /**
     * Constructor- Objects of this type will have:
     * Counter - a counter
     * mNumOfPassengers
     * t - a process
     * @param the gate number
     */
    Gate(int gateNum) {
        //throw new UnsupportedOperationException("Not yet implemented");
        mCounter = new Counter();
        mNoOfPassengers = 0;
        t = new Thread(this, ((Integer) gateNum).toString()); /*the thread has a gate ID */
        t.start();/*will start a threa upon instantiation*/

    }/*end constructor*/


    /**
     * Method add unit to buffer
     */
    public void addToBuffer(boolean add, int num) {
       unit = new Unit(add, num);
        mCounter.increase(unit);
    }/*end method*/



    /**
     * Method returns the number of passengers
     * @return
     */
    public int getNoOfPassengers() {
        return this.mNoOfPassengers;
    }


    /**
     *Method to close gates - ends all the threads
     */
    public void endThis() {
        t.interrupt();

    }/*end method*/

    @Override
    public void run() {
        
        /*the threads will execute this code below while not interrupted*/
        while (!t.isInterrupted()) {

            try {
                /*Remove element if buffer not empty*/
                Counter.mutex.acquire();
                if (!mCounter.buffer.isEmpty()) {
                    unit = mCounter.buffer.remove(0);
                }

            } catch (InterruptedException ex) {
                return; /*stop*/
            } try {
                Counter.mutex.release();
            }catch(Exception ex){

            }

            if (unit != null) {

                try {
                    Counter.mutex.acquire();/*block*/
                    
                        /*if in*/
                        if (((Counter.mCurrentValue + unit.number) <= (Counter.mMaxValue)) && unit.in == true) {
                            Counter.mCurrentValue += unit.number;
                            mCounter.in += unit.number;
                            this.mNoOfPassengers += unit.number;
                        
                        }
                        /*if no in*/
                        if ((Counter.mCurrentValue - unit.number) >= 0 && (unit.in == false)) {
                            Counter.mCurrentValue -= unit.number;
                            mCounter.out += unit.number;
                            this.mNoOfPassengers -= unit.number;
                        }

                } catch (InterruptedException ex) {
                   return; /*stop*/
                } try {
                    Counter.mutex.release(); 
                }catch(Exception ex){
                }
                unit = null;
            }
//            try {
//                Thread.sleep(3);
//            } catch (InterruptedException ex) {
//            }
        }

    }/*end run*/


    
}
