#!/bin/sh
java -Dorg.apache.cxf.Logger=org.apache.cxf.common.logging.Log4jLogger -classpath autobahn-framework.jar:lib/antlr-2.7.5H3.jar:lib/asm-2.2.3.jar:lib/asm-attrs.jar:lib/autobahn-common.jar:lib/autobahn-dm.jar:lib/autobahn-idm.jar:lib/autobahn-calendar.jar:lib/autobahn-ta.jar:lib/c3p0-0.9.1.2.jar:lib/cglib-nodep-2.2.jar:lib/commons-collections-3.1.jar:lib/commons-lang-2.4.jar:lib/commons-logging-1.1.1.jar:lib/cxf-2.2.2.jar:lib/cxf-manifest.jar:lib/dom4j-1.6.1.jar:lib/geronimo-activation_1.1_spec-1.0.2.jar:lib/geronimo-annotation_1.0_spec-1.1.1.jar:lib/geronimo-javamail_1.4_spec-1.6.jar:lib/geronimo-jaxws_2.1_spec-1.0.jar:lib/geronimo-servlet_2.5_spec-1.2.jar:lib/geronimo-stax-api_1.0_spec-1.0.1.jar:lib/geronimo-ws-metadata_2.0_spec-1.1.2.jar:lib/hibernate3.jar:lib/jaxb-api-2.1.jar:lib/jaxb-impl-2.1.9.jar:lib/jaxb-xjc-2.1.9.jar:lib/jetty-6.1.18.jar:lib/jetty-util-6.1.18.jar:lib/jta.jar:lib/log4j-1.2.14.jar:lib/neethi-2.0.4.jar:lib/postgresql-8.1-407.jdbc3.jar:lib/saaj-api-1.3.jar:lib/saaj-impl-1.3.2.jar:lib/stax-api-1.0.1.jar:lib/velocity-1.5.jar:lib/wsdl4j-1.6.1.jar:lib/wss4j-1.5.7.jar:lib/wstx-asl-3.2.8.jar:lib/XmlSchema-1.4.5.jar:lib/xpp3_min-1.1.4c.jar:lib/xstream-1.3.jar net.geant2.jra3.framework.Framework start