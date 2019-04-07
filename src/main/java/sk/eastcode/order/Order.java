package sk.eastcode.order;

public class Order {
    private Long id;
    private Long storeId;
    private String orderItem;

    public Order(Long id, Long storeId, String orderItem) {
        this.id = id;
        this.storeId = storeId;
        this.orderItem = orderItem;
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

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", storeId=" + storeId +
                ", orderItem='" + orderItem + '\'' +
                '}';
    }
}
