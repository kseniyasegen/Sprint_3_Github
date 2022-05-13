import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

//коммент
public class CourierLoginTest {

    private int courierId;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();

    }

    @Test
    @DisplayName("Авторизация с рандомными кредами /api/v1/courier/login")
    @Description("Курьер может авторизоваться;\n" +
            "Успешный запрос возвращает id;\n")
    public void courierLogin() {
        //создать курьера
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        //авторизоваться для получения id
        CourierCredentials creds = CourierCredentials.from(courier);
        courierClient.login(creds);
        //проверить, что возвращается статус код 200
        assertTrue(CourierClient.response.statusCode() == 200);
        //проверить, что возвращается id
        assertNotEquals(0, courierClient.courierId);
        //удалить курьера
        courierClient.delete(courierClient.courierId);
    }

    @Test
    @DisplayName("Авторизация без логина /api/v1/courier/login")
    @Description("Для авторизации нужно передать все обязательные поля;\n" +
            "Если какого-то поля нет, запрос возвращает ошибку;\n")
    public void courierLoginWithoutLogin() {
        //дернуть ручку с конструктором, в котором одно обязательное поле
        CourierCredentials creds = new CourierCredentials("", "Segen0123");
        courierClient.loginnWithWrong(creds);
        //проверить, что возвращается статус код 400
        assertTrue(CourierClient.response.statusCode() == 400);
        //проверить, что возвращается текст ошибки
        assertTrue(CourierClient.keysValue.equals("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без пароля /api/v1/courier/login")
    @Description("Для авторизации нужно передать все обязательные поля;\n" +
            "Если какого-то поля нет, запрос возвращает ошибку;\n")
    public void courierLoginWithoutPassword() {
        //дернуть ручку с конструктором, в котором одно обязательное поле
        CourierCredentials creds = new CourierCredentials("Segen0123", "");
        courierClient.loginnWithWrong(creds);
        //проверить, что возвращается статус код 400
        assertTrue(CourierClient.response.statusCode() == 400);
        //проверить, что возвращается текст ошибки
        assertTrue(CourierClient.keysValue.equals("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без логина и пароля /api/v1/courier/login")
    @Description("Для авторизации нужно передать все обязательные поля;\n" +
            "Если какого-то поля нет, запрос возвращает ошибку;\n")
    public void courierLoginWithoutLoginAndPassword() {
        //дернуть ручку с конструктором, в котором одно обязательное поле
        CourierCredentials creds = new CourierCredentials("", "");
        courierClient.loginnWithWrong(creds);
        //проверить, что возвращается статус код 400
        assertTrue(CourierClient.response.statusCode() == 400);
        //проверить, что возвращается текст ошибки
        assertTrue(CourierClient.keysValue.equals("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация с несуществующими кредами")
    @Description("Система вернёт ошибку, если неправильно указать логин или пароль;\n" +
            "Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;\n")
    public void courierLoginWithWrongCreds() {
        //указать несуществующие логие и пароль
        CourierCredentials creds = new CourierCredentials("Segen0123", "Segen0123");
        courierClient.loginnWithWrong(creds);
        //проверить, что возвращается статус код 400
        assertTrue(CourierClient.response.statusCode() == 404);
        //проверить, что возвращается текст ошибки
        assertTrue(CourierClient.keysValue.equals("Учетная запись не найдена"));
    }
}