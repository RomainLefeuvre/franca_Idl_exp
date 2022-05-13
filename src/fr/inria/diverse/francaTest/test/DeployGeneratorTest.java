/*******************************************************************************
 * Copyright (c) 2013 itemis AG (http://www.itemis.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package fr.inria.diverse.francaTest.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.franca.core.dsl.FrancaIDLHelpers;
import org.franca.core.dsl.FrancaPersistenceManager;
import org.franca.core.franca.FModel;
import org.franca.core.franca.FType;
import org.franca.core.franca.FTypeCollection;
import org.franca.deploymodel.core.FDModelExtender;
import org.franca.deploymodel.core.FDeployedInterface;
import org.franca.deploymodel.core.FDeployedTypeCollection;
import org.franca.deploymodel.dsl.FDeployPersistenceManager;
import org.franca.deploymodel.dsl.fDeploy.FDExtensionRoot;
import org.franca.deploymodel.dsl.fDeploy.FDInterface;
import org.franca.deploymodel.dsl.fDeploy.FDModel;
import org.franca.deploymodel.dsl.fDeploy.FDTypeDefinition;
import org.franca.deploymodel.dsl.fDeploy.FDTypedef;
import org.franca.deploymodel.dsl.tests.FDeployInjectorProvider;
import org.franca.deploymodel.ext.providers.FDeployedProvider;
import org.franca.deploymodel.ext.providers.ProviderExtension;
import org.franca.deploymodel.ext.providers.ProviderUtils;
import org.franca.deploymodel.extensions.ExtensionRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import fr.inria.diverse.francaTest.gen.Gen_DTO;
import fr.inria.diverse.francaTest.gen.WsJsonRpcGen;

/**
 * Testcase for using code generators based on Franca deployment models.
 * 
 * @author kbirken
 */
@RunWith(XtextRunner.class)
@InjectWith(FDeployInjectorProvider.class)
public class DeployGeneratorTest {

	@Inject
	FDeployPersistenceManager loader;
	
	@Inject
	FrancaPersistenceManager persistanceLoader;
	@Test
	public void testInterfaceGeneration() {
		System.out.println("*** DeployGeneratorTest / Interface Generation");

		// load example Franca IDL interface
		String inputfile = TestConfiguration.fdeployInterfaceFile; 
    	URI root = URI.createURI("classpath:/");
    	URI loc = URI.createFileURI(inputfile);
		FDModel fdmodel = loader.loadModel(loc, root);
		
		assertNotNull(fdmodel);
		
		// get first interface referenced by FDeploy model
		FDModelExtender fdmodelExt = new FDModelExtender(fdmodel);
		List<FDInterface> interfaces = fdmodelExt.getFDInterfaces();
		assertTrue(interfaces.size()>0);
		FDInterface api = interfaces.get(0);
		
		
	
		//FDeployedTypeCollection tc = new FDeployedTypeCollection(api.getTypes())
		// create wrapper and generate code from it 
		FDeployedInterface deployed = new FDeployedInterface(api);
		WsJsonRpcGen generator =
				new WsJsonRpcGen();
		String code = generator.generateInterface(deployed,"org.eclipse.gemoc.protocols.eaop.api").toString();
		
		// simply print the generated code to console
		System.out.println("Generated code:\n" + code);
		System.out.println("-----------------------------------------------------");
		
		
		EList<Resource> rs = api.eResource().getResourceSet().getResources();
		

		Resource fModelResource = rs.stream().filter(r -> r.getContents().get(0) instanceof FModel).findAny().get();
		FModel fmodel = (FModel) fModelResource.getContents().get(0);
		List<FTypeCollection> l =fmodel.getTypeCollections();
		System.out.println(l.get(0).eClass());
		System.out.println(l.get(0).eContents().get(0));
		System.out.println(l.get(0).eContents().get(0).eContents());

		Gen_DTO dtoGenerator = new Gen_DTO();
		code = dtoGenerator.generateDtoClass(l, "org.eclipse.gemoc.protocols.eaop.api").toString();
		// simply print the generated code to console		
		System.out.println("Generated code:\n" + code);
		System.out.println("-----------------------------------------------------");
	}
	
	public void testDtoGeneration() {
		String inputfile = TestConfiguration.fidlEaopFile; 
    	URI root = URI.createURI("classpath:/");
    	URI loc = URI.createFileURI(inputfile);
		FModel fmodel = persistanceLoader.loadModel(loc, root);
		List<FTypeCollection> l =fmodel.getTypeCollections();
		l.get(0);
		System.out.println("-----------------------------------------------------");

	}


}
