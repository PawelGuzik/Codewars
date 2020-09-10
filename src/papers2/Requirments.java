package papers2;

import java.util.ArrayList;
import java.util.List;



public class Requirments {
	List<Country> countryList;
	String wantedCriminal;
	private List<RequiredDoc> docWantedFromAll;

public Requirments() {
	countryList = new ArrayList<>();
	wantedCriminal = "";
	docWantedFromAll = new ArrayList<>();
}

public List<RequiredDoc> getDocWantedFromAll() {
	return docWantedFromAll;
}

public void setDocWantedFromAll(List<String> docWantedFromAll) {
	for (String string : docWantedFromAll) {
		this.docWantedFromAll.add(new RequiredDoc(string));
	}
}

public void setWantedCriminal(String criminal) {
	wantedCriminal = criminal;
}

public String getWantedCriminal() {
	return wantedCriminal;
}

public void addCountry(Country country) {

	countryList.add(country);
}

// Updates countries wanted docs with docWantedFromAll list
public void updateWantedDocs() {

	for (Country country : countryList) {
		country.addAllRequoredDocs(docWantedFromAll);
	}
}

public void addCountries(List<Country> countriesList) {
	countryList.addAll(countriesList);
}

public void addCountryAsString(String countryName) {
	countryList.add(new Country(countryName));
}

public void addCountriesAsString(List<String> countriesNames) {
	for (String countryName : countriesNames) {
		Country countryToAdd = new Country(countryName);
		if (!countryList.contains(countryToAdd)) {
			countryList.add(countryToAdd);
		}
	}
}

public void removeCountriesAsString(List<String> countriesToRemove) {

	for (String countryName : countriesToRemove) {
		Country countryToRemove = new Country(countryName);
		if (countryList.contains(countryToRemove)) {
			countryList.remove(countryToRemove);
		}
	}

}

public void addRequiredDocForCountries(List<String> docsNames, String excpetCountry) {
	docsNames.forEach(dn -> {
		countryList.forEach(c -> {
			if (!c.getName().equalsIgnoreCase(excpetCountry)) {
				c.addRequiredDoc(new RequiredDoc(dn));
			}
		});
	});
}

public void addRequiredVaccinations(String vaccination, List<Country> countriesNeedVaccin) {
	for (Country country : countryList) {
		if (countriesNeedVaccin.contains(country)) {
			country.addRequiderVaccination(vaccination);
		}
	}
}

public void addRequiredDocForCountry(String docName, String countryWithDoc) {
	for(Country country : countryList) {
		if(country.getName().equalsIgnoreCase(countryWithDoc)) {
			country.addRequiredDoc(new RequiredDoc(docName));
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
		countries.add(country.getName());
	}
	return countries;
}

}
