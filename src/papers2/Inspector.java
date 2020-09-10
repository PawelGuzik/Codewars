package papers2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import papers2.DocumentCreator.DocAttributes;

public class Inspector {
	List<List<String>> entrantDocuments = new ArrayList<List<String>>();
	String misstingDoc;
	String attrMismatch;
	String criminal;
	String docExp;
	String invDiplAuth;
	Requirments requirments = new Requirments();

	public enum docType {
		PASSPORT, ACCESS_PERMIT, WORK_PASS
	}

	public void receiveBulletin(String bulletin) {
		criminal = "";
		System.out.println("Bulletin: \n" + bulletin);
		System.out.println("************************************************");
		Scanner scanner = new Scanner(bulletin);

		try (scanner;) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.contains("Allow citizens of")) {
					String sub = line.substring(18);
					requirments.addCountriesAsString(Arrays.asList(sub.split(", ")));
				} else if (line.contains("Deny citizens of")) {
					String sub = line.substring(17);
					requirments.removeCountriesAsString(Arrays.asList(sub.split(", ")));
				} else if (line.contains("Entrants require")) {
					String sub = line.substring(17);
					requirments.setDocWantedFromAll(Arrays.asList(sub.split(", ")));
					// requirments.addRequiredDocForCountries(Arrays.asList(sub.split(", ")), "");
				} else if (line.contains("Wanted by the State:")) {
					requirments.setWantedCriminal(line.substring(21));
					criminal = line.substring(21);
				} else if (line.contains("Foreigners require")) {
					String sub = line.substring(19);
					requirments.addRequiredDocForCountries(Arrays.asList(sub.split(", ")), "Arstotzka");
				} else if(line.contains("Citizens of ")) {
					String[] splited = line.split(" require ");
					String sub = splited[0].substring(12);
					requirments.addRequiredDocForCountry(splited[1], sub);
				}
			}
			requirments.updateWantedDocs();

		}

	}

	public String inspect(Map<String, String> person) {

		for (Map.Entry<String, String> entry : person.entrySet()) {
			System.out.println(entry.getKey() + "\n");
			System.out.println(entry.getValue() + "\n");
		}
		Person personWithDoc = new Person(person);

		System.out.println("---------------------------------------------------");
		if (compareDocuments(personWithDoc) && checkNation(personWithDoc, requirments)
				&& checkDocPresence(personWithDoc) && !isCriminal(personWithDoc, requirments)
				&& checkExpDates(personWithDoc)) {
			return inspectAlert(personWithDoc, true);
		} else {
			return inspectAlert(personWithDoc, false);
		}

	}

	private boolean checkDocPresence(Person person) {
		boolean result = false;
		String nation = getInfoFromAllDocs(person, DocAttributes.NATION);
		List<String> requiredDocs = requirments.getCountyRequiredDocsList(nation);// Docs
		List<String> personsDocs = person.getDocumentsNames(); // required
		// for
		// persons
		// country
		if (requiredDocs.size() == 0) {
			return true;
		}
		for (String requiredDoc : requiredDocs) {
			requiredDoc = requiredDoc.replace(" ", "_");
			if (personsDocs.contains(requiredDoc)) {
				result = true;
			} else if (requiredDoc.equalsIgnoreCase("access_permit") && personsDocs.contains("grant_of_asylum")) {
				result = true;
			} else if (requiredDoc.equalsIgnoreCase("access_permit")
					&& personsDocs.contains("diplomatic_authorization")) {
				if (person.getDocByType("diplomatic_authorization").getNation().equalsIgnoreCase("Arstotzka")) {
					result = true;
				} else {
					invDiplAuth = "invalid diplomatic authorization";
					return false;
				}
			} else if (requiredDoc.equalsIgnoreCase("access_permit")
					&& person.getNation().equalsIgnoreCase("Arstotzka")) {
				result = true;
			} else {

				misstingDoc = requiredDoc.replace("_", " ");
				return false;
			}
		}
		return result;

	}

	private String inspectAlert(Person person, boolean success) {

		if (success) {
			if (person.getNation().equalsIgnoreCase("Arstotzka")) {
				return "Glory to Arstotzka.";
			} else {
				return "Cause no trouble.";
			}
		} else if (isCriminal(person, requirments)) {
			return "Detainment: Entrant is a wanted criminal.";
		} else if (person.diferentAttr != null) {
			if (person.diferentAttr.name.contains("NATION")) {
				return "Detainment: nationality mismatch.";
			}
			return "Detainment: " + person.diferentAttr + " number mismatch.";

		} else if (misstingDoc != null) {
			return "Entry denied: missing required " + misstingDoc + ".";
		} else if (docExp != null) {
			return "Entry denied: " + docExp + " expired.";
		} else if (invDiplAuth != null) {
			return "Entry denied: invalid diplomatic authorization.";
		} else {
			return "Entry denied: citizen of banned nation.";
		}

	}

	private String getInfoFromAllDocs(Person person, DocAttributes attrName) {
		String result = "";
		for (Document doc : person.docList) {
			result = result + getInforFromOneDoc(doc, attrName);
		}
		return result;
	}

	private String getInforFromOneDoc(Document doc, DocAttributes attrName) {

		return doc.getAttributeValue(attrName);
	}

	private boolean compareDocuments(Person person) {
		int s = person.docList.size();
		if (s == 1) {
			return true;
		} else {
			for (int i = 0; i < s; i++) {
				Document doc = person.docList.get(i);
				for (int j = 1; j < s; j++) {
					Document doc2 = person.docList.get(j);
					DocAttributes compRes = doc.compareDocuments(doc2);
					if (compRes != DocAttributes.COMPARATION_OK) {
						person.diferentAttr = compRes;
						return false;
					}
				}
			}
			return true;
		}
	}

	// Returns false when document have expired
	private boolean checkExpDates(Person person) {
		List<Document> documents = person.docList;
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy.MM.dd");

		try {
			Date d1 = sdformat.parse("1982.11.22");
			for (Document doc : documents) {
				if (d1.compareTo(doc.getExp()) >= 0) {
					docExp = doc.getType();
					return false;
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}

	private boolean checkNation(Person person, Requirments requirements) {
		if (requirements.getAllowedCountries().contains(person.getNation())) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isCriminal(Person person, Requirments requirments) {
		if (person.getName().equalsIgnoreCase(requirments.getWantedCriminal())) {
			return true;
		} else {
			return false;
		}
	}
}
