--
-- PostgreSQL database dump
--

--
-- Name: additive_constraint; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE additive_constraint (
    con_id bigint NOT NULL,
    value double precision
);


ALTER TABLE public.additive_constraint OWNER TO jra3;

--
-- Name: admin_domain; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE admin_domain (
    domain_id character varying(255) NOT NULL,
    asid character varying(255),
    name character varying(255),
    clientdomain boolean,
    idcpserver character varying(255)
);


ALTER TABLE public.admin_domain OWNER TO jra3;

--
-- Name: bod_user; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE bod_user (
    name character varying(255) NOT NULL,
    email character varying(255),
    homedomain character varying(255) NOT NULL
);


ALTER TABLE public.bod_user OWNER TO jra3;

--
-- Name: boolean_constraint; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE boolean_constraint (
    con_id bigint NOT NULL,
    value boolean
);


ALTER TABLE public.boolean_constraint OWNER TO jra3;

--
-- Name: domain_constraints; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE domain_constraints (
    dcon_id bigint NOT NULL,
    gcon_id bigint,
    domain_order integer
);


ALTER TABLE public.domain_constraints OWNER TO jra3;

--
-- Name: domainsids; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE domainsids (
    gcon_id bigint NOT NULL,
    domain_id character varying(255),
    domain_order integer NOT NULL
);


ALTER TABLE public.domainsids OWNER TO jra3;

--
-- Name: eth_link; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE eth_link (
    link_id bigint NOT NULL,
    discovery_protocol character varying(255),
    is_trunk boolean,
    is_l2_bndry boolean,
    native_vlan bigint
);


ALTER TABLE public.eth_link OWNER TO jra3;

--
-- Name: eth_logical_port; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE eth_logical_port (
    interface_id bigint NOT NULL
);


ALTER TABLE public.eth_logical_port OWNER TO jra3;

--
-- Name: eth_physical_port; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE eth_physical_port (
    interface_id bigint NOT NULL,
    interface_name character varying(255),
    mac_address character varying(255),
    duplex character varying(255),
    medium_dependent_interface character varying(255),
    is_tagged boolean
);


ALTER TABLE public.eth_physical_port OWNER TO jra3;

--
-- Name: generic_connection; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
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


ALTER TABLE public.generic_connection OWNER TO jra3;

--
-- Name: generic_interface; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
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
    mtu character varying(255),
    domain_id character varying(255),
    client_port boolean
);


ALTER TABLE public.generic_interface OWNER TO jra3;

--
-- Name: generic_link; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
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


ALTER TABLE public.generic_link OWNER TO jra3;

--
-- Name: glink_to_intrapath; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE glink_to_intrapath (
    pathid bigint NOT NULL,
    glink_id bigint NOT NULL,
    link_order integer NOT NULL
);


ALTER TABLE public.glink_to_intrapath OWNER TO jra3;

--
-- Name: global_constraints; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE global_constraints (
    gcon_id bigint NOT NULL
);


ALTER TABLE public.global_constraints OWNER TO jra3;

--
-- Name: hasrole; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE hasrole (
    nodeid character varying(255) NOT NULL,
    linkid character varying(255) NOT NULL,
    isdemarc boolean
);


ALTER TABLE public.hasrole OWNER TO jra3;

--
-- Name: ho_vc_group; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE ho_vc_group (
    ho_vc_group_id bigint NOT NULL,
    vlan_tag_id bigint,
    name character varying(255)
);


ALTER TABLE public.ho_vc_group OWNER TO jra3;

--
-- Name: ho_vc_link; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
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


ALTER TABLE public.ho_vc_link OWNER TO jra3;

--
-- Name: ho_vc_type; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE ho_vc_type (
    ho_vc_type_id bigint NOT NULL,
    name character varying(255),
    bandwidth bigint,
    payload bigint
);


ALTER TABLE public.ho_vc_type OWNER TO jra3;

--
-- Name: interdomain_node; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
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


ALTER TABLE public.interdomain_node OWNER TO jra3;

--
-- Name: interdomain_path; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE interdomain_path (
    path_id bigint NOT NULL,
    monetary_cost double precision,
    manual_cost double precision
);


ALTER TABLE public.interdomain_path OWNER TO jra3;

--
-- Name: interface_type; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE interface_type (
    interface_type_id bigint NOT NULL,
    switching_type character varying(255),
    data_encoding_type character varying(255)
);


ALTER TABLE public.interface_type OWNER TO jra3;

--
-- Name: intradomain_path; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE intradomain_path (
    pathid bigint NOT NULL,
    capacity bigint
);


ALTER TABLE public.intradomain_path OWNER TO jra3;

--
-- Name: intradomain_reservation; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE intradomain_reservation (
    reservationid character varying(255) NOT NULL,
    pathcreated boolean,
    active boolean,
    path_id bigint,
    params_id bigint
);


ALTER TABLE public.intradomain_reservation OWNER TO jra3;

--
-- Name: link; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
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


ALTER TABLE public.link OWNER TO jra3;

--
-- Name: link_to_path; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE link_to_path (
    path_id bigint NOT NULL,
    link_id character varying(255) NOT NULL,
    link_order integer NOT NULL
);


