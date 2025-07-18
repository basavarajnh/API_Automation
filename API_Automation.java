import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class CoinDeskApiAutomationTest {

    @Test
    public void testBpiCurrencies() {

        // Step 1: Send GET request
        RestAssured.baseURI = "https://api.coindesk.com";
        Response response = given()
                .when()
                .get("/v1/bpi/")
                .then()
                .statusCode(200)
                .extract().response();

        // Step 2a: Verify BPI contains USD, GBP, EUR
        String json = response.asString();
        Assert.assertTrue(json.contains("\"USD\""), "USD not found in BPI");
        Assert.assertTrue(json.contains("\"GBP\""), "GBP not found in BPI");
        Assert.assertTrue(json.contains("\"EUR\""), "EUR not found in BPI");

        // Step 2b: Verify GBP description is 'British Pound Sterling'
        String gbpDescription = response.path("bpi.GBP.description");
        Assert.assertEquals(gbpDescription, "British Pound Sterling", "Incorrect GBP description");

        System.out.println("All validations passed successfully.");
    }
}
