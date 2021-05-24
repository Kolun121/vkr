package ru.example.demo.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.example.demo.domain.enumeration.WaterBodyType;


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
    private WaterBodyType designatedFor;
    
    @OneToOne
    private WaterBody waterBody;
    
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
