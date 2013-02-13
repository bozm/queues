import java.util.Iterator;
import java.util.NoSuchElementException;
public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int N;
    private class Node{
        private Item item;
        private Node next;
        private Node former;
    }
    
   public Deque()                     
   {
       first = null;
       last = null;
       N = 0;
       assert check();
   }
   
   public boolean isEmpty()           
   {
       return first == null;
   }
   public int size()                
   {
       return N;
   }
   
   public void addFirst(Item item)   
   {
       if(isEmpty())
       {
           first = last = new Node();
           first.item = last.item = item;
           first.next = last.next = null;
           first.former = last.former = null;
       }
       else
       {
       Node oldfirst = first;
       first = new Node();
       first.item = item;
       first.former = null;
       first.next = oldfirst;
       oldfirst.former = first;
       }
       N++;
       assert check();
   }
   public void addLast(Item item)     
   {
       Node oldlast = last;
       last = new Node();
       last.item = item;
       last.next = null;
       last.former = oldlast;
       if (isEmpty()) first = last;
        else           oldlast.next = last;
        N++;
        assert check();
   }
   
   public Item removeFirst()         
   {
     if (isEmpty()) throw new NoSuchElementException("Deque underflow");
     Item item = first.item;
     first = first.next;
      N--;
      assert check();
      return item;   
   }
         
   public Item removeLast()           
   {
     if (isEmpty()) throw new NoSuchElementException("Deque underflow");
     Item item = last.item;
     last = last.former;
     N--;
     assert check();
     return item;
   }
     
   public Iterator<Item> iterator()   
   {  
       return new ListIterator();
   }
       private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
       private boolean check() {
        if (N == 0) {
            if (first != null) return false;
            if (last  != null) return false;
        }
        else if (N == 1) {
            if (first == null || last == null) return false;
            if (first != last)                 return false;
            if (first.next != null)            return false;
            if(last.former != null)            return false;
        }
        else {
            if (first == last)      return false;
            if (first.next == null) return false;
            if(first.former != null) return false;
            if (last.next  != null) return false;
            if(last.former == null) return false;

            // check internal consistency of instance variable N
            int numberOfNodes = 0;
            for (Node x = first; x != null; x = x.next) {
               numberOfNodes++;
            }
            if (numberOfNodes != N) return false;
            for (Node x = last; x != null; x = x.former) {
               numberOfNodes++;
            }
            if (numberOfNodes != N) return false;

            // check internal consistency of instance variable last
            Node lastNode = first;
            while (lastNode.next != null) {
               lastNode = lastNode.next;
            }
            if (last != lastNode) return false;
             Node nextNode = last;
            while (nextNode.former != null) {
               nextNode = nextNode.former;
            }
            if (first != nextNode) return false;
        }

        return true;
    } 
        public static void main(String[] args) {
        Deque<String> q = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) q.addFirst(item);
            else if (!q.isEmpty()) StdOut.print(q.removeFirst() + " ");
        }
        StdOut.println("(" + q.size() + " left on deque)");
    }
}
