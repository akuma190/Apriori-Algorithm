package TestPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<ArrayList<String>,Integer> hash=new HashMap<ArrayList<String>,Integer>();
		ArrayList<String> arr=new ArrayList<String>();
		ArrayList<String> arr1=new ArrayList<String>();
		arr.add("Ashish");
		arr.add("Kumar");
		arr1.add("Kumar");
		arr1.add("Ashish");
		Collections.sort(arr1);
        hash.put(arr,2);
        hash.put(arr1,3);
        System.out.println(hash);
	}

}
