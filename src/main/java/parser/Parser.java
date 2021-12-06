package parser;

import io.restassured.path.json.JsonPath;

public class Parser {

    public static Object getValueFromJson(String json, String path) {
        return new JsonPath(json).getJsonObject(path);
    }
}
