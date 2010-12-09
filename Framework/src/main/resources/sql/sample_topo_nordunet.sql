INSERT INTO node VALUES (1, NULL, NULL, 'Node3.1', NULL, NULL, NULL, NULL, NULL, NULL, 'Node3.1');
INSERT INTO node VALUES (2, NULL, NULL, 'Node3.2', NULL, NULL, NULL, NULL, NULL, NULL, 'Node3.2');
INSERT INTO node VALUES (3, NULL, NULL, 'DOM1-node-3', NULL, NULL, NULL, NULL, NULL, NULL, 'DOM1-node-3');
INSERT INTO node VALUES (4, NULL, NULL, 'host-node', NULL, NULL, NULL, NULL, NULL, NULL, 'host-node');
INSERT INTO node VALUES (5, NULL, NULL, 'DOM4-node-1', NULL, NULL, NULL, NULL, NULL, NULL, 'DOM4-node-1');


INSERT INTO generic_interface VALUES (1, NULL, NULL, NULL, 1, 'p3.1', NULL, 1000000000, NULL, 0, 'NORDUNET', false);
INSERT INTO generic_interface VALUES (2, NULL, NULL, NULL, 1, 'p3.2', NULL, 1000000000, NULL, 0, 'NORDUNET', false);
INSERT INTO generic_interface VALUES (3, NULL, NULL, NULL, 2, 'p3.3', NULL, 10000000000, NULL, 0, 'NORDUNET', false);
INSERT INTO generic_interface VALUES (4, NULL, NULL, NULL, 1, 'p3.4', NULL, 10000000000, NULL, 0, NULL, false);
INSERT INTO generic_interface VALUES (5, NULL, NULL, NULL, 2, 'p3.5', NULL, 10000000000, NULL, 0, NULL, false);
INSERT INTO generic_interface VALUES (6, NULL, NULL, NULL, 3, 'DOM1-port-8', NULL, 1000000000, NULL, 0, 'GRNET', false);
INSERT INTO generic_interface VALUES (7, NULL, NULL, NULL, 4, 'host-port', 'A host port of NORDUNET client domain', 10000000000, NULL, 0, 'http://client-domain.nordunet.dk', true);
INSERT INTO generic_interface VALUES (8, NULL, NULL, NULL, 5, 'DOM4-port-1', NULL, 1000000000, NULL, 0, 'JANET', false);


INSERT INTO generic_link VALUES (1, NULL, 1, 6, NULL, false, 0);
INSERT INTO generic_link VALUES (2, NULL, 3, 7, NULL, false, 0);
INSERT INTO generic_link VALUES (3, NULL, 4, 5, NULL, false, 0);
INSERT INTO generic_link VALUES (4, NULL, 2, 8, NULL, false, 0);


INSERT INTO eth_link VALUES (1, NULL, false, false, 0);
INSERT INTO eth_link VALUES (2, NULL, false, false, 0);
INSERT INTO eth_link VALUES (3, NULL, false, false, 0);
INSERT INTO eth_link VALUES (4, NULL, false, false, 0);


INSERT INTO vlan VALUES (1, NULL, NULL, 100, 200);
INSERT INTO vlan VALUES (2, NULL, NULL, 100, 200);
INSERT INTO vlan VALUES (3, NULL, NULL, 100, 200);
INSERT INTO vlan VALUES (4, NULL, NULL, 100, 200);


INSERT INTO spanning_tree VALUES (1, 1, NULL, 0);
INSERT INTO spanning_tree VALUES (2, 2, NULL, 0);
INSERT INTO spanning_tree VALUES (3, 3, NULL, 0);
INSERT INTO spanning_tree VALUES (4, 4, NULL, 0);
