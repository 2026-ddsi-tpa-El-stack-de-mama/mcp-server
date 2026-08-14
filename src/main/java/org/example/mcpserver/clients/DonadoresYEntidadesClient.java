package org.example.mcpserver.clients;

import org.example.mcpserver.dtos.DonadorDTO;
import org.example.mcpserver.dtos.DonadorStatsDTO;
import org.example.mcpserver.dtos.EntidadBeneficaDTO;
import org.example.mcpserver.dtos.NecesidadMaterialDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "donadoresYEntidades", url = "${FACHADA_DYE}")
public interface DonadoresYEntidadesClient {
    @PostMapping("/donadores")
    public ResponseEntity<DonadorDTO> agregarDonador(@RequestBody DonadorDTO donadorDTO);

    @GetMapping("/donadores/{id}/estadisticas")
    public ResponseEntity<DonadorStatsDTO> estadisticas(@PathVariable String id);

    @GetMapping("donadores/{id}")
    public ResponseEntity<DonadorDTO> buscarDonador(@PathVariable String id);

    @GetMapping("/donadores")
    public ResponseEntity<List<DonadorDTO>> obtenerDonadores();

    @PostMapping("/entidades")
    public ResponseEntity<EntidadBeneficaDTO> agregarEntidad(@RequestBody EntidadBeneficaDTO entidadDTO);

    @PutMapping("/entidades/{id}")
    public ResponseEntity<EntidadBeneficaDTO> modificarEntidad(@PathVariable String id, @RequestBody EntidadBeneficaDTO entidadDTO);

    @GetMapping("/entidades")
    public ResponseEntity<List<EntidadBeneficaDTO>> obtenerEntidades();

    @GetMapping("/entidades/{id}")
    public ResponseEntity<EntidadBeneficaDTO> buscarEntidad(@PathVariable String id);

    @PostMapping("/necesidades")
    public ResponseEntity<NecesidadMaterialDTO> registrarNecesidad(@RequestBody NecesidadMaterialDTO necesidadDTO);

    @DeleteMapping("/necesidades/{necesidadID}")
    public ResponseEntity<String> eliminarNecesidad(@PathVariable String necesidadID);

    @PutMapping("/necesidades/{necesidadID}")
    public ResponseEntity<NecesidadMaterialDTO> modificarNecesidad(@PathVariable String necesidadID, @RequestBody NecesidadMaterialDTO necesidadDTO);

    @GetMapping("/necesidades/{necesidadID}")
    public ResponseEntity<NecesidadMaterialDTO> obtenerNecesidad(@PathVariable String necesidadID);
}