ALTER TABLE public.link_to_path OWNER TO jra3;

--
-- Name: link_type; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE link_type (
    link_type_enum integer NOT NULL
);


ALTER TABLE public.link_type OWNER TO jra3;

--
-- Name: location; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
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


ALTER TABLE public.location OWNER TO jra3;

--
-- Name: minval_constraint; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE minval_constraint (
    con_id bigint NOT NULL,
    value double precision
);


ALTER TABLE public.minval_constraint OWNER TO jra3;

--
-- Name: network_constraint; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE network_constraint (
    con_id bigint NOT NULL
);


ALTER TABLE public.network_constraint OWNER TO jra3;

--
-- Name: node; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
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


ALTER TABLE public.node OWNER TO jra3;

--
-- Name: och; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE och (
    och_id bigint NOT NULL,
    payload character varying(255),
    status character varying(255)
);


ALTER TABLE public.och OWNER TO jra3;

--
-- Name: och_link; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE och_link (
    och_link_id bigint NOT NULL,
    ops_link_id bigint,
    och_id bigint,
    frequency double precision,
    status character varying(255)
);


ALTER TABLE public.och_link OWNER TO jra3;

--
-- Name: ops_link; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE ops_link (
    ops_link_id bigint NOT NULL,
    max_no_lambdas bigint,
    bitrate bigint,
    status character varying(255)
);


ALTER TABLE public.ops_link OWNER TO jra3;

--
-- Name: path; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE path (
    path_id bigint NOT NULL,
    version_id bigint,
    name character varying(255),
    status character varying(255)
);


ALTER TABLE public.path OWNER TO jra3;

--
-- Name: path_constraint; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE path_constraint (
    pcon_id bigint NOT NULL,
    dcon_id bigint,
    path_order integer
);


ALTER TABLE public.path_constraint OWNER TO jra3;

--
-- Name: pcon_add_constraints; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE pcon_add_constraints (
    pcon_id bigint NOT NULL,
    constraint_id bigint NOT NULL,
    constraint_order integer NOT NULL
);


ALTER TABLE public.pcon_add_constraints OWNER TO jra3;

--
-- Name: pcon_add_names; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE pcon_add_names (
    pcon_id bigint NOT NULL,
    name smallint,
    constraint_order integer NOT NULL
);


ALTER TABLE public.pcon_add_names OWNER TO jra3;

--
-- Name: pcon_bool_constraints; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE pcon_bool_constraints (
    pcon_id bigint NOT NULL,
    constraint_id bigint NOT NULL,
    constraint_order integer NOT NULL
);


ALTER TABLE public.pcon_bool_constraints OWNER TO jra3;

--
-- Name: pcon_bool_names; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE pcon_bool_names (
    pcon_id bigint NOT NULL,
    name smallint,
    constraint_order integer NOT NULL
);


ALTER TABLE public.pcon_bool_names OWNER TO jra3;

--
-- Name: pcon_minval_constraints; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE pcon_minval_constraints (
    pcon_id bigint NOT NULL,
    constraint_id bigint NOT NULL,
    constraint_order integer NOT NULL
);


ALTER TABLE public.pcon_minval_constraints OWNER TO jra3;

--
-- Name: pcon_minval_names; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE pcon_minval_names (
    pcon_id bigint NOT NULL,
    name smallint,
    constraint_order integer NOT NULL
);


ALTER TABLE public.pcon_minval_names OWNER TO jra3;

--
-- Name: pcon_range_constraints; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE pcon_range_constraints (
    pcon_id bigint NOT NULL,
    constraint_id bigint NOT NULL,
    constraint_order integer NOT NULL
);


ALTER TABLE public.pcon_range_constraints OWNER TO jra3;

--
-- Name: pcon_range_names; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE pcon_range_names (
    pcon_id bigint NOT NULL,
    name smallint,
    constraint_order integer NOT NULL
);


ALTER TABLE public.pcon_range_names OWNER TO jra3;

--
-- Name: port; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE port (
    port_id character varying(255) NOT NULL,
    address character varying(255),
    technology character varying(255),
    bundled boolean,
    node character varying(255) NOT NULL
);


ALTER TABLE public.port OWNER TO jra3;

--
-- Name: provisioning_domain; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE provisioning_domain (
    provdomain_id character varying(255) NOT NULL,
    provtype character varying(255),
    admindomain character varying(255) NOT NULL
);


ALTER TABLE public.provisioning_domain OWNER TO jra3;

--
-- Name: range; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE range (
    range_id bigint NOT NULL,
    min integer,
    max integer,
    con_id bigint NOT NULL,
    link_order integer
);


ALTER TABLE public.range OWNER TO jra3;

--
-- Name: range_constraint; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE range_constraint (
    con_id bigint NOT NULL
);


ALTER TABLE public.range_constraint OWNER TO jra3;

--
-- Name: reservation; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
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
    state_oper_enum integer,
    globalconstraints bigint,
    path bigint,
    srv_id character varying(255),
    res_index integer
);


ALTER TABLE public.reservation OWNER TO jra3;

