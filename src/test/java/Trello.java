import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import parser.Parser;

import static utils.ClientManager.getClientConfig;
import static utils.Const.*;

public class Trello extends BaseTest {

    @Test
    public void createBoardTest() {

        String boardName = "BoardForPost";
        String responseBoard = getClientConfig().body(String.format("{\"name\":\"%s\"}", boardName))
                .when()
                .post(BOARD_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        Assert.assertEquals(Parser.getValueFromJson(responseBoard, "closed"), false);
        Assert.assertEquals(Parser.getValueFromJson(responseBoard, "name"), boardName);

        boardId = Parser.getValueFromJson(responseBoard, "id").toString();

        getClientConfig().when()
                .get(BOARD_URL + boardId)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void createTodoListTest() {

        String boardName = "BoardForListTodo";
        String responseBoard = getClientConfig()
                .body(String.format("{\"name\":\"%s\"}", boardName))
                .when()
                .post(BOARD_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        boardId = Parser.getValueFromJson(responseBoard, "id").toString();

        String listName = "TODO";
        String responseFormPostList = getClientConfig()
                .body(String.format("{\"name\":\"%s\"}", listName))
                .queryParam("idBoard", boardId)
                .when()
                .post(LIST_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        Assert.assertEquals(Parser.getValueFromJson(responseFormPostList, "name"), listName);

        String idListTodo = Parser.getValueFromJson(responseFormPostList, "id").toString();
        String responseFormGetList = getClientConfig()
                .when()
                .get(LIST_URL + idListTodo)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        Assert.assertEquals(Parser.getValueFromJson(responseFormGetList, "closed"), false);
        Assert.assertEquals(Parser.getValueFromJson(responseFormGetList, "name"), listName);
    }


    @Test
    public void createDoingListTest() {

        String boardName = "BoardForListDoing";
        String responseBoard = getClientConfig()
                .body(String.format("{\"name\":\"%s\"}", boardName))
                .when()
                .post(BOARD_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        boardId = Parser.getValueFromJson(responseBoard, "id").toString();

        String listName = "Doing";
        String responseFormPostList = getClientConfig()
                .body(String.format("{\"name\":\"%s\"}", listName))
                .queryParam("idBoard", boardId)
                .when()
                .post(LIST_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        Assert.assertEquals(Parser.getValueFromJson(responseFormPostList, "name"), listName);

        String idListDoing = Parser.getValueFromJson(responseFormPostList, "id").toString();
        String responseFormGetList = getClientConfig()
                .when()
                .get(LIST_URL + idListDoing)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        Assert.assertEquals(Parser.getValueFromJson(responseFormGetList, "closed"), false);
        Assert.assertEquals(Parser.getValueFromJson(responseFormGetList, "name"), listName);
    }

    @Test
    public void createCardTest() {

        String boardName = "BoardForCardSpain";
        String responseBoard = getClientConfig()
                .body(String.format("{\"name\":\"%s\"}", boardName))
                .when()
                .post(BOARD_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        boardId = Parser.getValueFromJson(responseBoard, "id").toString();

        String listName = "ListTodoForCardSpain";
        String responseFormPostList = getClientConfig()
                .body(String.format("{\"name\":\"%s\"}", listName))
                .queryParam("idBoard", boardId)
                .when()
                .post(LIST_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        Assert.assertEquals(Parser.getValueFromJson(responseFormPostList, "closed"), false);

        String idListTodo = Parser.getValueFromJson(responseFormPostList, "id").toString();
        String cardName = "Spain";
        String responseFormPostCard = getClientConfig()
                .body(String.format("{\"name\":\"%s\"}", cardName))
                .queryParam("idList", idListTodo)
                .when()
                .post(CARD_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        Assert.assertEquals(Parser.getValueFromJson(responseFormPostCard, "name"), cardName);

        String idCardSpain = Parser.getValueFromJson(responseFormPostCard, "id").toString();
        String responseFormGetCard = getClientConfig()
                .when()
                .get(CARD_URL + idCardSpain)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        Assert.assertEquals(Parser.getValueFromJson(responseFormGetCard, "closed"), false);
        Assert.assertEquals(Parser.getValueFromJson(responseFormGetCard, "name"), cardName);
    }


    @Test
    public void moveCardTest() {

        String boardName = "BoardForMoveCardSpain";
        String responseBoard = getClientConfig()
                .body(String.format("{\"name\":\"%s\"}", boardName))
                .when()
                .post(BOARD_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        boardId = Parser.getValueFromJson(responseBoard, "id").toString();

        String listNameTodoCard = "ListTodoForMoveCardSpain";
        String responseFormPostListTodo = getClientConfig()
                .body(String.format("{\"name\":\"%s\"}", listNameTodoCard))
                .queryParam("idBoard", boardId)
                .when()
                .post(LIST_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        Assert.assertEquals(Parser.getValueFromJson(responseFormPostListTodo, "closed"), false);

        String idListTodo = Parser.getValueFromJson(responseFormPostListTodo, "id").toString();

        String listNameDoingCard = "ListDoingForMoveCardSpain";
        String responseFormPostListDoing = getClientConfig()
                .body(String.format("{\"name\":\"%s\"}", listNameDoingCard))
                .queryParam("idBoard", boardId)
                .when()
                .post(LIST_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        Assert.assertEquals(Parser.getValueFromJson(responseFormPostListDoing, "name"), listNameDoingCard);

        String idListDoing = Parser.getValueFromJson(responseFormPostListDoing, "id").toString();

        String cardName = "Spain";
        String responseFormPostCard = getClientConfig()
                .body(String.format("{\"name\":\"%s\"}", cardName))
                .queryParam("idList", idListTodo)
                .when()
                .post(CARD_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        Assert.assertEquals(Parser.getValueFromJson(responseFormPostCard, "name"), cardName);

        String idCardSpain = Parser.getValueFromJson(responseFormPostCard, "id").toString();
        String responseFormGetCard = getClientConfig()
                .when()
                .get(CARD_URL + idCardSpain)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        Assert.assertEquals(Parser.getValueFromJson(responseFormGetCard, "closed"), false);
        Assert.assertEquals(Parser.getValueFromJson(responseFormGetCard, "name"), cardName);

        String responseFormPutCard = getClientConfig()
                .when()
                .queryParam("idList", idListDoing)
                .put(CARD_URL + idCardSpain)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        Assert.assertEquals(Parser.getValueFromJson(responseFormPutCard, "name"), cardName);
    }

    @Test
    public void deleteBoardTest() {

        String boardName = "BoardForDelete";
        String responseBoard = getClientConfig().body(String.format("{\"name\":\"%s\"}", boardName))
                .when()
                .post(BOARD_URL)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        Assert.assertEquals(Parser.getValueFromJson(responseBoard, "closed"), false);
        Assert.assertEquals(Parser.getValueFromJson(responseBoard, "name"), boardName);

        boardId = Parser.getValueFromJson(responseBoard, "id").toString();

        getClientConfig().when()
                .get(BOARD_URL + boardId)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK);

        getClientConfig().when()
                .delete(BOARD_URL + boardId)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK);

        getClientConfig().when()
                .get(BOARD_URL + boardId)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}