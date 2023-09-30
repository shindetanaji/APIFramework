package com.qc.api.steps;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Reqres {

	RequestSpecification requestSpecification;
	Response response;
	@SuppressWarnings("rawtypes")
	HashMap responseData;
	JsonPath jsonData;
	
	@Given("To enter the base uri {string}")
	public void to_enter_the_base_uri(String baseUrl) {
		RestAssured.baseURI = baseUrl;
	}

	@Given("To enter the end point {string}")
	public void to_enter_the_end_point(String endPoint) {
		RestAssured.basePath = endPoint;
		requestSpecification = RestAssured.given();
	}
	
	@Given("To enter request body {string}")
	public void to_enter_request_body(String fileName) throws IOException, ParseException {
	    FileReader reader = new FileReader("src/test/resources/"+fileName);
	    JSONParser parser = new JSONParser();
	    JSONObject jsonObject = (JSONObject) parser.parse(reader);
	    requestSpecification.body(jsonObject);
	}
	
	@When("To select {string} method and send api to server")
	public void to_select_method_and_send_api_to_server(String methodName) {
		if(methodName.equals("POST")) {
			response = requestSpecification.contentType("application/json").when().post();
		}else {
			response = requestSpecification.contentType("application/json").when().put();	
		}
	    jsonData = response.jsonPath();
	    System.out.println("Response Body: "+jsonData);
	}

	@When("To select GET method and send api to server")
	public void to_select_get_method_and_send_api_to_server() {
		response = requestSpecification.request(Method.GET);
		responseData = response.path("data");
		System.out.println(responseData);
	}

	@Then("To validate response status code {int}")
	public void to_validate_response_status_code(Integer expStatusCode) {
		Integer actStatusCode = response.getStatusCode();
		Assert.assertEquals(actStatusCode, expStatusCode);
	}

	@Then("To validate response from data object {string}")
	public void to_validate_response_from_data_object(String expEmailAddress) {
		String actEmailAddress = (String) responseData.get("email");
		Assert.assertEquals(actEmailAddress, expEmailAddress);
	}

	@Then("To validate response with {string}")
	public void to_validate_response_with(String expEmailAddress) {
		String actEmailAddress = jsonData.get("name");
		Assert.assertEquals(actEmailAddress, expEmailAddress);
	}
	
	@Then("To validate response status code {string}")
	public void to_validate_response_status_code(String expName) {
	    Integer actName = response.getStatusCode();
	    Assert.assertEquals(actName.toString(), expName);
	}

}
