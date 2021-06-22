package ru.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "protective_measures")
public class ProtectiveMeasure  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    @Enumerated(value = EnumType.STRING)
    private PlaceType designatedFor;
    
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "protectiveMeasure")
    private List<CrowdedPlace> crowdedPlaces = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "protectiveMeasure")
    private List<SmallVesselOperationPlace> smallVesselOperationPlaces = new ArrayList<>();
    
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof ProtectiveMeasure)) {
            return false;
        }
        ProtectiveMeasure object = (ProtectiveMeasure) o;
        return Objects.equals(id, object.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(title, id);
    }
    
}
