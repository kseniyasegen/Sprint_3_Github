import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrdersListTest {

    private OrdersData ordersData;

    @Before
    public void setUp() {
        ordersData = new OrdersData();
    }

    @Test
    @DisplayName("Список заказов /api/v1/orders")
    @Description("Проверка возвращения списка заказов")
    public void ordersListTest() {
        OrdersList ordersList = new OrdersList(1, "", 10, 1);
        ordersData.ordersList(ordersList);
        //проверить, что статус код 200
        assertEquals(OrdersData.response.statusCode(), 200);
        //проверить, что orders не пустой
        OrdersData.response.then().assertThat().body("orders", Matchers.notNullValue());
    }
}