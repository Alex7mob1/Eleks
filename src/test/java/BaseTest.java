import io.restassured.http.ContentType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static io.restassured.RestAssured.given;
import static utils.Const.*;

public class BaseTest {

    protected String boardId;

    @BeforeMethod
    public void init() {
        boardId = null;
    }

    @AfterMethod(alwaysRun = true)
    public void deleteBoard() {
        given()
                .baseUri(HEAD_URL)
                .log().all()
                .contentType(ContentType.JSON)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN).delete("/1/boards/" + boardId);
    }
}
