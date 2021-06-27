package ru.example.demo.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.example.demo.domain.enumeration.PlaceType;


@Getter
@Setter
@Entity
@Table(name = "applied_protective_measures")
public class AppliedProtectiveMeasure  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double efficencyCoefficent = 0.0;
    private Integer protectivemeasureCost = 0;
    
    @ManyToOne
    private ProtectiveMeasure protectiveMeasure;
    
    @ManyToOne
    @JoinColumn(name = "municipality_forecast_id")
    private MunicipalityForecast municipalityForecast;
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof AppliedProtectiveMeasure)) {
            return false;
        }
        AppliedProtectiveMeasure object = (AppliedProtectiveMeasure) o;
        return Objects.equals(id, object.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(efficencyCoefficent, id);
    }
    
}
