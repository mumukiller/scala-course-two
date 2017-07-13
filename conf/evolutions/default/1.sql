# --- !Ups

create table tweets(
  id Long not null PRIMARY KEY,
  text varchar(255) not null,
  author varchar(255) not null,
  link varchar(255) not null
);

insert into tweets values(1,  'aa bb cc',             'g.gerasimov', 'link');
insert into tweets values(2,  'aa       dd',          'g.gerasimov', 'link');
insert into tweets values(3,  'aa bb    dd ee',       'g.gerasimov', 'link');
insert into tweets values(4,  'aa bb    dd ee ff',    'g.gerasimov', 'link');
insert into tweets values(5,  'aa bb cc dd',          'g.gerasimov', 'link');
insert into tweets values(6,  'aa bb cc dd ee',       'g.gerasimov', 'link');
insert into tweets values(7,  'aa bb cc dd ee ff',    'g.gerasimov', 'link');
insert into tweets values(8,  'aa bb cc dd ee ff gg', 'g.gerasimov', 'link');
insert into tweets values(9,  '         dd ee',       'g.gerasimov', 'link');
insert into tweets values(10, '      cc dd ee',       'g.gerasimov', 'link');

# --- !Downs

drop table users;