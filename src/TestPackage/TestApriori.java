package TestPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class TestApriori {
	public static HashMap<ArrayList<String>, Integer> frequentItems = new HashMap<ArrayList<String>, Integer>();
	public static HashMap<ArrayList<String>, Integer> nonFrequentItems = new HashMap<ArrayList<String>, Integer>();

	public static void main(String[] args) {
		int support = 2;
		int confidence = 2;
		String[] dataBases = { "DataBase1.csv", "DataBase2.csv", "DataBase3.csv", "DataBase4.csv", "DataBase5.csv" };
		HashMap<ArrayList<String>, Integer> dataBase1 = new HashMap<ArrayList<String>, Integer>();
		HashMap<ArrayList<String>, Integer> dataBase2 = new HashMap<ArrayList<String>, Integer>();
		HashMap<ArrayList<String>, Integer> dataBase3 = new HashMap<ArrayList<String>, Integer>();
		HashMap<ArrayList<String>, Integer> dataBase4 = new HashMap<ArrayList<String>, Integer>();
		HashMap<ArrayList<String>, Integer> dataBase5 = new HashMap<ArrayList<String>, Integer>();
		// String[] hashList = { "DataBase1", "DataBase2", "DataBase3", "DataBase4",
		// "DataBase5" };
		HashSet<String> uniqueFeatures = new HashSet<String>();

		// reading the database1 and getting the unique elements
		System.out.println("------------------Reading DataBase1---------");
		readDataBases(dataBase1, dataBases[0]);
		getUniqueElements(dataBase1, uniqueFeatures);
		generateAssociation(dataBase1, uniqueFeatures, support, confidence);

		// reading the database2 and getting the unique elements
		//System.out.println("------------------Reading DataBase2---------");
//		readDataBases(dataBase2, dataBases[1]);
//		getUniqueElements(dataBase1, uniqueFeatures);
//		generateAssociation(dataBase1, uniqueFeatures, support, confidence);

		// reading the database2 and getting the unique elements
		//System.out.println("------------------Reading DataBase3---------");
//		readDataBases(dataBase3, dataBases[2]);
//		getUniqueElements(dataBase1, uniqueFeatures);
//		generateAssociation(dataBase1, uniqueFeatures, support, confidence);

		// reading the database4 and getting the unique elements
//		System.out.println("------------------Reading DataBase4---------");
//		readDataBases(dataBase4, dataBases[3]);
//		getUniqueElements(dataBase1, uniqueFeatures);
//		generateAssociation(dataBase1, uniqueFeatures, support, confidence);

		// reading the database5 and getting the unique elements
//		System.out.println("------------------Reading DataBase5---------");
//		readDataBases(dataBase5, dataBases[4]);
//		getUniqueElements(dataBase1, uniqueFeatures);
//		generateAssociation(dataBase1, uniqueFeatures, support, confidence);

	}

	public static void readDataBases(HashMap<ArrayList<String>, Integer> dataBase, String fileName) {
		try {
			File file = new File(fileName);
			String[] factors;

			Scanner fileData = new Scanner(file);
			while (fileData.hasNext()) {
				String data = fileData.next();
				// System.out.println(data);
				factors = data.split(",");
				ArrayList<String> itemsList = new ArrayList<String>();
				for (int i = 1; i < factors.length; i++) {
					itemsList.add(factors[i]);
				}
				// System.out.println(factors[0]+"="+itemsList);
				dataBase.put(itemsList, Integer.parseInt(factors[0]));

			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

	}

	public static void getUniqueElements(HashMap<ArrayList<String>, Integer> dataBase, HashSet<String> hashList) {
		for (ArrayList<String> arr : dataBase.keySet()) {
			for (String str : arr) {
				hashList.add(str);
			}
		}
	}

	public static void generateAssociation(HashMap<ArrayList<String>, Integer> dataBase, HashSet<String> hashList,
			int support, int confidence) {

		HashMap<ArrayList<String>, Integer> dynamicMapping = new HashMap<ArrayList<String>, Integer>();
		// ArrayList<String> transfer=new ArrayList<String>();
		for (String str : hashList) {
			ArrayList<String> transfer = new ArrayList<String>();// try improving effi here
			transfer.add(str);
			dynamicMapping.put(transfer, 0);
			// transfer.remove(0);
		}

		int iteration = 1;
//		while(dynamicMapping.size()!=1) {
//			
//		}0
		calculateSupport(dataBase, dynamicMapping, iteration);
		validateSupport(dynamicMapping, support, iteration);

		iteration = iteration + 1;
		makeNewFrequentList(dynamicMapping, iteration);
		calculateSupport(dataBase, dynamicMapping, iteration);
		validateSupport(dynamicMapping, support, iteration);

		iteration = iteration + 1;
		makeNewFrequentList(dynamicMapping, iteration);
		System.out.println(frequentItems);
		System.out.println(dynamicMapping.size());
		if (dynamicMapping.size() == 0) {
			makeFinalCalculation(iteration, confidence, support);
		}
	}

	public static void calculateSupport(HashMap<ArrayList<String>, Integer> dataBase,
			HashMap<ArrayList<String>, Integer> dynamicMapping, int iteration) {

		boolean result = true;
		int length = 0;
		int count = 0;
		for (ArrayList<String> outer : dynamicMapping.keySet()) {
			for (ArrayList<String> inner : dataBase.keySet()) {
				length = outer.size();
				for (String str : outer) {
					if (inner.contains(str)) {
						count = count + 1;
					}
				}
				if (count == length) {
					// System.out.println(outer);
					dynamicMapping.put(outer, dynamicMapping.get(outer) + 1);
				}
				count = 0;
			}

		}

//		System.out.println("The support count of the elements after " + iteration + " iteration");
//		for (Map.Entry element : dynamicMapping.entrySet()) {
//			System.out.println(element.getKey() + "   " + element.getValue());
//		}
	}

	public static void validateSupport(HashMap<ArrayList<String>, Integer> dynamicMapping, int support, int iteration) {
		// checking the non valid support values
		for (ArrayList<String> arr : dynamicMapping.keySet()) {
			// System.out.println(dynamicMapping.get(arr));
			if (dynamicMapping.get(arr) < support) {
				// System.out.println("hi");
				nonFrequentItems.put(arr, dynamicMapping.get(arr));
				// dynamicMapping.remove(arr);
			} else {
				frequentItems.put(arr, iteration);
			}
		}

		// removing the nonvalid support items from the list
		for (ArrayList<String> arr : nonFrequentItems.keySet()) {
			if (dynamicMapping.containsKey(arr)) {
				dynamicMapping.remove(arr);
			}
		}

		System.out.println("--------------------------------------------------------");
		System.out.println("The new frequent items after " + iteration + " iteration");
		for (Map.Entry element : dynamicMapping.entrySet()) {
			System.out.println(element.getKey() + "   " + element.getValue());
		}
	}

	public static void makeNewFrequentList(HashMap<ArrayList<String>, Integer> dynamicMapping, int iteration) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> str1 : dynamicMapping.keySet()) {
			// list.add(new ArrayList<String>());
			list.add(str1);
		}
		// System.out.println(list.size());
		dynamicMapping.clear();
		// System.out.println(dynamicMapping);
		HashSet<String> hashInterface = new HashSet<String>();
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				for (int k = 0; k < list.get(i).size(); k++) {
					hashInterface.add(list.get(i).get(k));
				}
				for (int k = 0; k < list.get(j).size(); k++) {
					hashInterface.add(list.get(j).get(k));
				}
				if (hashInterface.size() == iteration) {
					ArrayList<String> newList = new ArrayList<String>();
					for (String stri : hashInterface) {
						newList.add(stri);
					}
					Collections.sort(newList);
					dynamicMapping.put(newList, 0);
				}
				hashInterface.clear();
			}
		}

//		System.out.println(dynamicMapping.size());
//		for (Map.Entry element : dynamicMapping.entrySet()) {
//			System.out.println(element.getKey() + "   " + element.getValue());
//		}
	}

	public static void makeFinalCalculation(int iteration, int confidence, int support) {
//		System.out.println(frequentItems.size());
//		for (Map.Entry element : frequentItems.entrySet()) {
//			System.out.println(element.getKey() + "   " + element.getValue());
//		}
	}
}
