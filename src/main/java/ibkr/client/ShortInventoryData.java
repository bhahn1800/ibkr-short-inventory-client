package ibkr.client;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class ShortInventoryData {

    private final String symbol;
    private final Long availableShares;
    private final boolean isGreaterThan;

}
