package endpoint;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.isep.cleancode.Main;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

// TODO: Implement the tests for the TodoController class
public class TodoEndpointTest {

    @BeforeAll
    public static void setup() {
        Main.startApp();
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 4567;

        try {
            Thread.sleep(1000); // laisser le temps à Spark de démarrer
        } catch (InterruptedException ignored) {}
    }

    @AfterAll
    public static void teardown() {
        Main.stopApp();
    }


    @Test
    public void testPostTodo() {
        String json = "{\"name\":\"Test REST\",\"dueDate\":null}";

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/todos")
                .then()
                .statusCode(201)
                .body("name", equalTo("Test REST"))
                .body("dueDate", nullValue());
    }

    @Test
    public void testGetTodos() {
        when()
                .get("/todos")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }
}
