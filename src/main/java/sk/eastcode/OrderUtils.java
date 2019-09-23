package sk.eastcode;

import sk.eastcode.json.order.Order;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongSupplier;

public class OrderUtils {

    private static final int STORE_COUNT = 4;

    private static final Random RANDOM = new Random();

    private static final LongSupplier orderIdSequence = new LongSupplier() {

        private final AtomicLong i = new AtomicLong(0);

        @Override
        public long getAsLong() {
            return i.getAndIncrement();
        }
    };

    private static final List<String> storeItems = Arrays.asList(
            "Bread",
            "Milk",
            "Butter",
            "Crackers",
            "Cereal",
            "Wine",
            "Beer",
            "Mineral water",
            "Toothpaste",
            "Shampoo",
            "Soap",
            "Onions",
            "Tomatoes",
            "Potatoes",
            "Pork chop",
            "Chicken breasts"
    );

    private static final List<String> countryCodes = Arrays.asList(
            "AD", "AE", "AF", "AG", "AI", "AL", "AM", "AO", "AQ", "AR", "AS", "AT", "AU", "AW", "AX", "AZ", "BA", "BB", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BL", "BM", "BN", "BO", "BQ", "BR", "BS", "BT", "BV", "BW", "BY", "BZ", "CA", "CC", "CD", "CF", "CG", "CH", "CI", "CK", "CL", "CM", "CN", "CO", "CR", "CU", "CV", "CW", "CX", "CY", "CZ", "DE", "DJ", "DK", "DM", "DO", "DZ", "EC", "EE", "EG", "EH", "ER", "ES", "ET", "FI", "FJ", "FK", "FM", "FO", "FR", "GA", "GB", "GD", "GE", "GF", "GG", "GH", "GI", "GL", "GM", "GN", "GP", "GQ", "GR", "GS", "GT", "GU", "GW", "GY", "HK", "HM", "HN", "HR", "HT", "HU", "ID", "IE", "IL", "IM", "IN", "IO", "IQ", "IR", "IS", "IT", "JE", "JM", "JO", "JP", "KE", "KG", "KH", "KI", "KM", "KN", "KP", "KR", "KW", "KY", "KZ", "LA", "LB", "LC", "LI", "LK", "LR", "LS", "LT", "LU", "LV", "LY", "MA", "MC", "MD", "ME", "MF", "MG", "MH", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS", "MT", "MU", "MV", "MW", "MX", "MY", "MZ", "NA", "NC", "NE", "NF", "NG", "NI", "NL", "NO", "NP", "NR", "NU", "NZ", "OM", "PA", "PE", "PF", "PG", "PH", "PK", "PL", "PM", "PN", "PR", "PS", "PT", "PW", "PY", "QA", "RE", "RO", "RS", "RU", "RW", "SA", "SB", "SC", "SD", "SE", "SG", "SH", "SI", "SJ", "SK", "SL", "SM", "SN", "SO", "SR", "SS", "ST", "SV", "SX", "SY", "SZ", "TC", "TD", "TF", "TG", "TH", "TJ", "TK", "TL", "TM", "TN", "TO", "TR", "TT", "TV", "TW", "TZ", "UA", "UG", "UM", "US", "UY", "UZ", "VA", "VC", "VE", "VG", "VI", "VN", "VU", "WF", "WS", "YE", "YT", "ZA", "ZM", "ZW"
    );

    public static String getRandomStoreItem() {
        return storeItems.get(RANDOM.nextInt(storeItems.size()));
    }

    public static String getRandomCountryCode() {
        return countryCodes.get(RANDOM.nextInt(countryCodes.size()));
    }

    public static Order getRandomOrder() {
        long orderId = orderIdSequence.getAsLong();
        long storeId = orderId % STORE_COUNT;
        String storeItem = getRandomStoreItem();
        String countryCode = getRandomCountryCode();

        return new Order(orderId, storeId, storeItem, countryCode, new Date());
    }
}
