package papers2;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DocumentCreatorUTest {
	@Test
	public void creationTest() {
		Map<String, String> josef = new HashMap<>();
		josef.put("passport",
				"ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");

		
		List<Document> docsList =  DocumentCreator.create(josef);
		Document doc = docsList.get(0);
		assertEquals("GC07D-FU8AR", doc.getId());
		assertEquals("Arstotzka", doc.getNation());
		assertEquals("Costanza, Josef", doc.getPersonName());
		assertEquals("1933.11.28", doc.getDob());
		assertEquals("M", doc.getSex());
		assertEquals("East Grestin", doc.getIss());
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy.MM.dd");
		Date exp =null;
		try {
			 exp = sdformat.parse("1983.03.15");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(exp, doc.getExp());
		
		Map<String, String> guyovich = new HashMap<>();
		guyovich.put("access_permit",
				"NAME: Guyovich, Russian\nNATION: Obristan\nID#: TE8M1-V3N7R\nPURPOSE: TRANSIT\nDURATION: 14 DAYS\nHEIGHT: 159cm\nWEIGHT: 60kg\nEXP: 1983.07.13");
		docsList.addAll(DocumentCreator.create(guyovich));
		doc  = docsList.get(1);
		assertEquals("Guyovich, Russian", doc.getPersonName());
		assertEquals("Obristan", doc.getNation());
		assertEquals("TE8M1-V3N7R", doc.getId());
		assertEquals("TRANSIT", doc.getPurpose());
		assertEquals("14 DAYS", doc.getDuration());
		assertEquals("159cm", doc.getHeight());
		assertEquals("60kg", doc.getWeight());
		
		
		try {
			 exp = sdformat.parse("1983.07.13");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(exp, doc.getExp());
		
	}

}
