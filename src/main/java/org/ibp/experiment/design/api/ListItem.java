
package org.ibp.experiment.design.api;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

public class ListItem implements Serializable {

	private static final long serialVersionUID = 9074176001883831000L;
	private String value;

	public ListItem() {
		super();
	}

	public ListItem(String value) {
		super();
		this.value = value;
	}

	@XmlAttribute
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ListItem [value=" + this.value + "]";
	}

}
