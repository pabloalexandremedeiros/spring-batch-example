package br.com.company.analisedadosdevenda.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Client extends Line implements Serializable {

    @Id
    private Long cnpj;
    private String name;
    private String businessArea;

    public Client(String fileId, Long cnpj, String name, String businessArea) {

        super(fileId, Client.class);

        this.cnpj = cnpj;
        this.name = name;
        this.businessArea = businessArea;
    }

    private Client(){ super("", Client.class); }

    public Long getCnpj() { return cnpj; }
    public String getName() { return name; }
    public String getBusinessArea() { return businessArea; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return Objects.equals(cnpj, client.cnpj) &&
                Objects.equals(name, client.name) &&
                Objects.equals(businessArea, client.businessArea);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cnpj, name, businessArea);
    }

    @Override
    public String toString() {
        return "Client{" +
                "fileId='" + super.getFileId()+ '\'' +
                "cnpj=" + cnpj +
                ", name='" + name + '\'' +
                ", businessArea='" + businessArea + '\'' +
                '}';
    }
}
