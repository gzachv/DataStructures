///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Title:            HashTable
//Files:            TestHash.java on 367 webpage
//Semester:         CS367 Fall 2012
//
//Author:           Gustavo Zach Vargas
//CS Login:         gustavo
//Lecturer's Name:  Skrentny
//Lab Section:      NA
//
//PAIR PROGRAMMERS COMPLETE THIS SECTION
//Pair Partner:     Ryan Ranney rranney@wisc.edu
//CS Login:         ranney
//Lecturer's Name:  Skrentny
//Lab Section:      NA
//
//STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
//Credits:         code skeleton, consulting from Sarah Gilliland
////////////////////////////80 columns wide //////////////////////////////////


import java.io.*;
import java.util.ArrayList;


/**
 * This class implements a hashtable that using chaining for collision handling.
 * The chains are implemented using ArrayLists.  When a hashtable is created, 
 * it's initial size, maximum load factor, and (optionally) maximum chain length
 * is specified.  The hashtable can hold arbitrarily many items and resizes 
 * itself whenever it reaches its maximum load factor or whenever it reaches 
 * its maximum chain length (if a maximum chain length has been specified).
 * Note that this hashtable allows duplicate entries.
 */
public class HashTable<T> {

	//fields
	private double loadFactor;
	private ArrayList<T>[] arr;
	private int initSize;
	private int maxChainLength;
	private int numItems;

	/**
	 * Constructs an empty hashtable with the given initial size, maximum load
	 * factor, and no maximum chain length.  The load factor should be a real 
	 * number greater than 0.0 (not a percentage).  For example, to create a 
	 * hash table with an initial size of 10 and a load factor of 0.85, one 
	 * would use:
	 * <dir><tt>HashTable ht = new HashTable(10, 0.85);</tt></dir>
	 *
	 * @param initSize The initial size of the hashtable.  If the size is less
	 * than or equal to 0, an IllegalArgumentException is thrown.
	 * @param loadFactor The load factor expressed as a real number.  If the
	 * load factor is less than or equal to 0.0, an IllegalArgumentException is
	 * thrown.
	 **/
	public HashTable(int initSize, double loadFactor) {
		if(initSize <= 0){
			throw new IllegalArgumentException();
		}

		if(loadFactor <= 0.0){
			throw new IllegalArgumentException();
		}

		this.loadFactor = loadFactor;
		this.initSize = initSize;

		this.arr = (ArrayList<T>[])(new ArrayList[initSize]);
	}


	/**
	 * Constructs an empty hashtable with the given initial size, maximum load
	 * factor, and maximum chain length.  The load factor should be a real 
	 * number greater than 0.0 (and not a percentage).  For example, to create 
	 * a hash table with an initial size of 10, a load factor of 0.85, and a 
	 * maximum chain length of 20, one would use:
	 * <dir><tt>HashTable ht = new HashTable(10, 0.85, 20);</tt></dir>
	 *
	 * @param initSize The initial size of the hashtable.  If the size is less
	 * than or equal to 0, an IllegalArgumentException is thrown.
	 * @param loadFactor The load factor expressed as a real number.  If the
	 * load factor is less than or equal to 0.0, an IllegalArgumentException is
	 * thrown.
	 * @param maxChainLength The maximum chain length.  If the maximum chain
	 * length is less than or equal to 0, an IllegalArgumentException is thrown.
	 **/
	public HashTable(int initSize, double loadFactor, int maxChainLength) {
		if(initSize <= 0){
			throw new IllegalArgumentException();
		}

		if(loadFactor <= 0.0){
			throw new IllegalArgumentException();
		}

		this.loadFactor = loadFactor;
		this.initSize = initSize;
		this.maxChainLength = maxChainLength;

		this.arr = (ArrayList<T>[])(new ArrayList[initSize]);
	}


	/**
	 * Determines if the given item is in the hashtable and returns it if 
	 * present.  If more than one copy of the item is in the hashtable, the 
	 * first copy encountered is returned.
	 *
	 * @param item the item to search for in the hashtable
	 * @return the item if it is found and null if not found
	 **/
	public T lookup(T item) {

		//find the hash index of the item
		int index = Math.abs(item.hashCode() % arr.length);

		//in arraylist at the given index find the item
		for(int j = 0;j<arr[index].size(); j++){

			if(arr[index].get(j).equals(item)){
				//return specified item once found
				return arr[index].get(j);
			}
		}

		//if item is not found return null
		return null;
	}


