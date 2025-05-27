package org.taskspfe.pfe.service.material;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.taskspfe.pfe.dto.material.MaterialDTO;
import org.taskspfe.pfe.dto.material.MaterialDTOMapper;
import org.taskspfe.pfe.exceptions.ResourceNotFoundException;
import org.taskspfe.pfe.model.material.Material;
import org.taskspfe.pfe.model.user.UserEntity;
import org.taskspfe.pfe.repository.MaterialRepository;
import org.taskspfe.pfe.service.user.UserEntityService;
import org.taskspfe.pfe.utility.CustomResponseEntity;

import java.util.List;
import java.util.UUID;

@Service
public class MaterialServiceImpl implements  MaterialService{

    private final MaterialRepository materialRepository;
    private final MaterialDTOMapper materialDTOMapper;
    private final UserEntityService userEntityService;
    public MaterialServiceImpl(MaterialRepository materialRepository, MaterialDTOMapper materialDTOMapper, UserEntityService userEntityService) {
        this.materialRepository = materialRepository;
        this.materialDTOMapper = materialDTOMapper;
        this.userEntityService = userEntityService;
    }

    @Override
    public ResponseEntity<CustomResponseEntity<MaterialDTO>> addMaterial(Material material, UUID clientId) {
        final UserEntity client = userEntityService.getUserEntityById(clientId);
        material.setClient(client);
        final Material savedMaterial = materialRepository.save(material);
        final MaterialDTO materialDTO = materialDTOMapper.apply(savedMaterial);
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.CREATED,materialDTO));
    }

    @Override
    public ResponseEntity<CustomResponseEntity<MaterialDTO>> updateMaterial(long materialId, Material material, UUID clientId) {
        Material savedMaterial = getMaterialById(materialId);
        final UserEntity client =(clientId != null) ? userEntityService.getUserEntityById(clientId) : savedMaterial.getClient();
        savedMaterial.setName(material.getName());
        savedMaterial.setDescription(material.getDescription());
        savedMaterial.setClient(client);
        final Material updatedMaterial = materialRepository.save(savedMaterial);
        final MaterialDTO materialDTO = materialDTOMapper.apply(updatedMaterial);
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK,materialDTO));
    }

    @Override
    public ResponseEntity<CustomResponseEntity<MaterialDTO>> fetchMaterialById(long materialId) {
        final Material material = getMaterialById(materialId);
        final MaterialDTO materialDTO = materialDTOMapper.apply(material);
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK,materialDTO));
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<MaterialDTO>>> fetchMaterialsByClientId(UUID clientId) {
        final UserEntity client = userEntityService.getUserEntityById(clientId);
        final List<Material> materials = materialRepository.fetchMaterialsByClientId(client.getId());
        final List<MaterialDTO> materialDTOS = materials.stream().map(materialDTOMapper).toList();
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK,materialDTOS));
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<MaterialDTO>>> fetchMaterialsByCurrentClient(UserDetails userDetails) {
        final UserEntity client = userEntityService.getUserEntityByEmail(userDetails.getUsername());
        final List<Material> materials = materialRepository.fetchMaterialsByClientId(client.getId());
        final List<MaterialDTO> materialDTOS = materials.stream().map(materialDTOMapper).toList();
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK,materialDTOS));
    }

    @Override
    public ResponseEntity<CustomResponseEntity<String>> deleteMaterialById(long materialId) {
        final Material material = getMaterialById(materialId);
        materialRepository.deleteMaterialById(materialId);
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK,"Material with id "+materialId+" deleted successfully."));
    }


    private Material getMaterialById(long materialId){
        return materialRepository.fetchMaterialById(materialId).orElseThrow(
                ()->new ResourceNotFoundException("Material with id "+materialId+" not found.")
                );
    }
}
