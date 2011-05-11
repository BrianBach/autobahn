--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: additive_constraint; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE additive_constraint (
    con_id bigint NOT NULL,
    value double precision
);


ALTER TABLE public.additive_constraint OWNER TO postgres;

--
-- Name: admin_domain; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE admin_domain (
    domain_id character varying(255) NOT NULL,
    asid character varying(255),
    name character varying(255),
    clientdomain boolean,
    idcpserver character varying(255)
);


ALTER TABLE public.admin_domain OWNER TO postgres;

--
-- Name: bod_user; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE bod_user (
    name character varying(255) NOT NULL,
    email character varying(255),
    homedomain character varying(255) NOT NULL
);


ALTER TABLE public.bod_user OWNER TO postgres;

--
-- Name: boolean_constraint; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE boolean_constraint (
    con_id bigint NOT NULL,
    value boolean,
    logic character varying(255)
);


ALTER TABLE public.boolean_constraint OWNER TO postgres;

--
-- Name: domain_constraints; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE domain_constraints (
    dcon_id bigint NOT NULL,
    gcon_id bigint,
    domain_order integer
);


ALTER TABLE public.domain_constraints OWNER TO postgres;

--
-- Name: domainsids; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE domainsids (
    gcon_id bigint NOT NULL,
    domain_id character varying(255),
    domain_order integer NOT NULL
);


ALTER TABLE public.domainsids OWNER TO postgres;

--
-- Name: eth_link; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE eth_link (
    link_id bigint NOT NULL,
    discovery_protocol character varying(255),
    is_trunk boolean,
    is_l2_bndry boolean,
    native_vlan bigint
);


ALTER TABLE public.eth_link OWNER TO postgres;

--
-- Name: eth_logical_port; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE eth_logical_port (
    interface_id bigint NOT NULL
);


ALTER TABLE public.eth_logical_port OWNER TO postgres;

--
-- Name: eth_physical_port; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE eth_physical_port (
    interface_id bigint NOT NULL,
    interface_name character varying(255),
    mac_address character varying(255),
    duplex character varying(255),
    medium_dependent_interface character varying(255),
    is_tagged boolean
);


ALTER TABLE public.eth_physical_port OWNER TO postgres;

--
-- Name: generic_connection; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE generic_connection (
    generic_connection_id bigint NOT NULL,
    version_id bigint,
    path_id bigint,
    link_id bigint,
    direction character varying(255),
    connection_type character varying(255),
    bandwidth double precision
);


ALTER TABLE public.generic_connection OWNER TO postgres;

--
-- Name: generic_interface; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE generic_interface (
    interface_id bigint NOT NULL,
    version_id bigint,
    interface_type_id bigint,
    parent_interface_id bigint,
    node_id bigint,
    name character varying(255),
    description character varying(255),
    bandwidth bigint,
    status character varying(255),
    mtu integer NOT NULL,
    domain_id character varying(255),
    client_port boolean
);


ALTER TABLE public.generic_interface OWNER TO postgres;

--
-- Name: generic_link; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE generic_link (
    link_id bigint NOT NULL,
    version_id bigint,
    start_interface_id bigint,
    end_interface_id bigint,
    direction character varying(255),
    protection boolean,
    prop_delay double precision
);


ALTER TABLE public.generic_link OWNER TO postgres;

--
-- Name: glink_to_intrapath; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE glink_to_intrapath (
    pathid bigint NOT NULL,
    glink_id bigint NOT NULL,
    link_order integer NOT NULL
);


ALTER TABLE public.glink_to_intrapath OWNER TO postgres;

--
-- Name: global_constraints; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE global_constraints (
    gcon_id bigint NOT NULL
);


ALTER TABLE public.global_constraints OWNER TO postgres;

--
-- Name: hasrole; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE hasrole (
    nodeid character varying(255) NOT NULL,
    linkid character varying(255) NOT NULL,
    isdemarc boolean
);


ALTER TABLE public.hasrole OWNER TO postgres;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 1, false);


--
-- Name: ho_vc_group; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ho_vc_group (
    ho_vc_group_id bigint NOT NULL,
    vlan_tag_id bigint,
    name character varying(255)
);


ALTER TABLE public.ho_vc_group OWNER TO postgres;

--
-- Name: ho_vc_link; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ho_vc_link (
    ho_vc_link_id bigint NOT NULL,
    ho_vc_group_id bigint,
    stm_link_id bigint,
    ho_vc_type_id bigint,
    time_slot bigint,
    date_modified date,
    group_sequence bigint,
    status character varying(255)
);


ALTER TABLE public.ho_vc_link OWNER TO postgres;

--
-- Name: ho_vc_type; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ho_vc_type (
    ho_vc_type_id bigint NOT NULL,
    name character varying(255),
    bandwidth bigint,
    payload bigint
);


ALTER TABLE public.ho_vc_type OWNER TO postgres;

--
-- Name: interdomain_node; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE interdomain_node (
    node_id character varying(255) NOT NULL,
    type character varying(255),
    address character varying(255),
    name character varying(255),
    country character varying(255),
    city character varying(255),
    institution character varying(255),
    longitude character varying(255),
    latitude character varying(255),
    provisioningdomain character varying(255) NOT NULL
);


ALTER TABLE public.interdomain_node OWNER TO postgres;

--
-- Name: interdomain_path; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE interdomain_path (
    path_id bigint NOT NULL,
    monetary_cost double precision,
    manual_cost double precision
);


ALTER TABLE public.interdomain_path OWNER TO postgres;

--
-- Name: interface_type; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE interface_type (
    interface_type_id bigint NOT NULL,
    switching_type character varying(255),
    data_encoding_type character varying(255)
);


ALTER TABLE public.interface_type OWNER TO postgres;

--
-- Name: intradomain_path; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE intradomain_path (
    pathid bigint NOT NULL,
    capacity bigint
);


ALTER TABLE public.intradomain_path OWNER TO postgres;

--
-- Name: intradomain_reservation; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE intradomain_reservation (
    reservationid character varying(255) NOT NULL,
    pathcreated boolean,
    active boolean,
    path_id bigint,
    params_id bigint
);


ALTER TABLE public.intradomain_reservation OWNER TO postgres;

--
-- Name: link; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE link (
    link_id character varying(255) NOT NULL,
    kind integer,
    startport character varying(255) NOT NULL,
    endport character varying(255) NOT NULL,
    bidirectional boolean,
    delay integer,
    manualcost double precision,
    monetarycost double precision,
    granularity bigint,
    minrescapacity bigint,
    maxrescapacity bigint,
    capacity bigint,
    resilience character varying(255),
    state_oper_enum integer,
    state_admin_enum integer,
    link_type_enum integer,
    localname character varying(255),
    "timestamp" timestamp without time zone
);


ALTER TABLE public.link OWNER TO postgres;

--
-- Name: link_to_path; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE link_to_path (
    path_id bigint NOT NULL,
    link_id character varying(255) NOT NULL,
    link_order integer NOT NULL
);


ALTER TABLE public.link_to_path OWNER TO postgres;

