package co.ucc.apipedidos.model;

public enum TipoEnvio {

    /** Envío por dron estándar: $5.000 por kg de peso */
    ESTANDAR(5000.0, "Dron Estándar"),

    /** Envío por dron exprés: $8.000 por kg de peso */
    EXPRES(8000.0, "Dron Exprés"),

    /** Envío internacional: costo fijo de $15.000 */
    INTERNACIONAL(15000.0, "Internacional");

    private final double tarifaPorKg;
    private final String descripcion;

    TipoEnvio(double tarifaPorKg, String descripcion) {
        this.tarifaPorKg = tarifaPorKg;
        this.descripcion = descripcion;
    }

    public double getTarifaPorKg() {
        return tarifaPorKg;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Calcula el costo de envío según el tipo y el peso.
     * Para INTERNACIONAL el costo es fijo sin importar el peso.
     */
    public double calcularCosto(double pesoKg) {
        if (this == INTERNACIONAL) {
            return tarifaPorKg; // costo fijo
        }
        return tarifaPorKg * pesoKg;
    }
}
