package com.example.api_processos_juridicos.domain.pessoa;

public enum TipoParte {

    AUTOR,
    REU,
    ADVOGADO;

    public static TipoParte fromString(String value) {
        for (TipoParte tipoParte : TipoParte.values()) {
            if (tipoParte.name().equalsIgnoreCase(value)) {
                return tipoParte;
            }
        }
        throw new IllegalArgumentException("TipoParte inválido: " + value);
    }
}
