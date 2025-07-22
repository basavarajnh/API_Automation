import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

public class CoinGeckoBitcoinTest {

    @Test
    public void testBitcoinEndpoint() {
        String endpoint = "https://api.coingecko.com/api/v3/coins/bitcoin";

        Response response = RestAssured.get(endpoint);
        assertEquals(200, response.getStatusCode(), "Status code is not 200");

        Map<String, Object> json = response.jsonPath().getMap("");
        Map<String, Object> marketData = (Map<String, Object>) json.get("market_data");

        // a. Verify 3 BPI currencies exist
        Map<String, Object> currentPrice = (Map<String, Object>) marketData.get("current_price");
        assertTrue(currentPrice.containsKey("usd"), "USD not found");
        assertTrue(currentPrice.containsKey("gbp"), "GBP not found");
        assertTrue(currentPrice.containsKey("eur"), "EUR not found");

        // b. Verify market_cap and total_volume
        assertTrue(marketData.containsKey("market_cap"), "market_cap missing");
        assertTrue(marketData.containsKey("total_volume"), "total_volume missing");

        // c. Verify price_change_percentage_24h
        Object change24h = marketData.get("price_change_percentage_24h");
        assertNotNull(change24h, "price_change_percentage_24h missing");

        // d. Verify homepage URL is not empty
        Map<String, Object> links = (Map<String, Object>) json.get("links");
        Object homepageList = links.get("homepage");
        assertNotNull(homepageList, "Homepage field missing");
        assertFalse(((java.util.List<?>) homepageList).isEmpty(), "Homepage list is empty");
        String homepage = ((java.util.List<?>) homepageList).get(0).toString();
        assertFalse(homepage.isEmpty(), "Homepage URL is empty");
    }
}
