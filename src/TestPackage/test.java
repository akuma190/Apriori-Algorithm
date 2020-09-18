package TestPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int iteration=3;
		HashMap<ArrayList<String>, Integer> frequentItems = new HashMap<ArrayList<String>, Integer>();
        ArrayList<String> arr1=new ArrayList<String>();
        ArrayList<String> arr2=new ArrayList<String>();
        ArrayList<String> arr3=new ArrayList<String>();
        arr1.add("Honees");
        arr1.add("Nodoz");
        arr2.add("Heliocare");
        arr2.add("Precise");
        arr3.add("FacialRazor");
        arr3.add("Solstice");
        frequentItems.put(arr1,2);
        frequentItems.put(arr2,2);
        frequentItems.put(arr3,3);
        System.out.println(frequentItems);
        
		ArrayList<ArrayList<String>> list=new ArrayList<ArrayList<String>>();
		for(ArrayList<String> str1:frequentItems.keySet()) {
			//list.add(new ArrayList<String>());
			list.add(str1);
		}
		System.out.println(list);
		frequentItems.clear();
		//System.out.println(frequentItems);
		HashSet<String> hashInterface=new HashSet<String>();
		for(int i=0;i<list.size();i++) {
			for(int j=i+1;j<list.size();j++) {
				for(int k=0;k<list.get(i).size();k++) {
					hashInterface.add(list.get(i).get(k));
				}
				for(int k=0;k<list.get(j).size();k++) {
					hashInterface.add(list.get(j).get(k));
				}
				System.out.println(hashInterface);
				if(hashInterface.size()==iteration) {
					ArrayList<String> newList=new ArrayList<String>();
					for(String stri:hashInterface) {
						newList.add(stri);
					}
					Collections.sort(newList);
					frequentItems.put(newList,0);
				}
				hashInterface.clear();
			}
		}
		System.out.println(frequentItems.size());
//		for (Map.Entry element : frequentItems.entrySet()) {
//			System.out.println(element.getKey() + "   " + element.getValue());
//		}
	}

}