--
-- Name: link_type; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE link_type (
    link_type_enum integer NOT NULL
);


ALTER TABLE public.link_type OWNER TO postgres;

--
-- Name: location; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE location (
    location_id bigint NOT NULL,
    name character varying(255),
    description character varying(255),
    country character varying(255),
    institution character varying(255),
    street character varying(255),
    floor integer,
    room_suite integer,
    row_ integer,
    cabinet integer,
    zip_code character varying(255),
    phone_number character varying(255),
    e_mail_address character varying(255),
    geo_latitude double precision,
    geo_longitude double precision,
    type character varying(255),
    zone character varying(255),
    altitude double precision
);


ALTER TABLE public.location OWNER TO postgres;

--
-- Name: minval_constraint; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE minval_constraint (
    con_id bigint NOT NULL,
    value double precision
);


ALTER TABLE public.minval_constraint OWNER TO postgres;

--
-- Name: mpls_link; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE mpls_link (
    link_id bigint NOT NULL
);


ALTER TABLE public.mpls_link OWNER TO postgres;

--
-- Name: network_constraint; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE network_constraint (
    con_id bigint NOT NULL
);


ALTER TABLE public.network_constraint OWNER TO postgres;

--
-- Name: node; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE node (
    node_id bigint NOT NULL,
    version_id bigint,
    location_id bigint,
    name character varying(255),
    description character varying(255),
    status character varying(255),
    vendor character varying(255),
    model character varying(255),
    os_name character varying(255),
    os_version character varying(255),
    ip_address character varying(255)
);


ALTER TABLE public.node OWNER TO postgres;

--
-- Name: och; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE och (
    och_id bigint NOT NULL,
    payload character varying(255),
    status character varying(255)
);


ALTER TABLE public.och OWNER TO postgres;

--
-- Name: och_link; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE och_link (
    och_link_id bigint NOT NULL,
    ops_link_id bigint,
    och_id bigint,
    frequency double precision,
    status character varying(255)
);


ALTER TABLE public.och_link OWNER TO postgres;

--
-- Name: ops_link; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ops_link (
    ops_link_id bigint NOT NULL,
    max_no_lambdas bigint,
    bitrate bigint,
    status character varying(255)
);


ALTER TABLE public.ops_link OWNER TO postgres;

--
-- Name: path; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE path (
    path_id bigint NOT NULL,
    version_id bigint,
    name character varying(255),
    status character varying(255)
);


ALTER TABLE public.path OWNER TO postgres;

--
-- Name: path_constraint; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE path_constraint (
    pcon_id bigint NOT NULL,
    dcon_id bigint,
    path_order integer
);


ALTER TABLE public.path_constraint OWNER TO postgres;

--
-- Name: pcon_add_constraints; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pcon_add_constraints (
    pcon_id bigint NOT NULL,
    constraint_id bigint NOT NULL,
    constraint_order integer NOT NULL
);


ALTER TABLE public.pcon_add_constraints OWNER TO postgres;

--
-- Name: pcon_add_names; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pcon_add_names (
    pcon_id bigint NOT NULL,
    name smallint,
    constraint_order integer NOT NULL
);


ALTER TABLE public.pcon_add_names OWNER TO postgres;

--
-- Name: pcon_bool_constraints; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pcon_bool_constraints (
    pcon_id bigint NOT NULL,
    constraint_id bigint NOT NULL,
    constraint_order integer NOT NULL
);


ALTER TABLE public.pcon_bool_constraints OWNER TO postgres;

--
-- Name: pcon_bool_names; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pcon_bool_names (
    pcon_id bigint NOT NULL,
    name smallint,
    constraint_order integer NOT NULL
);


ALTER TABLE public.pcon_bool_names OWNER TO postgres;

--
-- Name: pcon_minval_constraints; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pcon_minval_constraints (
    pcon_id bigint NOT NULL,
    constraint_id bigint NOT NULL,
    constraint_order integer NOT NULL
);


ALTER TABLE public.pcon_minval_constraints OWNER TO postgres;

--
-- Name: pcon_minval_names; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pcon_minval_names (
    pcon_id bigint NOT NULL,
    name smallint,
    constraint_order integer NOT NULL
);


ALTER TABLE public.pcon_minval_names OWNER TO postgres;

--
-- Name: pcon_range_constraints; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pcon_range_constraints (
    pcon_id bigint NOT NULL,
    constraint_id bigint NOT NULL,
    constraint_order integer NOT NULL
);


ALTER TABLE public.pcon_range_constraints OWNER TO postgres;

--
-- Name: pcon_range_names; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pcon_range_names (
    pcon_id bigint NOT NULL,
    name smallint,
    constraint_order integer NOT NULL
);


ALTER TABLE public.pcon_range_names OWNER TO postgres;

--
-- Name: pcons_to_intrapath; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pcons_to_intrapath (
    pathid bigint NOT NULL,
    pcon_id bigint NOT NULL,
    glink_map_id bigint NOT NULL
);


ALTER TABLE public.pcons_to_intrapath OWNER TO postgres;

--
-- Name: port; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE port (
    port_id character varying(255) NOT NULL,
    description character varying(255),
    technology character varying(255),
    bundled boolean,
    node character varying(255) NOT NULL
);


ALTER TABLE public.port OWNER TO postgres;

--
-- Name: provisioning_domain; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE provisioning_domain (
    provdomain_id character varying(255) NOT NULL,
    provtype character varying(255),
    admindomain character varying(255) NOT NULL
);


ALTER TABLE public.provisioning_domain OWNER TO postgres;

--
-- Name: range; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE range (
    range_id bigint NOT NULL,
    min integer,
    max integer,
    con_id bigint NOT NULL,
    link_order integer
);


ALTER TABLE public.range OWNER TO postgres;

--
-- Name: range_constraint; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE range_constraint (
    con_id bigint NOT NULL
);


ALTER TABLE public.range_constraint OWNER TO postgres;

--
-- Name: reservation; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE reservation (
    res_id character varying(255) NOT NULL,
    res_type character varying(255) NOT NULL,
    state integer,
    startport character varying(255) NOT NULL,
    endport character varying(255) NOT NULL,
    starttime timestamp without time zone,
    endtime timestamp without time zone,
    priority integer,
    capacity bigint,
    description character varying(255),
    maxdelay integer,
    resiliency character varying(255),
    bidirectional boolean,
    mtu integer,
    state_oper_enum integer,
    globalconstraints bigint,
    path bigint,
    srv_id character varying(255),
    res_index integer
);


ALTER TABLE public.reservation OWNER TO postgres;

--
-- Name: reservation_params; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE reservation_params (
    id bigint NOT NULL,
    capacity bigint,
    maxdelay integer,
    resiliency character varying(255),
    bidirectional boolean,
    starttime timestamp without time zone,
    endtime timestamp without time zone,
    mtu integer,
    pathconstraintsingress bigint NOT NULL,
    pathconstraintsegress bigint NOT NULL
);


ALTER TABLE public.reservation_params OWNER TO postgres;

