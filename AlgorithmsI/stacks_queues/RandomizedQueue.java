import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int     _n;
    private Item[]  _tab;
    // construct an empty randomized queue
    public RandomizedQueue(){
        _n = 0;
        _tab = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return _n == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return _n;
    }

    // add the item
    public void enqueue(Item item){
        if(item == null)
            throw new IllegalArgumentException();
        if (_n == _tab.length)
            resize(2 * _tab.length);
        _tab[_n] = item;
        _n++;
     }

    // remove and return a random item
    public Item dequeue(){
        if (isEmpty())
            throw new NoSuchElementException();
        int random = StdRandom.uniform(_n);
        Item item = _tab[random];
        _tab[random] = _tab[_n - 1];
	    _tab[_n - 1] = null;
        _n--;
        if (_n > 0 && _n == _tab.length/4)
            resize(_tab.length/2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if (isEmpty())
            throw new NoSuchElementException();
        int i = StdRandom.uniform(_n);
        return _tab[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
         return new ReverseArrayIterator();
    }
    private class ReverseArrayIterator implements Iterator<Item>{
        private int i = _n;
        private Item[] copy =(Item[]) new Object[_tab.length];
	    
	    public ReverseArrayIterator(){
		    for (int j = 0; j < _tab.length; i++){
		        copy[i] = _tab[i];
		    }
	    }
        public boolean hasNext() { return i > 0; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return _tab[--i]; 
        }
    }
    private void resize(int n)
    {
        Item[] copy = (Item[]) new Object[n];
        for (int i = 0; i < _n; i++)
            copy[i] = _tab[i];
        _tab = copy;
        copy = null;
    }
    public static void main(String[] args){
        RandomizedQueue<String> randomqueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-"))
                StdOut.print(randomqueue.dequeue());
            else
                randomqueue.enqueue(s);
        }
    }
}