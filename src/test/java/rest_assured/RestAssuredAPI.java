package rest_assured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.http.HttpConnection;
import org.hamcrest.Matchers;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestAssuredAPI {

public static Response getRequest(String endpoint){

    RestAssured.defaultParser= Parser.JSON;

    return given().headers("Content type", ContentType.JSON,"Accept",ContentType.JSON)
            .when().get(endpoint).
            then().contentType(ContentType.JSON).extract().response();


}

    public static void main(String[] args) {
        Response response=getRequest("https://reqres.in/api/users");//this link from reqres
        List<String> jsonResponse=response.jsonPath().getList("data");
        System.out.println(jsonResponse.size());

        //if we want to see the first name of all entries as an array:

        String fn=response.jsonPath().getString("data.first_name");//this path from postman

        System.out.println(fn);


        //if we want to get only the first first_name

        String fnOne=response.jsonPath().getString("data.first_name[0]");//0 is index of array of firstnames
        System.out.println(fnOne);

//if we ony had one record,we could use get company name

        /*Response responseMap=getRequest("https://reqres.in/api/users");
        Map<String,String> company=responseMap.jsonPath().getMap("data");
        System.out.println(company.get("email"));*/




        response.prettyPeek();//this code it shows exactly the same result with postman result send request
  response.then().assertThat().statusCode(HttpURLConnection.HTTP_OK).body("total", Matchers.equalTo(12));




    }



}
