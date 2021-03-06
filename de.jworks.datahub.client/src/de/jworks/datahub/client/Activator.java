package de.jworks.datahub.client;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.caucho.hessian.client.HessianProxyFactory;

import de.jworks.datahub.business.transform.boundary.TransformationService;

public class Activator extends AbstractUIPlugin {

	private static Activator instance;
	
	public static Activator getDefault() {
		return instance;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		instance = this;
		
		String baseUrl = "http://localhost:18080/de.jworks.datahub.server";
		
		HessianProxyFactory factory = new HessianProxyFactory();
		TransformationService transformationService = (TransformationService) factory.create(baseUrl + "/TransformationService");		
		
		context.registerService(TransformationService.class, transformationService, null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
		super.stop(context);
	}
	
}
