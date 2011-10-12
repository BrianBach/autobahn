-- Requires drop_reservations.sql first, otherwise foreign key errors may be thrown

DELETE FROM link_to_path;
DELETE FROM link;
DELETE FROM port;
DELETE FROM interdomain_node;
DELETE FROM provisioning_domain;
DELETE FROM bod_user;
DELETE FROM admin_domain;