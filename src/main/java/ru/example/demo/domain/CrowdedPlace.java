package ru.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "crowded_places")
public class CrowdedPlace implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title = "Не указано";
   
    private Integer protectiveMeasureCost = 0;
    
    private Integer deathToll = 0;
    
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
