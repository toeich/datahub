package de.jworks.datahub.business.util;

import freemarker.core.Expression;
import freemarker.core.FMParser;

public class FreemarkerTest {

	public static void main(String[] args) throws Exception {
		FMParser parser = FMParser.createExpressionParser("a.b");
		Expression expression = parser.Expression();
		System.out.println(expression);
	}
	
}
