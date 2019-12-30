package br.com.company.analisedadosdevenda.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Item implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private Long productId;
    private Long saleId;
    private Integer quantity;
    private Double price;

    public Item(Long productId, Long saleId, Integer quantity, Double price){

        this.productId = productId;
        this.saleId = saleId;
        this.quantity = quantity;
        this.price = price;
    }

    public Item(){}

    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public Long getSaleId() { return saleId; }
    public Integer getQuantity() { return quantity; }
    public Double getPrice() { return price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(productId, item.productId) &&
                Objects.equals(saleId, item.saleId) &&
                Objects.equals(quantity, item.quantity) &&
                Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, saleId, quantity, price);
    }

    @Override
    public String
    toString() {
        return "Item{" +
                "id=" + id +
                ", productId=" + productId +
                ", saleId=" + saleId +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
