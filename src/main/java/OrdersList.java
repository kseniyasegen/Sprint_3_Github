import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersList {

    private int courierId;
    private String nearestStation = null;
    private int limit;
    private int page;

}
