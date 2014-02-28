package de.jworks.datahub.business.util;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.output.NullWriter;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;


public class FreemarkerTest {

	public static void main(String[] args) throws Exception {
		Configuration configuration = new Configuration();
		
//		Template template = configuration.getTemplate("test");
		Template template = new Template("test", "${a} <#list b as c> ${c} </#list> ${e.f}", configuration);
		template.setClassicCompatible(true); // important for expressions like ${a.b} 
		
		Set<String> vars = new TreeSet<String>();
		template.process(new VarsExtractor(vars), new NullWriter());
		System.out.println(vars);
	}
	
	public static class VarsExtractor implements TemplateHashModel {
		
		private Set<String> vars;
		
		public VarsExtractor(Set<String> vars) {
			this.vars = vars;
		}

		@Override
		public TemplateModel get(String key) throws TemplateModelException {
			vars.add(key);
			return TemplateModel.NOTHING;
		}
		
		@Override
		public boolean isEmpty() throws TemplateModelException {
			return false;
		}
		
	}
	
}
