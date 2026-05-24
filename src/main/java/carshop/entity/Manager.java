package carshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Представляет менеджера автосалона.
 */
@Entity
@Table(name = "managers")
public class Manager extends Person {

    @OneToMany(mappedBy = "manager")
    private final List<Sale> sales = new ArrayList<>();

    @Column(name = "employee_number", nullable = false, unique = true, length = 20)
    private String employeeNumber;

    @Column(name = "position", nullable = false, length = 100)
    private String position;

    protected Manager() {
    }

    public Manager(String fullName, String email, String phone, String position) {
        super(fullName, email, phone);
        this.position = position;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void addSale(Sale sale) {
        if (sale != null) {
            this.sales.add(sale);
            sale.setManager(this);
        }
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + getId() +
                ", employeeNumber='" + employeeNumber + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}