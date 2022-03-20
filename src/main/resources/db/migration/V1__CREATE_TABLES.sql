CREATE TABLE salesmen (
                          id numeric not null primary key,
                          name text not null,
                          email text,
                          salary numeric not null,
                          commission numeric
);

CREATE TABLE sales (
                       id numeric not null primary key,
                       salesmen_id numeric not null,
                       product text not null,
                       quantity numeric not null,
                       value numeric not null,
                       constraint fk_sales_salesmen foreign key (salesmen_id) references salesmen(id)
);

CREATE SEQUENCE salesmen_sequence;

CREATE SEQUENCE sales_sequence;