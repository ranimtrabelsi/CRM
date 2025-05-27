package org.taskspfe.pfe.controller.material;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.taskspfe.pfe.dto.material.MaterialDTO;
import org.taskspfe.pfe.model.material.Material;
import org.taskspfe.pfe.service.material.MaterialService;
import org.taskspfe.pfe.utility.CustomResponseEntity;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/material")
@RestController
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping("")
    public ResponseEntity<CustomResponseEntity<MaterialDTO>> addMaterial(
            @Valid @RequestBody Material material,
            @RequestParam(name = "clientId", required = true) UUID clientId
    ) {
        return materialService.addMaterial(material, clientId);
    }

    @PutMapping("/{materialId}")
    public ResponseEntity<CustomResponseEntity<MaterialDTO>> updateMaterial(
            @PathVariable("materialId") long materialId,
            @RequestBody Material material,
            @RequestParam(name = "clientId", required = false) UUID clientId
    ) {
        return materialService.updateMaterial(materialId, material, clientId);
    }

    @GetMapping("/{materialId}")
    public ResponseEntity<CustomResponseEntity<MaterialDTO>> fetchMaterialById(
            @PathVariable("materialId") long materialId
    ) {
        return materialService.fetchMaterialById(materialId);
    }

    @GetMapping("/client")
    public ResponseEntity<CustomResponseEntity<List<MaterialDTO>>> fetchMaterialsByClientId(
            @RequestParam("clientId") UUID clientId
    ) {
        return materialService.fetchMaterialsByClientId(clientId);
    }
    @GetMapping("/client/current")
    public ResponseEntity<CustomResponseEntity<List<MaterialDTO>>> fetchMaterialsByCurrentClient(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return materialService.fetchMaterialsByCurrentClient(userDetails);
    }

    @DeleteMapping("/{materialId}")
    public ResponseEntity<CustomResponseEntity<String>> deleteMaterialById(
            @PathVariable("materialId") long materialId
    ) {
        return materialService.deleteMaterialById(materialId);
    }

}
