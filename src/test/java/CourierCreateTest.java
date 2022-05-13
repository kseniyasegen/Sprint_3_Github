import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

//коммент
public class CourierCreateTest {

    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Создание курьера /api/v1/courier")
    @Description("Курьера можно создать;\n" +
            "Успешный запрос возвращает ok: true;\n")
    public void courierCreate() {
        //создать курьера
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        //авторизоваться для получения id
        CourierCredentials creds = CourierCredentials.from(courier);
        courierClient.login(creds);
        //проверка результата теста
        assertTrue(CourierClient.status);
        //удалить курьера
        courierClient.delete(CourierClient.courierId);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров  /api/v1/courier")
    @Description("Нельзя создать двух одинаковых курьеров;\n" +
            "Если создать пользователя с логином, который уже есть, возвращается ошибка;\n")
    public void courierCreateSameCourier() {
        //создать курьера
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        //проверить, что курьер создан
        assertTrue(CourierClient.status);
        //создать курьера с теми же данными
        courierClient.createSameCourier(courier);
        //проверить, что возвращается статус код ошибки
        assertTrue(CourierClient.response.statusCode() == 409);
        //проверить, что возвращается текст ошибки
        assertTrue(CourierClient.keysValue.equals("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без логина  /api/v1/courier")
    @Description("Чтобы создать курьера, нужно передать в ручку все обязательные поля;\n" +
            "Если одного из полей нет, запрос возвращает ошибку;\n")
    public void courierCreateWithoutLogin() {
        //дернуть ручку с твумя ключами (нет одного обязательного)
        Courier courier = new Courier("", "Segen002", "Segen002");
        courierClient.createWithouSomeCred(courier);
        //проверить, что возвращается статус код ошибки
        assertTrue(CourierClient.response.statusCode() == 400);
        //проверить, что возвращается текст ошибки
        assertTrue(CourierClient.keysValue.equals("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля  /api/v1/courier")
    @Description("Чтобы создать курьера, нужно передать в ручку все обязательные поля;\n" +
            "Если одного из полей нет, запрос возвращает ошибку;\n")
    public void courierCreateWithoutPassword() {
        //дернуть ручку с твумя ключами (нет одного обязательного)
        Courier courier = new Courier("Segen002", "", "Segen002");
        courierClient.createWithouSomeCred(courier);
        //проверить, что возвращается статус код ошибки
        assertTrue(CourierClient.response.statusCode() == 400);
        //проверить, что возвращается текст ошибки
        assertTrue(CourierClient.keysValue.equals("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без логина и пароля  /api/v1/courier")
    @Description("Чтобы создать курьера, нужно передать в ручку все обязательные поля;\n" +
            "Если одного из полей нет, запрос возвращает ошибку;\n")
    public void courierCreateWithoutLoginAndPassword() {
        //дернуть ручку с твумя ключами (нет одного обязательного)
        Courier courier = new Courier("", "", "");
        courierClient.createWithouSomeCred(courier);
        //проверить, что возвращается статус код ошибки
        assertTrue(CourierClient.response.statusCode() == 400);
        //проверить, что возвращается текст ошибки
        assertTrue(CourierClient.keysValue.equals("Недостаточно данных для создания учетной записи"));
    }
}