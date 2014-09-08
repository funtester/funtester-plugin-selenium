package org.funtester.plugin.selenium.model;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.funtester.common.at.AbstractTestActionStep;
import org.funtester.common.at.AbstractTestCase;
import org.funtester.common.at.AbstractTestElement;
import org.funtester.common.at.AbstractTestMethod;
import org.funtester.common.at.AbstractTestOracleStep;
import org.funtester.common.at.AbstractTestStep;
import org.funtester.common.at.AbstractTestSuite;
import org.funtester.common.util.FilePathUtil;
import org.funtester.plugin.code.TestCase;
import org.funtester.plugin.code.TestMethod;

public class Transformer {
	
	private static final String JAVA_FILE_EXTENSION = ".java";
	
	public List< String > transform(
			final AbstractTestSuite suite,
			final String baseOutputDirectory,
			final String mainClass,
			final String packageName,
			final int timeoutToBeVisibleInMS
			) throws TransformException {
	
		//Gera o diretório
		String sourceCodeDir = baseOutputDirectory;
		if ( generateDirStructure( baseOutputDirectory, packageName ) ) {
			sourceCodeDir = dirFromPackageName( baseOutputDirectory, packageName );
		}
		
		List< String > fileNames = null;
		try {
			fileNames = generateCode( 
							suite, 
							sourceCodeDir, 
							mainClass,
							packageName, 
							timeoutToBeVisibleInMS 
						);

			//Arquivo de Configuração deve entrar aqui.
			//generateConfiguration( suite, baseOutputDirectory, packageName );
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new TransformException( e );
		}
		
		return fileNames;
	}
	
	private boolean generateDirStructure(
			final String baseOutputDirectory,
			final String packageName
			) {
		File baseDir = new File( baseOutputDirectory );
		if ( ! baseDir.isDirectory() ) {
			throw new RuntimeException( baseOutputDirectory + " should be a directory." );
		}
		if ( ! baseDir.exists() ) {
			throw new RuntimeException( "The baseOutputDirectory should exists: " + baseOutputDirectory );
		}		
		String packagePath = dirFromPackageName( baseOutputDirectory, packageName );
		File packageDir = new File( packagePath );		
		if ( packageDir.exists() ) {
			return true; // Nothing to be done
		}
		return packageDir.mkdirs();	
	}
	
	private String dirFromPackageName(
			final String baseDir,
			final String packageName
			) {
		String packageDirs = packageName.replace( ".", File.separator );
		return FilePathUtil.directoryWithSeparator( baseDir ) + packageDirs;
	}
	
	private List< String > generateCode(
			final AbstractTestSuite suite,
			final String outputDirectory,
			final String mainClass,
			final String packageName,
			final int timeoutToBeVisibleInMS
			) throws IOException, CodeGenerationException {		
		if ( null == suite ) {
			throw new IllegalArgumentException( "Suite can't be null." );
		}
		
		List< String > fileNames = new ArrayList< String >();	
		
		SeleniumCodeGenerator codeGenerator = new SeleniumCodeGenerator();
		codeGenerator.generate( suite, mainClass, packageName, timeoutToBeVisibleInMS );
		
		return fileNames;
	}	
}
