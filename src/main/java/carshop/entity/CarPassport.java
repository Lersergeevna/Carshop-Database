package carshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Представляет технический паспорт автомобиля.
 * Содержит VIN, цвет и номер двигателя.
 */
@Entity
@Table(name = "car_passports")
public class CarPassport extends BaseEntity {

    @Column(name = "vin", nullable = false, unique = true, length = 17)
    private String vin;

    @Column(name = "color", nullable = false, length = 50)
    private String color;

    @Column(name = "engine_number", nullable = false, unique = true, length = 50)
    private String engineNumber;

    @Column(name = "car_id", nullable = false, unique = true)
    private Long carId;

    protected CarPassport() {
    }

    public CarPassport(String vin, String color, String engineNumber) {
        this.vin = vin;
        this.color = color;
        this.engineNumber = engineNumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    @Override
    public String toString() {
        return "CarPassport{" +
                "id=" + getId() +
                ", carId=" + carId +
                ", vin='" + getVin() + '\'' +
                ", color='" + getColor() + '\'' +
                ", engineNumber='" + getEngineNumber() + '\'' +
                '}';
    }
}
