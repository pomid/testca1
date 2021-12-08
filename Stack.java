import java.lang.System;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * capacity = [0-Integer.MAX_VALUE]
 * size = [0-Integer.MAX_VALUE]
 * 
 * blocks = 
 *  b0 = [size = capacity = 0] + (this dude is full and empty at the same time so push and pop will result in an exception)
 *  b1 = [0 = size < capacity] + (empty stack should give isEmpty() True and isFull() False, pop should result in an exception) 
 *  b2 = [0 < size < capacity] + (not empty and not full stack -> can push and pop without exception)
 *  b3 = [0 < size = capacity] + (full stack , push results in exception)
 *  b4 = [0 < capacity < size] + (Unreachable state)
 *  
 * 
 */
public class Stack{
    private int capacity, size;

    public Stack(int cap){
        capacity = cap;
        size = 0;
    }

    public int getCapacity(){
        return capacity;
    }

    public int getSize(){
        return size;
    }

    public boolean isFull(){
        return size==capacity;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public void push(Object o) throws StackIsFullException{
        //blah blah
        if (isFull())
            throw new StackIsFullException();
        size += 1;
    }

    public Object pop() throws StackIsEmptyException{
        if (isEmpty())
            throw new StackIsEmptyException();
        size -= 1;
    }
}

public interface IStack{
    public int getCap();
    public int getSize();

    public default boolean isFull(){
        return getCap() == getSize();
    }

    public default boolean isEmpty(){
        return getSize() == 0;
    }

    public void push(Object o) throws StackIsFullException;
    public Object pop() throws StackIsEmptyException;
}

public final class IIStack implements IStack{
    private int cap, size;
    private Object[] s;

    public IIStack(int _cap){
        cap = _cap;
        size = 0;
        s = new Object[cap];
    }

    public int getCap(){
        return cap;
    }

    public int getSize(){
        return size;
    }

    public void push(Object o) throws StackIsFullException{
        if (isFull())
            throw new StackIsFullException();
        s[size] = o;
        size++;
    }

    public Object pop() throws StackIsEmptyException{
        if (isEmpty())
            throw new StackIsEmptyException();
        size--;
        return s[size];
    }

}

class StackTest{
    /// {size, capacity}
    static int[][] BaseChoices = new int[][] {
        {0, 0}, // b0 
        {0, 2}, {0, 1}, // b1
        {1, 2}, // b2
        {2, 2}, {1, 1}, // b3
        {2, 1}, {1, 0}, // b4
    };

    /**
     * also might want to do behavior input ?! which might be more reasonable
     * 
     * start with Stack(2)
     * now consider 3 pushes and 3 pops
     * given any order of these pushes and pops we'll get to b1 + b2 + b3 (b0 and b4 are out of reach) (might get exceptions)
     * also the stack must be empty at the end of the test if we didn't have any exceptions
     * can start with Stack(0) and cover b0
     * cannot start with any n Stack(n) such that we reach b4
     */

    /**
     * p = push, o = pop
     */



    @Test
    public void isp(IStack istack){
        
    }

    @Test
    public void ispv2(IStack istack){
        
    }


}


