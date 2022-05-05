package fr.inria.diverse.francaTest

import org.franca.core.franca.FInterface

import org.franca.deploymodel.core.FDeployedInterface
import org.franca.deploymodel.ext.providers.FDeployedProvider
import org.franca.deploymodel.dsl.fDeploy.FDExtensionRoot
import fr.inria.diverse.francaTest.RPCSpec.InterfacePropertyAccessor

class WsJsonRpcGen {
	InterfacePropertyAccessor deploy
		def generateInterface (FDeployedInterface deployed) {
		deploy = new InterfacePropertyAccessor(deployed)
		generateInterface(deployed.FInterface)	
	} 

	def private generateInterface (FInterface api)'''
	 «api.generateHeader»
	'''
	
	def private generateHeader (FInterface api) '''
		/*---------------------------------------------------------------------------------------------
		 * Copyright (c) 2020 Inria and others.
		 * All rights reserved. This program and the accompanying materials
		 * are made available under the terms of the Eclipse Public License v1.0
		 * which accompanies this distribution, and is available at
		 * http://www.eclipse.org/legal/epl-v10.html
		 *--------------------------------------------------------------------------------------------*/
		/* GENERATED FILE, DO NOT MODIFY MANUALLY */
		
		package org.eclipse.gemoc.protocols.eaop.api.services;
		
		import com.google.gson.annotations.SerializedName;
		import java.util.concurrent.CompletableFuture;
		import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
		import org.eclipse.gemoc.protocols.eaop.api.data.*;
		/** Server interface for the model execution trace protocol.
			Auto-generated from json schema. Do not edit manually.
		*/
	'''
}