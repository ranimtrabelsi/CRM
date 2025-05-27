package org.taskspfe.pfe.service.material;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.taskspfe.pfe.dto.material.MaterialDTO;
import org.taskspfe.pfe.model.material.Material;
import org.taskspfe.pfe.utility.CustomResponseEntity;

import java.util.List;
import java.util.UUID;

public interface MaterialService {

    public ResponseEntity<CustomResponseEntity<MaterialDTO>> addMaterial(final Material material , final UUID clientId);
    public ResponseEntity<CustomResponseEntity<MaterialDTO>> updateMaterial(final long materialId,Material material , UUID clientId);
    public ResponseEntity<CustomResponseEntity<MaterialDTO>> fetchMaterialById(final long materialId);
    public ResponseEntity<CustomResponseEntity<List<MaterialDTO>>> fetchMaterialsByClientId(final UUID clientId);
    public ResponseEntity<CustomResponseEntity<List<MaterialDTO>>> fetchMaterialsByCurrentClient(final UserDetails userDetails);
    public ResponseEntity<CustomResponseEntity<String>> deleteMaterialById(final long materialId);


}
