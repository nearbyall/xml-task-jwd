package by.epamtc.melnikov.xmltask.validator;

import org.junit.Assert;
import org.junit.Test;

public class XSDValidatorTest {

	@Test
	public void test() {
		
		XSDValidator validator = XSDValidator.getInstance();
		Assert.assertTrue(validator.validate(System.getProperty("user.dir") + "\\src\\main\\webapp\\xml\\devices.xml",
				System.getProperty("user.dir") + "\\src\\main\\webapp\\\\xsd\\devices.xsd"));
		
	}

}