--
-- Name: reservation_params; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE reservation_params (
    id bigint NOT NULL,
    capacity bigint,
    maxdelay integer,
    resiliency character varying(255),
    bidirectional boolean,
    starttime timestamp without time zone,
    endtime timestamp without time zone,
    pathconstraints bigint NOT NULL
);


ALTER TABLE public.reservation_params OWNER TO jra3;

--
-- Name: sdh_device; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE sdh_device (
    node_id bigint NOT NULL,
    sdh_domain_id bigint,
    name character varying(255),
    nsap bigint
);


ALTER TABLE public.sdh_device OWNER TO jra3;

--
-- Name: sdh_domain; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE sdh_domain (
    sdh_domain_id bigint NOT NULL,
    name character varying(255),
    provmethod character varying(255),
    equipment_provider character varying(255),
    date_modified date
);


ALTER TABLE public.sdh_domain OWNER TO jra3;

--
-- Name: sdh_port; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE sdh_port (
    port_id bigint NOT NULL,
    address character varying(255),
    phy_port_type character varying(255)
);


ALTER TABLE public.sdh_port OWNER TO jra3;

--
-- Name: service; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE service (
    srv_id character varying(255) NOT NULL,
    justification character varying(255),
    priority integer,
    bod_user character varying(255) NOT NULL
);


ALTER TABLE public.service OWNER TO jra3;

--
-- Name: spanning_tree; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE spanning_tree (
    link_id bigint NOT NULL,
    vlan_id bigint NOT NULL,
    state character varying(255),
    cost bigint
);


ALTER TABLE public.spanning_tree OWNER TO jra3;

--
-- Name: state_admin; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE state_admin (
    state_admin_enum integer NOT NULL
);


ALTER TABLE public.state_admin OWNER TO jra3;

--
-- Name: state_oper; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE state_oper (
    state_oper_enum integer NOT NULL
);


ALTER TABLE public.state_oper OWNER TO jra3;

--
-- Name: stm_link; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE stm_link (
    stm_link_id bigint NOT NULL,
    och_id bigint,
    stm_type_id bigint,
    status character varying(255)
);


ALTER TABLE public.stm_link OWNER TO jra3;

--
-- Name: stm_type; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE stm_type (
    stm_type_id bigint NOT NULL,
    name character varying(255),
    bandwidth bigint
);


ALTER TABLE public.stm_type OWNER TO jra3;

--
-- Name: version_info; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
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


ALTER TABLE public.version_info OWNER TO jra3;

--
-- Name: vlan; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE vlan (
    vlan_id bigint NOT NULL,
    vtp_domain_id bigint,
    name character varying(255),
    low_number bigint,
    high_number bigint
);


ALTER TABLE public.vlan OWNER TO jra3;

--
-- Name: vlan_port; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE vlan_port (
    interface_id bigint NOT NULL,
    vlan_id bigint
);


ALTER TABLE public.vlan_port OWNER TO jra3;

--
-- Name: vlan_tag; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE vlan_tag (
    vlan_tag_id bigint NOT NULL,
    payload character varying(255),
    date_modified date
);


ALTER TABLE public.vlan_tag OWNER TO jra3;

--
-- Name: vtp_domain; Type: TABLE; Schema: public; Owner: jra3; Tablespace: 
--

CREATE TABLE vtp_domain (
    vtp_domain_id bigint NOT NULL,
    name character varying(255),
    srv_ipv4_addr character varying(255)
);


ALTER TABLE public.vtp_domain OWNER TO jra3;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: jra3
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO jra3;

--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: jra3
--

SELECT pg_catalog.setval('hibernate_sequence', 1, false);


--
-- Data for Name: additive_constraint; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: admin_domain; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: bod_user; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: boolean_constraint; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: domain_constraints; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: domainsids; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: eth_link; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: eth_logical_port; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: eth_physical_port; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: generic_connection; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: generic_interface; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: generic_link; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: glink_to_intrapath; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: global_constraints; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: hasrole; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: ho_vc_group; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: ho_vc_link; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: ho_vc_type; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: interdomain_node; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: interdomain_path; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: interface_type; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: intradomain_path; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: intradomain_reservation; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: link; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: link_to_path; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: link_type; Type: TABLE DATA; Schema: public; Owner: jra3
--

INSERT INTO link_type VALUES (1);
INSERT INTO link_type VALUES (2);
INSERT INTO link_type VALUES (3);


--
-- Data for Name: location; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: minval_constraint; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: network_constraint; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: node; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: och; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: och_link; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: ops_link; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: path; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: path_constraint; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: pcon_add_constraints; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: pcon_add_names; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: pcon_bool_constraints; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: pcon_bool_names; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: pcon_minval_constraints; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: pcon_minval_names; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: pcon_range_constraints; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: pcon_range_names; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: port; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: provisioning_domain; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: range; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: range_constraint; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: reservation; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: reservation_params; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: sdh_device; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: sdh_domain; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: sdh_port; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: service; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: spanning_tree; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: state_admin; Type: TABLE DATA; Schema: public; Owner: jra3
--