--
-- Name: sdh_device; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE sdh_device (
    node_id bigint NOT NULL,
    sdh_domain_id bigint,
    name character varying(255),
    nsap bigint
);


ALTER TABLE public.sdh_device OWNER TO postgres;

--
-- Name: sdh_domain; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE sdh_domain (
    sdh_domain_id bigint NOT NULL,
    name character varying(255),
    provmethod character varying(255),
    equipment_provider character varying(255),
    date_modified date
);


ALTER TABLE public.sdh_domain OWNER TO postgres;

--
-- Name: sdh_port; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE sdh_port (
    port_id bigint NOT NULL,
    address character varying(255),
    phy_port_type character varying(255)
);


ALTER TABLE public.sdh_port OWNER TO postgres;

--
-- Name: service; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE service (
    srv_id character varying(255) NOT NULL,
    justification character varying(255),
    priority integer,
    bod_user character varying(255) NOT NULL
);


ALTER TABLE public.service OWNER TO postgres;

--
-- Name: spanning_tree; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE spanning_tree (
    link_id bigint NOT NULL,
    vlan_id bigint NOT NULL,
    state character varying(255),
    cost bigint
);


ALTER TABLE public.spanning_tree OWNER TO postgres;

--
-- Name: state_admin; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE state_admin (
    state_admin_enum integer NOT NULL
);


ALTER TABLE public.state_admin OWNER TO postgres;

--
-- Name: state_oper; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE state_oper (
    state_oper_enum integer NOT NULL
);


ALTER TABLE public.state_oper OWNER TO postgres;

--
-- Name: statistics; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE statistics (
    id integer NOT NULL,
    reservation_id character varying(255),
    intradomain boolean,
    setup_time bigint
);


ALTER TABLE public.statistics OWNER TO postgres;

--
-- Name: statistics_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE statistics_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.statistics_id_seq OWNER TO postgres;

--
-- Name: statistics_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('statistics_id_seq', 1, false);


--
-- Name: stm_link; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE stm_link (
    stm_link_id bigint NOT NULL,
    och_id bigint,
    stm_type_id bigint,
    status character varying(255)
);


ALTER TABLE public.stm_link OWNER TO postgres;

--
-- Name: stm_type; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE stm_type (
    stm_type_id bigint NOT NULL,
    name character varying(255),
    bandwidth bigint
);


ALTER TABLE public.stm_type OWNER TO postgres;

--
-- Name: version_info; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE version_info (
    version_id bigint NOT NULL,
    start_date date,
    end_date date,
    created_by character varying(255),
    modified_by character varying(255),
    date_created date,
    date_modified date
);


ALTER TABLE public.version_info OWNER TO postgres;

--
-- Name: vlan; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vlan (
    vlan_id bigint NOT NULL,
    vtp_domain_id bigint,
    name character varying(255),
    low_number bigint,
    high_number bigint
);


ALTER TABLE public.vlan OWNER TO postgres;

--
-- Name: vlan_port; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vlan_port (
    interface_id bigint NOT NULL,
    vlan_id bigint
);


ALTER TABLE public.vlan_port OWNER TO postgres;

--
-- Name: vlan_tag; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vlan_tag (
    vlan_tag_id bigint NOT NULL,
    payload character varying(255),
    date_modified date
);


ALTER TABLE public.vlan_tag OWNER TO postgres;

--
-- Name: vtp_domain; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vtp_domain (
    vtp_domain_id bigint NOT NULL,
    name character varying(255),
    srv_ipv4_addr character varying(255)
);


ALTER TABLE public.vtp_domain OWNER TO postgres;

--
-- Data for Name: additive_constraint; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY additive_constraint (con_id, value) FROM stdin;
\.


--
-- Data for Name: admin_domain; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY admin_domain (domain_id, asid, name, clientdomain, idcpserver) FROM stdin;
some_domain	\N	some_domain	f	\N
https://oscars-dev.es.net/axis2/services/OSCARS	\N	https://oscars-dev.es.net/axis2/services/OSCARS	t	\N
DoublinHost	\N	DoublinHost	t	\N
\.


--
-- Data for Name: bod_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY bod_user (name, email, homedomain) FROM stdin;
\.


--
-- Data for Name: boolean_constraint; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY boolean_constraint (con_id, value, logic) FROM stdin;
\.


--
-- Data for Name: domain_constraints; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY domain_constraints (dcon_id, gcon_id, domain_order) FROM stdin;
\.


--
-- Data for Name: domainsids; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY domainsids (gcon_id, domain_id, domain_order) FROM stdin;
\.


--
-- Data for Name: eth_link; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY eth_link (link_id, discovery_protocol, is_trunk, is_l2_bndry, native_vlan) FROM stdin;
1		f	f	0
2		f	t	1
3		f	t	1
4		f	t	1
\.


--
-- Data for Name: eth_logical_port; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY eth_logical_port (interface_id) FROM stdin;
\.


--
-- Data for Name: eth_physical_port; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY eth_physical_port (interface_id, interface_name, mac_address, duplex, medium_dependent_interface, is_tagged) FROM stdin;
\.


--
-- Data for Name: generic_connection; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY generic_connection (generic_connection_id, version_id, path_id, link_id, direction, connection_type, bandwidth) FROM stdin;
\.


--
-- Data for Name: generic_interface; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY generic_interface (interface_id, version_id, interface_type_id, parent_interface_id, node_id, name, description, bandwidth, status, mtu, domain_id, client_port) FROM stdin;
1	\N	\N	\N	1	cwt1;;Gig1/0/1	\N	1000000000	\N	0	some_domain	f
2	\N	\N	\N	2	cwt2;;Gig1/0/3	\N	1000000000	\N	0	some_domain	f
3	\N	\N	\N	2	cwt2;;Gig1/0/8	\N	1000000000	\N	0	some_domain	f
4	\N	\N	\N	3	DoublinHost	\N	1000000000	\N	0	DoublinHost	t
5	\N	\N	\N	1	cwt1;;Gig1/0/13	\N	1000000000	\N	0	some_domain	f
6	\N	\N	\N	4	HEANET1	\N	1000000000	\N	0	GEANT	f
7	\N	\N	\N	2	cwt2;;t	\N	1	\N	0	some_domain	f
8	\N	\N	\N	5	new york	null\nidcplink=urn:ogf:network:domain=dev.es.net:node=bois-cr1:port=xe-7/3/0:link=*	1000000000	\N	0	https://oscars-dev.es.net/axis2/services/OSCARS	t
\.


--
-- Data for Name: generic_link; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY generic_link (link_id, version_id, start_interface_id, end_interface_id, direction, protection, prop_delay) FROM stdin;
1	\N	1	2	\N	f	0
2	\N	3	4	\N	f	0
3	\N	5	6	\N	f	0
4	\N	7	8	\N	f	0
\.


--
-- Data for Name: glink_to_intrapath; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY glink_to_intrapath (pathid, glink_id, link_order) FROM stdin;
\.


--
-- Data for Name: global_constraints; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY global_constraints (gcon_id) FROM stdin;
\.


