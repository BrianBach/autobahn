
-- simple topology consisting of two client nodes and two idcp client nodes

INSERT INTO node VALUES (1, NULL, NULL, 'test-node', NULL, NULL, NULL, NULL, NULL, NULL, 'test-node', false);
INSERT INTO node VALUES (2, NULL, NULL, 'client-node1', NULL, NULL, NULL, NULL, NULL, NULL, 'client-node1', false);
INSERT INTO node VALUES (3, NULL, NULL, 'client-node2', NULL, NULL, NULL, NULL, NULL, NULL, 'client-node2', false);
INSERT INTO node VALUES (4, NULL, NULL, 'idcp-node', NULL, NULL, NULL, NULL, NULL, NULL, 'idcp-node', false);

INSERT INTO generic_interface VALUES (1, NULL, NULL, NULL, 1, 'p1', NULL, 1000000000, NULL, 0, 'http://localhost:8080/autobahn/interdomain', false);
INSERT INTO generic_interface VALUES (2, NULL, NULL, NULL, 2, 'host-port1', 'A host port 1', 1000000000, NULL, 0, 'http://client-domain1.com', true);
INSERT INTO generic_interface VALUES (3, NULL, NULL, NULL, 3, 'host-port2', 'A host port 2', 1000000000, NULL, 0, 'http://client-domain2.com', true);
INSERT INTO generic_interface VALUES (4, NULL, NULL, NULL, 4, 'idcp-port1', 'IDCP port 1', 1000000000, NULL, 0, 'http://client-domain.controlplane.net/oscars', true);
INSERT INTO generic_interface VALUES (5, NULL, NULL, NULL, 4, 'idcp-port2', 'IDCP port 2', 1000000000, NULL, 0, 'http://client-domain.controlplane.net/oscars', true);

INSERT INTO generic_link VALUES (1, NULL, 1, 2, NULL, false, 0);
INSERT INTO generic_link VALUES (2, NULL, 1, 3, NULL, false, 0);
INSERT INTO generic_link VALUES (3, NULL, 1, 4, NULL, false, 0);
INSERT INTO generic_link VALUES (4, NULL, 1, 5, NULL, false, 0);

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
