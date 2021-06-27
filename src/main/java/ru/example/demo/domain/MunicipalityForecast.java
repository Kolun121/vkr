package ru.example.demo.domain;


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
@Table(name = "municipality_forecasts")
public class MunicipalityForecast implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
        
    private Float populationDynamicsCoefficient;
    
    private Integer projectedPopulation;
    
    private Integer populationInFirstYear;
    
    private Integer populationInSecondYear;
    
    private Integer populationInThirdYear;
    
    private Integer populationInFourthYear;
    
    private Integer populationInFifthYear;
    
    private Double probabilityOfDeathMSLMunicipality;
    
    private Double riskOfDeathMSLMunicipality;
    
    private Double probabilityOfDeathMMSMunicipality;
    
    private Double riskOfDeathMMSMunicipality;
    
    private Double probabilityOfDeathMunicipality;
    
    private Double riskOfDeathMunicipality;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "municipalityForecast")
    private List<AppliedProtectiveMeasure> appliedProtectiveMeasure = new ArrayList<>();
    
    @OneToOne()
    private Municipality municipality;
    
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
        return Objects.hash(id);
    }
    
}
