package ru.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import ru.example.demo.domain.enumeration.FederalSubjectType;


@Getter
@Setter
@Entity
@Table(name = "federal_subjects")
public class FederalSubject implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Значение должно быть заполнено")
    private String title;
        
    @Min(value = 1, message = "Значение должно быть больше нуля")
    @NotNull(message = "Значение должно быть заполнено")
    private Integer averageHumanLifeCost = 0;
    
    @Min(value = 1, message = "Значение должно быть больше нуля")
    @NotNull(message = "Значение должно быть заполнено")
    private Integer acceptableProbabilityOfDeath = 0;
    
    @Enumerated(value = EnumType.STRING)
    private FederalSubjectType federalSubjectType;
    
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private FederalSubjectForecast federalSubjectForecast;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "federal_district_id")
    private FederalDistrict federalDistrict;
    
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "federalSubject")
    private List<Municipality> municipalities = new ArrayList<>();
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof FederalSubject)) {
            return false;
        }
        FederalSubject object = (FederalSubject) o;
        return Objects.equals(id, object.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(title, id);
    }
    
}
