package J2EE.com.example.project.enums;

public enum FulfillmentStatus {
    NEW("NEW"),
    CONFIRMED("CONFIRMED"),
    PACKING("PACKING"),
    SHIPPING("SHIPPING"),
    DELIVERED("DELIVERED"),
    DELIVERY_FAILED("DELIVERY_FAILED"),
    RETURNED("RETURNED"),
    CANCELLED("CANCELLED");

    private final String displayName;

    FulfillmentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static FulfillmentStatus fromValue(String value) {
        if (value == null || value.isBlank() || "PENDING".equalsIgnoreCase(value)) {
            return NEW;
        }

        for (FulfillmentStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)
                    || status.displayName.equalsIgnoreCase(value)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Unknown fulfillment status: " + value);
    }
}
