import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import lombok.AllArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
@AllArgsConstructor
public class OrderCreateTest {

    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;
    private int expectedStatusCode;

    @Parameterized.Parameters
    public static Object[][] getTextData() {
        return new Object[][]{
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", new String[]{"BLACK"}, 201},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", new String[]{"GRAY"}, 201},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", new String[]{"BLACK", "GRAY"}, 201},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", new String[]{""}, 201},
        };
    }

    @Test
    @DisplayName("Проверить создание заказа /api/v1/orders")
    @Description("можно указать один из цветов — BLACK или GREY;\n" +
            "можно указать оба цвета;\n" +
            "можно совсем не указывать цвет;\n" +
            "тело ответа содержит track.")
    public void orderCreateParametrized() {
        OrdersData ordersData = new OrdersData();
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ordersData.createOrder(order);
        //проверить, что ожидаемый статус код соответствует фактическому
        assertEquals(expectedStatusCode, OrdersData.response.statusCode());
        //проверить, что track не пустой
        OrdersData.response.then().assertThat().body("track", Matchers.notNullValue());
        //отменить заказ
        ordersData.cancelOrder();
    }
}