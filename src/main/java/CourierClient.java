
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {

    public static String keysValue = null;
    public static Response response;
    public static boolean status;
    public static int courierId;

    public void create(Courier courier) {
        response = given().log().all()
                .baseUri(Endpoints.URL)
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(Endpoints.COURIER_URL);
        status = response.path("ok");
    }

    public void createSameCourier(Courier courier) {
        response = given().log().all()
                .baseUri(Endpoints.URL)
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(Endpoints.COURIER_URL);
        keysValue = response.path("message");
    }

    public void createWithoutSomeCred(Courier courier) {
        response = given().log().all()
                .baseUri(Endpoints.URL)
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(Endpoints.COURIER_URL);
        keysValue = response.path("message");
    }

    public void login(CourierCredentials creds) {
        response = given().log().all()
                .baseUri(Endpoints.URL)
                .header("Content-type", "application/json")
                .body(creds)
                .when()
                .post(Endpoints.COURIER_URL_LOGIN);
        courierId = response.path("id");
    }

    public void loginWithWrong(CourierCredentials creds) {
        response = given().log().all()
                .baseUri(Endpoints.URL)
                .header("Content-type", "application/json")
                .body(creds)
                .when()
                .post(Endpoints.COURIER_URL_LOGIN);
        keysValue = response.path("message");
    }


    public void delete(int courierId) {
        given().log().all()
                .baseUri(Endpoints.URL)
                .header("Content-type", "application/json")
                .pathParams("courierId", courierId)
                .when()
                .delete(Endpoints.COURIER_URL_DELETE)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }
}
