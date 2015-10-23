
package org.ibp;

import java.io.IOException;

import org.ibp.experiment.design.api.BVDesignOutput;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
public class DesignerApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testParseBVDesignOutputFromJSON() throws JsonParseException, JsonMappingException, IOException {

		String json =
				"{\"returnCode\":0,"
						+ "\"bvHeaders\":[\"PLOT_NO\",\"REP_NO\",\"ENTRY_NO\"],"
						+ "\"bvResultList\":[[\"1\",\"1\",\"17\"],[\"2\",\"1\",\"8\"],[\"3\",\"1\",\"7\"],[\"4\",\"1\",\"4\"],[\"5\",\"1\",\"14\"],[\"6\",\"1\",\"11\"],[\"7\",\"1\",\"5\"],[\"8\",\"1\",\"16\"],[\"9\",\"1\",\"9\"],[\"10\",\"1\",\"20\"],[\"11\",\"1\",\"2\"],[\"12\",\"1\",\"13\"],[\"13\",\"1\",\"18\"],[\"14\",\"1\",\"6\"],[\"15\",\"1\",\"19\"],[\"16\",\"1\",\"10\"],[\"17\",\"1\",\"12\"],[\"18\",\"1\",\"1\"],[\"19\",\"1\",\"3\"],[\"20\",\"1\",\"15\"],[\"21\",\"2\",\"3\"],[\"22\",\"2\",\"2\"],[\"23\",\"2\",\"13\"],[\"24\",\"2\",\"7\"],[\"25\",\"2\",\"17\"],[\"26\",\"2\",\"20\"],[\"27\",\"2\",\"6\"],[\"28\",\"2\",\"5\"],[\"29\",\"2\",\"1\"],[\"30\",\"2\",\"4\"],[\"31\",\"2\",\"11\"],[\"32\",\"2\",\"16\"],[\"33\",\"2\",\"18\"],[\"34\",\"2\",\"9\"],[\"35\",\"2\",\"8\"],[\"36\",\"2\",\"10\"],[\"37\",\"2\",\"14\"],[\"38\",\"2\",\"19\"],[\"39\",\"2\",\"12\"],[\"40\",\"2\",\"15\"]]}";

		ObjectMapper mapper = new ObjectMapper();
		BVDesignOutput designOutput = mapper.readValue(json, BVDesignOutput.class);
		Assert.assertNotNull(designOutput);
		Assert.assertEquals(0, designOutput.getReturnCode());
		Assert.assertEquals(3, designOutput.getBvHeaders().length);
		Assert.assertEquals(40, designOutput.getBvResultList().size());
	}

}
