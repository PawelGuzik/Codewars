package papers2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import papers2.DocumentCreator.DocAttributes;

public class Person {
	List<Document> docList;
	DocAttributes diferentAttr;
	public Person(Map<String, String> personMap) {
		docList = DocumentCreator.create(personMap);

	}
	
	public String getNation() {
		for (Document doc : docList) {
			String nation = doc.getNation();
			if(nation.length()>0) {
				return nation; 
			}
		}
		return "";
	}
	
	public String getName() {
		for (Document doc : docList) {
			String name = doc.getPersonName();
			if(name.length()>0) {
				return name; 
			}
		}
		return "";
	}
	
	public List<String> getDocumentsNames(){
		List<String> docsNames = new ArrayList<>();
		for (Document doc : docList) {
			docsNames.add(doc.getType());
		}
		return docsNames;
	}
	
	public Document getDocByType(String type) {
		for (Document document : docList) {
			if(document.getType().equalsIgnoreCase(type)) {
				return document;
			}
		}
		return null;
	}
}