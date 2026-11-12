package cn.dsscm.pojo;

import java.math.BigDecimal;

/**
 * 销售订单明细实体类
 */
public class OrderDetail {
    private Integer id;         // 主键
    private Integer orderId;    // 订单ID（关联 tb_order.id）
    private Integer productId;  // 商品ID（关联 tb_product.id）
    private Integer quantity;   // 数量
    private BigDecimal cost;    // 单项消费金额

    public OrderDetail() {}

    public OrderDetail(Integer id, Integer orderId, Integer productId, Integer quantity, BigDecimal cost) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.cost = cost;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }
    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", cost=" + cost +
                '}';
    }
}