INSERT INTO state_admin VALUES (0);
INSERT INTO state_admin VALUES (1);
INSERT INTO state_admin VALUES (2);


--
-- Data for Name: state_oper; Type: TABLE DATA; Schema: public; Owner: jra3
--

INSERT INTO state_oper VALUES (0);
INSERT INTO state_oper VALUES (1);
INSERT INTO state_oper VALUES (3);


--
-- Data for Name: stm_link; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: stm_type; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: version_info; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: vlan; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: vlan_port; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: vlan_tag; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Data for Name: vtp_domain; Type: TABLE DATA; Schema: public; Owner: jra3
--



--
-- Name: additive_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY additive_constraint
    ADD CONSTRAINT additive_constraint_pkey PRIMARY KEY (con_id);


--
-- Name: admin_domain_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY admin_domain
    ADD CONSTRAINT admin_domain_pkey PRIMARY KEY (domain_id);


--
-- Name: bod_user_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY bod_user
    ADD CONSTRAINT bod_user_pkey PRIMARY KEY (name);


--
-- Name: boolean_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY boolean_constraint
    ADD CONSTRAINT boolean_constraint_pkey PRIMARY KEY (con_id);


--
-- Name: domain_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY domain_constraints
    ADD CONSTRAINT domain_constraints_pkey PRIMARY KEY (dcon_id);


--
-- Name: domainsids_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY domainsids
    ADD CONSTRAINT domainsids_pkey PRIMARY KEY (gcon_id, domain_order);


--
-- Name: eth_link_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY eth_link
    ADD CONSTRAINT eth_link_pkey PRIMARY KEY (link_id);


--
-- Name: eth_logical_port_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY eth_logical_port
    ADD CONSTRAINT eth_logical_port_pkey PRIMARY KEY (interface_id);


--
-- Name: eth_physical_port_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY eth_physical_port
    ADD CONSTRAINT eth_physical_port_pkey PRIMARY KEY (interface_id);


--
-- Name: generic_connection_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY generic_connection
    ADD CONSTRAINT generic_connection_pkey PRIMARY KEY (generic_connection_id);


--
-- Name: generic_interface_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY generic_interface
    ADD CONSTRAINT generic_interface_pkey PRIMARY KEY (interface_id);


--
-- Name: generic_link_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY generic_link
    ADD CONSTRAINT generic_link_pkey PRIMARY KEY (link_id);


--
-- Name: glink_to_intrapath_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY glink_to_intrapath
    ADD CONSTRAINT glink_to_intrapath_pkey PRIMARY KEY (pathid, link_order);


--
-- Name: global_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY global_constraints
    ADD CONSTRAINT global_constraints_pkey PRIMARY KEY (gcon_id);


--
-- Name: hasrole_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY hasrole
    ADD CONSTRAINT hasrole_pkey PRIMARY KEY (nodeid, linkid);


--
-- Name: ho_vc_group_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY ho_vc_group
    ADD CONSTRAINT ho_vc_group_pkey PRIMARY KEY (ho_vc_group_id);


--
-- Name: ho_vc_link_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY ho_vc_link
    ADD CONSTRAINT ho_vc_link_pkey PRIMARY KEY (ho_vc_link_id);


--
-- Name: ho_vc_type_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY ho_vc_type
    ADD CONSTRAINT ho_vc_type_pkey PRIMARY KEY (ho_vc_type_id);


--
-- Name: interdomain_node_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY interdomain_node
    ADD CONSTRAINT interdomain_node_pkey PRIMARY KEY (node_id);


--
-- Name: interdomain_path_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY interdomain_path
    ADD CONSTRAINT interdomain_path_pkey PRIMARY KEY (path_id);


--
-- Name: interface_type_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY interface_type
    ADD CONSTRAINT interface_type_pkey PRIMARY KEY (interface_type_id);


--
-- Name: intradomain_path_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY intradomain_path
    ADD CONSTRAINT intradomain_path_pkey PRIMARY KEY (pathid);


--
-- Name: intradomain_reservation_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY intradomain_reservation
    ADD CONSTRAINT intradomain_reservation_pkey PRIMARY KEY (reservationid);


--
-- Name: link_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY link
    ADD CONSTRAINT link_pkey PRIMARY KEY (link_id);


--
-- Name: link_to_path_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY link_to_path
    ADD CONSTRAINT link_to_path_pkey PRIMARY KEY (path_id, link_order);


--
-- Name: link_type_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY link_type
    ADD CONSTRAINT link_type_pkey PRIMARY KEY (link_type_enum);


--
-- Name: location_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY location
    ADD CONSTRAINT location_pkey PRIMARY KEY (location_id);


--
-- Name: minval_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY minval_constraint
    ADD CONSTRAINT minval_constraint_pkey PRIMARY KEY (con_id);


--
-- Name: network_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY network_constraint
    ADD CONSTRAINT network_constraint_pkey PRIMARY KEY (con_id);


--
-- Name: node_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY node
    ADD CONSTRAINT node_pkey PRIMARY KEY (node_id);


