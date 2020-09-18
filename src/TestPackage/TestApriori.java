package TestPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class TestApriori {
	public static HashMap<ArrayList<String>, Integer> frequentItems = new HashMap<ArrayList<String>, Integer>();
	public static HashMap<ArrayList<String>, Integer> nonFrequentItems = new HashMap<ArrayList<String>, Integer>();
	public static HashMap<String[], Integer> finalMapping = new HashMap<String[], Integer>();
	public static int databaseSize = 0;
	public static HashSet<String> uniqueFeatures = new HashSet<String>();

	public static void main(String[] args) {
		double support = 0.5;
		double confidence = 0.7;
		String[] dataBases = { "DataBase1.csv", "DataBase2.csv", "DataBase3.csv", "DataBase4.csv", "DataBase5.csv" };
		HashMap<ArrayList<String>, Integer> dataBase1 = new HashMap<ArrayList<String>, Integer>();
		HashMap<ArrayList<String>, Integer> dataBase2 = new HashMap<ArrayList<String>, Integer>();
		HashMap<ArrayList<String>, Integer> dataBase3 = new HashMap<ArrayList<String>, Integer>();
		HashMap<ArrayList<String>, Integer> dataBase4 = new HashMap<ArrayList<String>, Integer>();
		HashMap<ArrayList<String>, Integer> dataBase5 = new HashMap<ArrayList<String>, Integer>();
		// String[] hashList = { "DataBase1", "DataBase2", "DataBase3", "DataBase4",
		// "DataBase5" };

//		// reading the database1 and getting the unique elements
//		System.out.println("------------------Reading DataBase1---------");
//		readDataBases(dataBase1, dataBases[0]);
//		getUniqueElements(dataBase1, uniqueFeatures);
//		generateAssociation(dataBase1, uniqueFeatures, support, confidence);

		// reading the database2 and getting the unique elements
		System.out.println("------------------Reading DataBase2---------");
		readDataBases(dataBase2, dataBases[1]);
		databaseSize = dataBase2.size();
		System.out.println("The size is " + dataBase2.size());
		getUniqueElements(dataBase2, uniqueFeatures);
		generateAssociation(dataBase2, uniqueFeatures, support, confidence);

		// reading the database2 and getting the unique elements
		// System.out.println("------------------Reading DataBase3---------");
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

	// This is the method to read the data from the file and populate the HashMap
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

	// Thus is the method to get the list of unique from the list of all the
	// elements
	public static void getUniqueElements(HashMap<ArrayList<String>, Integer> dataBase, HashSet<String> hashList) {
		for (ArrayList<String> arr : dataBase.keySet()) {
			for (String str : arr) {
				hashList.add(str);
			}
		}
	}

	// This is the main driver method for the process
	public static void generateAssociation(HashMap<ArrayList<String>, Integer> dataBase, HashSet<String> hashList,
			double support, double confidence) {

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

		calculateSupport(dataBase, dynamicMapping, iteration);
		validateSupport(dynamicMapping, support, iteration);
		System.out.println(frequentItems);
		System.out.println(dynamicMapping.size());
		makeFinalList(iteration, confidence, support);
		calculateConfidence(dataBase, confidence, support);
//		if (dynamicMapping.size() == 0) {
//			makeFinalCalculation(iteration, confidence, support);
//		}
	}

	// This method will calculate the support of each generated sequence
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

		System.out.println("The support count of the elements after " + iteration + " iteration");
		for (Map.Entry element : dynamicMapping.entrySet()) {
			System.out.println(element.getKey() + "   " + element.getValue());
		}
	}

	// this will validate the support number from the user passed support
	public static void validateSupport(HashMap<ArrayList<String>, Integer> dynamicMapping, double support,
			int iteration) {
		// checking the non valid support values
		for (ArrayList<String> arr : dynamicMapping.keySet()) {
			// System.out.println(dynamicMapping.get(arr));
			if ((dynamicMapping.get(arr) / (double) databaseSize) < support) {
				// System.out.println("hi"+dynamicMapping.get(arr)+","+databaseSize+","+(dynamicMapping.get(arr)/(double)databaseSize));
				nonFrequentItems.put(arr, dynamicMapping.get(arr));
				// dynamicMapping.remove(arr);
			} else {
				frequentItems.put(arr, dynamicMapping.get(arr));
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

	// This is the Method to generate the itemlist from the previous iteration
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

	// In this method we will make the final confidence calculation
	public static void makeFinalList(int iteration, double confidence, double support) {
		System.out.println(frequentItems.size());
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		ArrayList<Integer> itemCount = new ArrayList<Integer>();
		for (ArrayList<String> arr : frequentItems.keySet()) {
			array.add(arr);
			itemCount.add(frequentItems.get(arr));
		}
		// System.out.println(itemCount);
		// String[] staging = new String[2];
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).size() > 1) {
				for (int j = 0; j < array.get(i).size(); j++) {
					String[] staging = new String[2];
					String[] staging1 = new String[2];
					staging[0] = array.get(i).get(j);
					staging1[1] = array.get(i).get(j);
					System.out.println(array.get(i).get(j));
					for (int k = 0; k < array.get(i).size(); k++) {
						if (k != j) {
							str.append(array.get(i).get(k) + ",");
						}
					}
					staging[1] = str.toString();
					staging1[0] = str.toString();
					System.out.println(Arrays.toString(staging));
					finalMapping.put(staging, itemCount.get(i));
					if (array.get(i).size() > 2) {
						System.out.println(Arrays.toString(staging1));
						finalMapping.put(staging1, itemCount.get(i));
					}
					str.delete(0, str.length());
					System.out.println("----------");
				}

			}
		}

//		for (Map.Entry element : finalMapping.entrySet()) {
//			String[] arrena=(String[]) element.getKey();
//			System.out.println(Arrays.toString(arrena));
//			System.out.println(arrena[0]);
//			System.out.println(element.getValue());
//			System.out.println("-------");
//		}

	}

	public static void calculateConfidence(HashMap<ArrayList<String>, Integer> dataBase, double confidence,
			double support) {
		// System.out.println(uniqueFeatures);
//		System.out.println(dataBase);
//		System.out.println(finalMapping);
		int elementCount;
		int finalCount;
		int desiredCount;
		HashMap<String[], Integer> confidenceCount = new HashMap<String[], Integer>();
		for (Map.Entry element : finalMapping.entrySet()) {
			String[] arrena = (String[]) element.getKey();
			// System.out.println(arrena[0]);
			if (arrena[0].indexOf(",") != -1) {
				elementCount = 0;
				String[] parts = arrena[0].split(",");
				desiredCount = 0;
				finalCount = 0;
				for (ArrayList<String> arr : dataBase.keySet()) {
					for (int i = 0; i < parts.length; i++) {
						if (arr.contains(parts[i])) {
							finalCount = finalCount + 1;
						}
					}
					// System.out.println("final "+finalCount);
					if (finalCount == parts.length) {
						elementCount = elementCount + 1;
					}
					finalCount = 0;
				}

			} else {
				elementCount = 0;
				for (ArrayList<String> arr : dataBase.keySet()) {
					if (arr.contains(arrena[0])) {
						elementCount = elementCount + 1;
					}
				}
				// System.out.println(arrena[0] + "=" + elementCount);
			}
			// System.out.println(arrena[0] +"="+element.getValue() +"="+elementCount);
			// int value=(int) element.getValue();
			// System.out.println((int)element.getValue()/(double)elementCount);
			if (((int) element.getValue() / (double) elementCount) > confidence) {
				System.out.println("The final value is =" + Arrays.toString(arrena));
			}
		}
//		System.out.println(nonFrequentItems);
	}

}