--
-- Data for Name: hasrole; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY hasrole (nodeid, linkid, isdemarc) FROM stdin;
\.


--
-- Data for Name: ho_vc_group; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ho_vc_group (ho_vc_group_id, vlan_tag_id, name) FROM stdin;
\.


--
-- Data for Name: ho_vc_link; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ho_vc_link (ho_vc_link_id, ho_vc_group_id, stm_link_id, ho_vc_type_id, time_slot, date_modified, group_sequence, status) FROM stdin;
\.


--
-- Data for Name: ho_vc_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ho_vc_type (ho_vc_type_id, name, bandwidth, payload) FROM stdin;
\.


--
-- Data for Name: interdomain_node; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY interdomain_node (node_id, type, address, name, country, city, institution, longitude, latitude, provisioningdomain) FROM stdin;
10.10.0.0	\N	\N	\N	\N	\N	\N	\N	\N	some_domain
10.10.0.3	\N	\N	\N	\N	\N	\N	\N	\N	https://oscars-dev.es.net/axis2/services/OSCARS
10.10.0.1	\N	\N	\N	\N	\N	\N	\N	\N	DoublinHost
10.10.0.2	\N	\N	\N	\N	\N	\N	\N	\N	some_domain
\.


--
-- Data for Name: interdomain_path; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY interdomain_path (path_id, monetary_cost, manual_cost) FROM stdin;
\.


--
-- Data for Name: interface_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY interface_type (interface_type_id, switching_type, data_encoding_type) FROM stdin;
\.


--
-- Data for Name: intradomain_path; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY intradomain_path (pathid, capacity) FROM stdin;
\.


--
-- Data for Name: intradomain_reservation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY intradomain_reservation (reservationid, pathcreated, active, path_id, params_id) FROM stdin;
\.


--
-- Data for Name: link; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY link (link_id, kind, startport, endport, bidirectional, delay, manualcost, monetarycost, granularity, minrescapacity, maxrescapacity, capacity, resilience, state_oper_enum, state_admin_enum, link_type_enum, localname, "timestamp") FROM stdin;
10.10.64.2	3	10.10.32.4	10.10.32.6	f	0	0	0	0	0	0	1	\N	1	1	2	\N	\N
10.10.64.1	3	10.10.32.2	10.10.32.5	f	0	0	0	0	0	0	1000000000	\N	1	1	2	\N	\N
10.10.64.0	1	10.10.32.0	10.10.32.1	f	0	0	0	0	0	0	1000000000	\N	1	1	2	\N	\N
\.


--
-- Data for Name: link_to_path; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY link_to_path (path_id, link_id, link_order) FROM stdin;
\.


--
-- Data for Name: link_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY link_type (link_type_enum) FROM stdin;
2
\.


--
-- Data for Name: location; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY location (location_id, name, description, country, institution, street, floor, room_suite, row_, cabinet, zip_code, phone_number, e_mail_address, geo_latitude, geo_longitude, type, zone, altitude) FROM stdin;
\.


--
-- Data for Name: minval_constraint; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY minval_constraint (con_id, value) FROM stdin;
\.


--
-- Data for Name: mpls_link; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY mpls_link (link_id) FROM stdin;
\.


--
-- Data for Name: network_constraint; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY network_constraint (con_id) FROM stdin;
\.


--
-- Data for Name: node; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY node (node_id, version_id, location_id, name, description, status, vendor, model, os_name, os_version, ip_address) FROM stdin;
1	\N	\N	cwt1	\N	\N	\N	\N	\N	\N	\N
2	\N	\N	cwt2	\N	\N	\N	\N	\N	\N	\N
3	\N	\N	external-node-2	\N	\N	\N	\N	\N	\N	\N
4	\N	\N	external-node-3	\N	\N	\N	\N	\N	\N	\N
5	\N	\N	external-node-4	\N	\N	\N	\N	\N	\N	\N
\.


--
-- Data for Name: och; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY och (och_id, payload, status) FROM stdin;
\.


--
-- Data for Name: och_link; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY och_link (och_link_id, ops_link_id, och_id, frequency, status) FROM stdin;
\.


--
-- Data for Name: ops_link; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ops_link (ops_link_id, max_no_lambdas, bitrate, status) FROM stdin;
\.


--
-- Data for Name: path; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY path (path_id, version_id, name, status) FROM stdin;
\.


--
-- Data for Name: path_constraint; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY path_constraint (pcon_id, dcon_id, path_order) FROM stdin;
\.


--
-- Data for Name: pcon_add_constraints; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pcon_add_constraints (pcon_id, constraint_id, constraint_order) FROM stdin;
\.


--
-- Data for Name: pcon_add_names; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pcon_add_names (pcon_id, name, constraint_order) FROM stdin;
\.


--
-- Data for Name: pcon_bool_constraints; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pcon_bool_constraints (pcon_id, constraint_id, constraint_order) FROM stdin;
\.


--
-- Data for Name: pcon_bool_names; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pcon_bool_names (pcon_id, name, constraint_order) FROM stdin;
\.


--
-- Data for Name: pcon_minval_constraints; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pcon_minval_constraints (pcon_id, constraint_id, constraint_order) FROM stdin;
\.


--
-- Data for Name: pcon_minval_names; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pcon_minval_names (pcon_id, name, constraint_order) FROM stdin;
\.


--
-- Data for Name: pcon_range_constraints; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pcon_range_constraints (pcon_id, constraint_id, constraint_order) FROM stdin;
\.


--
-- Data for Name: pcon_range_names; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pcon_range_names (pcon_id, name, constraint_order) FROM stdin;
\.


--
-- Data for Name: pcons_to_intrapath; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pcons_to_intrapath (pathid, pcon_id, glink_map_id) FROM stdin;
\.


--
-- Data for Name: port; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY port (port_id, description, technology, bundled, node) FROM stdin;
10.10.32.4	10.10.32.4	Ethernet	f	10.10.0.0
10.10.32.6	null\nidcplink=urn:ogf:network:domain=dev.es.net:node=bois-cr1:port=xe-7/3/0:link=*	Ethernet	f	10.10.0.3
10.10.32.2	10.10.32.2	Ethernet	f	10.10.0.0
10.10.32.5	10.10.32.5	Ethernet	f	10.10.0.1
10.10.32.0	10.10.32.0	Ethernet	f	10.10.0.2
10.10.32.1	10.10.32.1	Ethernet	f	10.10.0.0
\.


--
-- Data for Name: provisioning_domain; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY provisioning_domain (provdomain_id, provtype, admindomain) FROM stdin;
some_domain	\N	some_domain
https://oscars-dev.es.net/axis2/services/OSCARS	\N	https://oscars-dev.es.net/axis2/services/OSCARS
DoublinHost	\N	DoublinHost
\.


--
-- Data for Name: range; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY range (range_id, min, max, con_id, link_order) FROM stdin;
\.


--
-- Data for Name: range_constraint; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY range_constraint (con_id) FROM stdin;
\.


