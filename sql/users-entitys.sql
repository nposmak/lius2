create table roles(
	id serial,
	rname varchar (111),
	primary key (id)
);

create table offices(
	id serial,
	offname varchar (111),
	primary key (id)
);

create table departments(
	id serial,
	depname varchar (111),
	primary key (id)
);

create table positions(
	id serial,
	posname varchar (111),
	primary key (id)
);

create table users(
	id serial,
	name varchar (111) not null,
	sname varchar (111) not null,
	fname varchar (111) not null,
	email varchar (111) not null,
	password varchar (255) not null,
    role_id int,
    office_id int,
    department_id int,
    position_id int,
	primary key (id),
    foreign key (role_id) references roles (id),
	foreign key (office_id) references offices (id),
	foreign key (department_id) references departments (id),
	foreign key (position_id) references positions (id)
);