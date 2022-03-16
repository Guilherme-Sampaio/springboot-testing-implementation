CREATE TABLE salesman (
                          id numeric not null primary key,
                          name text not null,
                          email text,
                          salary numeric not null,
                          commission numeric
);

CREATE TABLE sales (
                       id numeric not null primary key,
                       salesman_id numeric not null,
                       product text not null,
                       quantity numeric not null,
                       value text not null,
                       constraint fk_sales_salesman foreign key (salesman_id) references salesman(id)
);

CREATE SEQUENCE salesman_sequence;

CREATE SEQUENCE sales_sequence;