--
-- Data for Name: reservation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY reservation (res_id, res_type, state, startport, endport, starttime, endtime, priority, capacity, description, maxdelay, resiliency, bidirectional, mtu, state_oper_enum, globalconstraints, path, srv_id, res_index) FROM stdin;
\.


--
-- Data for Name: reservation_params; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY reservation_params (id, capacity, maxdelay, resiliency, bidirectional, starttime, endtime, mtu, pathconstraintsingress, pathconstraintsegress) FROM stdin;
\.


--
-- Data for Name: sdh_device; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY sdh_device (node_id, sdh_domain_id, name, nsap) FROM stdin;
\.


--
-- Data for Name: sdh_domain; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY sdh_domain (sdh_domain_id, name, provmethod, equipment_provider, date_modified) FROM stdin;
\.


--
-- Data for Name: sdh_port; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY sdh_port (port_id, address, phy_port_type) FROM stdin;
\.


--
-- Data for Name: service; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY service (srv_id, justification, priority, bod_user) FROM stdin;
\.


--
-- Data for Name: spanning_tree; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY spanning_tree (link_id, vlan_id, state, cost) FROM stdin;
1	1	\N	0
2	2	\N	0
3	3	\N	0
4	4	\N	0
\.


--
-- Data for Name: state_admin; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY state_admin (state_admin_enum) FROM stdin;
1
\.


--
-- Data for Name: state_oper; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY state_oper (state_oper_enum) FROM stdin;
1
\.


--
-- Data for Name: statistics; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY statistics (id, reservation_id, intradomain, setup_time) FROM stdin;
\.


--
-- Data for Name: stm_link; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY stm_link (stm_link_id, och_id, stm_type_id, status) FROM stdin;
\.


--
-- Data for Name: stm_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY stm_type (stm_type_id, name, bandwidth) FROM stdin;
\.


--
-- Data for Name: version_info; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY version_info (version_id, start_date, end_date, created_by, modified_by, date_created, date_modified) FROM stdin;
\.


--
-- Data for Name: vlan; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY vlan (vlan_id, vtp_domain_id, name, low_number, high_number) FROM stdin;
1	\N	vlan-x	100	200
2	\N	vlan-ext	0	4096
3	\N	vlan-ext	0	4096
4	\N	vlan-ext	0	4096
\.


--
-- Data for Name: vlan_port; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY vlan_port (interface_id, vlan_id) FROM stdin;
\.


--
-- Data for Name: vlan_tag; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY vlan_tag (vlan_tag_id, payload, date_modified) FROM stdin;
\.


--
-- Data for Name: vtp_domain; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY vtp_domain (vtp_domain_id, name, srv_ipv4_addr) FROM stdin;
\.


--
-- Name: additive_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY additive_constraint
    ADD CONSTRAINT additive_constraint_pkey PRIMARY KEY (con_id);


--
-- Name: admin_domain_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY admin_domain
    ADD CONSTRAINT admin_domain_pkey PRIMARY KEY (domain_id);


--
-- Name: bod_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY bod_user
    ADD CONSTRAINT bod_user_pkey PRIMARY KEY (name);


--
-- Name: boolean_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY boolean_constraint
    ADD CONSTRAINT boolean_constraint_pkey PRIMARY KEY (con_id);


--
-- Name: domain_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY domain_constraints
    ADD CONSTRAINT domain_constraints_pkey PRIMARY KEY (dcon_id);


--
-- Name: domainsids_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY domainsids
    ADD CONSTRAINT domainsids_pkey PRIMARY KEY (gcon_id, domain_order);


--
-- Name: eth_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY eth_link
    ADD CONSTRAINT eth_link_pkey PRIMARY KEY (link_id);


--
-- Name: eth_logical_port_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY eth_logical_port
    ADD CONSTRAINT eth_logical_port_pkey PRIMARY KEY (interface_id);


--
-- Name: eth_physical_port_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY eth_physical_port
    ADD CONSTRAINT eth_physical_port_pkey PRIMARY KEY (interface_id);


--
-- Name: generic_connection_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY generic_connection
    ADD CONSTRAINT generic_connection_pkey PRIMARY KEY (generic_connection_id);


--
-- Name: generic_interface_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY generic_interface
    ADD CONSTRAINT generic_interface_pkey PRIMARY KEY (interface_id);


--
-- Name: generic_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY generic_link
    ADD CONSTRAINT generic_link_pkey PRIMARY KEY (link_id);


--
-- Name: glink_to_intrapath_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY glink_to_intrapath
    ADD CONSTRAINT glink_to_intrapath_pkey PRIMARY KEY (pathid, link_order);


--
-- Name: global_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY global_constraints
    ADD CONSTRAINT global_constraints_pkey PRIMARY KEY (gcon_id);


--
-- Name: hasrole_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hasrole
    ADD CONSTRAINT hasrole_pkey PRIMARY KEY (nodeid, linkid);


--
-- Name: ho_vc_group_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ho_vc_group
    ADD CONSTRAINT ho_vc_group_pkey PRIMARY KEY (ho_vc_group_id);


--
-- Name: ho_vc_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ho_vc_link
    ADD CONSTRAINT ho_vc_link_pkey PRIMARY KEY (ho_vc_link_id);


--
-- Name: ho_vc_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ho_vc_type
    ADD CONSTRAINT ho_vc_type_pkey PRIMARY KEY (ho_vc_type_id);


--
-- Name: interdomain_node_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY interdomain_node
    ADD CONSTRAINT interdomain_node_pkey PRIMARY KEY (node_id);


--
-- Name: interdomain_path_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY interdomain_path
    ADD CONSTRAINT interdomain_path_pkey PRIMARY KEY (path_id);


--
-- Name: interface_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY interface_type
    ADD CONSTRAINT interface_type_pkey PRIMARY KEY (interface_type_id);


--
-- Name: intradomain_path_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY intradomain_path
    ADD CONSTRAINT intradomain_path_pkey PRIMARY KEY (pathid);


--
-- Name: intradomain_reservation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY intradomain_reservation
    ADD CONSTRAINT intradomain_reservation_pkey PRIMARY KEY (reservationid);


--
-- Name: link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY link
    ADD CONSTRAINT link_pkey PRIMARY KEY (link_id);


--
-- Name: link_to_path_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY link_to_path
    ADD CONSTRAINT link_to_path_pkey PRIMARY KEY (path_id, link_order);


--
-- Name: link_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY link_type
    ADD CONSTRAINT link_type_pkey PRIMARY KEY (link_type_enum);


--
-- Name: location_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY location
    ADD CONSTRAINT location_pkey PRIMARY KEY (location_id);


--
-- Name: minval_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY minval_constraint
    ADD CONSTRAINT minval_constraint_pkey PRIMARY KEY (con_id);


--
-- Name: mpls_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mpls_link
    ADD CONSTRAINT mpls_link_pkey PRIMARY KEY (link_id);


