import java.util.Iterator;
import java.util.NoSuchElementException;
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;            // queue elements
    private int N = 0;           // number of elements on queue
    private int first = 0;       // index of first element of queue
    private int last  = 0;       // index of next available slot


   /**
     * Create an empty queue.
     */
      public RandomizedQueue()           // construct an empty randomized queue 
  {
        q = (Item[]) new Object[2];
    }
   /**
     * Is the queue empty?
     */
   public boolean isEmpty() { return N == 0;    }

 
   public int size()        { return N;         }
   
   private void resize(int max) {
        assert max >= N;
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last  = N;
    }

   
  public void enqueue(Item item) {
        // double size of array if necessary and recopy to front of array
        if (N == q.length) resize(2*q.length);   // double size of array if necessary
        q[last++] = item;                        // add item
    
        N++;
    }
  
   public Item dequeue()              // delete and return a random item
   {
       
       if (isEmpty()) throw new RuntimeException("Queue underflow");
       int position;
       Item item;
       position = StdRandom.uniform(last-first)+first;
       item = q[position];
       q[position] = q[first];
       q[first] = null;
       first++;
        N--;
        // shrink size of array if necessary
        if (N > 0 && N == q.length/4) resize(q.length/2); 
        return item;
   }

       
       
   public Item sample()      // return (but do not delete) a random item
   {
       if (isEmpty()) throw new RuntimeException("Queue underflow");
       int position;
       Item item;
       position = StdRandom.uniform(last-first)+first;
       item = q[position];
       return item;
   }
    public Iterator<Item> iterator() { return new ArrayIterator(); }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        public boolean hasNext()  { return i < N;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[(i + first) % q.length];
            i++;
            return item;
        }
    }
    
}
