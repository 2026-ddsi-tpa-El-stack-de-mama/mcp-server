package org.example.mcpserver.dtos;

import java.util.List;

public record DonadorStatsDTO(
        String id,
        String nombre,
        String apellido,
        Integer edad,
        EstadoDonadorEnum estado,
        String categoria,
        String misionActualID,
        List<String> insigniasID) {}