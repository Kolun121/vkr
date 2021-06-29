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
import lombok.Getter;
import lombok.Setter;
import ru.example.demo.domain.enumeration.RiskCriteria;


@Getter
@Setter
@Entity
@Table(name = "federal_subject_forecasts")
public class FederalSubjectForecast implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Float populationDynamicsCoefficient;
    
    private Integer projectedPopulation;
    
    private Integer populationInFirstYear = 0;
    
    private Integer populationInSecondYear = 0;
    
    private Integer populationInThirdYear = 0;
    
    private Integer populationInFourthYear = 0;
    
    private Integer populationInFifthYear = 0;
    
    private Double riskOfDeathFederalSubject;
    
    private Double acceptableRiskOfDeathFederalSubject;
    
    private Double costOfAdditionalProtectiveMeasures;
    
    private Double riskOfDeathWithTakenMeasuresFederalSubject;
    
    private Double effectivenessOfTakenMeasures;
    
    @Enumerated(value = EnumType.STRING)
    private RiskCriteria riskCriteria;
    
    @OneToOne()
    private FederalSubject federalSubject;
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof FederalSubjectForecast)) {
            return false;
        }
        FederalSubjectForecast object = (FederalSubjectForecast) o;
        return Objects.equals(id, object.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
}
