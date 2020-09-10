package papers3;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Inspector {
	public enum DocAttributes {
		NAME("NAME"), ID("ID#"), NATION("NATION"), DOB("DOB"), PURPOSE("PURPOSE"), DURATION("DURATION"),
		WEIGHT("WEIGHT"), HEIGHT("HEIGHT"), SEX("SEX"), ISS("ISS"), EXP("EXP"), ACCESS("ACCESS"), VACCINES("VACCINES"),
		COMPARATION_OK("COMPARATION_OK");

		public String name;

		private DocAttributes(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	String misstingDoc;
	String attrMismatch;
	String docExp;
	String invDiplAuth;
	Requirments requirments = new Requirments();

	public enum docType {
		PASSPORT, ACCESS_PERMIT, WORK_PASS
	}

	public void receiveBulletin(String bulletin) {
		System.out.println("Bulletin: \n" + bulletin);
		System.out.println("************************************************");
		Scanner scanner = new Scanner(bulletin);
		String sub = "";
		String vaccList = "";
		try (scanner;) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.contains("Allow citizens of")) {

					sub = line.substring(18);
					requirments.addCountriesAsString(sub);
				} else if (line.contains("Deny citizens of")) {

					sub = line.replace("Deny citizens of ", "");
					requirments.denyCountries(sub);
				} else if (line.contains("Entrants require ") && line.contains("vaccination")) {

					vaccList = line.replace("Entrants require ", "").replace(" vaccination", "");
					requirments.addRequiredVaccinations(vaccList, requirments.getCountryList());
				} else if (line.contains("Entrants require")) {

					sub = line.replace("Entrants require ", "");
					requirments.addRequiredDocForCountries(sub, "");
				} else if (line.contains("Wanted by the State:")) {

					requirments.setWantedCriminal(line.replace("Wanted by the State: ", ""));
				} else if (line.contains("vaccination") && line.contains("Foreigners require")) {

					vaccList = line.replace("Foreigners require ", "").replace(" vaccination", "");
					requirments.addRequiredVaccinations(vaccList, requirments.getForeignersCountries());
				} else if (line.contains("Foreigners require")) {

					sub = line.replace("Foreigners require ", "");
					requirments.addRequiredDocForCountries(sub, "Arstotzka");

				} else if (line.contains("Foreigners no longer require") && line.contains("vaccination")) {

					vaccList = line.replace("Foreigners no longer require ", "").replace(" vaccination", "");
					requirments.removeRequiredVaccinations(vaccList, requirments.countryList);
				} else if (line.contains("Entrants no longer require") && line.contains("vaccination")) {

					vaccList = line.replace("Entrants no longer require ", "").replace(" vaccination", "");
					requirments.removeRequiredVaccinations(vaccList, requirments.countryList);
				} else if (line.contains("Citizens of ") && line.contains("no longer")) {

					String[] splited = line.split(" no longer require ");
					sub = splited[0].replace("Citizens of ", "");

					if (splited[1].contains("vaccination")) {

						vaccList = splited[1].replace(" vaccination", "");
						requirments.removeRequiredVaccinations(vaccList, requirments.getCountriesFromString(sub));
					}
				} else if (line.contains("Citizens of ")) {

					String[] splited = line.split(" require ");
					sub = splited[0].substring(12);

					if (splited[1].contains("vaccination")) {

						vaccList = splited[1].replace(" vaccination", "");
						requirments.addRequiredVaccinations(vaccList, requirments.getCountriesFromString(sub));
					} else {

						requirments.addRequiredDocForCountry(splited[1], sub);
					}
				} else if (line.contains("Workers require work pass")) {

					requirments.addRequiredDocForCountries("work_pass", "Arstotzka");
				}
			}
		}
	}

	public void clearPersonData() {
		misstingDoc = null;
		attrMismatch = null;
		docExp = null;
		invDiplAuth = null;
	}

	public String inspect(Map<String, String> person) {
		for (Map.Entry<String, String> entry : person.entrySet()) {
			System.out.println(entry.getKey() + "\n");
			System.out.println(entry.getValue() + "\n");
		}
		clearPersonData();
		Person personWithDoc = new Person(person);

		System.out.println("---------------------------------------------------");
		if (!isCriminal(personWithDoc, requirments) && compareDocuments(personWithDoc)
				&& checkDocPresence(personWithDoc) && checkExpDates(personWithDoc) && checkWorkPass(personWithDoc)
				&& checkVaccinations(personWithDoc) && checkNation(personWithDoc, requirments)) {
			return inspectAlert(personWithDoc, true);
		} else {
			return inspectAlert(personWithDoc, false);
		}
	}

	private boolean checkVaccinations(Person person) {
		boolean result = false;
		String nation = person.getNation();
		List<String> requiredVacc = requirments.getRequiredVacc(nation);
		if (requiredVacc != null && requiredVacc.size() > 0) {
			for (String vacc : requiredVacc) {
				if (person.getVaccines() != null && person.getVaccines().contains(vacc)) {
					result = true;
				} else {
					misstingDoc = "vaccination";
					return false;
				}
			}
		} else {
			return true;
		}
		return result;
	}

	private boolean checkDocPresence(Person person) {
		boolean result = false;
		String nation = person.getNation();
		if (nation.length() == 0) {
			misstingDoc = "passport";
			return false;
		}
		List<String> requiredDocs = requirments.getCountyRequiredDocsList(nation);// Docs
		List<String> personsDocs = person.getDocumentsNames(); // required

		if (requiredDocs != null) {
			if (requiredDocs.size() == 0) {
				if (!personsDocs.contains("passport")) {
					misstingDoc = "passport";
					return false;
				}
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
					if (person.getDocByType("diplomatic_authorization").getAccesAsString().contains("Arstotzka")) {
						result = true;
					} else {
						invDiplAuth = "invalid diplomatic authorization";
						return false;
					}
				} else if (requiredDoc.equalsIgnoreCase("access_permit")
						&& person.getNation().equalsIgnoreCase("Arstotzka")) {
					result = true;
				} else if (requiredDoc.equalsIgnoreCase("work_pass")) {
					result = true;
				} else {
					misstingDoc = requiredDoc.replace("_", " ");
					return false;
				}
			}
		}
		return result;
	}

	public boolean checkWorkPass(Person person) {
		boolean result = true;
		if (requirments.getCountyRequiredDocsList(person.getNation()).contains("work_pass")) {

			if (person.isWorker() && person.getDocumentsNames().contains("work_pass")) {
				result = true;
			} else if (!person.isWorker()) {
				result = true;
			} else {
				misstingDoc = "work pass";
				result = false;
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
			} else if (person.diferentAttr == DocAttributes.ID) {
				return "Detainment: " + person.diferentAttr + " number mismatch.";
			} else {
				return "Detainment: " + person.diferentAttr.getName().toLowerCase() + " mismatch.";
			}

		} else if (misstingDoc != null) {
			if (misstingDoc.contains("certificate_of_vaccination")) {
				return "Entry denied: missing required certificate of vaccination.";
			}
			return "Entry denied: missing required " + misstingDoc + ".";
		} else if (docExp != null) {
			return "Entry denied: " + docExp + " expired.";
		} else if (invDiplAuth != null) {
			return "Entry denied: invalid diplomatic authorization.";
		} else {
			return "Entry denied: citizen of banned nation.";
		}
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
				if (doc.getExp() != null && d1.compareTo(doc.getExp()) >= 0) {
					docExp = doc.getType().replace("_", " ");
					return false;
				}
			}
		} catch (ParseException e) {
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
		String[] personNames = person.getName().split(", ");
		if (requirments.getWantedCriminal().contains(personNames[0])
				&& requirments.getWantedCriminal().contains(personNames[1])) {
			return true;
		} else {
			return false;
		}
	}

	private static class DocumentCreator {

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
					Pattern pattern = Pattern.compile("^" + attrName);
					pattern.matcher(line).matches();

					if (line.startsWith(attrName)) {
						return line.substring(attrName.length() + 2);
					}
				}
			}
			return "";
		}
	}

	private class Person {
		List<Document> docList;
		DocAttributes diferentAttr;

		public Person(Map<String, String> personMap) {
			docList = DocumentCreator.create(personMap);
			diferentAttr = null;
		}

		public boolean isWorker() {
			if (getDocumentsNames().contains("access_permit")
					&& getDocByType("access_permit").getPurpose().equalsIgnoreCase("WORK")) {
				return true;
			} else {
				return false;
			}
		}

		public String getNation() {
			for (Document doc : docList) {
				String nation = doc.getNation();
				if (nation.length() > 0) {
					return nation;
				}
			}
			return "";
		}

		public String getName() {
			for (Document doc : docList) {
				String name = doc.getPersonName();
				if (name.length() > 0) {
					return name;
				}
			}
			return "";
		}

		public List<String> getDocumentsNames() {
			List<String> docsNames = new ArrayList<>();
			for (Document doc : docList) {
				docsNames.add(doc.getType());
			}
			return docsNames;
		}

		public Document getDocByType(String type) {
			for (Document document : docList) {
				if (document.getType().equalsIgnoreCase(type)) {
					return document;
				}
			}
			return null;
		}

		public String getVaccines() {
			if (getDocByType("certificate_of_vaccination") != null) {
				return getDocByType("certificate_of_vaccination").getVaccinesAsString();
			} else
				return null;
		}
	}

	public static class Document {
		private final String type;
		private final String personName;
		private final String id;
		private final String nation;
		private final String dob;
		private final String purpose;
		private final String duration;
		private final String weight;
		private final String height;
		private final String sex;
		private final String iss;
		private final Date exp;
		private final String[] access;
		private final String[] vaccines;

		public String getType() {
			return type;
		}

		public String getPersonName() {
			return personName;
		}

		public String getId() {
			return id;
		}

		public String getNation() {
			return nation;
		}

		public String getDob() {
			return dob;
		}

		public String getPurpose() {
			return purpose;
		}

		public String getDuration() {
			return duration;
		}

		public String getWeight() {
			return weight;
		}

		public String getHeight() {
			return height;
		}

		public String getSex() {
			return sex;
		}

		public String getIss() {
			return iss;
		}

		public Date getExp() {
			return exp;
		}

		public String[] getAccess() {
			return access;
		}

		public String getAccesAsString() {
			String result = "";
			for (String string : access) {
				result += string;
			}
			return result;
		}

		public String[] getVaccines() {
			return vaccines;
		}

		public String getVaccinesAsString() {
			String result = "";
			for (String vaccine : vaccines) {
				result += vaccine;
			}
			return result;
		}

		private static class Builder {
			private final String type;

			private String personName = "";
			private String id = "";
			private String nation = "";
			private String dob = "";
			private String purpose = "";
			private String duration = "";
			private String weight = "";
			private String height = "";
			private String sex = "";
			private String iss = "";
			private Date exp = null;
			private String[] access = null;
			private String[] vaccines = null;

			public Builder(String type) {
				this.type = type;
			}

			public Builder personName(String name) {
				this.personName = name;
				return this;
			}

			public Builder id(String id) {
				this.id = id;
				return this;
			}

			public Builder nation(String nation) {
				this.nation = nation;
				return this;
			}

			public Builder dob(String dob) {
				this.dob = dob;
				return this;
			}

			public Builder purpose(String purpose) {
				this.purpose = purpose;
				return this;
			}

			public Builder duration(String duration) {
				this.duration = duration;
				return this;
			}

			public Builder weight(String weight) {
				this.weight = weight;
				return this;
			}

			public Builder height(String height) {
				this.height = height;
				return this;
			}

			public Builder sex(String sex) {
				this.sex = sex;
				return this;
			}

			public Builder iss(String iss) {
				this.iss = iss;
				return this;
			}

			public Builder exp(String exp) {
				SimpleDateFormat sdformat = new SimpleDateFormat("yyyy.MM.dd");
				if (exp.length() == 0) {
					this.exp = null;
					return this;
				}
				try {
					this.exp = sdformat.parse(exp);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return this;
			}

			public Builder access(String countriesAccess) {
				this.access = countriesAccess.split(", ");
				return this;
			}

			public Builder vaccines(String vaccines) {
				this.vaccines = vaccines.split(", ");
				return this;
			}

			// Calls function which is proper for given attribute name
			public Builder callFunction(DocAttributes attrName, String attrValue) {
				if (attrName == DocAttributes.NAME) {
					this.personName((String) attrValue);
				} else if (attrName == DocAttributes.ID) {
					this.id((String) attrValue);
				} else if (attrName == DocAttributes.NATION) {
					this.nation((String) attrValue);
				} else if (attrName == DocAttributes.DOB) {
					this.dob((String) attrValue);
				} else if (attrName == DocAttributes.PURPOSE) {
					this.purpose((String) attrValue);
				} else if (attrName == DocAttributes.DURATION) {
					this.duration((String) attrValue);
				} else if (attrName == DocAttributes.WEIGHT) {
					this.weight((String) attrValue);
				} else if (attrName == DocAttributes.HEIGHT) {
					this.height((String) attrValue);
				} else if (attrName == DocAttributes.SEX) {
					this.sex((String) attrValue);
				} else if (attrName == DocAttributes.ISS) {
					this.iss((String) attrValue);
				} else if (attrName == DocAttributes.EXP) {
					this.exp((String) attrValue);
				} else if (attrName == DocAttributes.ACCESS) {
					this.access(attrValue);
				} else if (attrName == DocAttributes.VACCINES) {
					this.vaccines(attrValue);
				}
				return this;
			}

			public Document build() {
				return new Document(this);
			}
		}

		private Document(Builder builder) {
			type = builder.type;
			personName = builder.personName;
			id = builder.id;
			nation = builder.nation;
			dob = builder.dob;
			purpose = builder.purpose;
			duration = builder.duration;
			weight = builder.weight;
			height = builder.height;
			sex = builder.sex;
			iss = builder.iss;
			exp = builder.exp;
			access = builder.access;
			vaccines = builder.vaccines;
		}

		public String getExpAsString() {
			DateFormat dateFormat = new SimpleDateFormat("yyyy.mm.dd");
			return dateFormat.format(exp);
		}

		public DocAttributes compareDocuments(Document secondDoc) {

			if ((personName.length() > 0 && secondDoc.personName.length() > 0)
					&& (!this.personName.equalsIgnoreCase(secondDoc.personName))) {
				return DocAttributes.NAME;
			}
			if ((id.length() > 0 && secondDoc.id.length() > 0) && (!this.id.equalsIgnoreCase(secondDoc.id))) {
				return DocAttributes.ID;
			}
			if ((nation.length() > 0 && secondDoc.nation.length() > 0)
					&& (!this.nation.equalsIgnoreCase(secondDoc.nation))) {
				return DocAttributes.NATION;
			}
			if ((dob.length() > 0 && secondDoc.dob.length() > 0) && (!this.dob.equalsIgnoreCase(secondDoc.dob))) {
				return DocAttributes.DOB;
			}
			if ((purpose.length() > 0 && secondDoc.purpose.length() > 0)
					&& (!this.purpose.equalsIgnoreCase(secondDoc.purpose))) {
				return DocAttributes.PURPOSE;
			}
			if ((duration.length() > 0 && secondDoc.duration.length() > 0)
					&& (!this.duration.equalsIgnoreCase(secondDoc.duration))) {
				return DocAttributes.DURATION;
			}
			if (weight.length() > 0 && secondDoc.weight.length() > 0
					&& !this.weight.equalsIgnoreCase(secondDoc.weight)) {
				return DocAttributes.WEIGHT;
			}
			if (height.length() > 0 && secondDoc.height.length() > 0
					&& !this.height.equalsIgnoreCase(secondDoc.height)) {
				return DocAttributes.HEIGHT;
			}
			if (sex.length() > 0 && secondDoc.sex.length() > 0 && !this.sex.equalsIgnoreCase(secondDoc.sex)) {
				return DocAttributes.SEX;
			}
			if (iss.length() > 0 && secondDoc.iss.length() > 0 && !this.iss.equalsIgnoreCase(secondDoc.iss)) {
				return DocAttributes.ISS;
			}
			return DocAttributes.COMPARATION_OK;
		}

		public String getAttributeValue(DocAttributes attrName) {
			if (attrName == DocAttributes.NAME) {
				return personName;
			} else if (attrName == DocAttributes.ID) {
				return id;
			} else if (attrName == DocAttributes.NATION) {
				return nation;
			} else if (attrName == DocAttributes.DOB) {
				return dob;
			} else if (attrName == DocAttributes.PURPOSE) {
				return purpose;
			} else if (attrName == DocAttributes.DURATION) {
				return duration;
			} else if (attrName == DocAttributes.WEIGHT) {
				return weight;
			} else if (attrName == DocAttributes.HEIGHT) {
				return height;
			} else if (attrName == DocAttributes.SEX) {
				return sex;
			} else if (attrName == DocAttributes.ISS) {
				return iss;
			} else if (attrName == DocAttributes.EXP) {
				return getExpAsString();
			}
			return "NO SUCH ATTRIBUTE";
		}
	}

	private class Requirments {
		List<Country> countryList;
		String wantedCriminal;

		public List<Country> getCountryList() {
			return countryList;
		}

		public Requirments() {
			countryList = List.of(new Country("Arstotzka"), new Country("Antegria"), new Country("Impor"),
					new Country("Kolechia"), new Country("Obristan"), new Country("Republia"),
					new Country("United Federation"));
			wantedCriminal = "";
		}

		public List<Country> getCountriesFromString(String stringOfCountries) {
			List<String> listCountry = List.of(stringOfCountries.split(", "));
			List<Country> resultlist = new ArrayList<>();
			for (Country countryAllved : countryList) {
				for (String countryToGet : listCountry) {
					if (countryAllved.getName().equalsIgnoreCase(countryToGet)) {
						resultlist.add(countryAllved);
					}
				}
			}
			return resultlist;
		}

		public List<String> getRequiredVacc(String country) {
			for (Country countryV : countryList) {
				if (countryV.getName().equalsIgnoreCase(country))
					return countryV.getRequiredVacc();
			}
			return null;
		}

		public void setWantedCriminal(String criminal) {
			wantedCriminal = criminal;
		}

		public String getWantedCriminal() {
			return wantedCriminal;
		}

		public void addCountriesAsString(String countriesListAsString) {
			List<String> countriesNames = List.of(countriesListAsString.split(", "));
			for (Country country : countryList) {
				if (countriesNames.contains(country.getName())) {
					country.setAllow(true);
				}
			}
		}

		public void denyCountries(String countriesToDenyLS) {
			List<String> countriesToDeny = List.of(countriesToDenyLS.split(", "));
			for (Country country : countryList) {
				if (countriesToDeny.contains(country.getName())) {
					country.setAllow(false);
				}
			}
		}

		public void addRequiredDocForCountries(String docsNamesStinrg, String exceptCountry) {
			List<String> docsNames = List.of(docsNamesStinrg.split(", "));
			docsNames.forEach(dn -> {
				countryList.forEach(c -> {
					if (!c.getName().equalsIgnoreCase(exceptCountry) && !c.getReqDocAsList().contains(dn)) {
						c.addRequiredDoc(dn);
					}
				});
			});
		}

		public void addRequiredVaccinations(String vaccinationsListString, List<Country> countriesNeedVaccin) {
			List<String> vaccinationsList = List.of(vaccinationsListString.split(", "));
			for (Country country : countryList) {
				if (countriesNeedVaccin.contains(country)) {
					country.addAllRequiredVaccinations(vaccinationsList);
					if (!country.requiredDocs.contains("certificate_of_vaccination")) {
						country.addRequiredDoc("certificate_of_vaccination");
					}
				}
			}
		}

		public void removeRequiredVaccinations(String vaccinationsToRemoveString,
				List<Country> countriesNolongerNeedVacc) {
			List<String> vaccinationsToRemove = List.of(vaccinationsToRemoveString.split(", "));
			for (Country country : countryList) {
				if (countriesNolongerNeedVacc.contains(country)) {
					country.removeAllVaccinations(vaccinationsToRemove);
					if (country.requiredVaccinations.size() == 0) {
						country.removeRequiredDoc("certificate_of_vaccination");
					}
				}
			}
		}

		public void addRequiredDocForCountry(String docName, String countryWithDoc) {
			for (Country country : countryList) {
				if (country.getName().equalsIgnoreCase(countryWithDoc)) {
					country.addRequiredDoc(docName);
				}
			}
		}

		public List<String> getCountyRequiredDocsList(String countryName) {

			for (Country country : countryList) {
				if (country.getName().equalsIgnoreCase(countryName)) {
					return country.getReqDocAsList();
				}
			}
			return null;
		}

		public List<String> getAllowedCountries() {
			List<String> countries = new ArrayList<>();
			for (Country country : countryList) {
				if (country.isAllow()) {
					countries.add(country.getName());
				}
			}
			return countries;
		}

		public List<Country> getForeignersCountries() {
			List<Country> newCountries = new ArrayList<Country>();
			for (Country country : countryList) {
				if (!country.getName().equalsIgnoreCase("Arstotzka")) {
					newCountries.add(country);
				}
			}
			return newCountries;
		}

		private class Country {
			String name;
			List<String> requiredVaccinations;
			List<String> requiredDocs;
			boolean allow;

			public Country(String name) {
				allow = false;
				this.name = name;
				requiredVaccinations = new ArrayList<>();
				requiredDocs = new ArrayList<>();
			}

			public boolean isAllow() {
				return allow;
			}

			public void setAllow(boolean allow) {
				this.allow = allow;
			}

			public String getName() {
				return name;
			}

			public List<String> getReqDocAsList() {
				List<String> result = new ArrayList<>();
				for (String requiredDoc : requiredDocs) {
					result.add(requiredDoc);
				}
				return result;
			}

			public List<String> getRequiredVacc() {
				return requiredVaccinations;
			}

			public void addAllRequiredVaccinations(List<String> vaccinList) {
				requiredVaccinations.addAll(vaccinList);
			}

			public void removeAllVaccinations(List<String> vaccinToRemove) {
				requiredVaccinations.removeAll(vaccinToRemove);
			}

			public void addRequiredDoc(String reqiredDoc) {
				requiredDocs.add(reqiredDoc);
			}

			public void removeRequiredDoc(String noLongerRequiredDoc) {
				for (int i = 0; i < requiredDocs.size(); i++) {
					if (requiredDocs.get(i).equalsIgnoreCase(noLongerRequiredDoc)) {
						requiredDocs.remove(i);
					}
				}
			}

		}
	}
}
