package papers2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DocumentCreator {

	public enum DocAttributes {
		NAME("NAME"), ID("ID#"), NATION("NATION"), DOB("DOB"), PURPOSE("PURPOSE"), DURATION("DURATION"),
		WEIGHT("WEIGHT"), HEIGHT("HEIGHT"), SEX("SEX"), ISS("ISS"), EXP("EXP"), 
		COMPARATION_OK("COMPARATION_OK");

		public String name;

		private DocAttributes(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public static List<Document> create(Map<String, String> documents) {
		List<Document> newDocuments = new ArrayList<>();
		for (Map.Entry<String, String> document : documents.entrySet()) {
			Document.Builder newDocument = new Document.Builder(document.getKey());
			for (DocAttributes attribute : DocAttributes.values()) {
				String attrValue = getInforFromOneDoc(document.getValue(), attribute.name);
				newDocument.callFunction(attribute, attrValue);

			}

			newDocuments.add(newDocument.build());
		}
		return newDocuments;
	}

	private static String getInforFromOneDoc(String doc, String attrName) {
		Scanner scanner = new Scanner(doc);
		try (scanner;) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.contains(attrName)) {
					return line.substring(attrName.length() + 2);
				}
			}
		}

		return "";
	}

}
