package org.example.mcpserver.dtos;

public record NecesidadMaterialDTO(
        String id,
        String entidadID,
        Integer nivelDeUrgencia,
        String descripcion,
        Integer cantidadObjetivo,
        Integer cantidadActual,
        String productoSolicitadoID,
        TipoNecesidadMaterialEnum tipo
) {
}
