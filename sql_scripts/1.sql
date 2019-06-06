drop schema if exists `computer-database-db`;
  create schema if not exists `computer-database-db`;
  use `computer-database-db`;

  drop table if exists computer;
  drop table if exists company;
  drop table if exists role;
  drop table if exists cdbuser;

  create table company (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    constraint pk_company primary key (id))
  ;

  create table computer (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    introduced                datetime NULL,
    discontinued              datetime NULL,
    company_id                bigint default NULL,
    constraint pk_computer primary key (id))
  ;

  create table role (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    constraint pk_role primary key (id)
  );

  create table cdbuser (
    id                        bigint not null auto_increment,
    username                  varchar(255),
    password                  varchar(255),
    role_id                   bigint default 0,
    constraint pk_user primary key (id)
  );

alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
alter table cdbuser add constraint fk_cdbuser_role_1 foreign key (role_id) references role (id) on delete restrict on update restrict;
create index ix_computer_company_1 on computer (company_id);
create index ix_computer_role_1 on cdbuser (role_id);
