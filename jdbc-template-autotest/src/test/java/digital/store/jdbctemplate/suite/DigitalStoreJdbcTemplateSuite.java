package digital.store.jdbctemplate.suite;

import digital.store.jdbctemplate.tests.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("DigitalStore — JdbcTemplate автотесты (полный набор)")
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
public class DigitalStoreJdbcTemplateSuite {
}
