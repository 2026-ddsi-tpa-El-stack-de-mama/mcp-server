package org.example.mcpserver.clients;

import org.example.mcpserver.dtos.DonadorDTO;
import org.example.mcpserver.dtos.DonadorStatsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}