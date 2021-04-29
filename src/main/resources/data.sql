insert ignore into user (username, password, is_active, role)
values ('admin','$2a$10$fArcoK3EabRqBwxcykkZd.rXhHi7nQJvzyMCWogDMD7zsJdkb006i',true,1);

insert ignore into staff (id, first_street, second_street, street_no, town, contact, gender, image, f_name, initial, l_name, salary_no)
values ('admin','sample first street','sample second street','1','imaduwa','',0,'images/Staffs/owner.jpeg','Administer','','','');