--
-- Name: och_link_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY och_link
    ADD CONSTRAINT och_link_pkey PRIMARY KEY (och_link_id);


--
-- Name: och_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY och
    ADD CONSTRAINT och_pkey PRIMARY KEY (och_id);


--
-- Name: ops_link_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY ops_link
    ADD CONSTRAINT ops_link_pkey PRIMARY KEY (ops_link_id);


--
-- Name: path_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY path_constraint
    ADD CONSTRAINT path_constraint_pkey PRIMARY KEY (pcon_id);


--
-- Name: path_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY path
    ADD CONSTRAINT path_pkey PRIMARY KEY (path_id);


--
-- Name: pcon_add_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY pcon_add_constraints
    ADD CONSTRAINT pcon_add_constraints_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_add_names_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY pcon_add_names
    ADD CONSTRAINT pcon_add_names_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_bool_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY pcon_bool_constraints
    ADD CONSTRAINT pcon_bool_constraints_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_bool_names_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY pcon_bool_names
    ADD CONSTRAINT pcon_bool_names_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_minval_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY pcon_minval_constraints
    ADD CONSTRAINT pcon_minval_constraints_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_minval_names_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY pcon_minval_names
    ADD CONSTRAINT pcon_minval_names_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_range_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY pcon_range_constraints
    ADD CONSTRAINT pcon_range_constraints_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_range_names_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY pcon_range_names
    ADD CONSTRAINT pcon_range_names_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: port_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY port
    ADD CONSTRAINT port_pkey PRIMARY KEY (port_id);


--
-- Name: provisioning_domain_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY provisioning_domain
    ADD CONSTRAINT provisioning_domain_pkey PRIMARY KEY (provdomain_id);


--
-- Name: range_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY range_constraint
    ADD CONSTRAINT range_constraint_pkey PRIMARY KEY (con_id);


--
-- Name: range_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY range
    ADD CONSTRAINT range_pkey PRIMARY KEY (range_id);


--
-- Name: reservation_params_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY reservation_params
    ADD CONSTRAINT reservation_params_pkey PRIMARY KEY (id);


--
-- Name: reservation_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (res_id);


--
-- Name: sdh_device_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY sdh_device
    ADD CONSTRAINT sdh_device_pkey PRIMARY KEY (node_id);


--
-- Name: sdh_domain_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY sdh_domain
    ADD CONSTRAINT sdh_domain_pkey PRIMARY KEY (sdh_domain_id);


--
-- Name: sdh_port_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY sdh_port
    ADD CONSTRAINT sdh_port_pkey PRIMARY KEY (port_id);


--
-- Name: service_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY service
    ADD CONSTRAINT service_pkey PRIMARY KEY (srv_id);


--
-- Name: spanning_tree_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY spanning_tree
    ADD CONSTRAINT spanning_tree_pkey PRIMARY KEY (link_id, vlan_id);


--
-- Name: state_admin_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY state_admin
    ADD CONSTRAINT state_admin_pkey PRIMARY KEY (state_admin_enum);


--
-- Name: state_oper_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY state_oper
    ADD CONSTRAINT state_oper_pkey PRIMARY KEY (state_oper_enum);


--
-- Name: stm_link_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY stm_link
    ADD CONSTRAINT stm_link_pkey PRIMARY KEY (stm_link_id);


--
-- Name: stm_type_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY stm_type
    ADD CONSTRAINT stm_type_pkey PRIMARY KEY (stm_type_id);


--
-- Name: version_info_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY version_info
    ADD CONSTRAINT version_info_pkey PRIMARY KEY (version_id);


--
-- Name: vlan_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY vlan
    ADD CONSTRAINT vlan_pkey PRIMARY KEY (vlan_id);


--
-- Name: vlan_port_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY vlan_port
    ADD CONSTRAINT vlan_port_pkey PRIMARY KEY (interface_id);


--
-- Name: vlan_tag_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY vlan_tag
    ADD CONSTRAINT vlan_tag_pkey PRIMARY KEY (vlan_tag_id);


--
-- Name: vtp_domain_pkey; Type: CONSTRAINT; Schema: public; Owner: jra3; Tablespace: 
--

ALTER TABLE ONLY vtp_domain
    ADD CONSTRAINT vtp_domain_pkey PRIMARY KEY (vtp_domain_id);


--
-- Name: fk10ab1424ba6ddf3a; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY link_to_path
    ADD CONSTRAINT fk10ab1424ba6ddf3a FOREIGN KEY (path_id) REFERENCES interdomain_path(path_id);


--
-- Name: fk10ab1424f426d31a; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY link_to_path
    ADD CONSTRAINT fk10ab1424f426d31a FOREIGN KEY (link_id) REFERENCES link(link_id);


--
-- Name: fk17d3c8d8b30e16d; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY minval_constraint
    ADD CONSTRAINT fk17d3c8d8b30e16d FOREIGN KEY (con_id) REFERENCES network_constraint(con_id);


--
-- Name: fk2328d7ac6abd1004; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7ac6abd1004 FOREIGN KEY (startport) REFERENCES port(port_id);


