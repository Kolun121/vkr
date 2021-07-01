package ru.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "municipalitiy_types")
public class MunicipalityType implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title = "Не указано";
    
    @JsonIgnore
    @OneToMany(mappedBy = "municipalityType")
    private List<Municipality> municipalities;
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof MunicipalityType)) {
            return false;
        }
        MunicipalityType object = (MunicipalityType) o;
        return Objects.equals(id, object.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(title, id);
    }
    
}
