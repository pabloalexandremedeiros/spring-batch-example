package br.com.company.analisedadosdevenda.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity(name = "SALE")
public class Sale extends Line implements Serializable {

    @Id
    private Long id;
    private String salesmanName;
    private Double totalValue;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "saleId", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<Item> items = new ArrayList<>();

    public Sale(String fileId, Long id, String salesmanName, Double totalValue, Collection<Item> items){

        super(fileId, Sale.class);

        this.id = id;
        this.salesmanName = salesmanName;
        this.totalValue = totalValue;
        this.items.addAll(items);
    }

    private Sale(){ super("", Sale.class); }

    public Long getId() { return id; }
    public String getSalesmanName() { return salesmanName; }
    private Double getTotalValue(){ return totalValue; }
    public Collection<Item> getItens() { return Collections.unmodifiableCollection(items); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Sale sale = (Sale) o;
        return Objects.equals(id, sale.id) &&
                Objects.equals(salesmanName, sale.salesmanName) &&
                Objects.equals(items, sale.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, salesmanName, items);
    }

    @Override
    public String toString() {
        return "Sale{" +
                "fileId='" + super.getFileId()+ '\'' +
                "id='" + id + '\'' +
                ", salesmanName='" + salesmanName + '\'' +
                ", items=" + items +
                '}';
    }
}
