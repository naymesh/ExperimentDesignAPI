
package org.ibp.experiment.design.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import au.com.bytecode.opencsv.CSVReader;

@RestController
@RequestMapping("/experimentdesign")
public class ExperimentalDesignResource {

	private static final Logger LOG = LoggerFactory.getLogger(ExperimentalDesignResource.class);

	@Autowired
	private Environment environmentConfig;

	@RequestMapping(value = "/generate", method = RequestMethod.POST, headers = "Content-Type=application/xml")
	@ResponseBody
	public List<String[]> gerenateDesign(@RequestBody String body) throws Exception {
		return this.runBVDesign(body);
	}

	public List<String[]> runBVDesign(String requestXML) throws JAXBException, IOException, FileNotFoundException {
		LOG.debug("Input XML is:\n{}", requestXML);

		MainDesign mainDesign = ExperimentalDesignResource.toMainDesign(requestXML);
		int returnCode = -1;

		ProcessBuilder pb =
				new ProcessBuilder(this.environmentConfig.getProperty("breeding.view.exe.path"), "-i"
						+ ExperimentalDesignResource.writeToFile(requestXML));
		Process p = pb.start();
		try {
			InputStreamReader isr = new InputStreamReader(p.getInputStream());
			BufferedReader br = new BufferedReader(isr);

			String lineRead;
			while ((lineRead = br.readLine()) != null) {
				ExperimentalDesignResource.LOG.debug(lineRead);
			}

			returnCode = p.waitFor();
		} catch (InterruptedException e) {
			ExperimentalDesignResource.LOG.error(e.getMessage(), e);
		} finally {
			if (p != null) {
				p.getInputStream().close();
				p.getOutputStream().close();
				p.getErrorStream().close();
			}
		}

		List<String[]> bvResult = new ArrayList<String[]>();
		String outputFileName = mainDesign.getDesign().getParameterValue("outputfile");

		if (returnCode == 0) {
			File outputFile = new File(outputFileName);
			FileReader fileReader = new FileReader(outputFile);
			CSVReader reader = new CSVReader(fileReader);
			bvResult = reader.readAll();
			fileReader.close();
			reader.close();
		}

		LOG.debug("Result:\n{}", bvResult);
		return bvResult;
	}

	private static String writeToFile(String xml) {
		String filenamePath = new File(System.currentTimeMillis() + "-bv-input.xml").getAbsolutePath();
		try {
			File file = new File(filenamePath);
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(xml);
			output.close();
			filenamePath = file.getAbsolutePath();
		} catch (IOException e) {
			ExperimentalDesignResource.LOG.error(e.getMessage(), e);
		}
		return filenamePath;
	}

	public static MainDesign toMainDesign(String xmlString) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(MainDesign.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (MainDesign) unmarshaller.unmarshal(new StringReader(xmlString));
	}
}
