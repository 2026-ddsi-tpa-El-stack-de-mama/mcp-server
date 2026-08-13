package org.example.mcpserver.tooling;

import org.example.mcpserver.clients.DonadoresYEntidadesClient;
import org.example.mcpserver.dtos.DonadorDTO;
import org.example.mcpserver.dtos.DonadorStatsDTO;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class DonadoresTools {
    private DonadoresYEntidadesClient donadoresYEntidadesClient;

    public DonadoresTools(DonadoresYEntidadesClient donadoresYEntidadesClient) {
        this.donadoresYEntidadesClient = donadoresYEntidadesClient;
    }

    @McpTool(name = "registrarDonador", description = "Un donador se registra en la app")
    public ResponseEntity<DonadorDTO> registrarDonador(@McpToolParam(description="DonadorDTO", required = true) DonadorDTO donadorDTO){
        return donadoresYEntidadesClient.agregarDonador(donadorDTO);
    }

    @McpTool(name = "consultarEstadisticas", description = "Se consultan las estadísticas de un donador")
    public ResponseEntity<DonadorStatsDTO> consultarEstadisticas(@McpToolParam(description="id", required = true) String id){
        return donadoresYEntidadesClient.estadisticas(id);
    }

    @McpTool(name = "consultarDonadorId", description = "Se consulta un donador mediante su id")
    public ResponseEntity<DonadorDTO> consultarDonadorPorId(@McpToolParam(description="id", required = true) String id){
        return donadoresYEntidadesClient.buscarDonador(id);
    }

    @McpTool(name = "consultarDonadores", description = "Se consultan todos los donadores")
    public ResponseEntity<List<DonadorDTO>> consultarDonadores(){
        return donadoresYEntidadesClient.obtenerDonadores();
    }


}
