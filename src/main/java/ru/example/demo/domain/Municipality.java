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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "municipalities")
public class Municipality implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Значение должно быть заполнено")
    private String title;
    
    @Min(value = 1, message = "Значение должно быть больше нуля")
    @NotNull(message = "Значение должно быть заполнено")
    private Integer currentPopulation;
    
    @Min(value = 1, message = "Значение должно быть больше нуля")
    @NotNull(message = "Значение должно быть заполнено")
    private Double averageHumanLifeCost;
    
    @ManyToOne
    @JoinColumn(name = "municipality_type_id")
    private MunicipalityType municipalityType;
    
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private MunicipalityForecast municipalityForecast;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "municipality")
    private List<WaterBody> waterBodies = new ArrayList<>();
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "federal_subject_id")
    private FederalSubject federalSubject;
    
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Municipality)) {
            return false;
        }
        Municipality object = (Municipality) o;
        return Objects.equals(id, object.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(title, id);
    }
    
}
