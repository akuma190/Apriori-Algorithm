package BasePackage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class MainBruteForce {
	public static HashSet<String> uniqueFeatures = new HashSet<String>();
	public static int databaseSize = 0;

	public static void main(String[] args) {
		double support = 0.5;
		double confidence = 0.7;
		String[] dataBases = { "DataBase1.csv", "DataBase2.csv", "DataBase3.csv", "DataBase4.csv", "DataBase5.csv" };
		HashMap<ArrayList<String>, Integer> dataBase1 = new HashMap<ArrayList<String>, Integer>();
		HashMap<ArrayList<String>, Integer> dataBase2 = new HashMap<ArrayList<String>, Integer>();
		HashMap<ArrayList<String>, Integer> dataBase3 = new HashMap<ArrayList<String>, Integer>();
		HashMap<ArrayList<String>, Integer> dataBase4 = new HashMap<ArrayList<String>, Integer>();
		HashMap<ArrayList<String>, Integer> dataBase5 = new HashMap<ArrayList<String>, Integer>();

		// reading the database1 and getting the unique elements
		System.out.println("------------------Reading DataBase1---------");
		readBruteDataBases(dataBase1, dataBases[1]);
		databaseSize = dataBase1.size();
		getBruteUniqueElements(dataBase1, uniqueFeatures);
		generateBruteAssociation(dataBase1, uniqueFeatures, support, confidence);

	}

	// This is the method to read the data from the file and populate the HashMap
	public static void readBruteDataBases(HashMap<ArrayList<String>, Integer> dataBase, String fileName) {
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

	// Thus is the method to get the list of unique from the list of all the
	// elements
	public static void getBruteUniqueElements(HashMap<ArrayList<String>, Integer> dataBase, HashSet<String> hashList) {

		for (ArrayList<String> arr : dataBase.keySet()) {
			for (String str : arr) {
				hashList.add(str);
			}
		}

	}

	public static void generateBruteAssociation(HashMap<ArrayList<String>, Integer> dataBase, HashSet<String> hashList,
			double support, double confidence) {

		HashMap<ArrayList<String>, Integer> dynamicMapping = new HashMap<ArrayList<String>, Integer>();
		System.out.println("The List of items in the database :");
		for (String str : hashList) {
			ArrayList<String> transfer = new ArrayList<String>();
			transfer.add(str);
			System.out.println(transfer);
			// This is creating the dynamic HashMap that we will use throughout the code.
			// For the start we are entering count as 0 for the elements
			dynamicMapping.put(transfer, 0);
		}

		System.out.println(dynamicMapping);

		// iteration will keep the count for size of each row in itemList
		int iteration = 1;
		boolean stop = true;
		
		while(true) {
			// calculate the support of already generated HashMap of items.
			calculateBruteSupport(dataBase, dynamicMapping, iteration);
			stop = validateBruteSupport(dynamicMapping, support, iteration);
			System.out.println(stop);
			if(!stop) {
				System.out.println("---------------------------");
				System.out.println("There are no frequent items from list : C"+iteration);
				break;
			}
			iteration = iteration + 1;
			// make the k+1 HasMap of items after eliminating on the basis of support.
			makeBruteNewFrequentList(dynamicMapping, iteration);
			
		}

		

//		calculateBruteSupport(dataBase, dynamicMapping, iteration);
//		stop = validateBruteSupport(dynamicMapping, support, iteration);
//		System.out.println(stop);
//		iteration = iteration + 1;
//		makeBruteNewFrequentList(dynamicMapping, iteration);
//
//		calculateBruteSupport(dataBase, dynamicMapping, iteration);
//		stop = validateBruteSupport(dynamicMapping, support, iteration);
//		System.out.println(stop);
//		iteration = iteration + 1;
//		makeBruteNewFrequentList(dynamicMapping, iteration);
//		
//		calculateBruteSupport(dataBase, dynamicMapping, iteration);
//		stop = validateBruteSupport(dynamicMapping, support, iteration);
//		System.out.println(stop);
	}

	public static void calculateBruteSupport(HashMap<ArrayList<String>, Integer> dataBase,
			HashMap<ArrayList<String>, Integer> dynamicMapping, int iteration) {

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
				// if all the elements of the row of dynamic HashMap is present in a row of
				// database.
				if (count == length) {
					dynamicMapping.put(outer, dynamicMapping.get(outer) + 1);
				}
				count = 0;
			}

		}

		System.out.println(dataBase);
		System.out.println("--------------------------------------------------------");
		System.out.println("The support count of the elements after " + iteration + " iteration : " + "C" + iteration);
		for (Map.Entry element : dynamicMapping.entrySet()) {
			System.out.println(element.getKey() + "   " + element.getValue());
		}

	}

	public static boolean validateBruteSupport(HashMap<ArrayList<String>, Integer> dynamicMapping, double support,
			int iteration) {

		boolean status = false;
		for (ArrayList<String> arr : dynamicMapping.keySet()) {
			if ((dynamicMapping.get(arr) / (double) databaseSize) >= support) {
				status = true;
			}
		}

		return status;

	}

	public static void makeBruteNewFrequentList(HashMap<ArrayList<String>, Integer> dynamicMapping, int iteration) {

		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> str1 : dynamicMapping.keySet()) {
			list.add(str1);
		}

		// here we are clearing the HashMap so that we can refill it with new Items.
		dynamicMapping.clear();
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
					// Collections.sort(newList);
					dynamicMapping.put(newList, 0);
				}
				hashInterface.clear();
			}

		}

		System.out.println("--------------------------------------------------------");
		System.out.println("The List of items after " + iteration + " iteration : ");
		if (dynamicMapping.size() == 0) {
			System.out.println("No combinations are formed");
		}
		for (Map.Entry element : dynamicMapping.entrySet()) {
			System.out.println(element.getKey() + "   " + element.getValue());
		}

	}
}

