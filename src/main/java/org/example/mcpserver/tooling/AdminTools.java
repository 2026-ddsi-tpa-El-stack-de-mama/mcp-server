package org.example.mcpserver.tooling;

import org.example.mcpserver.clients.DonadoresYEntidadesClient;
import org.example.mcpserver.dtos.DonadorDTO;
import org.example.mcpserver.dtos.EntidadBeneficaDTO;
import org.example.mcpserver.dtos.NecesidadMaterialDTO;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class AdminTools {
    private DonadoresYEntidadesClient donadoresYEntidadesClient;

    public AdminTools(DonadoresYEntidadesClient donadoresYEntidadesClient) {
        this.donadoresYEntidadesClient = donadoresYEntidadesClient;
    }

    @McpTool(name = "crearEntidad", description = "Se crea una entidad")
    public ResponseEntity<EntidadBeneficaDTO> crearEntidad(@McpToolParam(description="EntidadBeneficaDTO", required = true) EntidadBeneficaDTO entidadBeneficaDTO){
        return donadoresYEntidadesClient.agregarEntidad(entidadBeneficaDTO);
    }

    @McpTool(name = "modificarEntidad", description = "Se modifica una entidad")
    public ResponseEntity<EntidadBeneficaDTO> modificarEntidad(@McpToolParam(description="EntidadBeneficaDTO y id", required = true) String id, EntidadBeneficaDTO entidadBeneficaDTO){
        return donadoresYEntidadesClient.modificarEntidad(id, entidadBeneficaDTO);
    }

    @McpTool(name = "buscarEntidades", description = "Se buscan todas las entidades")
    public ResponseEntity<List<EntidadBeneficaDTO>> buscarEntidades(){
        return donadoresYEntidadesClient.obtenerEntidades();
    }

    @McpTool(name = "buscarEntidadPorId", description = "Se busca una entidad por id")
    public ResponseEntity<EntidadBeneficaDTO> buscarEntidadPorId(@McpToolParam(description="Id", required = true) String id){
        return donadoresYEntidadesClient.buscarEntidad(id);
    }

    @McpTool(name = "altaNecesidad", description = "Se da el alta de una necesidad")
    public ResponseEntity<NecesidadMaterialDTO> altaEntidad(@McpToolParam(description="NecesidadMaterialDTO", required = true) NecesidadMaterialDTO necesidadDTO){
        return donadoresYEntidadesClient.registrarNecesidad(necesidadDTO);
    }

    @McpTool(name = "bajaNecesidad", description = "Se da el baja de una necesidad")
    public ResponseEntity<String> bajaEntidad(@McpToolParam(description="NecesidadId", required = true) String necesidadID){
        return donadoresYEntidadesClient.eliminarNecesidad(necesidadID);
    }

    @McpTool(name = "modificarNecesidad", description = "Se modifica una necesidad")
    public ResponseEntity<NecesidadMaterialDTO> modificarEntidad(@McpToolParam(description="NecesidadID y necesidad material", required = true) String necesidadID, NecesidadMaterialDTO necesidadMaterialDTO){
        return donadoresYEntidadesClient.modificarNecesidad(necesidadID, necesidadMaterialDTO);
    }

    @McpTool(name = "consultarNecesidad", description = "Se consulta una necesidad")
    public ResponseEntity<NecesidadMaterialDTO> consultarEntidad(@McpToolParam(description="NecesidadID", required = true) String necesidadID){
        return donadoresYEntidadesClient.obtenerNecesidad(necesidadID);
    }

}
