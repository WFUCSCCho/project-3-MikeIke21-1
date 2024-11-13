import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Proj3 {
    // Sorting Method declarations
    ///WORKING
    // Merge Sort
    public static <T extends Comparable> void mergeSort(ArrayList<T> a, int left, int right) {

        if (left >= right) {
            return;//base
        }

        int mid = ((left + right) / 2 );
        mergeSort(a,left,mid);
        mergeSort(a,mid+1,right);
        merge(a,left,mid,right);


    }

    ///WORKING 
    //make this first to call it in mergesort
    public static <T extends Comparable> void merge(ArrayList<T> a, int left, int mid, int right) {
       //need 2 new arraylist
         ArrayList<T> tempLeft = new ArrayList<>(a.subList(left,mid + 1));
         ArrayList<T> tempRight = new ArrayList<>(a.subList(mid + 1,right+1));



        //initialize index for left, right list and one to keep track of the original list which we are inputting our 2 list into
        int leftIndex = 0;
        int rightIndex = 0;
        int originalIndex = left;

        //merging the list
        while (leftIndex < tempLeft.size() && rightIndex < tempRight.size()) {

            //right > left so we add left to the list
            if (tempLeft.get(leftIndex).compareTo(tempRight.get(rightIndex)) <= 0) {
                a.set(originalIndex,tempLeft.get(leftIndex));
                leftIndex++;
            }
            else {
                a.set(originalIndex,tempRight.get(rightIndex));
                rightIndex++;
            }
            originalIndex++;


        }

        //remaining elements in left or right list
        while (leftIndex < tempLeft.size()) {
            a.set(originalIndex, tempLeft.get(leftIndex));
            originalIndex++;
            leftIndex++;
        }
        while (rightIndex < tempRight.size()) {
            a.set(originalIndex, tempRight.get(rightIndex));
            originalIndex++;
            rightIndex++;
        }


    }



    ///WORKING
    // Quick Sort
    public static <T extends Comparable> void quickSort(ArrayList<T> a, int left, int right) {
        if (left >= right ){return; //base
        }

        //get partion
        int pivotIndex = partition(a,left,right);
        //sort left
        quickSort(a,left,pivotIndex-1);
        //sort right
        quickSort(a,pivotIndex+1,right);

    }

    ///WORKING
    public static <T extends Comparable> int partition (ArrayList<T> a, int left, int right) {
        T pivot = a.get(right); //make pivot rightmost element
        int index = left; //track leftmost

        for (int i = left; i <= right-1; i++) {//iterate through arraylist
            if (a.get(i).compareTo(pivot) < 0) {
                swap(a,i,index);
                index++;
            }
        }

        //put pivot at index position
        swap(a,index,right);
        return index;//return pivot index



        }


    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    // Heap Sort
    public static <T extends Comparable> void heapSort(ArrayList<T> a, int left, int right) {
        // first we need to make a max heap
        int n = a.size();


        for (int i = right/2 -1 ; i >= left; i--) {
            heapify(a, n, i);
        }

        //extract from heap one by one
        for (int i = right - 1; i >= left; i--) {
            // Move the current root (largest) to the end
            swap(a, left, i);

            // Call heapify on the reduced heap
            heapify(a, i, left);
        }




    }

    //do this first
    public static <T extends Comparable> void heapify (ArrayList<T> a, int left, int right) {

        //initializing all the indexes we need
        int largest = right;         // Initialize largest as root
        int leftChild = 2 * right + 1;  // Left child index
        int rightChild = 2 * right + 2; // Right child index

        // Check if left child is within bounds and is greater than root
        if (leftChild < left && a.get(leftChild).compareTo(a.get(largest)) > 0) {
            largest = leftChild;
        }

        // Check if right child is within bounds and is greater than the largest so far
        if (rightChild < left && a.get(rightChild).compareTo(a.get(largest)) > 0) {
            largest = rightChild;
        }

        // If largest is not root, swap and recursively heapify the affected subtree
        if (largest != right) {
            swap(a, right, largest);
            heapify(a, left, largest); // Recursively heapify the affected subtree
        }


    }

    ///WORKING
    // Bubble Sort
    public static <T extends Comparable> int bubbleSort(ArrayList<T> a, int size) {
        boolean swapped = false;
        int comparisonCount = 0;

        //outer loop
        while (size >= 1) {

            //inner forloop
            //swap and make bool true
            for (int i = 1; i < size; i++) {
                comparisonCount++;
                if (a.get(i - 1).compareTo(a.get(i)) > 0) {
                    swapped = true;
                    swap(a, i, i - 1);
                }
            }
            if (!swapped) {
                return comparisonCount;
            }
            size = size - 1;
        }
            return comparisonCount;
    }

    ///WORKING
    // Odd-Even Transposition Sort
    public static <T extends Comparable> int transpositionSort(ArrayList<T> a, int size) {
        boolean isSorted = false;
        int comparisonCount = 0;

        while (!isSorted) {
            isSorted = true;
            //even loop
            for (int i = 0; i < size-1; i=i+2) {
                //running a bubble
                comparisonCount++;
                if (a.get(i).compareTo(a.get(i+1)) > 0) {
                    swap(a, i, i+1);
                    isSorted = false;
                }
            }
            //oddloop
            for (int i = 1; i < size-1; i=i+2) {
                //running a bubble
                comparisonCount++;
                if (a.get(i).compareTo(a.get(i+1)) > 0) {
                    swap(a, i, i+1);
                    isSorted = false;
                }
            }
        }
        return comparisonCount;
    }


    ///NEEDS A LOT
    public static void main(String [] args)  throws IOException {
        //
        // Use command line arguments to specify the input file
        if (args.length != 3) {
            System.err.println("Proj3 {dataset-file} {sorting-algorithm-type} {number-of-lines}");
            System.exit(1);
        }


        String inputFileName = args[0];
        String algo = args[1];
        int numLines = Integer.parseInt(args[2]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        ArrayList dataArrList = new ArrayList();
        ArrayList<player> playerArrList = new ArrayList();


        //adding to the Arraylist
        while (inputFileNameScanner.hasNextLine()) {
            dataArrList.add(inputFileNameScanner.nextLine());
        }
        //making arraylist data player data

        String playerStats;
        String[] parsedStats;
        for (int i = 0; i < numLines-1; i++) {
            playerStats = (String) dataArrList.get(i);
            parsedStats = playerStats.split(",");
            if (parsedStats.length != 14){
                //System.out.println(playerStats.length);
            }
            //removes the first line
            else if (parsedStats[0].equals("overall_pick")){

            }
            else {
                playerArrList.add(new player(Integer.parseInt(parsedStats[0]),Integer.parseInt(parsedStats[1]),parsedStats[2],parsedStats[3],parsedStats[4],Integer.parseInt(parsedStats[6]),
                        Integer.parseInt(parsedStats[7]),Double.parseDouble(parsedStats[8]),Double.parseDouble(parsedStats[9]),Double.parseDouble(parsedStats[10]),Double.parseDouble(parsedStats[11]),Double.parseDouble(parsedStats[12]),Double.parseDouble(parsedStats[13])));
            }


        }

        ///*ALL METHODS ARE WORKING DATA IS IN PLAYERARRLIST*///
        ///*Make 3 list (sorted,shuffled and reversed)*/// DONE
        ///*RUN and time all algos*/// DONE
        ///*Export timing to new file */// DONE
        ///*Collect data and graph*///
        //TESTING SECTION

        //SORTED ARR LIST
        ArrayList sortedArrList = new ArrayList();
        sortedArrList.addAll(playerArrList);
        Collections.sort(sortedArrList);

        //random ARR List
        ArrayList randArrList = new ArrayList();
        randArrList.addAll(playerArrList);
        Collections.shuffle(randArrList);

        //REVERSE ARR LIST
        ArrayList revArrList = new ArrayList();
        revArrList.addAll(playerArrList);
        Collections.sort(playerArrList, Collections.reverseOrder());



        //BUBBLESORT IMPLEMENTATION
        if (algo.equals("bubbleSort")||algo.equals("BubbleSort")) {
            int comparisonSorted = 0;
            int comparisonRand = 0;
            int comparisonRev = 0;


            //timing and running bubble sort on all 3 list
            //sorted
            long sortedBubStart = System.nanoTime();
            comparisonSorted = bubbleSort(sortedArrList, sortedArrList.size());
            long sortedBubEnd = System.nanoTime();

            long sortedBubTime = sortedBubEnd - sortedBubStart;

            //rand
            long randBubStart = System.nanoTime();
            comparisonRand = bubbleSort(randArrList, randArrList.size());
            long randBubEnd = System.nanoTime();

            long randBubTime = randBubEnd - randBubStart;

            //reversed
            long revBubStart = System.nanoTime();
            comparisonRev = bubbleSort(revArrList, revArrList.size());
            long revBubEnd = System.nanoTime();

            long revBubTime = revBubEnd - revBubStart;

            //implement a filewriter
           writeTofile("======BUBBLESORT======", "analysis.txt");
            writeTofile("LINES Evaluated: " + args[2],"analysis.txt");
            writeTofile("---------------------", "analysis.txt");
            writeTofile("SortedList Time: " + sortedBubTime, "analysis.txt");
            writeTofile("RandomList Time: " + randBubTime, "analysis.txt");
            writeTofile("ReverseList Time: " + revBubTime, "analysis.txt");
            writeTofile("---------------------", "analysis.txt");
            writeTofile("SortedList Comparisons: " + comparisonSorted, "analysis.txt");
            writeTofile("RandomList Comparisons: " + comparisonRand, "analysis.txt");
            writeTofile("ReverseList Comparisons: " + comparisonRev, "analysis.txt");

            bubbleSort(playerArrList,playerArrList.size());
            writeTofile(playerArrList.toString(),"sorted.txt");



        }

        //ODD EVEN TRANSPOSITION IMPLEMENTATION
        if (algo.equals("transpositionSort")||algo.equals("TranspositionSort")) {
            int comparisonSorted = transpositionSort(sortedArrList, sortedArrList.size());
            int comparisonRand = transpositionSort(randArrList, randArrList.size());
            int comparisonRev = transpositionSort(revArrList, revArrList.size());

            writeTofile("======TRANSPOSITIONSORT======", "analysis.txt");
            writeTofile("LINES Evaluated: " + args[2],"analysis.txt");
            writeTofile("---------------------", "analysis.txt");
            writeTofile("SortedList Comparisons: " + comparisonSorted, "analysis.txt");
            writeTofile("RandomList Comparisons: " + comparisonRand, "analysis.txt");
            writeTofile("ReverseList Comparisons: " + comparisonRev, "analysis.txt");

            transpositionSort(playerArrList,playerArrList.size());
            writeTofile(playerArrList.toString(),"sorted.txt");

        }


        //MERGE SORT IMPLEMENTATION
        if (algo.equals("mergeSort")||algo.equals("MergeSort")) {
            //sorted
            long sortedMergeStart = System.nanoTime();
            mergeSort(sortedArrList,0,sortedArrList.size()-1);
            long sortedMergeEnd = System.nanoTime();

            long sortedMergeTime = sortedMergeEnd - sortedMergeStart;

            //rand
            long randMergeStart = System.nanoTime();
            mergeSort(randArrList,0,randArrList.size()-1);
            long randMergeEnd = System.nanoTime();

            long randMergeTime = randMergeEnd - randMergeStart;

            //reversed
            long revMergeStart = System.nanoTime();
            mergeSort(revArrList,0,revArrList.size()-1);
            long revMergeEnd = System.nanoTime();

            long revMergeTime = revMergeEnd - revMergeStart;

            writeTofile("======MERGESORT======", "analysis.txt");
            writeTofile("LINES Evaluated: " + args[2],"analysis.txt");
            writeTofile("---------------------", "analysis.txt");
            writeTofile("SortedList Time: " + sortedMergeTime, "analysis.txt");
            writeTofile("RandomList Time: " + randMergeTime, "analysis.txt");
            writeTofile("ReverseList Time: " + revMergeTime, "analysis.txt");


            mergeSort(playerArrList,0,playerArrList.size()-1);
            writeTofile(playerArrList.toString(),"sorted.txt");
        }

        //Quick SORT IMPLEMENTATION
        if (algo.equals("quickSort")||algo.equals("QuickSort")) {
            //sorted
            long sortedQuickStart = System.nanoTime();
            quickSort(sortedArrList,0,sortedArrList.size()-1);
            long sortedQuickEnd = System.nanoTime();

            long sortedQuickTime = sortedQuickEnd - sortedQuickStart;

            //rand
            long randQuickStart = System.nanoTime();
            quickSort(randArrList,0,randArrList.size()-1);
            long randQuickEnd = System.nanoTime();

            long randQuickTime = randQuickEnd - randQuickStart;

            //reversed
            long revQuickStart = System.nanoTime();
            quickSort(revArrList,0,revArrList.size()-1);
            long revQuickEnd = System.nanoTime();

            long revQuickTime = revQuickEnd - revQuickStart;

            writeTofile("======QUICKSORT======", "analysis.txt");
            writeTofile("LINES Evaluated: " + args[2],"analysis.txt");
            writeTofile("---------------------", "analysis.txt");
            writeTofile("SortedList Time: " + sortedQuickTime, "analysis.txt");
            writeTofile("RandomList Time: " + randQuickTime, "analysis.txt");
            writeTofile("ReverseList Time: " + revQuickTime, "analysis.txt");


            quickSort(playerArrList,0,playerArrList.size()-1);
            writeTofile(playerArrList.toString(),"sorted.txt");
        }

        //HEAPSORT IMPLEMENTATION
        if (algo.equals("heapSort")||algo.equals("HeapSort")) {
            //sorted
            long sortedHeapStart = System.nanoTime();
            heapSort(sortedArrList,0,sortedArrList.size()-1);
            long sortedHeapEnd = System.nanoTime();

            long sortedHeapTime = sortedHeapEnd - sortedHeapStart;

            //rand
            long randHeapStart = System.nanoTime();
            heapSort(randArrList,0,randArrList.size()-1);
            long randHeapEnd = System.nanoTime();

            long randHeapTime = randHeapEnd - randHeapStart;

            //reversed
            long revHeapStart = System.nanoTime();
            heapSort(revArrList,0,revArrList.size()-1);
            long revHeapEnd = System.nanoTime();

            long revHeapTime = revHeapEnd - revHeapStart;

            writeTofile("======HEAPSORT======", "analysis.txt");
            writeTofile("LINES Evaluated: " + args[2],"analysis.txt");
            writeTofile("---------------------", "analysis.txt");
            writeTofile("SortedList Time: " + sortedHeapTime, "analysis.txt");
            writeTofile("RandomList Time: " + randHeapTime, "analysis.txt");
            writeTofile("ReverseList Time: " + revHeapTime, "analysis.txt");

            heapSort(playerArrList,0,playerArrList.size());
            writeTofile(playerArrList.toString(),"sorted.txt");


        }

        ///DO THIS
       // In addition, your program will print out the sorted lists to a file named `sorted.txt`. Each time the program runs, it
       // will overwrite the previous sorted lists in `sorted.txt`.




















    }

    //FILEWRITER
    private static void writeTofile(String content, String filePath) throws IOException {
        FileWriter file  = new FileWriter(filePath, true);
        file.write(content + System.lineSeparator());
        file.close();
    }


}