	/**
	 * Inserts the given item into the hash table.  
	 * 
	 * If the load factor of the hashtable after the insert would exceed 
	 * (not equal) the maximum load factor (given in the constructor), then the 
	 * hashtable is resized.  
	 * 
	 * If the maximum chain length of the hashtable after insert would exceed
	 * (not equal) the maximum chain length (given in the constructor), then the
	 * hashtable is resized.
	 * 
	 * When resizing, to make sure the size of the table is good, the new size 
	 * is always 2 x <i>old size</i> + 1.  For example, size 101 would become 
	 * 203.  (This  guarantees that it will be an odd size.)
	 * 
	 * <p>Note that duplicates <b>are</b> allowed.</p>
	 *
	 * @param item the item to add to the hashtable
	 **/
	public void insert(T item) {

		int index = Math.abs(item.hashCode()) % arr.length;

		if(arr[index] == null){
			arr[index] = new ArrayList<T>();
		}

		arr[index].add(item);

		if(arr[index].size() > maxChainLength && !(maxChainLength == 0)){
			resize();
		}

		numItems++;

		if(currentLF() > loadFactor){
			resize();
		}
	}

	/**
	 * Resize array, to make sure the size of the table is good, the new size 
	 * is always 2 x old size + 1.  For example, size 101 would become 
	 * 203.  (This  guarantees that it will be an odd size.)
	 * 
	 */
	private void resize(){

		ArrayList<T>[] tmp = (ArrayList<T>[])(new ArrayList[2*arr.length + 1]);

		int index;
		for(int i = 0; i<arr.length; i++){
			
			if(!(arr[i] == null)){
				
				for(int j = 0; j < arr[i].size(); j++){
					index = Math.abs(arr[i].get(j).hashCode()) % tmp.length;
					
					if(tmp[index] == null){
						tmp[index] = new ArrayList<T>();
					}
					
					tmp[index].add(arr[i].get(j));
				}
				
			}

		}
		
		
		arr = tmp;
	}


	/**
	 * Removes and returns the given item from the hashtable.  If the item is 
	 * not in the hashtable, <tt>null</tt> is returned.  If more than one copy 
	 * of the item is in the hashtable, only the first copy encountered is 
	 * removed and returned.
	 *
	 * @param item the item to delete in the hashtable
	 * @return the removed item if it was found and null if not found
	 **/
	public T delete(T item) {
		

		int index = Math.abs(item.hashCode() % arr.length);

		if(arr[index] == null){
			return null;
		}

		arr[index].remove(item);

		numItems--;

		return item;  
	}


	/**
	 * Prints all the items in the hashtable to the PrintStream supplied.
	 * The items are printed in the order determined by the index of the 
	 * hashtable where they are stored (starting at 0 and going to (hashtable 
	 * size - 1)).  The values at each index are printed according to the order 
	 * in the ArrayList starting at index 0. 
	 *
	 * @param out the place to print all the output
	 **/
	public void dump(PrintStream out) {
		out.println("Hashtable Contents:");
		
		for(int i = 0; i < arr.length; i++){

			if(!(arr[i] == null)){
				out.print(i+": [");
				for(int j = 0; j<arr[i].size();j++){

					if(!(arr[i].get(j) == null)){

						out.print(arr[i].get(j));

						if(!(j == arr[i].size()-1)){
							out.print(", ");
						}
					}
				}
				out.println("]");
			}
		}
	}


	/**
	 * Prints statistics about the hashtable to the PrintStream supplied.
	 * The statistics displayed are: 
	 * <ul>
	 * <li>the current table size
	 * <li>the number of items currently in the table 
	 * <li>the current load factor
	 * <li>the length of the largest chain
	 * <li>the number of chains of length 0
	 * <li>the average length of the chains of length > 0
	 * </ul>
	 *
	 * @param out the place to print all the output
	 **/
	public void displayStats(PrintStream out) {
		out.println("HashTable Statistics: ");
		out.println("Current table size: " + arr.length);
		out.println("# items in table: " + numItems);
		out.println("Current load factor: " + currentLF());

		int [] lengthStats = compStats();
		out.println("Longest chain length: " + lengthStats[0]);
		out.println("# 0-length chains: " + lengthStats[1]);

		//computes the average length of non zero chains
		out.println("Avg (non-0) chain length: " +
				((double)lengthStats[2] / (arr.length - lengthStats[1])));
		out.println();

	}

	/**
	 * Computes the current load factor. Percentage in decimal form of the hash
	 *  table that is full (number of items in table / size of table) 
	 * 
	 * @return current load factor
	 *
	 **/
	private double currentLF(){

		return (((double)numItems)/arr.length);
	}

	/**
	 * Computes length based stats. 
	 * 
	 * @return ret, an array containing maxlength in 0th index, 
	 * 		number of zero length lists in 1, total (sum of list lengths) in 2
	 *
	 **/
	private int [] compStats(){
		int maxLength = 0;
		int numZero = 0;
		int total = 0;

		int [] ret = new int[3];

		for(int i = 0; i < arr.length; i++){
			if(arr[i] == null){
				numZero++;
			}
			else{
				if(arr[i].size() > maxLength){
					maxLength = arr[i].size();
				}

				total += arr[i].size();
			}
		}

		ret[0] = maxLength;
		ret[1] = numZero;
		ret[2] = total;

		return ret;
	}
}
