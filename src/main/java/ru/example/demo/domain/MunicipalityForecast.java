package ru.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Date;
import java.time.Year;
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
@Table(name = "municipality_forecasts")
public class MunicipalityForecast implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String year;
    
    private Integer projectedPopulation = 0;
    
    @OneToOne(cascade = CascadeType.MERGE)
    private Municipality municipality;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "municipalityForecast")
    private List<PlannedProtectiveMeasure> plannedProtectiveMeasures = new ArrayList<>();
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof MunicipalityForecast)) {
            return false;
        }
        MunicipalityForecast object = (MunicipalityForecast) o;
        return Objects.equals(id, object.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(year, id);
    }
    
}
