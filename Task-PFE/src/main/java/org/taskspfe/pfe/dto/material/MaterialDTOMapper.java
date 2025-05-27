package org.taskspfe.pfe.dto.material;

import org.springframework.stereotype.Service;
import org.taskspfe.pfe.dto.user.UserEntityDTOMapper;
import org.taskspfe.pfe.model.material.Material;

import java.util.function.Function;

@Service
public class MaterialDTOMapper implements Function<Material,MaterialDTO> {
    @Override
    public MaterialDTO apply(Material material) {
        return new MaterialDTO(
                material.getId(),
                material.getName(),
                material.getDescription(),
                new UserEntityDTOMapper().apply(material.getClient())
        );
    }
}