--
-- Name: network_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY network_constraint
    ADD CONSTRAINT network_constraint_pkey PRIMARY KEY (con_id);


--
-- Name: node_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY node
    ADD CONSTRAINT node_pkey PRIMARY KEY (node_id);


--
-- Name: och_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY och_link
    ADD CONSTRAINT och_link_pkey PRIMARY KEY (och_link_id);


--
-- Name: och_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY och
    ADD CONSTRAINT och_pkey PRIMARY KEY (och_id);


--
-- Name: ops_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ops_link
    ADD CONSTRAINT ops_link_pkey PRIMARY KEY (ops_link_id);


--
-- Name: path_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY path_constraint
    ADD CONSTRAINT path_constraint_pkey PRIMARY KEY (pcon_id);


--
-- Name: path_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY path
    ADD CONSTRAINT path_pkey PRIMARY KEY (path_id);


--
-- Name: pcon_add_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pcon_add_constraints
    ADD CONSTRAINT pcon_add_constraints_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_add_names_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pcon_add_names
    ADD CONSTRAINT pcon_add_names_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_bool_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pcon_bool_constraints
    ADD CONSTRAINT pcon_bool_constraints_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_bool_names_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pcon_bool_names
    ADD CONSTRAINT pcon_bool_names_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_minval_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pcon_minval_constraints
    ADD CONSTRAINT pcon_minval_constraints_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_minval_names_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pcon_minval_names
    ADD CONSTRAINT pcon_minval_names_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_range_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pcon_range_constraints
    ADD CONSTRAINT pcon_range_constraints_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_range_names_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pcon_range_names
    ADD CONSTRAINT pcon_range_names_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcons_to_intrapath_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pcons_to_intrapath
    ADD CONSTRAINT pcons_to_intrapath_pkey PRIMARY KEY (pathid, glink_map_id);


--
-- Name: port_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY port
    ADD CONSTRAINT port_pkey PRIMARY KEY (port_id);


--
-- Name: provisioning_domain_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY provisioning_domain
    ADD CONSTRAINT provisioning_domain_pkey PRIMARY KEY (provdomain_id);


--
-- Name: range_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY range_constraint
    ADD CONSTRAINT range_constraint_pkey PRIMARY KEY (con_id);


--
-- Name: range_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY range
    ADD CONSTRAINT range_pkey PRIMARY KEY (range_id);


--
-- Name: reservation_params_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY reservation_params
    ADD CONSTRAINT reservation_params_pkey PRIMARY KEY (id);


--
-- Name: reservation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (res_id);


--
-- Name: sdh_device_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sdh_device
    ADD CONSTRAINT sdh_device_pkey PRIMARY KEY (node_id);


--
-- Name: sdh_domain_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sdh_domain
    ADD CONSTRAINT sdh_domain_pkey PRIMARY KEY (sdh_domain_id);


--
-- Name: sdh_port_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sdh_port
    ADD CONSTRAINT sdh_port_pkey PRIMARY KEY (port_id);


--
-- Name: service_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY service
    ADD CONSTRAINT service_pkey PRIMARY KEY (srv_id);


--
-- Name: spanning_tree_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY spanning_tree
    ADD CONSTRAINT spanning_tree_pkey PRIMARY KEY (link_id, vlan_id);


--
-- Name: state_admin_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY state_admin
    ADD CONSTRAINT state_admin_pkey PRIMARY KEY (state_admin_enum);


--
-- Name: state_oper_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY state_oper
    ADD CONSTRAINT state_oper_pkey PRIMARY KEY (state_oper_enum);


--
-- Name: statistics_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY statistics
    ADD CONSTRAINT statistics_pkey PRIMARY KEY (id);


--
-- Name: stm_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY stm_link
    ADD CONSTRAINT stm_link_pkey PRIMARY KEY (stm_link_id);


--
-- Name: stm_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY stm_type
    ADD CONSTRAINT stm_type_pkey PRIMARY KEY (stm_type_id);


--
-- Name: version_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY version_info
    ADD CONSTRAINT version_info_pkey PRIMARY KEY (version_id);


--
-- Name: vlan_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vlan
    ADD CONSTRAINT vlan_pkey PRIMARY KEY (vlan_id);


--
-- Name: vlan_port_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vlan_port
    ADD CONSTRAINT vlan_port_pkey PRIMARY KEY (interface_id);


--
-- Name: vlan_tag_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vlan_tag
    ADD CONSTRAINT vlan_tag_pkey PRIMARY KEY (vlan_tag_id);


--
-- Name: vtp_domain_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vtp_domain
    ADD CONSTRAINT vtp_domain_pkey PRIMARY KEY (vtp_domain_id);


--
-- Name: fk10ab1424b06469e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY link_to_path
    ADD CONSTRAINT fk10ab1424b06469e FOREIGN KEY (link_id) REFERENCES link(link_id);


--
-- Name: fk10ab1424d14d52be; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY link_to_path
    ADD CONSTRAINT fk10ab1424d14d52be FOREIGN KEY (path_id) REFERENCES interdomain_path(path_id);


--
-- Name: fk17d3c8d5988bff1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY minval_constraint
    ADD CONSTRAINT fk17d3c8d5988bff1 FOREIGN KEY (con_id) REFERENCES network_constraint(con_id);


--
-- Name: fk2328d7ac819c8388; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7ac819c8388 FOREIGN KEY (startport) REFERENCES port(port_id);


--
-- Name: fk2328d7ac8262ff12; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7ac8262ff12 FOREIGN KEY (srv_id) REFERENCES service(srv_id);


--
-- Name: fk2328d7ac839bdbff; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7ac839bdbff FOREIGN KEY (state_oper_enum) REFERENCES state_oper(state_oper_enum);


--
-- Name: fk2328d7ac900b4caa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7ac900b4caa FOREIGN KEY (globalconstraints) REFERENCES global_constraints(gcon_id);


--
-- Name: fk2328d7aca0ba6381; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7aca0ba6381 FOREIGN KEY (endport) REFERENCES port(port_id);


--
-- Name: fk2328d7acae576e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7acae576e FOREIGN KEY (path) REFERENCES interdomain_path(path_id);


--
-- Name: fk23a7fa1c0ce5c1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY link
    ADD CONSTRAINT fk23a7fa1c0ce5c1 FOREIGN KEY (state_admin_enum) REFERENCES state_admin(state_admin_enum);


--
-- Name: fk23a7fa819c8388; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY link
    ADD CONSTRAINT fk23a7fa819c8388 FOREIGN KEY (startport) REFERENCES port(port_id);


--
-- Name: fk23a7fa839bdbff; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY link
    ADD CONSTRAINT fk23a7fa839bdbff FOREIGN KEY (state_oper_enum) REFERENCES state_oper(state_oper_enum);


--
-- Name: fk23a7faa0ba6381; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY link
    ADD CONSTRAINT fk23a7faa0ba6381 FOREIGN KEY (endport) REFERENCES port(port_id);


