package carshop.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Представляет факт продажи автомобиля.
 * Содержит дату продажи и итоговую стоимость.
 */
@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "sale_number", nullable = false, unique = true, length = 20)
    private String saleNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(name = "final_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal finalPrice;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(String saleNumber) {
        this.saleNumber = saleNumber;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    protected Sale() {
    }

    public Sale(LocalDate saleDate, BigDecimal finalPrice) {
        this.saleDate = saleDate;
        this.finalPrice = finalPrice;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + getId() +
                ", saleNumber='" + saleNumber + '\'' +
                ", saleDate=" + getSaleDate() +
                ", finalPrice=" + getFinalPrice() +
                ", clientId=" + getClient().getId() +
                ", managerId=" + getManager().getId() +
                ", carId=" + getCar().getId() +
                '}';
    }
}
