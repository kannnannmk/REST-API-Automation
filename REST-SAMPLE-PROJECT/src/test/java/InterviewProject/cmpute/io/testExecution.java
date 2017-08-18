package InterviewProject.cmpute.io;

import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import Utilities.AdvancedAssertions;
import Utilities.Student;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class testExecution {
	
	Response response;
	
	@Test(priority=0)
	public void getResponseDetails(){
	
		RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
	
	
	RequestSpecification request = RestAssured.given();
	
	 response = RestAssured.get("/posts"); // Library will make a call to the server

	}
	
	@Test(priority=1 , description="Check all responses in console and validate using Advanced assertions")
	public void Validate_Response_Headers(){
		
	System.out.println(response.getContentType());	
	System.out.println("Status code => "+response.getStatusCode());
	System.out.println("Status Line => "+response.getStatusLine());
	System.out.println("Cookies  => "+response.getCookies());	
	System.out.println("Headers  => "+response.headers());	
	System.out.println("Response Time  => "+response.getTime());

	
	//Header validation through custom assertion
	
	//Get content type from Response
	Student contentTypeActual = new Student(response.getContentType(),"");
	Student contentTypeExpected = new Student("application/json","");
	
	//Get status code and status line from Response and pass it to Student class instance where I created custom assertion
	Student statusCode_StatusLineExpected = new Student("200 OK",response.getStatusLine());
	Student statusCode_StatusLineActual = new Student(String.valueOf(200), String.valueOf(response.getStatusCode()));
	
	

	//Validating Content Type
	AdvancedAssertions.AssertPartiallyEquals(contentTypeActual, contentTypeExpected, "Content type is incorrect");
	//Validating Status Code and Status Line
	AdvancedAssertions.AssertPartiallyEquals(statusCode_StatusLineExpected, statusCode_StatusLineActual, "Content type is not matching");
    //Validating Response Body
	//AdvancedAssertions.
	}
	
	@Test(priority=2,dependsOnMethods={"Validate_Response_Headers"}, description="Validate Headers with JSON Parsing")
	public void Validate_Response_Body(){
		
		RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
		RequestSpecification spec = RestAssured.given();
		Response resp = RestAssured.get("/posts/1");
		
		//Using Google GSON to parse JSON => JAVA Objects, using POJO class JsonResponse.java
		
		Gson gson = new Gson();
		JsonResponse ObjectModel = gson.fromJson(resp.body().asString(), JsonResponse.class);

		//Validating ID and Title values using advanced Assertion class created in Package:Utilities
		
		
		Student IdAndTitleActual = new Student(ObjectModel.getID(),ObjectModel.getTitle()); // Actual Response with ID and TITLE fields
		Student  IdAndTitleExpected = new Student(String.valueOf(1),"sunt aut facere repellat provident"); //Expected Response with ID and TITLE fields
		
		//Using Partial Advanced Assertion to validate ID and Title fields
		
		AdvancedAssertions.AssertPartiallyEquals(IdAndTitleActual, IdAndTitleExpected, "Values does not match");
		
		//Partial validation on Body value
		Assert.assertTrue(ObjectModel.getBody().contains("quia et suscipit\nsuscipit recusandae"));
	
	}
	
	@Test(priority=3, dependsOnMethods={"Validate_Response_Body"}, description="Create new resource using Post")
	public void Create_PostResource(){
		
		RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
		
		//Input a JSON
		
		JSONObject userParams = new JSONObject();   // Try adding new JSON using Post
		userParams.put("userID", 132);
		userParams.put("id", 999);
		userParams.put("title", "Adding new JSON Object");
		userParams.put("body", "Successfully added new object using POST Method");
		
		
		
		RequestSpecification userRegRequest = RestAssured.given();
		userRegRequest.body(userParams.toJSONString());
		Response userResp = userRegRequest.post("/posts");
		
		//Verifying whether Response code is 201
		
		Assert.assertEquals(201,userResp.getStatusCode());  // New Post Request Created, status code 201
		
		
	}
	
	@Test(priority=4, description="Validating Delete Requests")
	public void DeleteResource(){
		
		RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
		RequestSpecification DeleteRequest = RestAssured.given();
		Response DeleteResponse = DeleteRequest.delete("/posts/1");   //CREATING A DELETE REQUEST
				
		Assert.assertEquals(200, DeleteResponse.getStatusCode());   // Delete Request Success and Returns status code 200
		
		
	}
	
	@Test(priority=5, description="Validate Cookies")
	public void ValidateLongResponse(){				
		
		RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
		RequestSpecification req = RestAssured.given();
		Response LongResponse = req.get("/photos");   
					
	    Gson gson = new Gson();  //Parsing JSON into Java Objects using GSON with the Help of POJO Class

	    ComplexResponse[] ObjectModel = gson.fromJson(LongResponse.body().asString(), ComplexResponse[].class); 
	    
	    //Creating the ObjectModel as an Array because the response has multiple lines JSON 
				
		Assert.assertEquals(5000, ObjectModel.length);
		
		//Validated a complex response having 5000 individual JSON representations
			
	}
	
	@Test(priority=6, description="Validating the cookies")
	public void ValidateCookies(){
		
		RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
		RequestSpecification req = RestAssured.given();
		Response CookResponse = req.get("/posts");   
		
		Cookie getCookie = CookResponse.getDetailedCookie("__cfduid");  //Creating object for Cookie with complete details
		
		// ALL POSSIBLE VALIDATION FOR THE COOKIE RETRIEVED
		
		Assert.assertTrue(getCookie.hasExpiryDate()); //Validating cookie with expiry date
		Assert.assertTrue(getCookie.hasValue()); //Validating cookie for value
		System.out.println("Cookie Domain => " + getCookie.getDomain()); //Getting domain details
		System.out.println("Is the Cookie Secured => " + getCookie.isSecured()); //Returns false
			
	}
	
	
	
}
