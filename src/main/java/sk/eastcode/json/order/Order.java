package sk.eastcode.json.order;

public class Order {
    private Long id;
    private Long storeId;
    private String item;

    public Order(Long id, Long storeId, String item) {
        this.id = id;
        this.storeId = storeId;
        this.item = item;
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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", storeId=" + storeId +
                ", item='" + item + '\'' +
                '}';
    }
}
