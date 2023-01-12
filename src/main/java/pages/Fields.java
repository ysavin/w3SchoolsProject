package pages;

public enum Fields {
    CUSTOMER_ID("CustomerID"),
    CUSTOMER_NAME("CustomerName"),
    CONTACT_NAME("ContactName"),
    ADDRESS("Address"),
    CITY("City"),
    POSTAL_CODE("PostalCode"),
    COUNTRY("Country");

    private final String text;

    Fields(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

