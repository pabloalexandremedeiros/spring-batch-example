package br.com.company.analisedadosdevenda.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "SALESMAN")
public class Salesman extends Line implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private Long cpf;
    private String name;
    private Double salary;

    public Salesman(String fileId, Long cpf, String name, Double salary){

        super(fileId, Salesman.class);

        this.cpf = cpf;
        this.name = name;
        this.salary = salary;
    }

    private Salesman(){ super("", Salesman.class); }

    public Long getId() { return id; }
    public Long getCpf() { return cpf; }
    public String getName() { return name; }
    public Double getSalary() { return salary; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Salesman salesman = (Salesman) o;
        return Objects.equals(cpf, salesman.cpf) &&
                Objects.equals(name, salesman.name) &&
                Objects.equals(salary, salesman.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cpf, name, salary);
    }

    @Override
    public String toString() {
        return "Salesman{" +
                "fileId='" + super.getFileId()+ '\'' +
                "cpf='" + cpf + '\'' +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