--
-- Name: fk2328d7ac88bbd03; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7ac88bbd03 FOREIGN KEY (state_oper_enum) REFERENCES state_oper(state_oper_enum);


--
-- Name: fk2328d7ac89daeffd; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7ac89daeffd FOREIGN KEY (endport) REFERENCES port(port_id);


--
-- Name: fk2328d7ac8adeaf16; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7ac8adeaf16 FOREIGN KEY (srv_id) REFERENCES service(srv_id);


--
-- Name: fk2328d7ac93dc7ae; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7ac93dc7ae FOREIGN KEY (globalconstraints) REFERENCES global_constraints(gcon_id);


--
-- Name: fk2328d7ace9cee3ea; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7ace9cee3ea FOREIGN KEY (path) REFERENCES interdomain_path(path_id);


--
-- Name: fk23a7fa3519253d; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY link
    ADD CONSTRAINT fk23a7fa3519253d FOREIGN KEY (state_admin_enum) REFERENCES state_admin(state_admin_enum);


--
-- Name: fk23a7fa471ed5; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY link
    ADD CONSTRAINT fk23a7fa471ed5 FOREIGN KEY (link_type_enum) REFERENCES link_type(link_type_enum);


--
-- Name: fk23a7fa6abd1004; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY link
    ADD CONSTRAINT fk23a7fa6abd1004 FOREIGN KEY (startport) REFERENCES port(port_id);


--
-- Name: fk23a7fa88bbd03; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY link
    ADD CONSTRAINT fk23a7fa88bbd03 FOREIGN KEY (state_oper_enum) REFERENCES state_oper(state_oper_enum);


--
-- Name: fk23a7fa89daeffd; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY link
    ADD CONSTRAINT fk23a7fa89daeffd FOREIGN KEY (endport) REFERENCES port(port_id);


--
-- Name: fk248e3b14ac9686d0; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY ho_vc_link
    ADD CONSTRAINT fk248e3b14ac9686d0 FOREIGN KEY (stm_link_id) REFERENCES stm_link(stm_link_id);


--
-- Name: fk248e3b14cae53bd7; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY ho_vc_link
    ADD CONSTRAINT fk248e3b14cae53bd7 FOREIGN KEY (ho_vc_type_id) REFERENCES ho_vc_type(ho_vc_type_id);


--
-- Name: fk248e3b14d7812c9d; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY ho_vc_link
    ADD CONSTRAINT fk248e3b14d7812c9d FOREIGN KEY (ho_vc_group_id) REFERENCES ho_vc_group(ho_vc_group_id);


--
-- Name: fk259081e9cd77a4; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY port
    ADD CONSTRAINT fk259081e9cd77a4 FOREIGN KEY (node) REFERENCES interdomain_node(node_id);


--
-- Name: fk2989aa30a7df722f; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY hasrole
    ADD CONSTRAINT fk2989aa30a7df722f FOREIGN KEY (linkid) REFERENCES link(link_id);


--
-- Name: fk2989aa30ab9a0c3f; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY hasrole
    ADD CONSTRAINT fk2989aa30ab9a0c3f FOREIGN KEY (nodeid) REFERENCES interdomain_node(node_id);


--
-- Name: fk2d0e835e407175b1; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY sdh_device
    ADD CONSTRAINT fk2d0e835e407175b1 FOREIGN KEY (node_id) REFERENCES node(node_id);


--
-- Name: fk2d0e835e71559326; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY sdh_device
    ADD CONSTRAINT fk2d0e835e71559326 FOREIGN KEY (sdh_domain_id) REFERENCES sdh_domain(sdh_domain_id);


--
-- Name: fk2d456237b94c2796; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY path_constraint
    ADD CONSTRAINT fk2d456237b94c2796 FOREIGN KEY (dcon_id) REFERENCES domain_constraints(dcon_id);


--
-- Name: fk33ae023c053871; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY node
    ADD CONSTRAINT fk33ae023c053871 FOREIGN KEY (version_id) REFERENCES version_info(version_id);


--
-- Name: fk33ae024312c151; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY node
    ADD CONSTRAINT fk33ae024312c151 FOREIGN KEY (location_id) REFERENCES location(location_id);


--
-- Name: fk3464253c053871; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY path
    ADD CONSTRAINT fk3464253c053871 FOREIGN KEY (version_id) REFERENCES version_info(version_id);


--
-- Name: fk3690c8452760cedc; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY och_link
    ADD CONSTRAINT fk3690c8452760cedc FOREIGN KEY (ops_link_id) REFERENCES ops_link(ops_link_id);


--
-- Name: fk3690c8452c1219d7; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY och_link
    ADD CONSTRAINT fk3690c8452c1219d7 FOREIGN KEY (och_id) REFERENCES och(och_id);


--
-- Name: fk374563460e0bd4; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY vlan
    ADD CONSTRAINT fk374563460e0bd4 FOREIGN KEY (vtp_domain_id) REFERENCES vtp_domain(vtp_domain_id);


