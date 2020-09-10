package papers2;

import java.util.ArrayList;
import java.util.List;



public class Country {
	String name;
	List<String> requiredVaccinations;
	List<RequiredDoc> requiredDocs;

	public Country(String name) {
		this.name = name;
		requiredVaccinations = new ArrayList<>();
		requiredDocs = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public List<String> getReqDocAsList() {
		List<String> result = new ArrayList<>();
		for (RequiredDoc requiredDoc : requiredDocs) {
			result.add(requiredDoc.getTitle());
		}
		return result;
	}

	public void addRequiderVaccination(String vaccination) {
		requiredVaccinations.add(vaccination);
	}

	public void addAllRequiredVaccinations(List<String> vaccinList) {
		requiredVaccinations.addAll(vaccinList);
	}

	public void addRequiredDoc(RequiredDoc reqiredDoc) {
		requiredDocs.add(reqiredDoc);
	}

	public void addAllRequoredDocs(List<RequiredDoc> requireddocs) {
		requiredDocs.addAll(requireddocs);
	}
}