package org.funtester.plugin.selenium;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.List;

import org.funtester.common.at.*;
import org.funtester.common.generation.TestGenerationConfiguration;
import org.funtester.common.generation.TestGenerationConfigurationRepository;
import org.funtester.plugin.selenium.model.AbstractTestSuiteRepository;
import org.funtester.plugin.selenium.json.*;
import org.funtester.plugin.selenium.model.*;
import org.funtester.plugin.selenium.model.configuration.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

	public static void main ( String [] args ){
		
		
		//Verifica se o arquivo existe
		File file = new File( abstractTestFileLocal( ) );
		if ( ! file.exists() ) {
			System.out.println( "File not find" );
			//Termina
		}
		
		System.out.println( "Reading configuration file..." );
		AbstractTestSuiteRepository abstractTestSuiteRepository =
				createAbstractTestSuit( abstractTestFileLocal() );
		
		AbstractTestSuite testSuite = null;
		
		try {
			testSuite = abstractTestSuiteRepository.first();
		} catch ( Exception e1 ) {			
			System.out.println( "Erro 1" );
			e1.printStackTrace();
		}
		
		//Transforma testes abstratos para selenium a partir daqui
		Transformer transformer = new Transformer();
		List< String > fileNames = new ArrayList< String >();
		
		try{
			List< String > files = transformer.transform( 
					testSuite, 
					"C:/Estagio/selenium/trunk/src/main/java/fat", 
					"MainActivity", 
					"com.appname.tests", 
					5000
			);
			fileNames.addAll( files );
	
		}catch( TransformException e ){
			e.printStackTrace();
		}
		
			
		/*
		List< AbstractTestCase > testCases = testSuite.getTestCases();
		for( AbstractTestCase abstractTestCase : testCases ) {
			System.out.println( abstractTestCase.getName() );
		}
		*/
	}
	
	//Retorna um repositório de testes abstratos
	private static JsonAbstractTestSuiteRepository createAbstractTestSuit(
			final String fileName) {
		return new JsonAbstractTestSuiteRepository( fileName );
	}
	
	//Local onde está o teste
	private static String abstractTestFileLocal() {
		return "C:/Estagio/selenium/trunk/src/main/java/fat/funtester.fat";
	}
}