--
-- Name: fk3a5c01ce457422a1; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY pcon_bool_constraints
    ADD CONSTRAINT fk3a5c01ce457422a1 FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fk3a5c01ceeafa014a; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY pcon_bool_constraints
    ADD CONSTRAINT fk3a5c01ceeafa014a FOREIGN KEY (constraint_id) REFERENCES boolean_constraint(con_id);


--
-- Name: fk41f156564d263e26; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY provisioning_domain
    ADD CONSTRAINT fk41f156564d263e26 FOREIGN KEY (admindomain) REFERENCES admin_domain(domain_id);


--
-- Name: fk4a2411d5bbb685a; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY range
    ADD CONSTRAINT fk4a2411d5bbb685a FOREIGN KEY (con_id) REFERENCES range_constraint(con_id);


--
-- Name: fk4dfb302b457422a1; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY pcon_add_constraints
    ADD CONSTRAINT fk4dfb302b457422a1 FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fk4dfb302b8fc25c1e; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY pcon_add_constraints
    ADD CONSTRAINT fk4dfb302b8fc25c1e FOREIGN KEY (constraint_id) REFERENCES additive_constraint(con_id);


--
-- Name: fk573948fd457422a1; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY pcon_add_names
    ADD CONSTRAINT fk573948fd457422a1 FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fk5b676bb3457422a1; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY pcon_minval_constraints
    ADD CONSTRAINT fk5b676bb3457422a1 FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fk5b676bb37eb58ad1; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY pcon_minval_constraints
    ADD CONSTRAINT fk5b676bb37eb58ad1 FOREIGN KEY (constraint_id) REFERENCES minval_constraint(con_id);


--
-- Name: fk61e7a62d2c1219d7; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY stm_link
    ADD CONSTRAINT fk61e7a62d2c1219d7 FOREIGN KEY (och_id) REFERENCES och(och_id);


--
-- Name: fk61e7a62d6f333ed0; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY stm_link
    ADD CONSTRAINT fk61e7a62d6f333ed0 FOREIGN KEY (stm_type_id) REFERENCES stm_type(stm_type_id);


--
-- Name: fk61e7a62dc8b953c7; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY stm_link
    ADD CONSTRAINT fk61e7a62dc8b953c7 FOREIGN KEY (stm_link_id) REFERENCES generic_link(link_id);


--
-- Name: fk69d2641a6f833a2; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY interdomain_node
    ADD CONSTRAINT fk69d2641a6f833a2 FOREIGN KEY (provisioningdomain) REFERENCES provisioning_domain(provdomain_id);


--
-- Name: fk6cf6cea52a317f33; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY ho_vc_group
    ADD CONSTRAINT fk6cf6cea52a317f33 FOREIGN KEY (ho_vc_group_id) REFERENCES generic_connection(generic_connection_id);


--
-- Name: fk6cf6cea5dec2c390; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY ho_vc_group
    ADD CONSTRAINT fk6cf6cea5dec2c390 FOREIGN KEY (vlan_tag_id) REFERENCES vlan_tag(vlan_tag_id);


--
-- Name: fk6eaa323f8b30e16d; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY range_constraint
    ADD CONSTRAINT fk6eaa323f8b30e16d FOREIGN KEY (con_id) REFERENCES network_constraint(con_id);


--
-- Name: fk882e936f1d348e30; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY intradomain_reservation
    ADD CONSTRAINT fk882e936f1d348e30 FOREIGN KEY (path_id) REFERENCES intradomain_path(pathid);


--
-- Name: fk882e936fdb1a8964; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY intradomain_reservation
    ADD CONSTRAINT fk882e936fdb1a8964 FOREIGN KEY (params_id) REFERENCES reservation_params(id);


--
-- Name: fk8a74d0e0457422a1; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY pcon_bool_names
    ADD CONSTRAINT fk8a74d0e0457422a1 FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fk948601739f99c3b6; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY bod_user
    ADD CONSTRAINT fk948601739f99c3b6 FOREIGN KEY (homedomain) REFERENCES admin_domain(domain_id);


--
-- Name: fk957f59792a48762a; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY reservation_params
    ADD CONSTRAINT fk957f59792a48762a FOREIGN KEY (pathconstraints) REFERENCES path_constraint(pcon_id);


--
-- Name: fk984c94308b30e16d; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY additive_constraint
    ADD CONSTRAINT fk984c94308b30e16d FOREIGN KEY (con_id) REFERENCES network_constraint(con_id);


--
-- Name: fka10f52953f39b1e0; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY service
    ADD CONSTRAINT fka10f52953f39b1e0 FOREIGN KEY (bod_user) REFERENCES bod_user(name);


--
-- Name: fka5af803f1e638d08; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY spanning_tree
    ADD CONSTRAINT fka5af803f1e638d08 FOREIGN KEY (link_id) REFERENCES eth_link(link_id);


--
-- Name: fka5af803fbe5e6ea5; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY spanning_tree
    ADD CONSTRAINT fka5af803fbe5e6ea5 FOREIGN KEY (vlan_id) REFERENCES vlan(vlan_id);


