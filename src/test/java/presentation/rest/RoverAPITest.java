package presentation.rest;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import lombok.val;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
@QuarkusTest
class RoverAPITest {

    @TestHTTPEndpoint(RoverAPI.class)
    @TestHTTPResource()
    String roverAPIUrl;

    @Test
    void whenGivenCorrectInput() {
       val response = given().contentType(ContentType.TEXT).body("5 5\n1 2 N\nLMLMLMLMM\n3 3 E\nMMRMMRMRRM")
                             .when().post(roverAPIUrl+"/navigate")
                             .then().statusCode(200)
                             .extract().body().asString();
        assertEquals("1 3 N\n5 1 E\n", response);
    }

    @Test
    void whenGivenIncorrectInput() {
        val response = given().contentType(ContentType.TEXT).body("5 5\n1 2 N\nLMLMLMLMM\n3 3 E\nMMRMMRMRRMX")
                .when().post(roverAPIUrl+"/navigate")
                .then().statusCode(400)
                .extract().body().asString();
        assertEquals("Rover instructions must be a string of L, M or R", response);
    }

    @Test
    void whenGivenEmptyInput() {
        val response = given().contentType(ContentType.TEXT).body("")
                .when().post(roverAPIUrl+"/navigate")
                .then().statusCode(400)
                .extract().body().asString();
        assertEquals("Input must not be empty", response);
    }


    @Test
    void whenRoverCollide()
    {
        val response = given().contentType(ContentType.TEXT).body("5 5\n1 2 N\nLMLMLMLMM\n1 1 N\nMLMLMLMLMM")
                .when().post(roverAPIUrl+"/navigate")
                .then().statusCode(400)
                .extract().body().asString();
        assertEquals("Rover cannot move to a position occupied by another rover", response);
    }

}