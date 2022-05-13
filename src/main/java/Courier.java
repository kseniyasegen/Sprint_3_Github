import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

//коммент
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Courier {

    private String login;
    private String password;
    private String firstName;

    public static Courier getRandom() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);

        return new Courier(login, password, firstName);
    }
}
