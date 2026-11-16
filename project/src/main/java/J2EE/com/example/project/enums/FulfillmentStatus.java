package J2EE.com.example.project.enums;

public enum FulfillmentStatus {
    NEW("Chờ xác nhận"),
    CONFIRMED("Đã xác nhận"),
    PACKING("Đang đóng gói"),
    SHIPPING("Đang giao"),
    DELIVERED("Đã giao hàng"),
    DELIVERY_FAILED("Giao hàng thất bại"),
    RETURNED("Đã hoàn hàng"),
    CANCELLED("Đã hủy");

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
