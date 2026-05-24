package carshop.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Представляет клиента автосалона.
 */
@Entity
@Table(name = "clients")
public class Client extends Person {

    @Column(name = "client_number", nullable = false, unique = true, length = 20)
    private String clientNumber;

    @Column(name = "discount_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @OneToMany(mappedBy = "client")
    private final List<Sale> sales = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "client_favorite_cars",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private final List<Car> favoriteCars = new ArrayList<>();

    protected Client() {
    }

    public Client(String fullName, String email, String phone) {
        super(fullName, email, phone);
        this.discountPercentage = BigDecimal.ZERO;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void addSale(Sale sale) {
        if (sale != null) {
            sales.add(sale);
            sale.setClient(this);
        }
    }

    public List<Car> getFavoriteCars() {
        return favoriteCars;
    }

    public void addFavoriteCar(Car car) {
        if (car != null && !favoriteCars.contains(car)) {
            favoriteCars.add(car);
            car.getInterestedClients().add(this);
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + getId() +
                ", clientNumber='" + getClientNumber() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", discountPercentage=" + discountPercentage +
                '}';
    }
}
