create sequence account_sequence start 1 increment 1;
create sequence course_sequence start 1 increment 1;
create sequence fees_sequence start 1 increment 1;
create sequence folder_sequence start 1 increment 1;
create sequence parent_sequence start 1 increment 1;
create sequence part_sequence start 1 increment 1;
create sequence person_sequence start 1 increment 1;
create sequence quitus_sequence start 1 increment 1;
create table account (account_id int8 not null, fk_account_creator_id int8, primary key (account_id));
create table course (course_id int8 not null, cycle varchar(100) not null, faculty varchar(100) not null, is_open boolean, level int4 not null, speciality varchar(80) not null, year varchar(9) not null, primary key (course_id));
create table fees (fees_id int8 not null, amount int4 not null, object varchar(100) not null, type varchar(2), fk_course_id int8, primary key (fees_id));
create table folder (folder_id int8 not null, creation_date DATETIME, deposit_date DATE, folder_registration_number varchar(15) not null, generation_date DATE, is_editable boolean, validated boolean not null, course_course_id int8, primary key (folder_id));
create table parent (parent_id int8 not null, attribute varchar(1), contact varchar(15), job varchar(100), names varchar(255), place varchar(100), region_of_origin varchar(100), fk_person_student_id int8, primary key (parent_id));
create table part (part_id int8 not null, archive_path varchar(255), description TEXT, file_type varchar(10), name varchar(100) not null, size int8, upload_date DATETIME, folder_folder_id int8, primary key (part_id));
create table person (person_id int8 not null, birth_date DATE not null, birth_place varchar(100) not null, contact varchar(20) not null, country varchar(100) not null, email varchar(100) not null, first_name varchar(100) not null, last_name varchar(100), sex CHAR not null, account_account_id int8, primary key (person_id));
create table quitus (quitus_id int8 not null, amount int4, object varchar(150), paiement_place varchar(150), status boolean, folder_folder_id int8, primary key (quitus_id));
create table student (country_of_graduation varchar(80) not null, diploma_option varchar(50) not null, english_level varchar(4) not null, entrance_diploma varchar(80) not null, french_level varchar(4) not null, photo_path varchar(255), region_of_origin varchar(80) not null, registration_id varchar(15), school_of_graduation varchar(80) not null, year_of_graduation varchar(4) not null, person_id int8 not null, folder_folder_id int8, primary key (person_id));
alter table folder add constraint FolderRegistrationNumberUniqueConstraint unique (folder_registration_number);
alter table part add constraint ArchivePathUniqueConstraint unique (archive_path);
alter table person add constraint EMAIL_UNIQUE_CONSTRAINT unique (email);
alter table person add constraint CONTACT_UNIQUE_CONSTRAINT unique (contact);
alter table student add constraint RegistrationIdUniqueConstraint unique (registration_id);
alter table account add constraint FKq8p5wjtcd7y5phx72rfxmih6b foreign key (fk_account_creator_id) references account;
alter table fees add constraint FKm710q5pnmsqsliwxlot879kxl foreign key (fk_course_id) references course;
alter table folder add constraint FKp3y3c84qppfo5u8svp0e98np foreign key (course_course_id) references course;
alter table parent add constraint FKdb65fj72pril8m0dtecwwwv9h foreign key (fk_person_student_id) references student;
alter table part add constraint FKpksvvngsdcoxp3wrh2v5xs13q foreign key (folder_folder_id) references folder;
alter table person add constraint FK8bqpehhghr43dq7xdwifsysmq foreign key (account_account_id) references account;
alter table quitus add constraint FKrykhmrj0i1k0n7yvb3mxb5p4l foreign key (folder_folder_id) references folder;
alter table student add constraint FKntii119fw4i41p6ewe3e93iyv foreign key (folder_folder_id) references folder;
alter table student add constraint FKnwsufvlvlnsxqv60ltj06bbfx foreign key (person_id) references person;