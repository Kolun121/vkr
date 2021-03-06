package ru.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import ru.example.demo.domain.enumeration.TypeOfCrowdedPlace;


@Getter
@Setter
@Entity
@Table(name = "crowded_places")
public class CrowdedPlace implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Значение должно быть заполнено")
    private String title = "Не указано";
   
    @Min(value = 0, message = "Значение не должно быть отрицательно")
    private Integer protectiveMeasureCost = 0;
    
    @Min(value = 0, message = "Значение не должно быть отрицательно")
    private Integer deathToll = 0;
    
    @Enumerated(value = EnumType.STRING)
    private TypeOfCrowdedPlace typeOfCrowdedPlace;
            
            
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "protective_measure_id")
    private ProtectiveMeasure protectiveMeasure;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "water_body_id")
    private WaterBody waterBody;
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof CrowdedPlace)) {
            return false;
        }
        CrowdedPlace object = (CrowdedPlace) o;
        return Objects.equals(id, object.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(title, id);
    }
    
}
