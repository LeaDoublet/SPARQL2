package org.doublet;

import java.io.*;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
//import com.hp.hpl.jena.rdf.model.ModelFactory;




class Querying_SPARQL {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model;
		try{
			// Open the bloggers RDF graph from the filesystem
			InputStream in = new FileInputStream( new File( "C:/Users/ldoublet/Documents/FIA5/periode 2/vc-db-1.rdf" ));
			InputStream in2 = new FileInputStream(new File("C:/Users/ldoublet/Documents/FIA5/periode 2/vc-db-2.rdf"));
 


			// Create an empty in-memory model and populate it from the graph
			model = ModelFactory.createMemModelMaker().createFreshModel();//.createModel();
			model.read(in,null); // null base URI, since model URIs are absolute
			model.read(in2,null);
			in.close();
		}catch (Exception e) {
			System.err.println( "Error! : FILE NOT FOUND (" + e + ")" );
			return;
		}
		// Create a new query
		String queryString = 
				
		//Query With full URI
				/*
		        "SELECT ?x "+
				"WHERE { " +
		                  "?x  <http://www.w3.org/2001/vcard-rdf/3.0#FN>  \"John Smith\" .}";
				*/
		//Query With Prefix
				/*
			    "PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#> " +
		        "SELECT ?x "+
				"WHERE { " +
		                  "?x  vcard:FN  \"John Smith\" .}";

				 */
		//Query With full name of all people in the database
				/*
				"PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#> " +
				"SELECT ?x ?name " +
				"WHERE { " +
				"  ?x vcard:FN ?name . " +
				"} " +
				"ORDER BY ?name";
				*/
		//Query What is the given name of all the people in the knowledge base whose family name is Smith.
			/*
				"PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#> " +
    		"SELECT ?givenName " +
    		"WHERE { " +
					"  ?person vcard:N ?name . " +
    				"  ?name vcard:Family \"Smith\" . " +
    				"  ?name vcard:Given ?givenName . " +
    		"}";
			 */
		//Query Change the query just a little to return the anonymous node as well?
				/*
				"PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#> " +
						"SELECT ?nameNode ?givenName " +
						"WHERE { " +
						"  ?person vcard:N ?nameNode . " +
						"  ?nameNode vcard:Family \"Smith\" . " +
						"  ?nameNode vcard:Given ?givenName . " +
						"}";

				 */
		//Query Find the URI of all people in the knowledge base whose age is greater than or equals to 24 years old.
				"PREFIX info: <http://somewhere/peopleInfo#> " +
						"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
						"SELECT ?person " +
						"WHERE { " +
						"  ?person info:age ?age . " +
						"  FILTER(?age >= 24) " +
						"}";

		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();

		// Output query results	
		ResultSetFormatter.out(System.out, results, query);

		// Important - free up resources used running the query
		qe.close();
	}

}
