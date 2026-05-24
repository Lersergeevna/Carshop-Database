package carshop.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Представляет автомобиль автосалона.
 * Содержит основные характеристики и стоимость автомобиля.
 */
@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    @Column(name = "inventory_number", nullable = false, unique = true, length = 20)
    private String inventoryNumber;

    @Column(name = "brand", nullable = false, length = 50)
    private String brand;

    @Column(name = "model", nullable = false, length = 50)
    private String model;

    @Column(name = "manufacture_year", nullable = false)
    private Integer manufactureYear;

    @Column(name = "horse_power", nullable = false)
    private Integer horsePower;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @OneToMany(mappedBy = "car")
    private final List<Sale> sales = new ArrayList<>();

    @ManyToMany(mappedBy = "favoriteCars")
    private final List<Client> interestedClients = new ArrayList<>();

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private CarPassport passport;

    protected Car() {
    }

    public Car(String brand, String model, Integer manufactureYear, Integer horsePower, BigDecimal price) {
        this.brand = brand;
        this.model = model;
        this.manufactureYear = manufactureYear;
        this.horsePower = horsePower;
        this.price = price;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public Integer getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(Integer horsePower) {
        this.horsePower = horsePower;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void addSale(Sale sale) {
        if (sale != null) {
            sales.add(sale);
            sale.setCar(this);
        }
    }

    public List<Client> getInterestedClients() {
        return interestedClients;
    }

    public CarPassport getPassport() {
        return passport;
    }

    public void setPassport(CarPassport passport) {
        this.passport = passport;

        if (passport != null) {
            passport.setCar(this);
        }
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + getId() +
                ", inventoryNumber='" + inventoryNumber + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", manufactureYear=" + manufactureYear +
                ", horsePower=" + horsePower +
                ", price=" + price +
                '}';
    }
}
