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

public class BruteForce {

	static String temp_arr[];
    static float finalSupp;
    static long start;
    static long end;
	public static void main(String[] args) {
		HashMap<HashSet<String>, Integer> Trans = new HashMap<>();
		HashMap<HashSet<String>, Integer> storage = new HashMap<>();
		HashSet<String> Item = new HashSet<>();
		HashSet<String> ItemAfterStage1 = new HashSet<>();
		int support = 2;
		
		int confidence = 2;
		int totaltrans = 0;
		BufferedReader br;
		int check = 1;
		start = System.currentTimeMillis();
		Scanner myObj = new Scanner(System.in);
		System.out.println("------------BRUTE FORCE-----------");
		System.out.println("Please enter the file name:");			//Enter a valid file name
		String input = myObj.nextLine();
		File file = new File(input);
		try {
			
			//Scanner myObj = new Scanner(System.in);
			System.out.println("Enter Support Value in percentage%"); // enter support between 0-100
			support = myObj.nextInt();
			//support = 10;
			finalSupp=20;
			System.out.println("Enter Confidence Value in percentage Eg:70 for 70%"); //enter confidence between 0-100
			confidence = myObj.nextInt();
			//confidence = 15;
			//myObj.close();
			br = new BufferedReader(new FileReader(file));	//Start reading the file from here
			String st;
			while ((st = br.readLine()) != null) {
				String strArray[] = st.split(",");
				HashSet<String> setOfTrans = new HashSet<>(Arrays.asList(strArray));
				if (Trans.containsKey(setOfTrans)) {
					Trans.put(setOfTrans, Trans.get(setOfTrans) + 1);
				} else {
					Trans.put(setOfTrans, 1);
				}
				totaltrans++;
				Item.addAll(setOfTrans);
			}
			//System.out.println(Trans);// HashMap containing all the lines of the file.
			//System.out.println(Item);
			support = (int) (((float) support / 100) * totaltrans);
			Iterator<String> value = Item.iterator();
			while (value.hasNext()) {
				String itemTemp = value.next();
				int val = 0;
				for (Map.Entry<HashSet<String>, Integer> entry : Trans.entrySet()) {
					if (entry.getKey().contains(itemTemp)) {
						val += entry.getValue();
					}
				}
//				if (val >= support) {
					ItemAfterStage1.add(itemTemp);
					HashSet<String> tempStorage = new HashSet<>();
					tempStorage.add(itemTemp);
					storage.put(tempStorage, val);
				//}
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
				setIteration();
				obj = new HashSet<>();
				combination(new String[iteration], 0, 0);
				System.out.println("-------------------------------");
				System.out.println("Item list after the iteration : " + getIteration());
				for (HashSet<String> comb : obj) {
					System.out.println(comb);
				}
				System.out.println("-------------------------------");
				for (HashSet<String> comb : obj) {
					int k = 0;
					if (k == 0) {
						int val = 0;
						for (Map.Entry<HashSet<String>, Integer> entry : Trans.entrySet()) {
							if (entry.getKey().containsAll(comb)) {
								val += entry.getValue();
							}
						}
						storage.put(comb, val);
						if (val >= support) {
							
							exit = 0;
						} 
					}
				}
				
				  System.out.println("The individual items and their count after the iteration :" +
				  getIteration()); for (HashSet<String> str : storage.keySet()) {
				  System.out.println(str + " = " + storage.get(str)); }
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
				HashSet<String> tempx = new HashSet<>();
				tempx.addAll(entry.getKey());
				for (HashSet<String> ls : orr) {
					tempx.removeAll(ls);
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
					//System.out.println(supp+"="+finalSupp);
					if (conf >= confidence && supp>=finalSupp) {
						check = 0;
						System.out.println(ls.toString().replace("[", "").replace("]", "") + "-->"
								+ tempx.toString().replace("[", "").replace("]", "") + ":[" + formatter.format(supp)
								+ "%," + formatter.format(conf) + "%]");
					}
					tempx.addAll(entry.getKey());
				}
				orr = new HashSet<HashSet<String>>();
			}
			// END Calculating the support and confidence
			if (check == 1) {
				System.out.println("Nothing to display with the given confidece and support");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		end = System.currentTimeMillis();
		System.out.println("Total time to run the program is : "+(end -start));
	}

	static String[] smt = {};
	static HashSet<HashSet<String>> obj = new HashSet<>();
	static int iteration = 1;
	static String[] atest;
	static HashSet<HashSet<String>> orr = new HashSet<HashSet<String>>();

	public static int getIteration() {
		return iteration;
	}

	public static void setIteration() {
		BruteForce.iteration += 1;
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