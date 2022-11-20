create table cartao (
  codigo bigint not null auto_increment,
  numero_cartao varchar(16) not null,
  saldo numeric(10,2) not null,
  senha varchar(6) not null,
  
  primary key (codigo)
) engine=InnoDB default charset=utf8;