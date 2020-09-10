package papers2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import papers2.DocumentCreator.DocAttributes;

public class Document {
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

	public static class Builder {
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

			try {
				this.exp = sdformat.parse(exp);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return this;
		}

		// Calls function which is proper for given attribute name
		public Builder callFunction(DocAttributes attrName, Object attrValue) {
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
	}
	
	public String getExpAsString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy.mm.dd");  
        return  dateFormat.format(exp);  
	}

	public DocAttributes compareDocuments(Document secondDoc) {

		if (!this.personName.equalsIgnoreCase(secondDoc.personName)) {
			return DocAttributes.NAME;
		}
		if (!this.id.equalsIgnoreCase(secondDoc.id)) {
			return DocAttributes.ID;
		}
		if (!this.nation.equalsIgnoreCase(secondDoc.nation)) {
			return DocAttributes.NATION;
		}
		if (!this.dob.equalsIgnoreCase(secondDoc.dob)) {
			return DocAttributes.DOB;
		}
		if (!this.purpose.equalsIgnoreCase(secondDoc.purpose)) {
			return DocAttributes.PURPOSE;
		}
		if (!this.duration.equalsIgnoreCase(secondDoc.duration)) {
			return DocAttributes.DURATION;
		}
		if (!this.weight.equalsIgnoreCase(secondDoc.weight)) {
			return DocAttributes.WEIGHT;
		}
		if (!this.height.equalsIgnoreCase(secondDoc.height)) {
			return DocAttributes.HEIGHT;
		}
		if (!this.sex.equalsIgnoreCase(secondDoc.sex)) {
			return DocAttributes.SEX;
		}
		if (!this.iss.equalsIgnoreCase(secondDoc.iss)) {
			return DocAttributes.ISS;
		}
		if (!this.exp.equals(secondDoc.exp)) {
			return DocAttributes.EXP;
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
