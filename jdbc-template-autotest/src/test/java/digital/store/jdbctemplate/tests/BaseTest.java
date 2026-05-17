package digital.store.jdbctemplate.tests;

import digital.store.jdbctemplate.config.DbConfig;
import digital.store.jdbctemplate.steps.*;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseTest {

    protected final JdbcTemplate      jdbcTemplate     = new DbConfig().jdbcTemplate();

    protected final ArtistSteps       artistSteps      = new ArtistSteps(jdbcTemplate);
    protected final AlbumSteps        albumSteps       = new AlbumSteps(jdbcTemplate);
    protected final GenreSteps        genreSteps       = new GenreSteps(jdbcTemplate);
    protected final MediaTypeSteps    mediaTypeSteps   = new MediaTypeSteps(jdbcTemplate);
    protected final TrackSteps        trackSteps       = new TrackSteps(jdbcTemplate);
    protected final CustomerSteps     customerSteps    = new CustomerSteps(jdbcTemplate);
    protected final EmployeeSteps     employeeSteps    = new EmployeeSteps(jdbcTemplate);
    protected final InvoiceSteps      invoiceSteps     = new InvoiceSteps(jdbcTemplate);
    protected final InvoiceLineSteps  invoiceLineSteps = new InvoiceLineSteps(jdbcTemplate);
}
