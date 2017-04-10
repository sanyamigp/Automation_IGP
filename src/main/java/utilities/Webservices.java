package utilities;

import static com.jayway.restassured.RestAssured.given;
import java.net.URI;
import java.net.URISyntaxException;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class Webservices {
		
	 public static Response Post(String uRI, String JSON){
		  RequestSpecification requestspecification = RestAssured.given().body(JSON);
		  requestspecification.contentType(ContentType.JSON);
		  Response response = requestspecification.post(uRI);
		  return response;
		 }
		 public static Response Get(String uRI){
		  RequestSpecification requestspecification = RestAssured.given();
		  requestspecification.contentType(ContentType.JSON);
		  Response response = requestspecification.get(uRI);
		  return response;
		 }
		 public static Response Put(String uRI, String JSON){
		  RequestSpecification requestspecification = RestAssured.given().body(JSON);
		  requestspecification.contentType(ContentType.JSON);
		  Response response = requestspecification.put(uRI);
		  return response;
		 }
		 public static Response Delete(String uRI){
		  RequestSpecification requestspecification = RestAssured.given();
		  requestspecification.contentType(ContentType.JSON);
		  Response response = requestspecification.delete(uRI);
		  return response;
		 }
		 
		 public static int getStatusCode(String url) throws URISyntaxException{
		    return given()
		    .config(RestAssured.config().sslConfig(
		    new SSLConfig().allowAllHostnames())).get(new URI(url)).getStatusCode();
		    
		   }

}
