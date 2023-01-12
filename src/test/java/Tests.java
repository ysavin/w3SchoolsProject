import org.junit.jupiter.api.Test;
import pages.Fields;


import static org.junit.jupiter.api.Assertions.*;

class Tests extends BaseTest {

    @Test
    void checkAddressByContactNameTest() {
        page.open();
        page.runQuery();
        assertEquals("Via Ludovico il Moro 22", page.getInfoByFieldName(
                        page.getResultsByContactName("Giovanni Rovelli"),
                        Fields.ADDRESS).get(0),
                "Address for ContactName is correct");
    }

    @Test
    void checkResultTableSizeTest() {
        page.open();
        page.inputQuery(page.CITY_SQL);
        page.runQuery();
        assertEquals(6, page.getTableRows().size(), "Result size is correct");
    }

    @Test
    void addNewRowTest() {
        page.open();
        page.inputQuery(page.INSERT_SQL);
        page.runQuery();
        assertEquals("You have made changes to the database. Rows affected: 1",
                page.getChangeMessage(), "Message after insert is correct");
        page.inputQuery(page.CHECK_INSERT_SQL);
        page.runQuery();
        assertEquals(1, page.getTableRows().size(), "New row is added to the table");
    }

    @Test
    void updateRowTest() {
        page.open();
        page.inputQuery(page.UPDATE_SQL);
        page.runQuery();
        assertEquals("You have made changes to the database. Rows affected: 1",
                page.getChangeMessage(), "Message after insert is correct");
        page.inputQuery(page.CHECK_UPDATE_SQL);
        page.runQuery();
        assertTrue(page.getResultByFieldName(Fields.CUSTOMER_NAME).startsWith("UPDATED_"), "Customer name is updated");
        assertTrue(page.getResultByFieldName(Fields.CONTACT_NAME).startsWith("UPDATED_"), "Contact name name is updated");
        assertTrue(page.getResultByFieldName(Fields.ADDRESS).startsWith("UPDATED_"), "Address is updated");
        assertTrue(page.getResultByFieldName(Fields.CITY).startsWith("UPDATED_"), "City is updated");
        assertTrue(page.getResultByFieldName(Fields.POSTAL_CODE).startsWith("UPDATED_"), "Postal code is updated");
        assertTrue(page.getResultByFieldName(Fields.COUNTRY).startsWith("UPDATED_"), "Country is updated");
    }

    @Test
    void restoreDbTest() {
        page.open();
        page.inputQuery(page.INSERT_SQL);
        page.runQuery();
        assertEquals("You have made changes to the database. Rows affected: 1",
                page.getChangeMessage(), "Message after insert is correct");
        page.inputQuery(page.CHECK_INSERT_SQL);
        page.runQuery();
        assertEquals(1, page.getTableRows().size(), "New row is added to the table");
        page.restoreDb();
        page.acceptAlert();
        assertEquals("The database is fully restored.", page.getRestoreMessage(), "Database is restored");
        page.inputQuery(page.CHECK_INSERT_SQL);
        page.runQuery();
        assertEquals("No result.", page.getNoResultMessage(), "Added row is restored");
    }
}
