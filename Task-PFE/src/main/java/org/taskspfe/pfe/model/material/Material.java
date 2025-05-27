package org.taskspfe.pfe.model.material;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.taskspfe.pfe.model.user.UserEntity;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "materials")
public class Material {


    @SequenceGenerator(
            name = "material_sequence",
            sequenceName = "material_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE ,
            generator = "material_sequence"
    )
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description" , nullable = false, columnDefinition = "TEXT")
    private String description;

    @OneToOne
    @JoinColumn(name = "client_id")
    private UserEntity client;

}
