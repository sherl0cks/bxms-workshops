package com.redhat.brms.service.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;
import org.kie.api.runtime.ExecutionResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default Transformer. This class will set the Response based on the QueryInfo
 * annotation on a method in the response.
 * 
 * 
 * @param <Response>
 */

public class ReflectiveExecutionResultsTransformer {

	private static Logger	logger	= LoggerFactory.getLogger( ReflectiveExecutionResultsTransformer.class );

	public ReflectiveExecutionResultsTransformer() {

	}

	public static < Response > Response transform( ExecutionResults results, Class< Response > responseClazz ) {
		if ( responseClazz == null || results == null ){
			return null;
		}
		
		Response response = null;
		try {
			response = responseClazz.newInstance();
		}
		catch ( InstantiationException | IllegalAccessException e ) {
			logger.error( String.format( "New instance of response class could not be created: %s", responseClazz.getName() ) );
		}

		for ( Field field : QueryUtils.getAllFields( responseClazz ) ) {
			KieQuery queryInfo = field.getAnnotation( KieQuery.class );
			if ( queryInfo != null ) {
				String queryName = queryInfo.queryName();
				String binding = queryInfo.binding();
				Class< ? > type = field.getType();
				if ( Collection.class.equals( type ) ) {
					try {
						Collection< ? > list = QueryUtils.extractCollectionFromExecutionResults( results, queryName, binding );
						if ( list == null ) {
							logger.warn( String.format( "Query results were empty for query: %s", queryName ) );
						}
						PropertyUtils.setProperty( response, field.getName(), list );
					}
					catch ( IllegalArgumentException | IllegalAccessException | InvocationTargetException
							| NoSuchMethodException e ) {
						logger.error( String.format( "Could not set results on propery: ", field.getName() ) );
					}
				}
				else {
					logger.error( "QueryInfo annotation can not be used on " + field.getName()
							+ ". It only be used on fields which are of Type Collection" );
				}
			}
		}
		return response;
	}
}