class Node{
	String Key;
	String Value;
	boolean DataExists = false;
	
	public Node(String Key, String Value, Boolean DataExists) {
		this.Key = Key;
		this.Value = Value;
		this.DataExists = DataExists;
	}
}
class MyHashtTable{
	int tableSize;
	Node[] hashTable;
	int number_of_elements_filled;
	int collision_count;
	
	MyHashtTable(int tableSize) {
		this.tableSize = tableSize;
		this.hashTable = new Node[tableSize];
		this.number_of_elements_filled = 0;
	}
	//Function to add key value pairs to the array.
	void addKeyValue(String Key, String Value, boolean count_collisions_during_rehash) {
		
		//Checking for load factor changes and rehashing if required.
		//Rehashing is performed before adding new key value pair.
		//Load Factor calculation is made assuming new key value pair is already added.
		if (((number_of_elements_filled+1.0)/tableSize)>0.5)
			{
				System.out.println("Rehashing to be done............");
				this.hashTable = RehashAllElements(this.hashTable,count_collisions_during_rehash);
				System.out.println("Rehashing performed. Table size before rehashing: "+this.tableSize+" Table size after rehashing: "+this.hashTable.length);
				this.tableSize = this.hashTable.length;
			}
		
		//Function call to find the position at which respective key is to be inserted.
		int pos = FindPosition(Key, hashTable, tableSize, false);
		if (pos==-1)
		{
			System.out.print("Could not add: "+Key);
			return;
		}
		else if (pos==-2) {
			System.out.println("Duplicate key found: "+Key);
			return;
		}
		else
		{
			Node temp = new Node(Key, Value, true);
			this.number_of_elements_filled++;
			hashTable[pos] = temp;
		}
	}

	//Function to rehash all elements.
	private Node[] RehashAllElements(Node[] tempHashTable, boolean count_collisions_during_rehash) {
		//Function call to find the new table size.
		int newSize = getNewTableSize();
		Node[] newHashTable = new Node[newSize];
		
		for(int i=0;i<tempHashTable.length;i++)
		{
			if((tempHashTable[i]!=null)&&(tempHashTable[i].DataExists))
			{
				//Function call to find the new position at which respective key is to be inserted.
				int pos = FindPosition(tempHashTable[i].Key, newHashTable, newSize, !count_collisions_during_rehash);
				Node temp = new Node(tempHashTable[i].Key, tempHashTable[i].Value, true);
				newHashTable[pos] = temp;
			}
		}
		return newHashTable;
	}

	//Function to determine new table size.
	private int getNewTableSize() {
		int temp = 2*tableSize;
		while(!isPrime(temp)) {
			temp = temp + 1;
		}
		return temp;
	}
	
	//Function to check if a number is prime.
	public static boolean isPrime(int num){
	    if ( num > 2 && num%2 == 0 ) {
	        return false;
	    }
	    int top = (int)Math.sqrt(num) + 1;
	    for(int i = 3; i < top; i+=2){
	        if(num % i == 0){
	            return false;
	        }
	    }
	    return true; 
	}
	
	//Function to find the position at which key is to be inserted.
	private int FindPosition(String key, Node[] tempHashTable, int tableSize, boolean rehashinhg) {
		int pos = -1;
		int currpos = 1;
		boolean breakLoop = false;
		int asciiSum = 0;
		
		//Getting sum of ASCII values of every character of the key.
		for(int i=0;i<key.length();i++)
			asciiSum = asciiSum + ((int) key.charAt(i));
		int hashVal = asciiSum % tableSize;
		
		//Ensuring duplicate keys are not allowed.
		if ((tempHashTable[hashVal]!=null)&&(tempHashTable[hashVal].Key == key))
		{	
			pos = -2; 
		}
		else
		{
			if((tempHashTable[hashVal]==null) || ((tempHashTable[hashVal]!=null)&&(!tempHashTable[hashVal].DataExists)))
			{
				pos = hashVal;
			}
			else 
			{
				while(!breakLoop) {
					//In case of collision, resolution is made using quadratic probing.
					hashVal = (hashVal + ((2*currpos)-1)) % tableSize; //Optimized quadratic probing calculation for square.
					if((tempHashTable[hashVal]==null) || ((tempHashTable[hashVal]!=null)&&(!tempHashTable[hashVal].DataExists)))
					{
						pos = hashVal;
						breakLoop = true;
					}
					if ((tempHashTable[hashVal]!=null)&&(tempHashTable[hashVal].Key == key))
					{	
						pos = -2; 
						breakLoop = true;
					}
					currpos = currpos + 1;
				}
				//Collision detected during rehashing entire array would be counted if required. 
				//But collision detected during normal addition of element will always be counted.
				if ((!rehashinhg)&&(pos>=0))
				{
					this.collision_count=this.collision_count+(currpos-1);
					System.out.println(key+" collided "+(currpos-1)+" times.");
				}
			}
			
		}
		return pos;
	}
}
public class HashTableImplementation {

