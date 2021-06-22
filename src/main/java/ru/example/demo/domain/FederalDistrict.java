package ru.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Date;
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
@Table(name = "federal_districts")
public class FederalDistrict implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title = "Не указано";
        
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "federalDistrict")
    private List<FederalSubject> federalSubjects = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "federalDistrict")
    private List<FederalDistrictForecast> federalDistrictForecasts = new ArrayList<>();
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof FederalDistrict)) {
            return false;
        }
        FederalDistrict object = (FederalDistrict) o;
        return Objects.equals(id, object.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(title, id);
    }
    
}
