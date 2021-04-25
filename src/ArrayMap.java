import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
/**
 * 
 * @author omargebril
 *
 * @param <K> should hold Keys
 * @param <V> should hold values
 * 
 * this class replaces HashMap from project 2.
 * it's a generic type map so it should work on whatever 
 * type you instantiate it to.
 * 
 */
public class ArrayMap<K, V> extends AbstractMap<K, V> {

	Object keyArray[];
	Object valArray[];
	int entries;
	  
	public ArrayMap() {
		entries = 0;
		keyArray = new Object[20];
		valArray = new Object[20]; 
		for (int i = 0; i < keyArray.length; i++) {
			keyArray[i]=null;
			valArray[i]=null;

		}
	}
	/**
	 * Method put() responsible for inserting 
	 * user entered keys and values into Map
	 * 
	 * @param key to hold the key the user enters
	 * @param value to hold the value the user enters
	 * @return return V type old value if replaced and null if fresh insert
	 */
	@Override
	public V put(K key, V value) {
		if (entries == keyArray.length) { // CHECK entries
			// create new temporary array
			Object draftKeyArray[] = new Object[keyArray.length + 20];
			Object draftValArray[] = new Object[keyArray.length + 20];

			for (int i = 0; i < keyArray.length; i++) {
				draftKeyArray[i] = keyArray[i];
				draftValArray[i] = valArray[i];
			}
			keyArray = draftKeyArray;
			valArray = draftValArray;

		}
		V valToReturn = null;
		for (int i = 0; i < keyArray.length; i++) {
			if (keyArray[i]==(null)) {
				keyArray[i] = key;
				valArray[i] = value;
				entries++;
				return null;
			}
			
			else if(keyArray[i].equals(key)) {
				valToReturn = (V) valArray[i];
				valArray[i] = value;
				break;
			}
		}

		return valToReturn; 
	}

	/**
	 * Method size() returns size of map.
	 * 
	 * @return returns size of map, entry wise
	 */
	@Override
	public int size() {
		return entries;
	}
	
	/**
	 * returns Set of entries of keys and maps
	 * 
	 * @return Set of type ArrayMapEntrySet
	 */
	@Override
	public Set<Entry<K, V>> entrySet() {
		ArrayMapEntrySet entrySet = new ArrayMapEntrySet();
		return entrySet;
	}

	/**
	 * 
	 * serves as a Set class extends AbstractSet
	 * 
	 * @author omargebril
	 *
	 */
	private class ArrayMapEntrySet extends AbstractSet<Entry<K, V>> {
		/**
		 * Method contains(Object) returns true if Object is found in Set,
		 * returns false otherwise
		 * 
		 * @param o object specified by caller
		 * @return boolean true if object found in and false otherwise
		 */
		@Override
		public boolean contains(Object o) {
			for(int i = 0; i < keyArray.length; i++) {
				SimpleEntry<K, V> simpleEntry = new SimpleEntry<K, V>( (K) keyArray[i], (V) valArray[i]); //TODO type arguments?
			
				if(o.equals(simpleEntry)) {
					return true;
				}
			}
			return false;
		}
		
		/**
		 * Method size() returns size of Set
		 * 
		 * @return returns size of Set
		 */
		@Override
		public int size() {
			return entries;
		}
		
		/**
		 * 
		 * Method iterator() returns an Iterator
		 * 
		 * @return return iterator of type Iterator<Entry<K, V>
		 */
		@Override//////////////////////////////////////////////////////////////
		public Iterator<Entry<K, V>> iterator() {
			ArrayMapEntrySetIterator<Entry<K, V>> iter = new ArrayMapEntrySetIterator<Entry<K, V>>();
			return iter;
		}
	}

	/**
	 * 
	 * serves as ArrayMapEntrySet iterator class
	 * 
	 * @author omargebril
	 *
	 * @param <T> entry type
	 */
	private class ArrayMapEntrySetIterator<T> implements Iterator<T> {
		int curr = 0;
		boolean removeCalled = false;
		boolean nextCalled = false;
		
		/**
		 * Method hasNext() returns true if next entry exists in set,
		 * returns false otherwise
		 * 
		 * @return return boolean true if next exists, false otherwise
		 */
		@Override
		public boolean hasNext() {
			if(curr < entries) {
				return true;
			}
			return false;
		}

		/**
		 * Method next() creates a SimpleEntry of keys and values,
		 * returns next entry in set if exist.
		 * returns null and an error message otherwise without catching exceptions.
		 * 
		 * @return T next entry in set
		 */
		@Override
		public T next() {
			if(!hasNext()) {
				System.out.println("Possible Data Corruption: next does not exist\n");
				return null;
			}
			
			SimpleEntry<K, V> simpleEntry = new SimpleEntry<K, V>((K)keyArray[curr], (V)valArray[curr]); //TODO type arguments?
			curr++; // increment current
			removeCalled = false;
			return (T) simpleEntry;
			
		}

		/**
		 * Method Remove() throws IllegalStateException if called twice in a row after a call to next()
		 * otherwise, removes the entry the iterator is currently on
		 * 
		 * @throws IllegalStateException if remove() gets called twice after next() being called
		 */
		@Override
		public void remove() throws IllegalStateException {
			if (removeCalled) {
				throw new IllegalStateException();
			}
			keyArray[curr-1] = null;
			valArray[curr-1] = null;
			int pos=0;
			for(int i = 0; i<entries;i++) {
				if(keyArray[i]==(null)) {
					pos = i;
					break;
				}
			}
			Object temp=keyArray[0];
		     for(int i=pos;i<entries-1;i++)
		     {
		    	 keyArray[i]=keyArray[i+1];}
		     keyArray[entries-1]=temp;
		     Object temp2=valArray[0];
		     for(int i=pos;i<entries-1;i++)
		     {
		    	 if(valArray[i]==(null)) {
		    	 valArray[i]=valArray[i+1];
		    	 }
		     }
		     valArray[entries-1]=temp2;
			entries--;
			removeCalled = true;
		}
	}
}

