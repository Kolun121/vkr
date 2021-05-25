package ru.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "small_vessels_operation_places")
public class SmallVesselOperationPlace implements Serializable{
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
        if (!(o instanceof SmallVesselOperationPlace)) {
            return false;
        }
        SmallVesselOperationPlace object = (SmallVesselOperationPlace) o;
        return Objects.equals(id, object.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(title, id);
    }
    
}