package TestPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
		String[] hashList = { "DataBase1", "DataBase2", "DataBase3", "DataBase4", "DataBase5" };
		HashSet<String> uniqueFeatures = new HashSet<String>();

		// reading the database1 and getting the unique elements
		readDataBases(dataBase1, dataBases[0]);
		getUniqueElements(dataBase1, uniqueFeatures);
		System.out.println(uniqueFeatures.size());

		// reading the database2 and getting the unique elements
		readDataBases(dataBase2, dataBases[1]);

		// reading the database2 and getting the unique elements
		readDataBases(dataBase3, dataBases[2]);

		// reading the database4 and getting the unique elements
		readDataBases(dataBase4, dataBases[3]);

		// reading the database5 and getting the unique elements
		readDataBases(dataBase5, dataBases[4]);

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
	
	public static void generateAssociation(HashMap<ArrayList<String>, Integer> dataBase, HashSet<String> hashList,int support,int confidence) {
		HashMap<ArrayList<String>, Integer> dynamicMapping=new HashMap<ArrayList<String>, Integer>();
		ArrayList<String> transfer=new ArrayList<String>();
		for(String str:hashList) {
			transfer.add(str);
			dynamicMapping.put(transfer,0);
			transfer.remove(0);
		}
		System.out.println(dynamicMapping);
		calculateSupport(dataBase,dynamicMapping);
	}
	
	public static void calculateSupport(HashMap<ArrayList<String>, Integer> dataBase,HashMap<ArrayList<String>, Integer> dynamicMapping) {
		
	}
}