--
-- Name: fkab0ef6e08702dada; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY eth_link
    ADD CONSTRAINT fkab0ef6e08702dada FOREIGN KEY (link_id) REFERENCES generic_link(link_id);


--
-- Name: fkafd9f894102e372; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY sdh_port
    ADD CONSTRAINT fkafd9f894102e372 FOREIGN KEY (port_id) REFERENCES generic_interface(interface_id);


--
-- Name: fkb3aca85457422a1; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY pcon_minval_names
    ADD CONSTRAINT fkb3aca85457422a1 FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fkb5e93809b75ff91a; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY domainsids
    ADD CONSTRAINT fkb5e93809b75ff91a FOREIGN KEY (gcon_id) REFERENCES global_constraints(gcon_id);


--
-- Name: fkb73f7ac3abf533ba; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY eth_physical_port
    ADD CONSTRAINT fkb73f7ac3abf533ba FOREIGN KEY (interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fkbecc0e463c053871; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY generic_connection
    ADD CONSTRAINT fkbecc0e463c053871 FOREIGN KEY (version_id) REFERENCES version_info(version_id);


--
-- Name: fkbecc0e468702dada; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY generic_connection
    ADD CONSTRAINT fkbecc0e468702dada FOREIGN KEY (link_id) REFERENCES generic_link(link_id);


--
-- Name: fkbecc0e46933d9ed1; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY generic_connection
    ADD CONSTRAINT fkbecc0e46933d9ed1 FOREIGN KEY (path_id) REFERENCES path(path_id);


--
-- Name: fkd25fa15d110d2abb; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY glink_to_intrapath
    ADD CONSTRAINT fkd25fa15d110d2abb FOREIGN KEY (pathid) REFERENCES intradomain_path(pathid);


--
-- Name: fkd25fa15d529be893; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY glink_to_intrapath
    ADD CONSTRAINT fkd25fa15d529be893 FOREIGN KEY (glink_id) REFERENCES generic_link(link_id);


--
-- Name: fkd36c82072c66b21f; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY pcon_range_constraints
    ADD CONSTRAINT fkd36c82072c66b21f FOREIGN KEY (constraint_id) REFERENCES range_constraint(con_id);


--
-- Name: fkd36c8207457422a1; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY pcon_range_constraints
    ADD CONSTRAINT fkd36c8207457422a1 FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fkd4675b1bb75ff91a; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY domain_constraints
    ADD CONSTRAINT fkd4675b1bb75ff91a FOREIGN KEY (gcon_id) REFERENCES global_constraints(gcon_id);


--
-- Name: fkd776d3ddabf533ba; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY eth_logical_port
    ADD CONSTRAINT fkd776d3ddabf533ba FOREIGN KEY (interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fkd9142fd9457422a1; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY pcon_range_names
    ADD CONSTRAINT fkd9142fd9457422a1 FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fke38a50d130e5a9f6; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY generic_interface
    ADD CONSTRAINT fke38a50d130e5a9f6 FOREIGN KEY (interface_type_id) REFERENCES interface_type(interface_type_id);


--
-- Name: fke38a50d13c053871; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY generic_interface
    ADD CONSTRAINT fke38a50d13c053871 FOREIGN KEY (version_id) REFERENCES version_info(version_id);


--
-- Name: fke38a50d1407175b1; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY generic_interface
    ADD CONSTRAINT fke38a50d1407175b1 FOREIGN KEY (node_id) REFERENCES node(node_id);


--
-- Name: fke38a50d1835e792f; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY generic_interface
    ADD CONSTRAINT fke38a50d1835e792f FOREIGN KEY (parent_interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fkf053ed623c053871; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY generic_link
    ADD CONSTRAINT fkf053ed623c053871 FOREIGN KEY (version_id) REFERENCES version_info(version_id);


--
-- Name: fkf053ed6240b909e; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY generic_link
    ADD CONSTRAINT fkf053ed6240b909e FOREIGN KEY (end_interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fkf053ed62dd8f8877; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY generic_link
    ADD CONSTRAINT fkf053ed62dd8f8877 FOREIGN KEY (start_interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fkf5ca8a748b30e16d; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY boolean_constraint
    ADD CONSTRAINT fkf5ca8a748b30e16d FOREIGN KEY (con_id) REFERENCES network_constraint(con_id);


--
-- Name: fkf6eaf1ddabf533ba; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY vlan_port
    ADD CONSTRAINT fkf6eaf1ddabf533ba FOREIGN KEY (interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fkf6eaf1ddbe5e6ea5; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY vlan_port
    ADD CONSTRAINT fkf6eaf1ddbe5e6ea5 FOREIGN KEY (vlan_id) REFERENCES vlan(vlan_id);


--
-- Name: fkf90719671d9b5e4d; Type: FK CONSTRAINT; Schema: public; Owner: jra3
--

ALTER TABLE ONLY ops_link
    ADD CONSTRAINT fkf90719671d9b5e4d FOREIGN KEY (ops_link_id) REFERENCES generic_link(link_id);

