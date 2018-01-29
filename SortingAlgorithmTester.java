 /**
 * CS 331
 * Professor: Tannaz Rezaei
 *
 * Project #1 - Task #1
 * 
 * Description: program exchange sort, merge sort and quick sort to sort a list of n elements. 
 * carry out test with n = 10,000, 20,000, 50,000, 100,000... and so on until 10 minutes is required
 * or computer cannot handle size of n.  
 *
 * @author - Eric Schenck
 * last modified: February 1, 2018
 *   
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class SortingAlgorithmTester {
	
	private static long tenMinutes = 1000*60*10;								// ms -> 10 mins
	public static boolean runTimeUnderTenMins = true;							// setting boolean true initialy
	
	/**
	 * exchange sort
	 * @param list
	 * @return
	 */
	public static int[] exchangeSort( int[] list){
		
		for(int i = 0; i < list.length; ++i){					// for loop for first index					
			for(int j = (i+1); j < list.length ; ++j){			// for loop for second index
				
				if(list[i] > list[j]){							// if first item is more than second item
					swap(list, i, j);							// swap to obtain non-decreasing sorted list					
				}
			}
		}
		return list;											// returning sorted list
	}

	/**
	 * merge sort
	 * @param list
	 * @param first
	 * @param last
	 * @return
	 */
	public static int[] mergeSort(int[] list, int first, int last){
				
			if ((last - first) == 0) {							// Base case, continue unitl index is same
			return new int[] { list[last] };			
		}
			int mid = (first + last) / 2 ; 						// getting a mid index
			
			int[] subList1 = mergeSort(list, first, mid);		// recursive call that divides list into subList (leftside)
			int[] subList2 = mergeSort(list, mid + 1 , last);	// recursive call that divides rigth list into subList
			
			return merge(subList1, subList2);					// merging subLists 
	}
	
	/**
	 * merge is used by mergeSort to merge two sublists into one list
	 * @param subList1
	 * @param subList2
	 * @return
	 */
	public static int[] merge(int[] subList1, int[] subList2){
		
		try{
			
		
		int[] result = new int[subList1.length + subList2.length];	// making a result array size of sum of both arrays
	
		int sub1First = 0;											// index of subList1
		int sub2First = 0;											// index of subList2
		
		for (int i = 0; i < result.length; ++i){					// going through arrays, size of result array
			
			if (sub1First == subList1.length){						// subList1 has been accounted for 
				result[i] = subList2[sub2First++];	  				// so copy subList2 to result array
			}else if( sub2First == subList2.length){				// SubList 2 is finished
				result[i] = subList1[sub1First++];					// so copy whats left of sublist1 into result[]
			}else if( subList1[sub1First] <= subList2[sub2First]){	// subList1 entry is less than sublist2 entry
				result[i] = subList1[sub1First++];					// adding entry from subList1 into result[]
			}else {													// subList entry is more than sublist2 entry
				result[i] = subList2[sub2First++];					// adding entry from subList2 into result[]
			}
		}
		
		return result;
		}catch(Exception e ){
			return null;
		}
	}
	
	
	
		
	/**
	 * quickSort()
	 * 
	 * @param list
	 * @param first
	 * @param last
	 * @return
	 */
	public static int[] quickSort(int[] list , int first, int last){
		
		if(first < last){												// as long as first index is less than last index
			
			int pivotIndex = partitionList(list, first, last);			// getting a pivotIndex and partitioning list around pivot
			
			quickSort(list, first, pivotIndex - 1);						// recursively calling quickSort on lower(left) half of list
			quickSort(list, pivotIndex + 1, last);						// recursively calling quickSort on upper(right) half of list
		}
		return list;													//returning sorted list
	}
	
	/**
	 * partition list for quickSort()
	 * 
	 * @param list
	 * @param first
	 * @param last
	 * @return
	 */
	public static int partitionList(int[] list, int first, int last){
		
		int lastAssignedPos = first;									// last Assigned position used track last index assigned
		
		int pivot = list[last];											// choosing last element as pivot
		
		for(int i = first; i < last ; ++i){								// goes through array
			if(list[i] <= pivot){										// if current element is less than pivot 
				swap(list, lastAssignedPos, i);							// swapping elements so current is on left of pivot
				++lastAssignedPos;										// increasing lastAssignedPos index
			}
		}
		
		swap(list, last, lastAssignedPos);								// swapping pivot item with last item thats larger than it
		return lastAssignedPos;											// returning last Assigned position index
	}
	
	/**
	 * Code to swap items in list
	 * 
	 * @param list
	 * @param i
	 * @param j
	 */
	public static void swap(int[] list, int i, int j){							
		int temp = list[i];												// swapping elements 
		list[i] = list[j];
		list[j] = temp;		
	}
	
	
	public static void main(String[] args) throws IOException {
	
		
		// exchangeSort 
		
		String exchangeSortFileName = "exchangeSortData.txt";					// filename
		
		PrintWriter exchangeSortOutputFile = new PrintWriter(exchangeSortFileName);
		
		
		Random rand1 = new Random();										// used to generate random list of integers
		
			exchangeSortOutputFile.println("Currently Running - exchangeSort()" );	// used to make better sense of output data
			exchangeSortOutputFile.println("-----------------------------------");
			exchangeSortOutputFile.println(" n   |    RunTime (s)             |");
			
			int n1 = 10000;												// n starts at size 10,000
		
		
			while(runTimeUnderTenMins){
			
	
				int[] tempArray = new int[n1];								// creating an array to store list of unsorted integers
			
				for(int i = 0; i < n1 ; ++i){								// for loop to fill array with random integers
					tempArray[i] = rand1.nextInt(9999) + 1;					// filling array with integers 1 - 10000 
				}
			
				Timer myTimer = new Timer();								// creating timer
				myTimer.schedule(new TimerTask() {								// scheduling for 10 mins
					public void run(){
						runTimeUnderTenMins = false;						// setting false
						myTimer.cancel();	
					}
				}, tenMinutes);
			
				long initTime = System.currentTimeMillis();					// getting current time in ms
			
				tempArray = exchangeSort(tempArray);						// calling Sort on tempArray
				
				long endTime = System.currentTimeMillis();					// getting current time in ms
			
				exchangeSortOutputFile.print(n1);
				exchangeSortOutputFile.println("  |  time: " + ((endTime - initTime) / 1000.0) + " secs");	// printing runtime to outputfile
			
				if(n1 >= 100000){
					n1 += 100000;
				}else if(n1 == 50000){													// if n == 50000 
					n1 = 100000;
				}else if(n1 == 10000){											// if n == 10000 then make n = 20000
					n1 = 20000;
				}else if(n1 == 20000){											// if n == 20000 then make n = 50000
					n1 = 50000;
				}
				
															// cancel timer so it doesnt keep running
				myTimer.cancel();
			
			}
		
		
		exchangeSortOutputFile.close();
		
		System.out.println("ExchangeSort Completed");
		
		// quickSort
		
		String quickSortFileName = "quickSortData.txt";					// filename for quicksort data
		
		PrintWriter quickSortOutputFile = new PrintWriter(quickSortFileName);
		
		
		Random rand2 = new Random();										// used to generate random list of integers
		
			quickSortOutputFile.println("Currently Running - QuickSort()" );	// used to make better sense of output data
			quickSortOutputFile.println("-----------------------------------");
			quickSortOutputFile.println(" n   |    RunTime (s)             |");
			
			int n2 = 10000;												// n starts at size 10,000
		
			runTimeUnderTenMins = true;									// setting true for quickSort to begin
			
			while(runTimeUnderTenMins){
			
	
				int[] tempArray = new int[n2];								// creating an array to store list of unsorted integers
			
				for(int i = 0; i < n2 ; ++i){								// for loop to fill array with random integers
					tempArray[i] = rand2.nextInt(9999) + 1;					// filling array with integers 1 - 10000 
				}
			
			
			
			
				Timer myTimer1 = new Timer();								// creating new timer 
				myTimer1.schedule(new TimerTask() {								// scheduling for 10 mins
					public void run(){
						runTimeUnderTenMins = false;						// setting to false
						myTimer1.cancel();
					}
				}, tenMinutes);
			
				long initTime = System.currentTimeMillis();					// getting current time in ms
			
				tempArray = quickSort(tempArray, 0, tempArray.length -1);
			
				long endTime = System.currentTimeMillis();					// getting current time in ms
			
				quickSortOutputFile.print(n2);
				quickSortOutputFile.println("  |  time: " + ((endTime - initTime) / 1000.0) + " secs");	// printing runtime to outputfile
			
			
				if(n2 >= 1000000){
					n2 += 5000000;
				}else if(n2 == 100000){
					n2 += 1000000;
				}else if(n2 == 50000){													// if n == 50000 then n *= 2
					n2 = 100000;
				}else if(n2 == 10000){											// if n == 10000 then make n = 20000
					n2 = 20000;
				}else if(n2 == 20000){											// if n == 20000 then make n = 50000
					n2 = 50000;
				}
				
															// cancel timer so it doesnt keep running
				myTimer1.cancel();
			
			}
		
		
		quickSortOutputFile.close();
		

		System.out.println("quickSort Completed");
		
		// merge sort
		
		String fileName = "MergeSortData.txt";
		
		PrintWriter outputFile = new PrintWriter(fileName);
		
		
		Random rand = new Random();										// used to generate random list of integers
		
			outputFile.println("Currently Running - MergeSort()" );	// used to make better sense of output data
			outputFile.println("-----------------------------------");
			outputFile.println(" n   |    RunTime (s)             |");
			
			int n = 10000;												// n starts at size 10,000
		
		try{
			

			runTimeUnderTenMins = true;									// setting true for mergeSort to begin
		
			
			A: while(runTimeUnderTenMins){
			
				if(n > 136000000){
					break A;
				}
				
				int[] tempArray = new int[n];								// creating an array to store list of unsorted integers
			
				for(int i = 0; i < n ; ++i){								// for loop to fill array with random integers
					tempArray[i] = rand.nextInt(9999) + 1;					// filling array with integers 1 - 10000 
				}
			
			
			
			
				Timer myTimer2 = new Timer();								// creating timer object
				myTimer2.schedule(new TimerTask() {								// scheduling for 10 mins
					public void run(){										// running code on timer 
						runTimeUnderTenMins = false;						// set to false
						myTimer2.cancel();									// cancel timer, use is over
					}
				}, tenMinutes);
			
				long initTime = System.currentTimeMillis();					// getting current time in ms
			
																			// calling Sort on tempArray
				tempArray = mergeSort(tempArray, 0, tempArray.length -1);
				
				long endTime = System.currentTimeMillis();					// getting current time in ms
			
				outputFile.print(n);
				outputFile.println("  |  time: " + ((endTime - initTime) / 1000.0) + " secs");	// printing runtime to outputfile
			
				if(n >= 1000000){
					n += 5000000;
				}else if(n == 100000){
					n += 1000000;
				}else if(n == 50000){													// if n == 50000 then n *= 2
					n = 100000;
				}else if(n == 10000){											// if n == 10000 then make n = 20000
					n = 20000;
				}else if(n == 20000){											// if n == 20000 then make n = 50000
					n = 50000;
				}
															// cancel timer so it doesnt keep running
				myTimer2.cancel();
				
			}
		}catch(Exception e){
			outputFile.println("outOfMemoryError");
		}
		
		outputFile.close();									// closing file
		
		

		System.out.println("MergeSort Completed");
			
	}
}
