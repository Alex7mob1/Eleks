package utils;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static utils.Const.*;

public class ClientManager {

    private ClientManager() {
    }

    public static RequestSpecification getClientConfig() {
        return given()
                .baseUri(HEAD_URL)
                .log().all()
                .contentType(ContentType.JSON)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN);
    }
}