--
-- Name: fk23a7faeb793059; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY link
    ADD CONSTRAINT fk23a7faeb793059 FOREIGN KEY (link_type_enum) REFERENCES link_type(link_type_enum);


--
-- Name: fk248e3b143af08f99; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ho_vc_link
    ADD CONSTRAINT fk248e3b143af08f99 FOREIGN KEY (ho_vc_group_id) REFERENCES ho_vc_group(ho_vc_group_id);


--
-- Name: fk248e3b1462bf895b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ho_vc_link
    ADD CONSTRAINT fk248e3b1462bf895b FOREIGN KEY (ho_vc_type_id) REFERENCES ho_vc_type(ho_vc_type_id);


--
-- Name: fk248e3b14a93a78cc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ho_vc_link
    ADD CONSTRAINT fk248e3b14a93a78cc FOREIGN KEY (stm_link_id) REFERENCES stm_link(stm_link_id);


--
-- Name: fk259081aceb28; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY port
    ADD CONSTRAINT fk259081aceb28 FOREIGN KEY (node) REFERENCES interdomain_node(node_id);


--
-- Name: fk2989aa30bebee5b3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hasrole
    ADD CONSTRAINT fk2989aa30bebee5b3 FOREIGN KEY (linkid) REFERENCES link(link_id);


--
-- Name: fk2989aa30c2797fc3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hasrole
    ADD CONSTRAINT fk2989aa30c2797fc3 FOREIGN KEY (nodeid) REFERENCES interdomain_node(node_id);


--
-- Name: fk2d0e835e3d1567ad; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sdh_device
    ADD CONSTRAINT fk2d0e835e3d1567ad FOREIGN KEY (node_id) REFERENCES node(node_id);


--
-- Name: fk2d0e835ed4c4f622; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sdh_device
    ADD CONSTRAINT fk2d0e835ed4c4f622 FOREIGN KEY (sdh_domain_id) REFERENCES sdh_domain(sdh_domain_id);


--
-- Name: fk2d4562374019ac92; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY path_constraint
    ADD CONSTRAINT fk2d4562374019ac92 FOREIGN KEY (dcon_id) REFERENCES domain_constraints(dcon_id);


--
-- Name: fk33ae028835554d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY node
    ADD CONSTRAINT fk33ae028835554d FOREIGN KEY (location_id) REFERENCES location(location_id);


--
-- Name: fk33ae028ee852f5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY node
    ADD CONSTRAINT fk33ae028ee852f5 FOREIGN KEY (version_id) REFERENCES version_info(version_id);


--
-- Name: fk3464258ee852f5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY path
    ADD CONSTRAINT fk3464258ee852f5 FOREIGN KEY (version_id) REFERENCES version_info(version_id);


--
-- Name: fk3690c845239669d3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY och_link
    ADD CONSTRAINT fk3690c845239669d3 FOREIGN KEY (och_id) REFERENCES och(och_id);


--
-- Name: fk3690c8452404c0d8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY och_link
    ADD CONSTRAINT fk3690c8452404c0d8 FOREIGN KEY (ops_link_id) REFERENCES ops_link(ops_link_id);


--
-- Name: fk37456398f12658; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vlan
    ADD CONSTRAINT fk37456398f12658 FOREIGN KEY (vtp_domain_id) REFERENCES vtp_domain(vtp_domain_id);


--
-- Name: fk3a5c01ce71c78646; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pcon_bool_constraints
    ADD CONSTRAINT fk3a5c01ce71c78646 FOREIGN KEY (constraint_id) REFERENCES boolean_constraint(con_id);


--
-- Name: fk3a5c01ce8a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pcon_bool_constraints
    ADD CONSTRAINT fk3a5c01ce8a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fk3de1469d562fbeb7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pcons_to_intrapath
    ADD CONSTRAINT fk3de1469d562fbeb7 FOREIGN KEY (pathid) REFERENCES intradomain_path(pathid);


--
-- Name: fk3de1469d6bed35da; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pcons_to_intrapath
    ADD CONSTRAINT fk3de1469d6bed35da FOREIGN KEY (glink_map_id) REFERENCES generic_link(link_id);


--
-- Name: fk3de1469d8a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pcons_to_intrapath
    ADD CONSTRAINT fk3de1469d8a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fk41f1565644aa8e22; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY provisioning_domain
    ADD CONSTRAINT fk41f1565644aa8e22 FOREIGN KEY (admindomain) REFERENCES admin_domain(domain_id);


--
-- Name: fk4a2411da0ddfc56; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY range
    ADD CONSTRAINT fk4a2411da0ddfc56 FOREIGN KEY (con_id) REFERENCES range_constraint(con_id);


--
-- Name: fk4dfb302b8a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pcon_add_constraints
    ADD CONSTRAINT fk4dfb302b8a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fk4dfb302be2a576a2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pcon_add_constraints
    ADD CONSTRAINT fk4dfb302be2a576a2 FOREIGN KEY (constraint_id) REFERENCES additive_constraint(con_id);


--
-- Name: fk573948fd8a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pcon_add_names
    ADD CONSTRAINT fk573948fd8a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fk5b676bb38a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pcon_minval_constraints
    ADD CONSTRAINT fk5b676bb38a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fk5b676bb3d198a555; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pcon_minval_constraints
    ADD CONSTRAINT fk5b676bb3d198a555 FOREIGN KEY (constraint_id) REFERENCES minval_constraint(con_id);


--
-- Name: fk61e7a62d1b9c6e4b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY stm_link
    ADD CONSTRAINT fk61e7a62d1b9c6e4b FOREIGN KEY (stm_link_id) REFERENCES generic_link(link_id);


--
-- Name: fk61e7a62d239669d3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY stm_link
    ADD CONSTRAINT fk61e7a62d239669d3 FOREIGN KEY (och_id) REFERENCES och(och_id);


--
-- Name: fk61e7a62d6bd730cc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY stm_link
    ADD CONSTRAINT fk61e7a62d6bd730cc FOREIGN KEY (stm_type_id) REFERENCES stm_type(stm_type_id);


--
-- Name: fk69d2641b1753026; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY interdomain_node
    ADD CONSTRAINT fk69d2641b1753026 FOREIGN KEY (provisioningdomain) REFERENCES provisioning_domain(provdomain_id);


--
-- Name: fk6cf6cea52f8fa6b7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ho_vc_group
    ADD CONSTRAINT fk6cf6cea52f8fa6b7 FOREIGN KEY (ho_vc_group_id) REFERENCES generic_connection(generic_connection_id);


--
-- Name: fk6cf6cea5db66b58c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ho_vc_group
    ADD CONSTRAINT fk6cf6cea5db66b58c FOREIGN KEY (vlan_tag_id) REFERENCES vlan_tag(vlan_tag_id);


--
-- Name: fk6eaa323f5988bff1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY range_constraint
    ADD CONSTRAINT fk6eaa323f5988bff1 FOREIGN KEY (con_id) REFERENCES network_constraint(con_id);


