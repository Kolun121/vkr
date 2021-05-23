package ru.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
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
@Table(name = "municipalities")
public class Municipality implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    @OneToOne
    private MunicipalityType municipalityType;
    
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
