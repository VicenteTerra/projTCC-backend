# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table aluno (
  id                            integer auto_increment not null,
  nome                          varchar(255),
  cpf                           varchar(255),
  email                         varchar(255),
  instituicao                   integer,
  data_nascimento               varchar(255),
  telefone                      varchar(255),
  senha                         varchar(255),
  ativo                         integer,
  tipo_usuario                  integer,
  constraint uq_aluno_cpf unique (cpf),
  constraint uq_aluno_email unique (email),
  constraint pk_aluno primary key (id)
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

create table instituicao (
  id                            integer auto_increment not null,
  ativo                         integer,
  nome                          varchar(255),
  endereco                      varchar(255),
  email                         varchar(255),
  nome_representante            varchar(255),
  cpf_representante             varchar(255),
  telefone                      varchar(255),
  senha                         varchar(255),
  constraint pk_instituicao primary key (id)
);

create table instituicao_estabelecimento (
  instituicao_id                integer not null,
  estabelecimento_id            integer not null,
  constraint pk_instituicao_estabelecimento primary key (instituicao_id,estabelecimento_id)
);

create table usuario (
  id                            integer auto_increment not null,
  login                         varchar(255),
  tipo                          integer,
  descricao                     varchar(255),
  constraint uq_usuario_login unique (login),
  constraint pk_usuario primary key (id)
);

create table documento (
  id                            integer auto_increment not null,
  owner_id                      integer,
  base64_string                 longtext,
  file_name                     varchar(255),
  file_type                     varchar(255),
  file_size                     integer,
  constraint pk_documento primary key (id)
);

alter table instituicao_estabelecimento add constraint fk_instituicao_estabelecimento_instituicao foreign key (instituicao_id) references instituicao (id) on delete restrict on update restrict;
create index ix_instituicao_estabelecimento_instituicao on instituicao_estabelecimento (instituicao_id);

alter table instituicao_estabelecimento add constraint fk_instituicao_estabelecimento_estabelecimento foreign key (estabelecimento_id) references estabelecimento (id) on delete restrict on update restrict;
create index ix_instituicao_estabelecimento_estabelecimento on instituicao_estabelecimento (estabelecimento_id);


# --- !Downs

alter table instituicao_estabelecimento drop foreign key fk_instituicao_estabelecimento_instituicao;
drop index ix_instituicao_estabelecimento_instituicao on instituicao_estabelecimento;

alter table instituicao_estabelecimento drop foreign key fk_instituicao_estabelecimento_estabelecimento;
drop index ix_instituicao_estabelecimento_estabelecimento on instituicao_estabelecimento;

drop table if exists aluno;

drop table if exists estabelecimento;

drop table if exists instituicao;

drop table if exists instituicao_estabelecimento;

drop table if exists usuario;

drop table if exists documento;