--
-- Name: fk882e936f61e80e60; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY intradomain_reservation
    ADD CONSTRAINT fk882e936f61e80e60 FOREIGN KEY (params_id) REFERENCES reservation_params(id);


--
-- Name: fk882e936f6257222c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY intradomain_reservation
    ADD CONSTRAINT fk882e936f6257222c FOREIGN KEY (path_id) REFERENCES intradomain_path(pathid);


--
-- Name: fk8a74d0e08a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pcon_bool_names
    ADD CONSTRAINT fk8a74d0e08a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fk94860173971e13b2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bod_user
    ADD CONSTRAINT fk94860173971e13b2 FOREIGN KEY (homedomain) REFERENCES admin_domain(domain_id);


--
-- Name: fk957f5979778518db; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reservation_params
    ADD CONSTRAINT fk957f5979778518db FOREIGN KEY (pathconstraintsegress) REFERENCES path_constraint(pcon_id);


--
-- Name: fk957f5979f1a229d9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reservation_params
    ADD CONSTRAINT fk957f5979f1a229d9 FOREIGN KEY (pathconstraintsingress) REFERENCES path_constraint(pcon_id);


--
-- Name: fk984c94305988bff1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY additive_constraint
    ADD CONSTRAINT fk984c94305988bff1 FOREIGN KEY (con_id) REFERENCES network_constraint(con_id);


--
-- Name: fka10f52952a6bc364; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY service
    ADD CONSTRAINT fka10f52952a6bc364 FOREIGN KEY (bod_user) REFERENCES bod_user(name);


--
-- Name: fka5af803f21cdd1a1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY spanning_tree
    ADD CONSTRAINT fka5af803f21cdd1a1 FOREIGN KEY (vlan_id) REFERENCES vlan(vlan_id);


--
-- Name: fka5af803f7d93788c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY spanning_tree
    ADD CONSTRAINT fka5af803f7d93788c FOREIGN KEY (link_id) REFERENCES eth_link(link_id);


--
-- Name: fkab0ef6e0d9e5f55e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY eth_link
    ADD CONSTRAINT fkab0ef6e0d9e5f55e FOREIGN KEY (link_id) REFERENCES generic_link(link_id);


--
-- Name: fkae729cafd9e5f55e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mpls_link
    ADD CONSTRAINT fkae729cafd9e5f55e FOREIGN KEY (link_id) REFERENCES generic_link(link_id);


--
-- Name: fkafd9f89833fbb6e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sdh_port
    ADD CONSTRAINT fkafd9f89833fbb6e FOREIGN KEY (port_id) REFERENCES generic_interface(interface_id);


--
-- Name: fkb3aca858a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pcon_minval_names
    ADD CONSTRAINT fkb3aca858a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fkb5e938093e2d7e16; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY domainsids
    ADD CONSTRAINT fkb5e938093e2d7e16 FOREIGN KEY (gcon_id) REFERENCES global_constraints(gcon_id);


--
-- Name: fkb73f7ac3ee320bb6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY eth_physical_port
    ADD CONSTRAINT fkb73f7ac3ee320bb6 FOREIGN KEY (interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fkbecc0e468ee852f5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY generic_connection
    ADD CONSTRAINT fkbecc0e468ee852f5 FOREIGN KEY (version_id) REFERENCES version_info(version_id);


--
-- Name: fkbecc0e468fe190cd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY generic_connection
    ADD CONSTRAINT fkbecc0e468fe190cd FOREIGN KEY (path_id) REFERENCES path(path_id);


--
-- Name: fkbecc0e46d9e5f55e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY generic_connection
    ADD CONSTRAINT fkbecc0e46d9e5f55e FOREIGN KEY (link_id) REFERENCES generic_link(link_id);


--
-- Name: fkd25fa15d562fbeb7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY glink_to_intrapath
    ADD CONSTRAINT fkd25fa15d562fbeb7 FOREIGN KEY (pathid) REFERENCES intradomain_path(pathid);


--
-- Name: fkd25fa15da57f0317; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY glink_to_intrapath
    ADD CONSTRAINT fkd25fa15da57f0317 FOREIGN KEY (glink_id) REFERENCES generic_link(link_id);


--
-- Name: fkd36c82077189461b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pcon_range_constraints
    ADD CONSTRAINT fkd36c82077189461b FOREIGN KEY (constraint_id) REFERENCES range_constraint(con_id);


--
-- Name: fkd36c82078a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pcon_range_constraints
    ADD CONSTRAINT fkd36c82078a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fkd4675b1b3e2d7e16; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY domain_constraints
    ADD CONSTRAINT fkd4675b1b3e2d7e16 FOREIGN KEY (gcon_id) REFERENCES global_constraints(gcon_id);


--
-- Name: fkd776d3ddee320bb6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY eth_logical_port
    ADD CONSTRAINT fkd776d3ddee320bb6 FOREIGN KEY (interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fkd9142fd98a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pcon_range_names
    ADD CONSTRAINT fkd9142fd98a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fke38a50d13d1567ad; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY generic_interface
    ADD CONSTRAINT fke38a50d13d1567ad FOREIGN KEY (node_id) REFERENCES node(node_id);


--
-- Name: fke38a50d1576c337a; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY generic_interface
    ADD CONSTRAINT fke38a50d1576c337a FOREIGN KEY (interface_type_id) REFERENCES interface_type(interface_type_id);


--
-- Name: fke38a50d18ee852f5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY generic_interface
    ADD CONSTRAINT fke38a50d18ee852f5 FOREIGN KEY (version_id) REFERENCES version_info(version_id);


--
-- Name: fke38a50d1c59b512b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY generic_interface
    ADD CONSTRAINT fke38a50d1c59b512b FOREIGN KEY (parent_interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fkf053ed621fcc6073; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY generic_link
    ADD CONSTRAINT fkf053ed621fcc6073 FOREIGN KEY (start_interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fkf053ed624648689a; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY generic_link
    ADD CONSTRAINT fkf053ed624648689a FOREIGN KEY (end_interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fkf053ed628ee852f5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY generic_link
    ADD CONSTRAINT fkf053ed628ee852f5 FOREIGN KEY (version_id) REFERENCES version_info(version_id);


--
-- Name: fkf5ca8a745988bff1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY boolean_constraint
    ADD CONSTRAINT fkf5ca8a745988bff1 FOREIGN KEY (con_id) REFERENCES network_constraint(con_id);


--
-- Name: fkf6eaf1dd21cdd1a1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vlan_port
    ADD CONSTRAINT fkf6eaf1dd21cdd1a1 FOREIGN KEY (vlan_id) REFERENCES vlan(vlan_id);


--
-- Name: fkf6eaf1ddee320bb6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vlan_port
    ADD CONSTRAINT fkf6eaf1ddee320bb6 FOREIGN KEY (interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fkf9071967707e78d1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ops_link
    ADD CONSTRAINT fkf9071967707e78d1 FOREIGN KEY (ops_link_id) REFERENCES generic_link(link_id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--
 