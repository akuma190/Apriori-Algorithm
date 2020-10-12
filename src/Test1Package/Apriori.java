package Test1Package;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Apriori {

	static String temp_arr[];
	public static long start;
	public static long end;

	public static void main(String[] args) {
		HashMap<HashSet<String>, Integer> Transaction = new HashMap<>();
		HashMap<HashSet<String>, Integer> storage = new HashMap<>();
		HashSet<String> Item = new HashSet<>();
		HashSet<String> ItemAfterStage1 = new HashSet<>();
		HashSet<HashSet<String>> discardSet = new HashSet<>();
		int support = 2;
		int confidence = 2;
		int totaltrans = 0;
		BufferedReader br;
		int check = 1;
		start = System.currentTimeMillis();
		Scanner myObj = new Scanner(System.in);
		System.out.println("-------------APRIORI ALGORITHM------------");
		System.out.println("Please enter the file name:");
		String input = myObj.nextLine();
		File file = new File(input);
		try {
			// Enter Support and confidence
			//Scanner myObj = new Scanner(System.in);
			System.out.println("Enter Support Value in percentage");//Enter values between 0-100
			support = myObj.nextInt();
			//support = 20;
			System.out.println("Enter Confidence Value in percentage");
			confidence = myObj.nextInt();
			//confidence = 40;
			// myObj.close();
			// END Enter Support and confidence
			// Loading the data from database
			
			br = new BufferedReader(new FileReader(file));
			String st;
			while ((st = br.readLine()) != null) {
				String strArray[] = st.split(",");
				HashSet<String> transactionSet = new HashSet<>(Arrays.asList(strArray));
				if (Transaction.containsKey(transactionSet)) {
					Transaction.put(transactionSet, Transaction.get(transactionSet) + 1);
				} else {
					Transaction.put(transactionSet, 1);
				}
				totaltrans++;
				Item.addAll(transactionSet);
			}
			//System.out.println(Transaction);// HashMap containing all the lines of the file.
			System.out.println(Item);
			support = (int) (((float) support / 100) * totaltrans);
			// END Loading the data from database
			// Calculating the support and confidence
			Iterator<String> value = Item.iterator();
			while (value.hasNext()) {
				String itemTemp = value.next();
				int val = 0;
				for (Map.Entry<HashSet<String>, Integer> entry : Transaction.entrySet()) {
					if (entry.getKey().contains(itemTemp)) {
						val += entry.getValue();
					}
				}
				if (val >= support) {
					ItemAfterStage1.add(itemTemp);
					HashSet<String> tempStorage = new HashSet<>();
					tempStorage.add(itemTemp);
					storage.put(tempStorage, val);
				} else {
					HashSet<String> tempStorage = new HashSet<>();
					tempStorage.add(itemTemp);
					discardSet.add(tempStorage);
				}
			}
			//System.out.println(storage);// the count after the first iteration
			System.out.println("--------------------------------");
			System.out.println("The individual items and their count");
			for (HashSet<String> str : storage.keySet()) {
				System.out.println(str + " = " + storage.get(str));
			}
			System.out.println("--------------------------------");
			temp_arr = ItemAfterStage1.toArray(new String[0]);
			//System.out.println("= " + Arrays.toString(temp_arr));
			int exit = 0;
			while (exit == 0) {
				exit = 1;
				setIterationValue();
				obj = new HashSet<>();
				combination(new String[iteration], 0, 0);
				System.out.println("-------------------------------");
				System.out.println("Item list after the iteration : " + getIterationValue());
				for (HashSet<String> comb : obj) {
					System.out.println(comb);
				}
				System.out.println("-------------------------------");
				for (HashSet<String> comb : obj) {
					int k = 0;
					for (HashSet<String> elim : discardSet) {
						if (comb.containsAll(elim)) {
							k = 1;
							break;
						}
					}
					if (k == 0) {
						int val = 0;
						for (Map.Entry<HashSet<String>, Integer> entry : Transaction.entrySet()) {
							if (entry.getKey().containsAll(comb)) {
								val += entry.getValue();
							}
						}
						if (val >= support) {
							storage.put(comb, val);
							exit = 0;
						} else {
							discardSet.add(comb);
						}
					}
				}
				System.out.println("The individual items and their count after the iteration :" + getIterationValue());
				for (HashSet<String> str : storage.keySet()) {
					System.out.println(str + " = " + storage.get(str));
				}
				System.out.println("--------------------------------");
			}
			System.out.println("The final frequent item list is :");
			for (HashSet<String> frr : storage.keySet()) {
				System.out.println(frr + "-->" + storage.get(frr));
			}
			System.out.println("-------------------------------------------------");
			System.out.println("The items satisfying the association rules are :");
			for (Map.Entry<HashSet<String>, Integer> entry : storage.entrySet()) {
				atest = new String[entry.getKey().size()];
				entry.getKey().toArray(atest);
				smt = atest;
				for (int u = 1; u < atest.length; u++) {
					candidateRule(new String[u], 0, 0, u);
				}
				HashSet<String> temp = new HashSet<>();
				temp.addAll(entry.getKey());
				for (HashSet<String> ls : orr) {
					temp.removeAll(ls);
					NumberFormat formatter = new DecimalFormat("#0.00");
					Integer t2 = entry.getValue();
					Integer t3 = 0;
					if (storage.containsKey(ls)) {
						t3 = storage.get(ls);
					} else {
						System.out.println("error");
					}
					float conf = ((float) t2 / t3) * 100;
					float supp = ((float) t2 / totaltrans) * 100;
					if (conf >= confidence) {
						check = 0;
						System.out.println(ls.toString().replace("[", "").replace("]", "") + "-->"
								+ temp.toString().replace("[", "").replace("]", "") + ":[" + formatter.format(supp)
								+ "%," + formatter.format(conf) + "%]");
					}
					temp.addAll(entry.getKey());
				}
				orr = new HashSet<HashSet<String>>();
			}
			if (check == 1) {
				System.out.println("Nothing to display with the given confidece and support");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		end = System.currentTimeMillis();
		System.out.println("Total time to run the program is: "+(end -start) +"msec");
	}

	static String[] smt = {};
	static HashSet<HashSet<String>> obj = new HashSet<>();
	static int iteration = 1;
	static String[] atest;
	static HashSet<HashSet<String>> orr = new HashSet<HashSet<String>>();

	public static int getIterationValue() {
		return iteration;
	}

	public static void setIterationValue() {
		Apriori.iteration += 1;
	}
	public static void combination(String data[], int start, int index) {
		if (index == iteration) {
			HashSet<String> temp = new HashSet<>();
			for (int j = 0; j < iteration; j++)
				temp.add(data[j]);
			obj.add(temp);
			return;
		}
		for (int i = start; i <= temp_arr.length - 1 && temp_arr.length - i >= iteration - index; i++) {
			data[index] = temp_arr[i];
			combination(data, i + 1, index + 1);
		}
	}
	public static void candidateRule(String data[], int start, int index, int iterator) {
		if (index == iterator) {
			HashSet<String> temp = new HashSet<>();
			for (int j = 0; j < iterator; j++)
				temp.add(data[j]);
			orr.add(temp);
			return;
		}
		for (int i = start; i <= smt.length - 1 && smt.length - i >= iterator - index; i++) {
			data[index] = smt[i];
			candidateRule(data, i + 1, index + 1, iterator);
		}
	}

	
}