-- �������� ������� genre
CREATE TABLE IF NOT EXISTS genre (
    genre_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at DATE NOT NULL,
    genre_direction VARCHAR(50) NOT NULL
    );

-- �������� ������� media_type
CREATE TABLE IF NOT EXISTS media_type (
    media_type_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at DATE NOT NULL
    );

-- �������� ������� artist
CREATE TABLE IF NOT EXISTS artist (
    artist_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    pseudonym VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL
    );

-- �������� ������� album
CREATE TABLE IF NOT EXISTS album (
    album_id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    album_type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    artist_id UUID NOT NULL,
    CONSTRAINT fk_album_artist_id FOREIGN KEY (artist_id)
        REFERENCES artist(artist_id) ON DELETE CASCADE
    );

-- �������� ������� track
CREATE TABLE IF NOT EXISTS track (
    track_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    milliseconds INTEGER,
    bytes INTEGER,
    unit_price NUMERIC(10,2) NOT NULL,
    album_id UUID NOT NULL,
    genre_id UUID NOT NULL,
    media_type_id UUID NOT NULL,
    CONSTRAINT fk_track_album_id FOREIGN KEY (album_id)
    REFERENCES album(album_id) ON DELETE CASCADE,
    CONSTRAINT fk_track_genre_id FOREIGN KEY (genre_id)
    REFERENCES genre(genre_id) ON DELETE CASCADE,
    CONSTRAINT fk_track_media_type_id FOREIGN KEY (media_type_id)
    REFERENCES media_type(media_type_id) ON DELETE CASCADE
    );


-- �������� ������� customer
CREATE TABLE IF NOT EXISTS customer (
    customer_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    birth_date DATE,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(255),
    state VARCHAR(255),
    country VARCHAR(255),
    postal_code VARCHAR(255),
    phone VARCHAR(255),
    fax VARCHAR(255),
    email VARCHAR(255) NOT NULL
    );

-- �������� ������� employee
CREATE TABLE IF NOT EXISTS employee (
    employee_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    position VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,
    hire_date DATE NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(255),
    state VARCHAR(255),
    country VARCHAR(255),
    postal_code VARCHAR(255),
    phone VARCHAR(255),
    fax VARCHAR(255),
    email VARCHAR(255) NOT NULL
    );

-- �������� ������� invoice
CREATE TABLE IF NOT EXISTS invoice (
    invoice_id UUID PRIMARY KEY,
    invoice_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    billing_address VARCHAR(255) NOT NULL,
    billing_city VARCHAR(255),
    billing_state VARCHAR(255),
    billing_country VARCHAR(255),
    billing_postal_code VARCHAR(255),
    total NUMERIC(10,2) NOT NULL,
    customer_id UUID NOT NULL,
    employee_id UUID NOT NULL,
    CONSTRAINT fk_invoice_customer_id FOREIGN KEY (customer_id)
    REFERENCES customer(customer_id) ON DELETE CASCADE,
    CONSTRAINT fk_invoice_employee_id FOREIGN KEY (employee_id)
    REFERENCES employee(employee_id) ON DELETE CASCADE
    );

-- �������� ������� invoice_line
CREATE TABLE IF NOT EXISTS invoice_line (
    invoice_line_id UUID PRIMARY KEY,
    unit_price NUMERIC(10,2) NOT NULL,
    quantity INTEGER NOT NULL,
    invoice_id UUID NOT NULL,
    track_id UUID NOT NULL,
    CONSTRAINT fk_invoice_line_invoice_id FOREIGN KEY (invoice_id)
    REFERENCES invoice(invoice_id) ON DELETE CASCADE,
    CONSTRAINT fk_invoice_line_track_id FOREIGN KEY (track_id)
    REFERENCES track(track_id) ON DELETE CASCADE
    );

-- �������� ������� users (�������� �������� �� ������� ��� ���������� ��������)
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    user_name VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    email VARCHAR(255),
    user_role VARCHAR(50)
    );

-- �������� �������� ��� ��������� ������������������
CREATE INDEX IF NOT EXISTS idx_album_artist_id ON album(artist_id);
CREATE INDEX IF NOT EXISTS idx_track_album_id ON track(album_id);
CREATE INDEX IF NOT EXISTS idx_track_genre_id ON track(genre_id);
CREATE INDEX IF NOT EXISTS idx_track_media_type_id ON track(media_type_id);
CREATE INDEX IF NOT EXISTS idx_invoice_customer_id ON invoice(customer_id);
CREATE INDEX IF NOT EXISTS idx_invoice_employee_id ON invoice(employee_id);
CREATE INDEX IF NOT EXISTS idx_invoice_line_invoice_id ON invoice_line(invoice_id);
CREATE INDEX IF NOT EXISTS idx_invoice_line_track_id ON invoice_line(track_id);
CREATE INDEX IF NOT EXISTS idx_users_username ON users(user_name);

