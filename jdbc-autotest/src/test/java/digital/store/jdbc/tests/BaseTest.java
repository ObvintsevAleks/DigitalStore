package digital.store.jdbc.tests;

import digital.store.jdbc.config.DbConnection;
import digital.store.jdbc.steps.*;

public abstract class BaseTest {

    protected final DbConnection     db               = new DbConnection();

    protected final ArtistSteps      artistSteps      = new ArtistSteps(db);
    protected final AlbumSteps       albumSteps       = new AlbumSteps(db);
    protected final GenreSteps       genreSteps       = new GenreSteps(db);
    protected final MediaTypeSteps   mediaTypeSteps   = new MediaTypeSteps(db);
    protected final TrackSteps       trackSteps       = new TrackSteps(db);
    protected final CustomerSteps    customerSteps    = new CustomerSteps(db);
    protected final EmployeeSteps    employeeSteps    = new EmployeeSteps(db);
    protected final InvoiceSteps     invoiceSteps     = new InvoiceSteps(db);
    protected final InvoiceLineSteps invoiceLineSteps = new InvoiceLineSteps(db);
}
