import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GenericValue<GenericType> {
    private GenericType genericValue;
}
