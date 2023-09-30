@reqresApi
Feature: To validate api response

Background:
		Given To enter the base uri "https://reqres.in"

@GetAPI
Scenario: To validate response status code and response body for GET api
	Given To enter the end point "/api/users/1"
	When To select GET method and send api to server
	Then To validate response status code 200
	And To validate response from data object "george.bluth@reqres.in"

@PostPutAPI	
Scenario Outline: To validate response from POST and PUT api
	Given To enter the end point "<endPoint>"
	And To enter request body "<dataFile>"
	When To select "<methodType>" method and send api to server
	Then To validate response status code "<expStatusCode>"
	And To validate response with "<expResponseData>"
	Examples:
			|  endPoint 	| 	 dataFile	 		| methodType | expStatusCode  | expResponseData   |
			| /api/users	|post_request.json|		POST		 |			 201			| Queue Codes	 	    |
			| /api/users/2|put_request.json |		PUT 		 |			 200			| Selenium Testing	|	
	

