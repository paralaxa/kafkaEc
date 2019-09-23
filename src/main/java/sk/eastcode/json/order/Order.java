package sk.eastcode.json.order;

import java.util.Date;

public class Order {
    private Long id;
    private Long storeId;
    private String item;
    private String countryCode;
    private Date timestamp;

    public Order(Long id, Long storeId, String item) {
        this.id = id;
        this.storeId = storeId;
        this.item = item;
    }

    public Order(Long id, Long storeId, String item, String countryCode) {
        this.id = id;
        this.storeId = storeId;
        this.item = item;
        this.countryCode = countryCode;
    }

    public Order(Long id, Long storeId, String item, String countryCode, Date timestamp) {
        this.id = id;
        this.storeId = storeId;
        this.item = item;
        this.countryCode = countryCode;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", storeId=" + storeId +
                ", item='" + item + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
