alter table account drop constraint FKq8p5wjtcd7y5phx72rfxmih6b;
alter table fees drop constraint FKm710q5pnmsqsliwxlot879kxl;
alter table folder drop constraint FKp3y3c84qppfo5u8svp0e98np;
alter table parent drop constraint FKdb65fj72pril8m0dtecwwwv9h;
alter table part drop constraint FKpksvvngsdcoxp3wrh2v5xs13q;
alter table person drop constraint FK8bqpehhghr43dq7xdwifsysmq;
alter table quitus drop constraint FKrykhmrj0i1k0n7yvb3mxb5p4l;
alter table student drop constraint FKntii119fw4i41p6ewe3e93iyv;
alter table student drop constraint FKnwsufvlvlnsxqv60ltj06bbfx;
drop table if exists account cascade;
drop table if exists course cascade;
drop table if exists fees cascade;
drop table if exists folder cascade;
drop table if exists parent cascade;
drop table if exists part cascade;
drop table if exists person cascade;
drop table if exists quitus cascade;
drop table if exists student cascade;
drop sequence if exists account_sequence;
drop sequence if exists course_sequence;
drop sequence if exists fees_sequence;
drop sequence if exists folder_sequence;
drop sequence if exists parent_sequence;
drop sequence if exists part_sequence;
drop sequence if exists person_sequence;
drop sequence if exists quitus_sequence;
alter table account drop constraint FKq8p5wjtcd7y5phx72rfxmih6b;
alter table fees drop constraint FKm710q5pnmsqsliwxlot879kxl;
alter table folder drop constraint FKp3y3c84qppfo5u8svp0e98np;
alter table parent drop constraint FKdb65fj72pril8m0dtecwwwv9h;
alter table part drop constraint FKpksvvngsdcoxp3wrh2v5xs13q;
alter table person drop constraint FK8bqpehhghr43dq7xdwifsysmq;
alter table quitus drop constraint FKrykhmrj0i1k0n7yvb3mxb5p4l;
alter table student drop constraint FKntii119fw4i41p6ewe3e93iyv;
alter table student drop constraint FKnwsufvlvlnsxqv60ltj06bbfx;
drop table if exists account cascade;
drop table if exists course cascade;
drop table if exists fees cascade;
drop table if exists folder cascade;
drop table if exists parent cascade;
drop table if exists part cascade;
drop table if exists person cascade;
drop table if exists quitus cascade;
drop table if exists student cascade;
drop sequence if exists account_sequence;
drop sequence if exists course_sequence;
drop sequence if exists fees_sequence;
drop sequence if exists folder_sequence;
drop sequence if exists parent_sequence;
drop sequence if exists part_sequence;
drop sequence if exists person_sequence;
drop sequence if exists quitus_sequence;