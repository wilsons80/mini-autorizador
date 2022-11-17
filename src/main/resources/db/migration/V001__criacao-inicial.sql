create table cartao (
  id number not null auto_increment,
  numeroCartao varchar(16) not null,
  senha varchar(6) not null,
  
  primary key (id)
) engine=InnoDB default charset=utf8;