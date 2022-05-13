import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

//коммент
public class OrdersData {

    public static Response response;
    public static int track;
    public static String keysValue = null;

    public void createOrder(Order order) {
        response = given().log().all()
                .baseUri(Endpoints.URL)
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(Endpoints.ORDER_URL);
        track = response.path("track");
        System.out.println("track: "+ response.path("track").toString());
    }

    public void cancelOrder() {
        given().log().all()
                .baseUri(Endpoints.URL)
                .header("Content-type", "application/json")
                .queryParam("track", track)
                .when()
                .put(Endpoints.ORDER_CANCEL)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    public void ordersList(OrdersList ordersList) {
        response = given().log().all()
                .baseUri(Endpoints.URL)
                .header("Content-type", "application/json")
                .body(ordersList)
                .when()
                .get(Endpoints.ORDER_URL);
    }
}
