package papers2;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runners.JUnit4;



import java.util.*;

public class SolutionTest {

	@Test
	public void preliminaryTraining() {
		Inspector inspector = new Inspector();
		inspector.receiveBulletin("Entrants require passport\nAllow citizens of Arstotzka, Obristan");

		Map<String, String> josef = new HashMap<>();
		josef.put("passport",
				"ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");

		Map<String, String> guyovich = new HashMap<>();
		guyovich.put("access_permit",
				"NAME: Guyovich, Russian\nNATION: Obristan\nID#: TE8M1-V3N7R\nPURPOSE: TRANSIT\nDURATION: 14 DAYS\nHEIGHT: 159cm\nWEIGHT: 60kg\nEXP: 1983.07.13");

		Map<String, String> roman = new HashMap<>();
		roman.put("passport",
				"ID#: WK9XA-LKM0Q\nNATION: United Federation\nNAME: Dolanski, Roman\nDOB: 1933.01.01\nSEX: M\nISS: Shingleton\nEXP: 1983.05.12");
		roman.put("grant_of_asylum",
				"NAME: Dolanski, Roman\nNATION: United Federation\nID#: Y3MNC-TPWQ2\nDOB: 1933.01.01\nHEIGHT: 176cm\nWEIGHT: 71kg\nEXP: 1983.09.20");

		assertEquals("Glory to Arstotzka.", inspector.inspect(josef));
		assertEquals("Entry denied: missing required passport.", inspector.inspect(guyovich));
		assertEquals("Detainment: ID number mismatch.", inspector.inspect(roman));

		// Next day
		inspector.receiveBulletin(
				"Entrants require passport\n" + "Allow citizens of Arstotzka\n" + "Wanted by the State: Yvonna Zitna");

		Map<String, String> bernard = new HashMap<String, String>();
		bernard.put("passport", "NATION: Arstotzka\n" + "DOB: 1932.02.10\n" + "SEX: M\r\n" + "ISS: East Grestin\n"
				+ "ID#: Y2API-W7BS6\n" + "EXP: 1984.01.14\n" + "NAME: Petrova, Bernard");

		Map<String, String> misha = new HashMap<String, String>();
		misha.put("passport", "passport=NATION: Kolechia\r\n" + "DOB: 1957.08.26\n" + "SEX: F\n" + "ISS: Yurko City\n"
				+ "ID#: B72RX-PW99W\n" + "EXP: 1983.03.08\n" + "NAME: Burke, Misha");

		assertEquals("Glory to Arstotzka.", inspector.inspect(bernard));
		assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(misha));
		// Next day
		inspector.receiveBulletin("Entrants require passport\r\n" + "Allow citizens of Arstotzka\r\n"
				+ "Wanted by the State: Werner Zitna");

		Map<String, String> pyotr = new HashMap<String, String>();
		pyotr.put("passport", "NATION: Antegria\r\n" + "DOB: 1921.04.10\r\n" + "SEX: M\r\n" + "ISS: Outer Grouse\r\n"
				+ "ID#: TU5DQ-SZB5M\r\n" + "EXP: 1985.05.23\r\n" + "NAME: Jordan, Pyotr");

		assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(pyotr));

		Map<String, String> naomi = new HashMap<String, String>();
		naomi.put("passport", "NATION: United Federation\r\n" + "DOB: 1947.08.10\r\n" + "SEX: F\r\n"
				+ "ISS: Shingleton\r\n" + "ID#: I28ZZ-QK4EA\r\n" + "EXP: 1981.04.02\r\n" + "NAME: Schneider, Naomi");

		assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(naomi));

		Map<String, String> nicolai = new HashMap<String, String>();
		nicolai.put("passport", "NATION: Arstotzka\r\n" + "DOB: 1945.06.14\r\n" + "SEX: M\r\n" + "ISS: Orvech Vonor\r\n"
				+ "ID#: CBOS9-QM734\r\n" + "EXP: 1984.01.23\r\n" + "NAME: Vincenza, Nicolai");

		assertEquals("Glory to Arstotzka.", inspector.inspect(nicolai));

		Map<String, String> christiana = new HashMap<String, String>();
		christiana.put("passport", "NATION: Antegria\r\n" + "DOB: 1925.10.18\r\n" + "SEX: F\r\n" + "ISS: Glorian\r\n"
				+ "ID#: K0938-SZ0MQ\r\n" + "EXP: 1985.12.28\r\n" + "NAME: Radic, Christina");

		assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(christiana));

		Map<String, String> mikaela = new HashMap<String, String>();
		mikaela.put("passport", "NATION: Arstotzka\r\n" + "DOB: 1937.05.26\r\n" + "SEX: F\r\n" + "ISS: Orvech Vonor\r\n"
				+ "ID#: KYEF2-I1KYM\r\n" + "EXP: 1983.05.17\r\n" + "NAME: Borg, Mikaela");

		assertEquals("Glory to Arstotzka.", inspector.inspect(mikaela));

		Map<String, String> teresa = new HashMap<String, String>();
		teresa.put("passport", "NATION: Obristan\r\n" + "DOB: 1939.08.14\r\n" + "SEX: F\r\n" + "ISS: Skal\r\n"
				+ "ID#: BE5CV-BZPM9\r\n" + "EXP: 1985.05.06\r\n" + "NAME: Klass, Teresa");

		assertEquals("Cause no trouble.", inspector.inspect(teresa));

		Map<String, String> dimitry = new HashMap<String, String>();
		dimitry.put("passport", "NATION: Kolechia\r\n" + "DOB: 1942.02.12\r\n" + "SEX: M\r\n" + "ISS: Yurko City\r\n"
				+ "ID#: XSN5L-LELXR\r\n" + "EXP: 1985.02.23\r\n" + "NAME: Kremenliev, Dimitry");

		assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(dimitry));

		Map<String, String> aleksander = new HashMap<String, String>();
		aleksander.put("passport", "NATION: Kolechia\r\n" + "DOB: 1916.03.16\r\n" + "SEX: M\r\n" + "ISS: Yurko City\r\n"
				+ "ID#: HGRSM-U3N2P\r\n" + "EXP: 1984.04.06\r\n" + "NAME: Pejic, Aleksander");

		assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(aleksander));

		Map<String, String> werner = new HashMap<String, String>();
		werner.put("passport", "NATION: Antegria\r\n" + "DOB: 1932.03.20\r\n" + "SEX: M\r\n" + "ISS: Glorian\r\n"
				+ "ID#: GOH9D-UJ1QM\r\n" + "EXP: 1985.01.02\r\n" + "NAME: Zitna, Werner");

		assertEquals("Detainment: Entrant is a wanted criminal.", inspector.inspect(werner));

	}

	@Test
	public void newTest() {
		Inspector inspector = new Inspector();
		inspector.receiveBulletin("Allow citizens of Antegria, Impor, Kolechia, Obristan, Republia, United Federation\n"
				+ "Wanted by the State: Katherine Lindberg");

		Map<String, String> daniela = new HashMap<String, String>();
		daniela.put("passport", "NATION: Antegria\r\n" + "DOB: 1918.07.04\r\n" + "SEX: F\r\n" + "ISS: Outer Grouse\r\n"
				+ "ID#: BZG6O-K2O8B\r\n" + "EXP: 1985.07.11\r\n" + "NAME: Pai, Daniela");

		assertEquals("Cause no trouble.", inspector.inspect(daniela));

	}

	@Test
	public void newTest1() {
		Inspector inspector = new Inspector();
		inspector.receiveBulletin(
				"Allow citizens of Antegria, Impor, Kolechia, Obristan, Republia, United Federation\r\n"
						+ "Wanted by the State: Ivan Romanoff");

		Map<String, String> karl = new HashMap<String, String>();
		karl.put("passport", "NATION: Republia\r\n" + "DOB: 1938.01.27\r\n" + "SEX: M\r\n" + "ISS: True Glorian\r\n"
				+ "ID#: JZXS8-JX0D8\r\n" + "EXP: 1982.08.23\r\n" + "NAME: Carlstrom, Karl");

		assertEquals("Entry denied: passport expired.", inspector.inspect(karl));
	}

	@Test
	public void newTest2() {
		Inspector inspector = new Inspector();
		inspector.receiveBulletin("Foreigners require access permit\n" + "Wanted by the State: Brenna Ramos");

		Map<String, String> sasha = new HashMap<String, String>();
		sasha.put("passport", "NATION: United Federation\r\n" + "DOB: 1944.07.18\r\n" + "SEX: M\r\n"
				+ "ISS: Korista City\r\n" + "ID#: A1KH9-JD3XX\r\n" + "EXP: 1983.12.14\r\n" + "NAME: Medici, Sasha");
		sasha.put("access_permit",
				"NAME: Medici, Sasha\r\n" + "NATION: United Federation\r\n" + "ID#: A1KH9-JD3XX\r\n"
						+ "PURPOSE: TRANSIT\r\n" + "DURATION: 2 DAYS\r\n" + "HEIGHT: 175.0cm\r\n" + "WEIGHT: 84.0kg\r\n"
						+ "EXP: 1985.07.04");

		assertEquals("Cause no trouble.", inspector.inspect(sasha));

	}

	@Test
	public void newTest3() {
		Inspector inspector = new Inspector();
		inspector.receiveBulletin("Foreigners require access permit\r\n" + "Wanted by the State: Agnes Feyd");

		Map<String, String> agnes = new HashMap<String, String>();
		agnes.put("passport", "NATION: United Federation\r\n" + "DOB: 1942.12.12\r\n" + "SEX: F\r\n"
				+ "ISS: Korista City\r\n" + "ID#: HMG0X-FZ1NE\r\n" + "EXP: 1983.05.06\r\n" + "NAME: Feyd, Agnes");
		agnes.put("access_permit",
				"NAME: Feyd, Agnes\r\n" + "NATION: United Federation\r\n" + "ID#: HMG0X-FZ1NE\r\n"
						+ "PURPOSE: TRANSIT\r\n" + "DURATION: 2 DAYS\r\n" + "HEIGHT: 174.0cm\r\n" + "WEIGHT: 82.0kg\r\n"
						+ "EXP: 1982.04.11");

		assertEquals("Detainment: Entrant is a wanted criminal.", inspector.inspect(agnes));
	}

	@Test
	public void newTest4() {
		Inspector inspector = new Inspector();
		inspector.receiveBulletin("Foreigners require access permit\r\n" + "Wanted by the State: Agnes Feyd");

		Map<String, String> helga = new HashMap<String, String>();
		helga.put("work_pass", "NAME: Shaw, Helga\r\n" + "FIELD: Food service\r\n" + "EXP: 1983.07.20");
		helga.put("passport", "NATION: Republia\r\n" + "DOB: 1956.02.17\r\n" + "SEX: F\r\n" + "ISS: True Glorian\r\n"
				+ "ID#: FK7M2-JDSOT\r\n" + "EXP: 1983.04.21");
		helga.put("access_permit",
				"NAME: Shaw, Helga\r\n" + "NATION: Republia\r\n" + "ID#: FK7M2-JDSOT\r\n" + "PURPOSE: WORK\r\n"
						+ "DURATION: 6 MONTHS\r\n" + "HEIGHT: 153.0cm\r\n" + "WEIGHT: 51.0kg\r\n" + "EXP: 1981.05.27");

		assertEquals("Entry denied: access permit expired.", inspector.inspect(helga));

	}

	@Test
	public void newTest5() {
		Inspector inspector = new Inspector();
		inspector.receiveBulletin("Foreigners require access permit\r\n" + "Wanted by the State: Andre Hansson");

		Map<String, String> simon = new HashMap<String, String>();
		simon.put("passport", "NATION: Kolechia\r\n" + "DOB: 1946.03.14\r\n" + "SEX: M\r\n" + "ISS: West Grestin\r\n"
				+ "ID#: H247U-U7EAT\r\n" + "EXP: 1985.06.10\r\n" + "NAME: Stolichnaya, Simon");
		simon.put("access_permit",
				"NAME: Stolichnaya, Simon\r\n" + "NATION: Kolechia\r\n" + "ID#: H247U-U7EAT\r\n" + "PURPOSE: VISIT\r\n"
						+ "DURATION: 2 MONTHS\r\n" + "HEIGHT: 156.0cm\r\n" + "WEIGHT: 56.0kg\r\n" + "EXP: 1982.05.20");

		assertEquals("Entry denied: access permit expired.", inspector.inspect(simon));

	}

	@Test
	public void newTest6() {
		Inspector inspector = new Inspector();
		inspector.receiveBulletin("Foreigners require access permit\r\n" + "Wanted by the State: Matthew Reichenbach");

		Map<String, String> aron = new HashMap<String, String>();
		aron.put("passport", "NATION: Arstotzka\r\n" + "DOB: 1960.12.30\r\n" + "SEX: M\r\n" + "ISS: East Grestin\r\n"
				+ "ID#: G7NX9-ORM61\r\n" + "EXP: 1983.05.08\r\n" + "NAME: Fonseca, Aron");

		assertEquals("Glory to Arstotzka.", inspector.inspect(aron));

	}

	@Test
	public void newTest7() {
		Inspector inspector = new Inspector();
		inspector.receiveBulletin("Entrants require passport\r\n" + "Allow citizens of Arstotzka\r\n"
				+ "Wanted by the State: Jorge Strauss");

		Map<String, String> zera = new HashMap<String, String>();
		zera.put("passport", "NATION: Obristan\r\n" + "DOB: 1939.03.05\r\n" + "SEX: F\r\n" + "ISS: Mergerous\r\n"
				+ "ID#: SLPCE-TR2YK\r\n" + "EXP: 1985.10.07\r\n" + "NAME: Schulz, Zera");

		assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(zera));

	}

	@Test
	public void newTest8() {
		Inspector inspector = new Inspector();
		inspector.receiveBulletin("Entrants require passport\r\n" + "Allow citizens of Arstotzka\r\n"
				+ "Wanted by the State: Naomi Roberts");
		inspector.receiveBulletin(
				"Allow citizens of Antegria, Impor, Kolechia, Obristan, Republia, United Federation\r\n"
						+ "Wanted by the State: Michael Heintz");
		inspector.receiveBulletin("Foreigners require access permit\r\n" + "Wanted by the State: Wilma Bosch");

		Map<String, String> leonid = new HashMap<String, String>();
		leonid.put("passport", "NATION: Republia\r\n" + "DOB: 1944.11.17\r\n" + "SEX: M\r\n" + "ISS: True Glorian\r\n"
				+ "ID#: WVJ43-IV3PC\r\n" + "EXP: 1984.06.28\r\n" + "NAME: Knapik, Leonid");
		leonid.put("access_permit",
				"NAME: Knapik, Leonid\r\n" + "NATION: Republia\r\n" + "ID#: WVJ43-IV3PC\r\n" + "PURPOSE: TRANSIT\r\n"
						+ "DURATION: 14 DAYS\r\n" + "HEIGHT: 167.0cm\r\n" + "WEIGHT: 73.0kg\r\n" + "EXP: 1982.06.02");

		Map<String, String> adriana = new HashMap<String, String>();
		adriana.put("passport", "NATION: Kolechia\r\n" + "DOB: 1944.06.15\r\n" + "SEX: F\r\n" + "ISS: Vedor\r\n"
				+ "ID#: LM2NX-ASDTY\r\n" + "EXP: 1983.12.24\r\n" + "NAME: Chernovski, Adriana");
		adriana.put("diplomatic_authorization",
				"NATION: Kolechia\r\n" + "NAME: Chernovski, Adriana\r\n" + "ID#: LM2NX-ASDTY\r\n" + "ACCESS: Impor");

		assertEquals("Entry denied: invalid diplomatic authorization.", inspector.inspect(adriana));

	}

	@Test
	public void newTest9() {
		Inspector inspector = new Inspector();
		inspector.receiveBulletin("Citizens of Arstotzka require ID card\r\n" + "Deny citizens of Antegria\r\n"
				+ "Wanted by the State: Emma Atreides");
		
		Map<String, String> erika = new HashMap<String, String>();
		
		erika.put("passport", "NATION: Impor\r\n" + 
				"DOB: 1916.04.20\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Enkyo\r\n" + 
				"ID#: JO6NY-HQTRP\r\n" + 
				"EXP: 1983.05.14\r\n" + 
				"NAME: Jager, Erika");
		erika.put("diplomatic_authorization", "NATION: Impor\r\n" + 
				"NAME: Jager, Erika\r\n" + 
				"ID#: JO6NY-HQTRP\r\n" + 
				"ACCESS: Impor, Antegria, Republia");
		
		assertEquals("Cause no trouble.", inspector.inspect(erika));
		
	}
	
	@Test
	public void newTestFromCodeWars() {
		Inspector inspector = new Inspector();
		inspector.receiveBulletin("Entrants require passport\r\n" + 
				"Allow citizens of Arstotzka\r\n" + 
				"Wanted by the State: Stefani Odom");
		
		Map<String, String> renee = new HashMap<String, String>();
		renee.put("passport", "NATION: Arstotzka\r\n" + 
				"DOB: 1955.04.27\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Orvech Vonor\r\n" + 
				"ID#: C91QQ-EUZ24\r\n" + 
				"EXP: 1984.02.23\r\n" + 
				"NAME: Newman, Renee");
		
		assertEquals("Glory to Arstotzka.", inspector.inspect(renee));
		
		Map<String, String> anastasia = new HashMap<String, String>();
		anastasia.put("passport", "NATION: United Federation\r\n" + 
				"DOB: 1962.12.17\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Great Rapid\r\n" + 
				"ID#: EQETX-YEU77\r\n" + 
				"EXP: 1984.04.10\r\n" + 
				"NAME: Kowalska, Anastasia");
		
		assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(anastasia));
		
		Map<String, String> hubert = new HashMap<String, String>();
		hubert.put("passport", "NATION: United Federation\r\n" + 
				"DOB: 1927.01.01\r\n" + 
				"SEX: M\r\n" + 
				"ISS: Shingleton\r\n" + 
				"ID#: F0J8Q-N8YIE\r\n" + 
				"EXP: 1983.03.03\r\n" + 
				"NAME: Lovska, Hubert");
		
		assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(hubert));
		
		Map<String, String> anita = new HashMap<String, String>();
		anita.put("passport", "NATION: United Federation\r\n" + 
				"DOB: 1933.03.30\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Korista City\r\n" + 
				"ID#: TVFJT-DDLZ4\r\n" + 
				"EXP: 1983.08.28\r\n" + 
				"NAME: Kirsch, Anita");
		
		assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(anita));
		
		Map<String, String> misha = new HashMap<String, String>();
		misha.put("passport", "NATION: Obristan\r\n" + 
				"DOB: 1959.12.10\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Skal\r\n" + 
				"ID#: GDH54-N35D4\r\n" + 
				"EXP: 1983.12.29\r\n" + 
				"NAME: Jordan, Misha");
		
		assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(misha));
		
		Map<String, String> yvona = new HashMap<String, String>();
		yvona.put("passport", "NATION: Impor\r\n" + 
				"DOB: 1926.07.27\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Enkyo\r\n" + 
				"ID#: BBSUL-H5EWE\r\n" + 
				"EXP: 1984.02.23\r\n" + 
				"NAME: Bergman, Yvonna");
		
		assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(yvona));
		
		Map<String, String> leonid = new HashMap<String, String>();
		leonid.put("passport", "NATION: Kolechia\r\n" + 
				"DOB: 1951.02.08\r\n" + 
				"SEX: M\r\n" + 
				"ISS: Vedor\r\n" + 
				"ID#: GQ10L-C6G69\r\n" + 
				"EXP: 1984.08.11\r\n" + 
				"NAME: Thunstrom, Leonid");
		
		assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(leonid));
		
		Map<String, String> nadia = new HashMap<String, String>();
		nadia.put("passport", "NATION: Arstotzka\r\n" + 
				"DOB: 1937.06.04\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Paradizna\r\n" + 
				"ID#: N06YT-HC9SY\r\n" + 
				"EXP: 1985.05.24\r\n" + 
				"NAME: Guillot, Nadia");
		
		assertEquals("Glory to Arstotzka.", inspector.inspect(nadia));
		
		Map<String, String> gabriela = new HashMap<String, String>();
		gabriela.put("passport", "NATION: United Federation\r\n" + 
				"DOB: 1926.12.01\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Shingleton\r\n" + 
				"ID#: OL4WJ-FTR5Y\r\n" + 
				"EXP: 1981.07.12\r\n" + 
				"NAME: Lukowski, Gabriela");
		
		assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(gabriela));
		
		Map<String, String> teresa = new HashMap<String, String>();
		teresa.put("passport", "NATION: Arstotzka\r\n" + 
				"DOB: 1937.01.01\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Orvech Vonor\r\n" + 
				"ID#: ABOH3-DPIDT\r\n" + 
				"EXP: 1984.05.12\r\n" + 
				"NAME: Jordan, Teresa");
		
		assertEquals("Glory to Arstotzka.", inspector.inspect(teresa));
		
		Map<String, String> stefani = new HashMap<String, String>();
		stefani.put("passport", "NATION: Antegria\r\n" + 
				"DOB: 1956.10.03\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Glorian\r\n" + 
				"ID#: REO0A-AIO9G\r\n" + 
				"EXP: 1985.11.16\r\n" + 
				"NAME: Odom, Stefani");
		
		assertEquals("Detainment: Entrant is a wanted criminal.", inspector.inspect(stefani));
		
		Map<String, String> christina = new HashMap<String, String>();
		christina.put("passport", "NATION: Obristan\r\n" + 
				"DOB: 1954.09.27\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Lorndaz\r\n" + 
				"ID#: CR24O-GSJ14\r\n" + 
				"EXP: 1983.07.15\r\n" + 
				"NAME: Mateo, Christina");
		
		assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(christina));
		
		inspector.receiveBulletin("Allow citizens of Antegria, Impor, Kolechia, Obristan, Republia, United Federation\r\n" + 
				"Wanted by the State: Sharona Babayev");
		
		Map<String, String> michael = new HashMap<String, String>();
		michael.put("passport", "NATION: Obristan\r\n" + 
				"DOB: 1952.03.24\r\n" + 
				"SEX: M\r\n" + 
				"ISS: Mergerous\r\n" + 
				"ID#: VMXJN-K3VPD\r\n" + 
				"EXP: 1985.02.26\r\n" + 
				"NAME: Graham, Michael");
		
		assertEquals("Cause no trouble.", inspector.inspect(michael));
		
		Map<String, String> rafal = new HashMap<String, String>();
		rafal.put("passport", "NATION: Impor\r\n" + 
				"DOB: 1929.01.01\r\n" + 
				"SEX: M\r\n" + 
				"ISS: Enkyo\r\n" + 
				"ID#: JHAPE-RJVFP\r\n" + 
				"EXP: 1983.03.09\r\n" + 
				"NAME: Graham, Rafal");
		
		assertEquals("Cause no trouble.", inspector.inspect(rafal));
		
		Map<String, String> sharona = new HashMap<String, String>();
		sharona.put("passport", "NATION: Impor\r\n" + 
				"DOB: 1930.07.12\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Haihan\r\n" + 
				"ID#: V3XMI-Y0A3A\r\n" + 
				"EXP: 1981.11.14\r\n" + 
				"NAME: Babayev, Sharona");
		
		assertEquals("Detainment: Entrant is a wanted criminal.", inspector.inspect(sharona));
		
		Map<String, String> danil = new HashMap<String, String>();
		danil.put("passport", "NATION: Obristan\r\n" + 
				"DOB: 1924.09.23\r\n" + 
				"SEX: M\r\n" + 
				"ISS: Lorndaz\r\n" + 
				"ID#: OSAB4-S1S7H\r\n" + 
				"EXP: 1984.07.29\r\n" + 
				"NAME: Karnov, Danil");
		
		assertEquals("Cause no trouble.", inspector.inspect(danil));
		
		Map<String, String> victoria = new HashMap<String, String>();
		victoria.put("passport", "NATION: Kolechia\r\n" + 
				"DOB: 1949.08.31\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Yurko City\r\n" + 
				"ID#: W3UHJ-J5SBD\r\n" + 
				"EXP: 1983.02.07\r\n" + 
				"NAME: Pai, Victoria");
		
		assertEquals("Cause no trouble.", inspector.inspect(victoria));
		
		Map<String, String> abdullah = new HashMap<String, String>();
		abdullah.put("passport", "NATION: United Federation\r\n" + 
				"DOB: 1936.04.29\r\n" + 
				"SEX: M\r\n" + 
				"ISS: Shingleton\r\n" + 
				"ID#: LY121-O2ZK3\r\n" + 
				"EXP: 1984.11.04\r\n" + 
				"NAME: Kerr, Abdullah");
		
		assertEquals("Cause no trouble.", inspector.inspect(abdullah));
		
		Map<String, String> lorena = new HashMap<String, String>();
		lorena.put("passport", "NATION: Kolechia\r\n" + 
				"DOB: 1951.07.21\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Vedor\r\n" + 
				"ID#: FLFBE-KDUXG\r\n" + 
				"EXP: 1985.02.17\r\n" + 
				"NAME: Kaczynska, Lorena");
		
		assertEquals("Cause no trouble.", inspector.inspect(lorena));
		
		Map<String, String> attila = new HashMap<String, String>();
		attila.put("passport", "NATION: Obristan\r\n" + 
				"DOB: 1942.07.03\r\n" + 
				"SEX: M\r\n" + 
				"ISS: Lorndaz\r\n" + 
				"ID#: OWH09-QR46F\r\n" + 
				"EXP: 1985.02.06\r\n" + 
				"NAME: Smirnov, Attila");
		
		assertEquals("Cause no trouble.", inspector.inspect(attila));
		
		Map<String, String> andrei = new HashMap<String, String>();
		andrei.put("passport", "NATION: Arstotzka\r\n" + 
				"DOB: 1956.12.27\r\n" + 
				"SEX: M\r\n" + 
				"ISS: Orvech Vonor\r\n" + 
				"ID#: RM3AQ-JKI2P\r\n" + 
				"EXP: 1983.02.28\r\n" + 
				"NAME: Thunstrom, Andrej\r\n" + 
				"");
		
		assertEquals("Glory to Arstotzka.", inspector.inspect(andrei));
		
		Map<String, String> sasha = new HashMap<String, String>();
		sasha.put("passport", "NATION: Impor\r\n" + 
				"DOB: 1953.08.19\r\n" + 
				"SEX: M\r\n" + 
				"ISS: Tsunkeido\r\n" + 
				"ID#: KEXCK-X41QN\r\n" + 
				"EXP: 1981.09.23\r\n" + 
				"NAME: Peterson, Sasha");
		
		assertEquals("Entry denied: passport expired.", inspector.inspect(sasha));
		
		Map<String, String> bernerd = new HashMap<String, String>();
		bernerd.put("passport", "NATION: United Federation\r\n" + 
				"DOB: 1937.05.27\r\n" + 
				"SEX: M\r\n" + 
				"ISS: Great Rapid\r\n" + 
				"ID#: XPDYA-E3I5E\r\n" + 
				"EXP: 1982.12.17\r\n" + 
				"NAME: Bosch, Bernard");
		
		assertEquals("Cause no trouble.", inspector.inspect(bernerd));
		
		Map<String, String> frederic = new HashMap<String, String>();
		frederic.put("passport", "NATION: Obristan\r\n" + 
				"DOB: 1931.07.23\r\n" + 
				"SEX: M\r\n" + 
				"ISS: Skal\r\n" + 
				"ID#: R2GCH-ECDQX\r\n" + 
				"EXP: 1985.01.01\r\n" + 
				"NAME: Odom, Frederic");
		
		assertEquals("Cause no trouble.", inspector.inspect(frederic));
		
		
		inspector.receiveBulletin("Foreigners require access permit\r\n" + 
				"Wanted by the State: Galina Heintz");
		
		Map<String, String> katarina = new HashMap<String, String>();
		katarina.put("passport", "NATION: United Federation\r\n" + 
				"DOB: 1947.04.03\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Korista City\r\n" + 
				"ID#: DWEC7-X5Q5G\r\n" + 
				"EXP: 1984.04.23\r\n" + 
				"NAME: Pearl, Katarina");
		katarina.put("diplomatic_authorization", "NATION: United Federation\r\n" + 
				"NAME: Pearl, Katarina\r\n" + 
				"ID#: DWEC7-X5Q5G\r\n" + 
				"ACCESS: Arstotzka, Antegria");
		
		assertEquals("Cause no trouble.", inspector.inspect(katarina));
		
		Map<String, String> hubert1 = new HashMap<String, String>();
		hubert1.put("passport", "NATION: Kolechia\r\n" + 
				"DOB: 1916.05.03\r\n" + 
				"SEX: M\r\n" + 
				"ISS: Vedor\r\n" + 
				"ID#: GFIZU-XMXQQ\r\n" + 
				"EXP: 1983.12.26\r\n" + 
				"NAME: Kremenliev, Hubert");
		hubert1.put("access_permit", "NAME: Kremenliev, Hubert\r\n" + 
				"NATION: United Federation\r\n" + 
				"ID#: GFIZU-XMXQQ\r\n" + 
				"PURPOSE: IMMIGRATE\r\n" + 
				"DURATION: FOREVER\r\n" + 
				"HEIGHT: 181.0cm\r\n" + 
				"WEIGHT: 92.0kg\r\n" + 
				"EXP: 1983.07.09");
		
		assertEquals("Detainment: nationality mismatch.", inspector.inspect(hubert1));
		
		Map<String, String> lena = new HashMap<String, String>();
		lena.put("passport", "NATION: Arstotzka\r\n" + 
				"DOB: 1959.05.30\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Paradizna\r\n" + 
				"ID#: FGP3L-ZLB2X\r\n" + 
				"EXP: 1982.09.21\r\n" + 
				"NAME: Novak, Lena");
		
		assertEquals("Entry denied: passport expired.", inspector.inspect(lena));
		
		Map<String, String> ivana = new HashMap<String, String>();
		ivana.put("passport", "NATION: Arstotzka\r\n" + 
				"DOB: 1942.02.25\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Paradizna\r\n" + 
				"ID#: SVWMB-MTG8N\r\n" + 
				"EXP: 1983.01.12\r\n" + 
				"NAME: Johannson, Ivana");
		
		assertEquals("Glory to Arstotzka.", inspector.inspect(ivana));
		
		
		Map<String, String> dimitry = new HashMap<String, String>();
		dimitry.put("passport", "NATION: Kolechia\r\n" + 
				"DOB: 1955.01.10\r\n" + 
				"SEX: M\r\n" + 
				"ISS: Vedor\r\n" + 
				"ID#: IITXL-G7FR0\r\n" + 
				"EXP: 1983.09.12\r\n" + 
				"NAME: Watson, Dimitry");
		dimitry.put("access_permit", "NAME: Watson, Dimitry\r\n" + 
				"NATION: Kolechia\r\n" + 
				"ID#: IITXL-G7FR0\r\n" + 
				"PURPOSE: IMMIGRATE\r\n" + 
				"DURATION: FOREVER\r\n" + 
				"HEIGHT: 155.0cm\r\n" + 
				"WEIGHT: 55.0kg\r\n" + 
				"EXP: 1981.12.16");
		
		assertEquals("Entry denied: access permit expired.", inspector.inspect(dimitry));
		
		Map<String, String> ingrid = new HashMap<String, String>();
		ingrid.put("passport", "NATION: United Federation\r\n" + 
				"DOB: 1948.10.19\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Great Rapid\r\n" + 
				"ID#: BPR8N-CKZZQ\r\n" + 
				"EXP: 1983.03.31\r\n" + 
				"NAME: Babayev, Ingrid");
		ingrid.put("access_permit", "NAME: Babayev, Ingrid\r\n" + 
				"NATION: United Federation\r\n" + 
				"ID#: BPR8N-CKZZQ\r\n" + 
				"PURPOSE: VISIT\r\n" + 
				"DURATION: 3 MONTHS\r\n" + 
				"HEIGHT: 153.0cm\r\n" + 
				"WEIGHT: 52.0kg\r\n" + 
				"EXP: 1983.01.16");
		
		assertEquals("Cause no trouble.", inspector.inspect(ingrid));
		
		Map<String, String> galina = new HashMap<String, String>();
		galina.put("passport", "NATION: Obristan\r\n" + 
				"DOB: 1955.05.31\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Skal\r\n" + 
				"ID#: XD0FV-KPWYZ\r\n" + 
				"EXP: 1984.11.29\r\n" + 
				"NAME: Heintz, Galina");
		
		assertEquals("Detainment: Entrant is a wanted criminal.", inspector.inspect(galina));

		Map<String, String> nicole = new HashMap<String, String>();
		nicole.put("passport", "NATION: Republia\r\n" + 
				"DOB: 1919.06.17\r\n" + 
				"SEX: F\r\n" + 
				"ISS: True Glorian\r\n" + 
				"ID#: FS1H1-LHPXD\r\n" + 
				"EXP: 1982.02.14\r\n" + 
				"NAME: Blanco, Nicole");
		nicole.put("access_permit", "NAME: Blanco, Nicole\r\n" + 
				"NATION: Impor\r\n" + 
				"ID#: FS1H1-LHPXD\r\n" + 
				"PURPOSE: VISIT\r\n" + 
				"DURATION: 3 MONTHS\r\n" + 
				"HEIGHT: 167.0cm\r\n" + 
				"WEIGHT: 71.0kg\r\n" + 
				"EXP: 1985.02.06");
		
		assertEquals("Detainment: nationality mismatch.", inspector.inspect(nicole));
		
		Map<String, String> agnes = new HashMap<String, String>();
		agnes.put("work_pass", "NAME: Hansson, Agnes\r\n" + 
				"FIELD: General labor\r\n" + 
				"EXP: 1981.08.03");
		agnes.put("passport", "NATION: Obristan\r\n" + 
				"DOB: 1934.02.19\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Lorndaz\r\n" + 
				"ID#: IBFLP-BNOP5\r\n" + 
				"EXP: 1984.02.25\r\n" + 
				"NAME: Hansson, Agnes\r\n" + 
				"");
		agnes.put("access_permit", "NAME: Hansson, Agnes\r\n" + 
				"NATION: Obristan\r\n" + 
				"ID#: IBFLP-BNOP5\r\n" + 
				"PURPOSE: WORK\r\n" + 
				"DURATION: 2 MONTHS\r\n" + 
				"HEIGHT: 181.0cm\r\n" + 
				"WEIGHT: 93.0kg\r\n" + 
				"EXP: 1984.03.16");
		
		assertEquals("Entry denied: work pass expired.", inspector.inspect(agnes));
		
		Map<String, String> javier = new HashMap<String, String>();
		javier.put("passport", "NATION: Antegria\r\n" + 
				"DOB: 1921.08.08\r\n" + 
				"SEX: M\r\n" + 
				"ISS: Outer Grouse\r\n" + 
				"ID#: ND66Q-O64SF\r\n" + 
				"EXP: 1984.06.23\r\n" + 
				"NAME: Olah, Javier");
		javier.put("access_permit", "NAME: Olah, Javier\r\n" + 
				"NATION: Antegria\r\n" + 
				"ID#: ND66Q-O64SF\r\n" + 
				"PURPOSE: IMMIGRATE\r\n" + 
				"DURATION: FOREVER\r\n" + 
				"HEIGHT: 177.0cm\r\n" + 
				"WEIGHT: 86.0kg\r\n" + 
				"EXP: 1985.11.20");
		
		assertEquals("Cause no trouble.", inspector.inspect(javier));
		
		Map<String, String> anna = new HashMap<String, String>();
		anna.put("passport", "NATION: Arstotzka\r\n" + 
				"DOB: 1949.03.14\r\n" + 
				"SEX: F\r\n" + 
				"ISS: East Grestin\r\n" + 
				"ID#: M8M0A-L05YD\r\n" + 
				"EXP: 1983.10.14\r\n" + 
				"NAME: Watson, Anna");
		
		assertEquals("Glory to Arstotzka.", inspector.inspect(anna));
		
		Map<String, String> david = new HashMap<String, String>();
		david.put("passport", "NATION: Arstotzka\r\n" + 
				"DOB: 1928.09.05\r\n" + 
				"SEX: M\r\n" + 
				"ISS: Paradizna\r\n" + 
				"ID#: H624I-VQP06\r\n" + 
				"EXP: 1983.06.21\r\n" + 
				"NAME: Fischer, David");
		
		assertEquals("Glory to Arstotzka.", inspector.inspect(anna));
		
		inspector.receiveBulletin("Citizens of Arstotzka require ID card\r\n" + 
				"Deny citizens of Republia\r\n" + 
				"Foreigners require polio vaccination\r\n" + 
				"Wanted by the State: Michelle Lang");
		
		Map<String, String> beatrix = new HashMap<String, String>();
		beatrix.put("passport", "NATION: Republia\r\n" + 
				"DOB: 1949.10.04\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Bostan\r\n" + 
				"ID#: OJ6QH-T51IQ\r\n" + 
				"EXP: 1984.12.16\r\n" + 
				"NAME: Latva, Beatrix");
		beatrix.put("access_permit", "NAME: Latva, Beatrix\r\n" + 
				"NATION: Republia\r\n" + 
				"ID#: OJ6QH-T51IQ\r\n" + 
				"PURPOSE: TRANSIT\r\n" + 
				"DURATION: 2 DAYS\r\n" + 
				"HEIGHT: 167.0cm\r\n" + 
				"WEIGHT: 72.0kg\r\n" + 
				"EXP: 1983.12.27");
		beatrix.put("certificate_of_vaccination", "NAME: Latva, Beatrix\r\n" + 
				"ID#: OJ6QH-T51IQ\r\n" + 
				"VACCINES: polio, HPV, yellow fever");
		
		
		assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(beatrix));

		
	}
	
	@Test
	public void day27thTest() {
		Inspector inspector = new Inspector();
		inspector.receiveBulletin("Entrants require passport\r\n" + 
				"Allow citizens of Arstotzka\r\n" + 
				"Wanted by the State: Gregory Roberts");
		
		Map<String, String> ivanka = new HashMap<String, String>();
		ivanka.put("passport", "NATION: United Federation\r\n" + 
				"DOB: 1919.06.14\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Great Rapid\r\n" + 
				"ID#: PJ2F1-NUY44\r\n" + 
				"EXP: 1984.12.27\r\n" + 
				"NAME: Seczek, Ivanka");
		
		assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(ivanka));
		
		inspector.receiveBulletin("Allow citizens of Antegria, Impor, Kolechia, Obristan, Republia, United Federation\r\n" + 
				"Wanted by the State: Samantha Roberts");
		
		Map<String, String> eva = new HashMap<String, String>();
		eva.put("passport", "NATION: Republia\r\n" + 
				"DOB: 1951.02.01\r\n" + 
				"SEX: F\r\n" + 
				"ISS: True Glorian\r\n" + 
				"ID#: CLT8O-JJKQO\r\n" + 
				"EXP: 1984.06.16\r\n" + 
				"NAME: Aji, Eva");
		
		assertEquals("Cause no trouble.", inspector.inspect(eva));
		
		inspector.receiveBulletin("Foreigners require access permit\r\n" + 
				"Wanted by the State: Isabella Schneider");
		
		Map<String, String> elena = new HashMap<String, String>();
		elena.put("passport", "NATION: Arstotzka\r\n" + 
				"DOB: 1943.12.24\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Orvech Vonor\r\n" + 
				"ID#: MGNS1-S1I20\r\n" + 
				"EXP: 1984.08.12\r\n" + 
				"NAME: Young, Elena");
		
		assertEquals("Glory to Arstotzka.", inspector.inspect(elena));
		
		inspector.receiveBulletin("Citizens of Arstotzka require ID card\r\n" + 
				
				"Wanted by the State: Omid Tsarnaeva");
		
		Map<String, String> artur = new HashMap<String, String>();
		artur.put("access_permit", "NAME: Macek, Artour\r\n" + 
				"NATION: Obristan\r\n" + 
				"ID#: T0OZC-ZOUP7\r\n" + 
				"PURPOSE: VISIT\r\n" + 
				"DURATION: 14 DAYS\r\n" + 
				"HEIGHT: 175.0cm\r\n" + 
				"WEIGHT: 83.0kg\r\n" + 
				"EXP: 1981.10.18");
		assertEquals("Entry denied: missing required passport.", inspector.inspect(artur));
		
		inspector.receiveBulletin(
				"Workers require work pass\r\n" + 
				"Wanted by the State: Gregor Reed");
		
		Map<String, String> vlad = new HashMap<String, String>();
		vlad.put("passport", "NATION: Obristan\r\n" + 
				"DOB: 1958.12.06\r\n" + 
				"SEX: M\r\n" + 
				"ISS: Lorndaz\r\n" + 
				"ID#: W3P8Z-D5AYN\r\n" + 
				"EXP: 1983.11.04\r\n" + 
				"NAME: Andrevska, Vlad");
		
		assertEquals("Entry denied: missing required access permit.", inspector.inspect(vlad));
		
		inspector.receiveBulletin("Deny citizens of Antegria\r\n" + 
				"Entrants require polio vaccination\r\n" + 
				"Wanted by the State: Naomi Muller");
		
		Map<String, String> katarina = new HashMap<String, String>();
		katarina.put("passport", "NATION: Kolechia\r\n" + 
				"DOB: 1959.05.04\r\n" + 
				"SEX: F\r\n" + 
				"ISS: Vedor\r\n" + 
				"ID#: QGVZV-RA968\r\n" + 
				"EXP: 1983.01.28\r\n" + 
				"NAME: Hammerstein, Katarina");
		
		assertEquals("Entry denied: missing required access permit.", inspector.inspect(katarina));
		
		Map<String, String> sasha2 = new HashMap<String, String>();
		sasha2.put("passport", "NATION: Arstotzka\r\n" + 
				"DOB: 1934.09.10\r\n" + 
				"SEX: M\r\n" + 
				"ISS: East Grestin\r\n" + 
				"ID#: N3MMI-BRG50\r\n" + 
				"EXP: 1983.05.02\r\n" + 
				"NAME: Leonov, Sasha");
		
		assertEquals("Entry denied: missing required ID card.", inspector.inspect(sasha2));

		

	}

}
