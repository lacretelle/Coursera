import java.util.Iterator;
import java.util.NoSuchElementException;
// import java.lang.Iterable;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private int     _n;
    private Node    _first;
    private Node    _last;

    private class Node
    {
        Item item;
        Node prev;
        Node next;
    }
    // construct an empty deque
    public Deque(){
        _first = null;
        _last = null;
        _n = 0;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return _n == 0;
    }

    // return the number of items on the deque
    public int size(){
        return _n;
    }

    // add the item to the front
    public void addFirst(Item item){
        if (item == null)
            throw new IllegalArgumentException();
        Node oldFirst = _first;
        _first = new Node();
        _first.item = item;
        _first.next = oldFirst;
        _first.prev = null;
        if (isEmpty())
            _last = _first;
        else
            oldFirst.prev = _first;
        _n++;
    }

    // add the item to the back
    public void addLast(Item item){
        if (item == null)
            throw new IllegalArgumentException();
        Node oldLast = _last;
        _last = new Node();
        _last.item = item;
        _last.next = null;
        _last.prev = oldLast;
        if (isEmpty())
            _first = _last;
        else
            oldLast.next = _last;
        _n++;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if (isEmpty())
            throw new NoSuchElementException();
        Item i = _first.item;
        _first = _first.next;
        if (_first != null)
            _first.prev = null;
        else
            _last = null;
        _n--;
        return i;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if (isEmpty())
            throw new NoSuchElementException();
        Item i = _last.item;
        _last = _last.prev;
        if (_last != null)
            _last.next = null;
        else
            _first = null;
        _n--;
        return i;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>{
        private Node current = _first;
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next()
        {
             if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    // unit testing (required)
    public static void main(String[] args)
    {
        Deque<String> deque = new Deque<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-"))
                StdOut.print(deque.removeLast());
            else
                deque.addFirst(s);
        }
    }
}