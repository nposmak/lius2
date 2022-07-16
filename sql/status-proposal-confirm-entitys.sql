create table status(
    id serial,
    statusName varchar (150),
    primary key (id)
);

create table proposal(
	id serial,
    author_id int,
    propDate varchar (150),
    gpa_id int,
    gtu_id int,
    cbn_id int,
    description varchar (500),
    status_id int,
    primary key (id),
    foreign key (author_id) references users (id),
    foreign key (gpa_id) references gpas (id),
    foreign key (gtu_id) references gtus (id),
    foreign key (cbn_id) references cbns (id),
    foreign key (status_id) references status (id)
);

create table propconfirm(
	id serial,
	proposal_id int,
    confirmDate varchar (150),
    message varchar (200),
	coordinator_id int,
	desicion boolean,
	primary key (id),
	foreign key (proposal_id) references proposal (id),
	foreign key (coordinator_id) references users (id)
);
