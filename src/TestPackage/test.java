package TestPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] str = { "Amberen", "Honees", "FacialRazor", "PJmasks", "Compound", "Soltice", "Nodoz", "Solstice",
				"Titan", "Nodozer", "Precise","Leader","Heliocare","Natural","Oster","Yogourmet"};
		int[] arr = {4,3,3,4,2,2,2,4,2,2,4,4,2,2,2,2};
		int iteration=2;
		HashMap<ArrayList<String>, Integer> frequentItems = new HashMap<ArrayList<String>, Integer>();
		for(int i=0;i<str.length;i++) {
			ArrayList<String> arr1=new ArrayList<String>();
			arr1.add(str[i]);
			frequentItems.put(arr1,arr[i]);
		}
		//System.out.println(frequentItems);
		
		ArrayList<ArrayList<String>> list=new ArrayList<ArrayList<String>>();
		for(ArrayList<String> str1:frequentItems.keySet()) {
			//list.add(new ArrayList<String>());
			list.add(str1);
		}
		System.out.println(list.size());
		frequentItems.clear();
		System.out.println(frequentItems);
		HashSet<String> hashInterface=new HashSet<String>();
		for(int i=0;i<list.size();i++) {
			for(int j=i+1;j<list.size();j++) {
				for(int k=0;k<list.get(i).size();k++) {
					hashInterface.add(list.get(i).get(k));
				}
				for(int k=0;k<list.get(j).size();k++) {
					hashInterface.add(list.get(j).get(k));
				}
				if(hashInterface.size()==2) {
					ArrayList<String> newList=new ArrayList<String>();
					for(String stri:hashInterface) {
						newList.add(stri);
					}
					frequentItems.put(newList,0);
				}
				hashInterface.clear();
			}
		}
		System.out.println(frequentItems.size());
		for (Map.Entry element : frequentItems.entrySet()) {
			System.out.println(element.getKey() + "   " + element.getValue());
		}
	}

}