	public static void main(String[] args) {
		int number_of_keys = 20;
		int initial_table_size = 31;
		boolean count_collisions_during_rehash = true; //Collision detected during rehashing entire array would be counted if required. 
		
		String[] key = new String[number_of_keys];
		String[] Values = new String[number_of_keys];
		MyHashtTable hashTblObj = new MyHashtTable(initial_table_size);
		
		key[number_of_keys-1] = "Hey";
		key[number_of_keys-2] = "There";
		key[number_of_keys-3] = "WhatsUp";
		key[number_of_keys-4] = "Numbers";
		key[number_of_keys-5] = "One1";
		key[number_of_keys-6] = "Two2";
		key[number_of_keys-7] = "Three3";
		key[number_of_keys-8] = "4Four";
		key[number_of_keys-9] = "Hi5";
		key[number_of_keys-10] = "6Sticks";
		key[number_of_keys-11] = "7Eleven";
		key[number_of_keys-12] = "8Byte";
		key[number_of_keys-13] = "9One1";
		key[number_of_keys-14] = "OhGOD";
		key[number_of_keys-15] = "Graphs8";
		key[number_of_keys-16] = "Trees3";
		key[number_of_keys-17] = "Linked12";
		key[number_of_keys-18] = "99Array";
		key[number_of_keys-19] = "Comets";
		key[number_of_keys-20] = "UTD";

		
		Values[number_of_keys-1] = "Value1";
		Values[number_of_keys-2] = "Value2";
		Values[number_of_keys-3] = "Value3";
		Values[number_of_keys-4] = "Value4";
		Values[number_of_keys-5] = "Value5";
		Values[number_of_keys-6] = "Value6";
		Values[number_of_keys-7] = "Value7";
		Values[number_of_keys-8] = "Value8";
		Values[number_of_keys-9] = "Value9";
		Values[number_of_keys-10] = "Value10";
		Values[number_of_keys-11] = "Value11";
		Values[number_of_keys-12] = "Value12";
		Values[number_of_keys-13] = "Value13";
		Values[number_of_keys-14] = "Value14";
		Values[number_of_keys-15] = "Value15";
		Values[number_of_keys-16] = "Value16";
		Values[number_of_keys-17] = "Value17";
		Values[number_of_keys-18] = "Value18";
		Values[number_of_keys-19] = "Value19";
		Values[number_of_keys-20] = "Value20";
		
		for(int i=0;i<number_of_keys;i++)
			hashTblObj.addKeyValue(key[i], Values[i], count_collisions_during_rehash);
		
		System.out.println("\n");
		System.out.println("Initial table size: "+initial_table_size);
		System.out.println("Final table size: "+hashTblObj.tableSize);
		System.out.println("Total Number of collisions: "+hashTblObj.collision_count);
		System.out.println("Number of elements: "+hashTblObj.number_of_elements_filled);
		
		
		System.out.println();
		int asciiSum=0;
		for(int j=0;j<hashTblObj.hashTable.length;j++)
		{
			asciiSum = 0;
			if((hashTblObj.hashTable[j]!=null)&&(hashTblObj.hashTable[j].DataExists))
			{
				for(int i=0;i<hashTblObj.hashTable[j].Key.length();i++)
				{
					asciiSum = asciiSum + ((int) hashTblObj.hashTable[j].Key.charAt(i));
				}
				System.out.println(hashTblObj.hashTable[j].Key + "		at index " + j /*+ " ascii_sum%" + initial_table_size + " " + (asciiSum%initial_table_size)+" ascii_sum%" +hashTblObj.tableSize + " " + (asciiSum%hashTblObj.tableSize)*/);
			}
		}
			
	}

}
