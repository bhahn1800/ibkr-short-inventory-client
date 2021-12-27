package ibkr.client;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@ToString
public class ShortInventoryData {

    private final String symbol;
    private final Long availableShares;
    private final boolean isGreaterThan;

}
