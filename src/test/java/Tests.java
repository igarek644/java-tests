import static io.restassured.RestAssured.get;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Test;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;

public class Tests {

    private String apiKey = "a26fc8e0";
    private String host = "http://omdbapi.com";

    @Test
    public void testCase1() {
        get(host + "/?apikey=" + apiKey + "&s=Rambo")
                .then()
                .body("Search.findAll{it.Type.equals(\"movie\")}", hasSize(5));
    }

    @Test
    public void testCase2() {
        Response responseByName =
                get(host + "/?t=Ready+Player+One&apikey=" + apiKey);

        JSONObject jsonObject = new JSONObject(responseByName.asString());

        String imdbId = jsonObject.get("imdbID").toString();

        Response responseByImdbSearch =
                get(host + "/?i=" + imdbId +"&apikey=" + apiKey);

        assertEquals(responseByName.asString(), responseByImdbSearch.asString());
    }
}
