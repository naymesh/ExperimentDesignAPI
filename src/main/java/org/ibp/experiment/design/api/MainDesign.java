
package org.ibp.experiment.design.api;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Templates")
public class MainDesign implements Serializable {

	private static final long serialVersionUID = -1000046634614287686L;
	private ExpDesign design;

	public MainDesign() {

	}

	public MainDesign(ExpDesign design) {
		super();
		this.design = design;
	}

	@XmlElement(name = "Template")
	public ExpDesign getDesign() {
		return this.design;
	}

	public void setDesign(ExpDesign design) {
		this.design = design;
	}

	@Override
	public String toString() {
		return "MainDesign [design=" + this.design + "]";
	}

}
