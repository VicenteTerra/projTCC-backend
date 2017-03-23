# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table aluno (
  id                            integer auto_increment not null,
  nome                          varchar(255),
  cpf                           varchar(255),
  email                         varchar(255),
  instituicao                   varchar(255),
  data_nascimento               varchar(255),
  telefone                      varchar(255),
  senha                         varchar(255),
  ativo                         integer,
  tipo_usuario                  integer,
  constraint uq_aluno_cpf unique (cpf),
  constraint uq_aluno_email unique (email),
  constraint pk_aluno primary key (id)
);

create table aluno_estabelecimento (
  aluno_id                      integer not null,
  estabelecimento_id            integer not null,
  constraint pk_aluno_estabelecimento primary key (aluno_id,estabelecimento_id)
);

create table estabelecimento (
  id                            integer auto_increment not null,
  nome                          varchar(255),
  cnpj                          varchar(255),
  nome_responsavel              varchar(255),
  cpf_reponsavel                varchar(255),
  telefone                      varchar(255),
  endereco                      varchar(255),
  email                         varchar(255),
  tipo_estabelecimento          integer,
  tipo_usuario                  integer,
  ativo                         integer,
  senha                         varchar(255),
  constraint uq_estabelecimento_cnpj unique (cnpj),
  constraint uq_estabelecimento_email unique (email),
  constraint pk_estabelecimento primary key (id)
);

alter table aluno_estabelecimento add constraint fk_aluno_estabelecimento_aluno foreign key (aluno_id) references aluno (id) on delete restrict on update restrict;
create index ix_aluno_estabelecimento_aluno on aluno_estabelecimento (aluno_id);

alter table aluno_estabelecimento add constraint fk_aluno_estabelecimento_estabelecimento foreign key (estabelecimento_id) references estabelecimento (id) on delete restrict on update restrict;
create index ix_aluno_estabelecimento_estabelecimento on aluno_estabelecimento (estabelecimento_id);


# --- !Downs

alter table aluno_estabelecimento drop foreign key fk_aluno_estabelecimento_aluno;
drop index ix_aluno_estabelecimento_aluno on aluno_estabelecimento;

alter table aluno_estabelecimento drop foreign key fk_aluno_estabelecimento_estabelecimento;
drop index ix_aluno_estabelecimento_estabelecimento on aluno_estabelecimento;

drop table if exists aluno;

drop table if exists aluno_estabelecimento;

drop table if exists estabelecimento;

