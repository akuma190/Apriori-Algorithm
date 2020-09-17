package TestPackage;

import java.io.File;
import java.util.Scanner;

public class TestApriori {
	public static void main(String[] args) {
		try {
			File file = new File("DataBase1.csv");
			Scanner fileData = new Scanner(file);
			while (fileData.hasNext()) {
				System.out.println(fileData.next());
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
