package digital.store.jdbc.suite;

import digital.store.jdbc.tests.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("DigitalStore — JDBC автотесты (полный набор)")
@SelectClasses({
        GenreTest.class,
        MediaTypeTest.class,
        ArtistTest.class,
        AlbumTest.class,
        CustomerTest.class,
        EmployeeTest.class,
        TrackTest.class,
        InvoiceTest.class,
        InvoiceLineTest.class
})
public class DigitalStoreJdbcSuite {
}
