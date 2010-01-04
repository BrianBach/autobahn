
/**
 * OSCARSNotifyStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */
        package net.geant2.jra3.oscars.notify;

        

        /*
        *  OSCARSNotifyStub java implementation
        */

        
        public class OSCARSNotifyStub extends org.apache.axis2.client.Stub
        {
        protected org.apache.axis2.description.AxisOperation[] _operations;

        //hashmaps to keep the fault mapping
        private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
        private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
        private java.util.HashMap faultMessageMap = new java.util.HashMap();

        private static int counter = 0;

        private static synchronized String getUniqueSuffix(){
            // reset the counter if it is greater than 99999
            if (counter > 99999){
                counter = 0;
            }
            counter = counter + 1; 
            return Long.toString(System.currentTimeMillis()) + "_" + counter;
        }

    
    private void populateAxisService() throws org.apache.axis2.AxisFault {

     //creating the Service with a unique name
     _service = new org.apache.axis2.description.AxisService("OSCARSNotify" + getUniqueSuffix());
     addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[8];
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS", "Subscribe"));
	    _service.addOperation(__operation);
	    

	    
	    (__operation).getMessage(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_OUT_VALUE).getPolicySubject().attachPolicy(getPolicy("<wsp:Policy xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><wsp:ExactlyOne><wsp:All><sp:SignedParts xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Body /></sp:SignedParts><sp:AsymmetricBinding xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:InitiatorToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/AlwaysToRecipient\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:InitiatorToken><sp:RecipientToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Always\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:RecipientToken><sp:AlgorithmSuite xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:TripleDesRsa15 /></wsp:Policy></sp:AlgorithmSuite><sp:Layout><wsp:Policy><sp:Strict /></wsp:Policy></sp:Layout><sp:IncludeTimestamp /><sp:OnlySignEntireHeadersAndBody /></wsp:Policy></sp:AsymmetricBinding><sp:Wss10 xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Policy><sp:MustSupportRefKeyIdentifier /><sp:MustSupportRefIssuerSerial /></sp:Policy></sp:Wss10></wsp:All></wsp:ExactlyOne></wsp:Policy>"));
	    
	    (__operation).getMessage(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE).getPolicySubject().attachPolicy(getPolicy("<wsp:Policy wsu:Id=\"X509BindingPolicy\" xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsp:ExactlyOne><wsp:All><sp:AsymmetricBinding xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:InitiatorToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/AlwaysToRecipient\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:InitiatorToken><sp:RecipientToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Always\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:RecipientToken><sp:AlgorithmSuite xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:TripleDesRsa15 /></wsp:Policy></sp:AlgorithmSuite><sp:Layout><wsp:Policy><sp:Strict /></wsp:Policy></sp:Layout><sp:IncludeTimestamp /><sp:OnlySignEntireHeadersAndBody /></wsp:Policy></sp:AsymmetricBinding><sp:Wss10 xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Policy><sp:MustSupportRefKeyIdentifier /><sp:MustSupportRefIssuerSerial /></sp:Policy></sp:Wss10></wsp:All></wsp:ExactlyOne></wsp:Policy>"));
	    
	    
            _operations[0]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS", "ResumeSubscription"));
	    _service.addOperation(__operation);
	    

	    
	    (__operation).getMessage(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_OUT_VALUE).getPolicySubject().attachPolicy(getPolicy("<wsp:Policy xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><wsp:ExactlyOne><wsp:All><sp:SignedParts xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Body /></sp:SignedParts><sp:AsymmetricBinding xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:InitiatorToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/AlwaysToRecipient\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:InitiatorToken><sp:RecipientToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Always\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:RecipientToken><sp:AlgorithmSuite xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:TripleDesRsa15 /></wsp:Policy></sp:AlgorithmSuite><sp:Layout><wsp:Policy><sp:Strict /></wsp:Policy></sp:Layout><sp:IncludeTimestamp /><sp:OnlySignEntireHeadersAndBody /></wsp:Policy></sp:AsymmetricBinding><sp:Wss10 xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Policy><sp:MustSupportRefKeyIdentifier /><sp:MustSupportRefIssuerSerial /></sp:Policy></sp:Wss10></wsp:All></wsp:ExactlyOne></wsp:Policy>"));
	    
	    (__operation).getMessage(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE).getPolicySubject().attachPolicy(getPolicy("<wsp:Policy wsu:Id=\"X509BindingPolicy\" xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsp:ExactlyOne><wsp:All><sp:AsymmetricBinding xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:InitiatorToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/AlwaysToRecipient\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:InitiatorToken><sp:RecipientToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Always\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:RecipientToken><sp:AlgorithmSuite xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:TripleDesRsa15 /></wsp:Policy></sp:AlgorithmSuite><sp:Layout><wsp:Policy><sp:Strict /></wsp:Policy></sp:Layout><sp:IncludeTimestamp /><sp:OnlySignEntireHeadersAndBody /></wsp:Policy></sp:AsymmetricBinding><sp:Wss10 xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Policy><sp:MustSupportRefKeyIdentifier /><sp:MustSupportRefIssuerSerial /></sp:Policy></sp:Wss10></wsp:All></wsp:ExactlyOne></wsp:Policy>"));
	    
	    
            _operations[1]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS", "RegisterPublisher"));
	    _service.addOperation(__operation);
	    

	    
	    (__operation).getMessage(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_OUT_VALUE).getPolicySubject().attachPolicy(getPolicy("<wsp:Policy xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><wsp:ExactlyOne><wsp:All><sp:SignedParts xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Body /></sp:SignedParts><sp:AsymmetricBinding xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:InitiatorToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/AlwaysToRecipient\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:InitiatorToken><sp:RecipientToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Always\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:RecipientToken><sp:AlgorithmSuite xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:TripleDesRsa15 /></wsp:Policy></sp:AlgorithmSuite><sp:Layout><wsp:Policy><sp:Strict /></wsp:Policy></sp:Layout><sp:IncludeTimestamp /><sp:OnlySignEntireHeadersAndBody /></wsp:Policy></sp:AsymmetricBinding><sp:Wss10 xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Policy><sp:MustSupportRefKeyIdentifier /><sp:MustSupportRefIssuerSerial /></sp:Policy></sp:Wss10></wsp:All></wsp:ExactlyOne></wsp:Policy>"));
	    
	    (__operation).getMessage(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE).getPolicySubject().attachPolicy(getPolicy("<wsp:Policy wsu:Id=\"X509BindingPolicy\" xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsp:ExactlyOne><wsp:All><sp:AsymmetricBinding xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:InitiatorToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/AlwaysToRecipient\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:InitiatorToken><sp:RecipientToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Always\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:RecipientToken><sp:AlgorithmSuite xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:TripleDesRsa15 /></wsp:Policy></sp:AlgorithmSuite><sp:Layout><wsp:Policy><sp:Strict /></wsp:Policy></sp:Layout><sp:IncludeTimestamp /><sp:OnlySignEntireHeadersAndBody /></wsp:Policy></sp:AsymmetricBinding><sp:Wss10 xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Policy><sp:MustSupportRefKeyIdentifier /><sp:MustSupportRefIssuerSerial /></sp:Policy></sp:Wss10></wsp:All></wsp:ExactlyOne></wsp:Policy>"));
	    
	    
            _operations[2]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS", "Unsubscribe"));
	    _service.addOperation(__operation);
	    

	    
	    (__operation).getMessage(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_OUT_VALUE).getPolicySubject().attachPolicy(getPolicy("<wsp:Policy xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><wsp:ExactlyOne><wsp:All><sp:SignedParts xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Body /></sp:SignedParts><sp:AsymmetricBinding xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:InitiatorToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/AlwaysToRecipient\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:InitiatorToken><sp:RecipientToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Always\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:RecipientToken><sp:AlgorithmSuite xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:TripleDesRsa15 /></wsp:Policy></sp:AlgorithmSuite><sp:Layout><wsp:Policy><sp:Strict /></wsp:Policy></sp:Layout><sp:IncludeTimestamp /><sp:OnlySignEntireHeadersAndBody /></wsp:Policy></sp:AsymmetricBinding><sp:Wss10 xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Policy><sp:MustSupportRefKeyIdentifier /><sp:MustSupportRefIssuerSerial /></sp:Policy></sp:Wss10></wsp:All></wsp:ExactlyOne></wsp:Policy>"));
	    
	    (__operation).getMessage(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE).getPolicySubject().attachPolicy(getPolicy("<wsp:Policy wsu:Id=\"X509BindingPolicy\" xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsp:ExactlyOne><wsp:All><sp:AsymmetricBinding xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:InitiatorToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/AlwaysToRecipient\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:InitiatorToken><sp:RecipientToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Always\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:RecipientToken><sp:AlgorithmSuite xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:TripleDesRsa15 /></wsp:Policy></sp:AlgorithmSuite><sp:Layout><wsp:Policy><sp:Strict /></wsp:Policy></sp:Layout><sp:IncludeTimestamp /><sp:OnlySignEntireHeadersAndBody /></wsp:Policy></sp:AsymmetricBinding><sp:Wss10 xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Policy><sp:MustSupportRefKeyIdentifier /><sp:MustSupportRefIssuerSerial /></sp:Policy></sp:Wss10></wsp:All></wsp:ExactlyOne></wsp:Policy>"));
	    
	    
            _operations[3]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS", "PauseSubscription"));
	    _service.addOperation(__operation);
	    

	    
	    (__operation).getMessage(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_OUT_VALUE).getPolicySubject().attachPolicy(getPolicy("<wsp:Policy xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><wsp:ExactlyOne><wsp:All><sp:SignedParts xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Body /></sp:SignedParts><sp:AsymmetricBinding xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:InitiatorToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/AlwaysToRecipient\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:InitiatorToken><sp:RecipientToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Always\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:RecipientToken><sp:AlgorithmSuite xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:TripleDesRsa15 /></wsp:Policy></sp:AlgorithmSuite><sp:Layout><wsp:Policy><sp:Strict /></wsp:Policy></sp:Layout><sp:IncludeTimestamp /><sp:OnlySignEntireHeadersAndBody /></wsp:Policy></sp:AsymmetricBinding><sp:Wss10 xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Policy><sp:MustSupportRefKeyIdentifier /><sp:MustSupportRefIssuerSerial /></sp:Policy></sp:Wss10></wsp:All></wsp:ExactlyOne></wsp:Policy>"));
	    
	    (__operation).getMessage(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE).getPolicySubject().attachPolicy(getPolicy("<wsp:Policy wsu:Id=\"X509BindingPolicy\" xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsp:ExactlyOne><wsp:All><sp:AsymmetricBinding xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:InitiatorToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/AlwaysToRecipient\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:InitiatorToken><sp:RecipientToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Always\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:RecipientToken><sp:AlgorithmSuite xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:TripleDesRsa15 /></wsp:Policy></sp:AlgorithmSuite><sp:Layout><wsp:Policy><sp:Strict /></wsp:Policy></sp:Layout><sp:IncludeTimestamp /><sp:OnlySignEntireHeadersAndBody /></wsp:Policy></sp:AsymmetricBinding><sp:Wss10 xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Policy><sp:MustSupportRefKeyIdentifier /><sp:MustSupportRefIssuerSerial /></sp:Policy></sp:Wss10></wsp:All></wsp:ExactlyOne></wsp:Policy>"));
	    
	    
            _operations[4]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS", "Renew"));
	    _service.addOperation(__operation);
	    

	    
	    (__operation).getMessage(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_OUT_VALUE).getPolicySubject().attachPolicy(getPolicy("<wsp:Policy xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><wsp:ExactlyOne><wsp:All><sp:SignedParts xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Body /></sp:SignedParts><sp:AsymmetricBinding xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:InitiatorToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/AlwaysToRecipient\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:InitiatorToken><sp:RecipientToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Always\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:RecipientToken><sp:AlgorithmSuite xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:TripleDesRsa15 /></wsp:Policy></sp:AlgorithmSuite><sp:Layout><wsp:Policy><sp:Strict /></wsp:Policy></sp:Layout><sp:IncludeTimestamp /><sp:OnlySignEntireHeadersAndBody /></wsp:Policy></sp:AsymmetricBinding><sp:Wss10 xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Policy><sp:MustSupportRefKeyIdentifier /><sp:MustSupportRefIssuerSerial /></sp:Policy></sp:Wss10></wsp:All></wsp:ExactlyOne></wsp:Policy>"));
	    
	    (__operation).getMessage(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE).getPolicySubject().attachPolicy(getPolicy("<wsp:Policy wsu:Id=\"X509BindingPolicy\" xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsp:ExactlyOne><wsp:All><sp:AsymmetricBinding xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:InitiatorToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/AlwaysToRecipient\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:InitiatorToken><sp:RecipientToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Always\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:RecipientToken><sp:AlgorithmSuite xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:TripleDesRsa15 /></wsp:Policy></sp:AlgorithmSuite><sp:Layout><wsp:Policy><sp:Strict /></wsp:Policy></sp:Layout><sp:IncludeTimestamp /><sp:OnlySignEntireHeadersAndBody /></wsp:Policy></sp:AsymmetricBinding><sp:Wss10 xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Policy><sp:MustSupportRefKeyIdentifier /><sp:MustSupportRefIssuerSerial /></sp:Policy></sp:Wss10></wsp:All></wsp:ExactlyOne></wsp:Policy>"));
	    
	    
            _operations[5]=__operation;
            
        
                    __operation = new org.apache.axis2.description.OutOnlyAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS", "Notify"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[6]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS", "DestroyRegistration"));
	    _service.addOperation(__operation);
	    

	    
	    (__operation).getMessage(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_OUT_VALUE).getPolicySubject().attachPolicy(getPolicy("<wsp:Policy xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><wsp:ExactlyOne><wsp:All><sp:SignedParts xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Body /></sp:SignedParts><sp:AsymmetricBinding xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:InitiatorToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/AlwaysToRecipient\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:InitiatorToken><sp:RecipientToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Always\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:RecipientToken><sp:AlgorithmSuite xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:TripleDesRsa15 /></wsp:Policy></sp:AlgorithmSuite><sp:Layout><wsp:Policy><sp:Strict /></wsp:Policy></sp:Layout><sp:IncludeTimestamp /><sp:OnlySignEntireHeadersAndBody /></wsp:Policy></sp:AsymmetricBinding><sp:Wss10 xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Policy><sp:MustSupportRefKeyIdentifier /><sp:MustSupportRefIssuerSerial /></sp:Policy></sp:Wss10></wsp:All></wsp:ExactlyOne></wsp:Policy>"));
	    
	    (__operation).getMessage(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE).getPolicySubject().attachPolicy(getPolicy("<wsp:Policy wsu:Id=\"X509BindingPolicy\" xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsp:ExactlyOne><wsp:All><sp:AsymmetricBinding xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:InitiatorToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/AlwaysToRecipient\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:InitiatorToken><sp:RecipientToken><wsp:Policy><sp:X509Token sp:IncludeToken=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Always\"><wsp:Policy><sp:RequireThumbprintReference /><sp:WssX509V3Token10 /></wsp:Policy></sp:X509Token></wsp:Policy></sp:RecipientToken><sp:AlgorithmSuite xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><wsp:Policy><sp:TripleDesRsa15 /></wsp:Policy></sp:AlgorithmSuite><sp:Layout><wsp:Policy><sp:Strict /></wsp:Policy></sp:Layout><sp:IncludeTimestamp /><sp:OnlySignEntireHeadersAndBody /></wsp:Policy></sp:AsymmetricBinding><sp:Wss10 xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\"><sp:Policy><sp:MustSupportRefKeyIdentifier /><sp:MustSupportRefIssuerSerial /></sp:Policy></sp:Wss10></wsp:All></wsp:ExactlyOne></wsp:Policy>"));
	    
	    
            _operations[7]=__operation;
            
        
        }

    //populates the faults
    private void populateFaults(){
         
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","TopicExpressionDialectUnknownFault"),"net.geant2.jra3.oscars.notify.TopicExpressionDialectUnknownFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","TopicExpressionDialectUnknownFault"),"net.geant2.jra3.oscars.notify.TopicExpressionDialectUnknownFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","TopicExpressionDialectUnknownFault"),"org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","NotifyMessageNotSupportedFault"),"net.geant2.jra3.oscars.notify.NotifyMessageNotSupportedFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","NotifyMessageNotSupportedFault"),"net.geant2.jra3.oscars.notify.NotifyMessageNotSupportedFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","NotifyMessageNotSupportedFault"),"org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","InvalidProducerPropertiesExpressionFault"),"net.geant2.jra3.oscars.notify.InvalidProducerPropertiesExpressionFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","InvalidProducerPropertiesExpressionFault"),"net.geant2.jra3.oscars.notify.InvalidProducerPropertiesExpressionFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","InvalidProducerPropertiesExpressionFault"),"org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","InvalidFilterFault"),"net.geant2.jra3.oscars.notify.InvalidFilterFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","InvalidFilterFault"),"net.geant2.jra3.oscars.notify.InvalidFilterFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","InvalidFilterFault"),"org.oasis_open.docs.wsn.b_2.InvalidFilterFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","TopicNotSupportedFault"),"net.geant2.jra3.oscars.notify.TopicNotSupportedFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","TopicNotSupportedFault"),"net.geant2.jra3.oscars.notify.TopicNotSupportedFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","TopicNotSupportedFault"),"org.oasis_open.docs.wsn.b_2.TopicNotSupportedFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnacceptableInitialTerminationTimeFault"),"net.geant2.jra3.oscars.notify.UnacceptableInitialTerminationTimeFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnacceptableInitialTerminationTimeFault"),"net.geant2.jra3.oscars.notify.UnacceptableInitialTerminationTimeFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnacceptableInitialTerminationTimeFault"),"org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","SubscribeCreationFailedFault"),"net.geant2.jra3.oscars.notify.SubscribeCreationFailedFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","SubscribeCreationFailedFault"),"net.geant2.jra3.oscars.notify.SubscribeCreationFailedFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","SubscribeCreationFailedFault"),"org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","InvalidMessageContentExpressionFault"),"net.geant2.jra3.oscars.notify.InvalidMessageContentExpressionFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","InvalidMessageContentExpressionFault"),"net.geant2.jra3.oscars.notify.InvalidMessageContentExpressionFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","InvalidMessageContentExpressionFault"),"org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","InvalidTopicExpressionFault"),"net.geant2.jra3.oscars.notify.InvalidTopicExpressionFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","InvalidTopicExpressionFault"),"net.geant2.jra3.oscars.notify.InvalidTopicExpressionFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","InvalidTopicExpressionFault"),"org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"net.geant2.jra3.oscars.notify.ResourceUnknownFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"net.geant2.jra3.oscars.notify.ResourceUnknownFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnrecognizedPolicyRequestFault"),"net.geant2.jra3.oscars.notify.UnrecognizedPolicyRequestFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnrecognizedPolicyRequestFault"),"net.geant2.jra3.oscars.notify.UnrecognizedPolicyRequestFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnrecognizedPolicyRequestFault"),"org.oasis_open.docs.wsn.b_2.UnrecognizedPolicyRequestFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnsupportedPolicyRequestFault"),"net.geant2.jra3.oscars.notify.UnsupportedPolicyRequestFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnsupportedPolicyRequestFault"),"net.geant2.jra3.oscars.notify.UnsupportedPolicyRequestFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnsupportedPolicyRequestFault"),"org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.geant2.jra3.oscars.notify.AAAFaultMessage");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.geant2.jra3.oscars.notify.AAAFaultMessage");
              faultMessageMap.put( new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.es.oscars.oscars.AAAFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"net.geant2.jra3.oscars.notify.ResourceUnknownFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"net.geant2.jra3.oscars.notify.ResourceUnknownFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","ResumeFailedFault"),"net.geant2.jra3.oscars.notify.ResumeFailedFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","ResumeFailedFault"),"net.geant2.jra3.oscars.notify.ResumeFailedFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","ResumeFailedFault"),"org.oasis_open.docs.wsn.b_2.ResumeFailedFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.geant2.jra3.oscars.notify.AAAFaultMessage");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.geant2.jra3.oscars.notify.AAAFaultMessage");
              faultMessageMap.put( new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.es.oscars.oscars.AAAFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/br-2","PublisherRegistrationFailedFault"),"net.geant2.jra3.oscars.notify.PublisherRegistrationFailedFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/br-2","PublisherRegistrationFailedFault"),"net.geant2.jra3.oscars.notify.PublisherRegistrationFailedFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/br-2","PublisherRegistrationFailedFault"),"org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","TopicNotSupportedFault"),"net.geant2.jra3.oscars.notify.TopicNotSupportedFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","TopicNotSupportedFault"),"net.geant2.jra3.oscars.notify.TopicNotSupportedFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","TopicNotSupportedFault"),"org.oasis_open.docs.wsn.b_2.TopicNotSupportedFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnacceptableInitialTerminationTimeFault"),"net.geant2.jra3.oscars.notify.UnacceptableInitialTerminationTimeFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnacceptableInitialTerminationTimeFault"),"net.geant2.jra3.oscars.notify.UnacceptableInitialTerminationTimeFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnacceptableInitialTerminationTimeFault"),"org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","InvalidTopicExpressionFault"),"net.geant2.jra3.oscars.notify.InvalidTopicExpressionFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","InvalidTopicExpressionFault"),"net.geant2.jra3.oscars.notify.InvalidTopicExpressionFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","InvalidTopicExpressionFault"),"org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"net.geant2.jra3.oscars.notify.ResourceUnknownFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"net.geant2.jra3.oscars.notify.ResourceUnknownFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/br-2","PublisherRegistrationRejectedFault"),"net.geant2.jra3.oscars.notify.PublisherRegistrationRejectedFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/br-2","PublisherRegistrationRejectedFault"),"net.geant2.jra3.oscars.notify.PublisherRegistrationRejectedFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/br-2","PublisherRegistrationRejectedFault"),"org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnableToDestroySubscriptionFault"),"net.geant2.jra3.oscars.notify.UnableToDestroySubscriptionFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnableToDestroySubscriptionFault"),"net.geant2.jra3.oscars.notify.UnableToDestroySubscriptionFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnableToDestroySubscriptionFault"),"org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"net.geant2.jra3.oscars.notify.ResourceUnknownFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"net.geant2.jra3.oscars.notify.ResourceUnknownFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.geant2.jra3.oscars.notify.AAAFaultMessage");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.geant2.jra3.oscars.notify.AAAFaultMessage");
              faultMessageMap.put( new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.es.oscars.oscars.AAAFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","PauseFailedFault"),"net.geant2.jra3.oscars.notify.PauseFailedFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","PauseFailedFault"),"net.geant2.jra3.oscars.notify.PauseFailedFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","PauseFailedFault"),"org.oasis_open.docs.wsn.b_2.PauseFailedFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"net.geant2.jra3.oscars.notify.ResourceUnknownFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"net.geant2.jra3.oscars.notify.ResourceUnknownFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.geant2.jra3.oscars.notify.AAAFaultMessage");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.geant2.jra3.oscars.notify.AAAFaultMessage");
              faultMessageMap.put( new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.es.oscars.oscars.AAAFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"net.geant2.jra3.oscars.notify.ResourceUnknownFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"net.geant2.jra3.oscars.notify.ResourceUnknownFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.geant2.jra3.oscars.notify.AAAFaultMessage");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.geant2.jra3.oscars.notify.AAAFaultMessage");
              faultMessageMap.put( new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.es.oscars.oscars.AAAFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnacceptableTerminationTimeFault"),"net.geant2.jra3.oscars.notify.UnacceptableTerminationTimeFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnacceptableTerminationTimeFault"),"net.geant2.jra3.oscars.notify.UnacceptableTerminationTimeFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","UnacceptableTerminationTimeFault"),"org.oasis_open.docs.wsn.b_2.UnacceptableTerminationTimeFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"net.geant2.jra3.oscars.notify.ResourceUnknownFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"net.geant2.jra3.oscars.notify.ResourceUnknownFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/r-2","ResourceUnknownFault"),"org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/br-2","ResourceNotDestroyedFault"),"net.geant2.jra3.oscars.notify.ResourceNotDestroyedFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/br-2","ResourceNotDestroyedFault"),"net.geant2.jra3.oscars.notify.ResourceNotDestroyedFault");
              faultMessageMap.put( new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/br-2","ResourceNotDestroyedFault"),"org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.geant2.jra3.oscars.notify.AAAFaultMessage");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.geant2.jra3.oscars.notify.AAAFaultMessage");
              faultMessageMap.put( new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","AAAFault"),"net.es.oscars.oscars.AAAFault");
           


    }

    /**
      *Constructor that takes in a configContext
      */

    public OSCARSNotifyStub(org.apache.axis2.context.ConfigurationContext configurationContext,
       java.lang.String targetEndpoint)
       throws org.apache.axis2.AxisFault {
         this(configurationContext,targetEndpoint,false);
   }


   /**
     * Constructor that takes in a configContext  and useseperate listner
     */
   public OSCARSNotifyStub(org.apache.axis2.context.ConfigurationContext configurationContext,
        java.lang.String targetEndpoint, boolean useSeparateListener)
        throws org.apache.axis2.AxisFault {
         //To populate AxisService
         populateAxisService();
         populateFaults();

        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext,_service);
        
        _service.applyPolicy();
        
	
        configurationContext = _serviceClient.getServiceContext().getConfigurationContext();

        _serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(
                targetEndpoint));
        _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
        
            //Set the soap version
            _serviceClient.getOptions().setSoapVersionURI(org.apache.axiom.soap.SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
        
    
    }

    /**
     * Default Constructor
     */
    public OSCARSNotifyStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {
        
                    this(configurationContext,"https://oscars-dev.es.net:9090/axis2/services/OSCARSNotify" );
                
    }

    /**
     * Default Constructor
     */
    public OSCARSNotifyStub() throws org.apache.axis2.AxisFault {
        
                    this("https://oscars-dev.es.net:9090/axis2/services/OSCARSNotify" );
                
    }

    /**
     * Constructor taking the target endpoint
     */
    public OSCARSNotifyStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null,targetEndpoint);
    }



        
                    /**
                     * Auto generated method signature
                     * 
                     * @see net.geant2.jra3.oscars.notify.OSCARSNotify#Subscribe
                     * @param subscribe60
                    
                     * @throws net.geant2.jra3.oscars.notify.TopicExpressionDialectUnknownFault : 
                     * @throws net.geant2.jra3.oscars.notify.NotifyMessageNotSupportedFault : 
                     * @throws net.geant2.jra3.oscars.notify.InvalidProducerPropertiesExpressionFault : 
                     * @throws net.geant2.jra3.oscars.notify.InvalidFilterFault : 
                     * @throws net.geant2.jra3.oscars.notify.TopicNotSupportedFault : 
                     * @throws net.geant2.jra3.oscars.notify.UnacceptableInitialTerminationTimeFault : 
                     * @throws net.geant2.jra3.oscars.notify.SubscribeCreationFailedFault : 
                     * @throws net.geant2.jra3.oscars.notify.InvalidMessageContentExpressionFault : 
                     * @throws net.geant2.jra3.oscars.notify.InvalidTopicExpressionFault : 
                     * @throws net.geant2.jra3.oscars.notify.ResourceUnknownFault : 
                     * @throws net.geant2.jra3.oscars.notify.UnrecognizedPolicyRequestFault : 
                     * @throws net.geant2.jra3.oscars.notify.UnsupportedPolicyRequestFault : 
                     * @throws net.geant2.jra3.oscars.notify.AAAFaultMessage : 
                     */

                    

                            public  org.oasis_open.docs.wsn.b_2.SubscribeResponse Subscribe(

                            org.oasis_open.docs.wsn.b_2.Subscribe subscribe60)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,net.geant2.jra3.oscars.notify.TopicExpressionDialectUnknownFault
                        ,net.geant2.jra3.oscars.notify.NotifyMessageNotSupportedFault
                        ,net.geant2.jra3.oscars.notify.InvalidProducerPropertiesExpressionFault
                        ,net.geant2.jra3.oscars.notify.InvalidFilterFault
                        ,net.geant2.jra3.oscars.notify.TopicNotSupportedFault
                        ,net.geant2.jra3.oscars.notify.UnacceptableInitialTerminationTimeFault
                        ,net.geant2.jra3.oscars.notify.SubscribeCreationFailedFault
                        ,net.geant2.jra3.oscars.notify.InvalidMessageContentExpressionFault
                        ,net.geant2.jra3.oscars.notify.InvalidTopicExpressionFault
                        ,net.geant2.jra3.oscars.notify.ResourceUnknownFault
                        ,net.geant2.jra3.oscars.notify.UnrecognizedPolicyRequestFault
                        ,net.geant2.jra3.oscars.notify.UnsupportedPolicyRequestFault
                        ,net.geant2.jra3.oscars.notify.AAAFaultMessage{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
              _operationClient.getOptions().setAction("http://oscars.es.net/OSCARS/Subscribe");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    subscribe60,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                    "Subscribe")));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             org.oasis_open.docs.wsn.b_2.SubscribeResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (org.oasis_open.docs.wsn.b_2.SubscribeResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.TopicExpressionDialectUnknownFault){
                          throw (net.geant2.jra3.oscars.notify.TopicExpressionDialectUnknownFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.NotifyMessageNotSupportedFault){
                          throw (net.geant2.jra3.oscars.notify.NotifyMessageNotSupportedFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.InvalidProducerPropertiesExpressionFault){
                          throw (net.geant2.jra3.oscars.notify.InvalidProducerPropertiesExpressionFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.InvalidFilterFault){
                          throw (net.geant2.jra3.oscars.notify.InvalidFilterFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.TopicNotSupportedFault){
                          throw (net.geant2.jra3.oscars.notify.TopicNotSupportedFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.UnacceptableInitialTerminationTimeFault){
                          throw (net.geant2.jra3.oscars.notify.UnacceptableInitialTerminationTimeFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.SubscribeCreationFailedFault){
                          throw (net.geant2.jra3.oscars.notify.SubscribeCreationFailedFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.InvalidMessageContentExpressionFault){
                          throw (net.geant2.jra3.oscars.notify.InvalidMessageContentExpressionFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.InvalidTopicExpressionFault){
                          throw (net.geant2.jra3.oscars.notify.InvalidTopicExpressionFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.ResourceUnknownFault){
                          throw (net.geant2.jra3.oscars.notify.ResourceUnknownFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.UnrecognizedPolicyRequestFault){
                          throw (net.geant2.jra3.oscars.notify.UnrecognizedPolicyRequestFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.UnsupportedPolicyRequestFault){
                          throw (net.geant2.jra3.oscars.notify.UnsupportedPolicyRequestFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.AAAFaultMessage){
                          throw (net.geant2.jra3.oscars.notify.AAAFaultMessage)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * 
                     * @see net.geant2.jra3.oscars.notify.OSCARSNotify#ResumeSubscription
                     * @param resumeSubscription62
                    
                     * @throws net.geant2.jra3.oscars.notify.ResourceUnknownFault : 
                     * @throws net.geant2.jra3.oscars.notify.ResumeFailedFault : 
                     * @throws net.geant2.jra3.oscars.notify.AAAFaultMessage : 
                     */

                    

                            public  org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse ResumeSubscription(

                            org.oasis_open.docs.wsn.b_2.ResumeSubscription resumeSubscription62)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,net.geant2.jra3.oscars.notify.ResourceUnknownFault
                        ,net.geant2.jra3.oscars.notify.ResumeFailedFault
                        ,net.geant2.jra3.oscars.notify.AAAFaultMessage{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
              _operationClient.getOptions().setAction("http://oscars.es.net/OSCARS/ResumeSubscription");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    resumeSubscription62,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                    "ResumeSubscription")));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.ResourceUnknownFault){
                          throw (net.geant2.jra3.oscars.notify.ResourceUnknownFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.ResumeFailedFault){
                          throw (net.geant2.jra3.oscars.notify.ResumeFailedFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.AAAFaultMessage){
                          throw (net.geant2.jra3.oscars.notify.AAAFaultMessage)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * 
                     * @see net.geant2.jra3.oscars.notify.OSCARSNotify#RegisterPublisher
                     * @param registerPublisher64
                    
                     * @throws net.geant2.jra3.oscars.notify.PublisherRegistrationFailedFault : 
                     * @throws net.geant2.jra3.oscars.notify.TopicNotSupportedFault : 
                     * @throws net.geant2.jra3.oscars.notify.UnacceptableInitialTerminationTimeFault : 
                     * @throws net.geant2.jra3.oscars.notify.InvalidTopicExpressionFault : 
                     * @throws net.geant2.jra3.oscars.notify.ResourceUnknownFault : 
                     * @throws net.geant2.jra3.oscars.notify.PublisherRegistrationRejectedFault : 
                     */

                    

                            public  org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse RegisterPublisher(

                            org.oasis_open.docs.wsn.br_2.RegisterPublisher registerPublisher64)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,net.geant2.jra3.oscars.notify.PublisherRegistrationFailedFault
                        ,net.geant2.jra3.oscars.notify.TopicNotSupportedFault
                        ,net.geant2.jra3.oscars.notify.UnacceptableInitialTerminationTimeFault
                        ,net.geant2.jra3.oscars.notify.InvalidTopicExpressionFault
                        ,net.geant2.jra3.oscars.notify.ResourceUnknownFault
                        ,net.geant2.jra3.oscars.notify.PublisherRegistrationRejectedFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
              _operationClient.getOptions().setAction("http://oscars.es.net/OSCARS/RegisterPublisher");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    registerPublisher64,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                    "RegisterPublisher")));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.PublisherRegistrationFailedFault){
                          throw (net.geant2.jra3.oscars.notify.PublisherRegistrationFailedFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.TopicNotSupportedFault){
                          throw (net.geant2.jra3.oscars.notify.TopicNotSupportedFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.UnacceptableInitialTerminationTimeFault){
                          throw (net.geant2.jra3.oscars.notify.UnacceptableInitialTerminationTimeFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.InvalidTopicExpressionFault){
                          throw (net.geant2.jra3.oscars.notify.InvalidTopicExpressionFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.ResourceUnknownFault){
                          throw (net.geant2.jra3.oscars.notify.ResourceUnknownFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.PublisherRegistrationRejectedFault){
                          throw (net.geant2.jra3.oscars.notify.PublisherRegistrationRejectedFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * 
                     * @see net.geant2.jra3.oscars.notify.OSCARSNotify#Unsubscribe
                     * @param unsubscribe66
                    
                     * @throws net.geant2.jra3.oscars.notify.UnableToDestroySubscriptionFault : 
                     * @throws net.geant2.jra3.oscars.notify.ResourceUnknownFault : 
                     * @throws net.geant2.jra3.oscars.notify.AAAFaultMessage : 
                     */

                    

                            public  org.oasis_open.docs.wsn.b_2.UnsubscribeResponse Unsubscribe(

                            org.oasis_open.docs.wsn.b_2.Unsubscribe unsubscribe66)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,net.geant2.jra3.oscars.notify.UnableToDestroySubscriptionFault
                        ,net.geant2.jra3.oscars.notify.ResourceUnknownFault
                        ,net.geant2.jra3.oscars.notify.AAAFaultMessage{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
              _operationClient.getOptions().setAction("http://oscars.es.net/OSCARS/Unsubscribe");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    unsubscribe66,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                    "Unsubscribe")));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             org.oasis_open.docs.wsn.b_2.UnsubscribeResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (org.oasis_open.docs.wsn.b_2.UnsubscribeResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.UnableToDestroySubscriptionFault){
                          throw (net.geant2.jra3.oscars.notify.UnableToDestroySubscriptionFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.ResourceUnknownFault){
                          throw (net.geant2.jra3.oscars.notify.ResourceUnknownFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.AAAFaultMessage){
                          throw (net.geant2.jra3.oscars.notify.AAAFaultMessage)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * 
                     * @see net.geant2.jra3.oscars.notify.OSCARSNotify#PauseSubscription
                     * @param pauseSubscription68
                    
                     * @throws net.geant2.jra3.oscars.notify.PauseFailedFault : 
                     * @throws net.geant2.jra3.oscars.notify.ResourceUnknownFault : 
                     * @throws net.geant2.jra3.oscars.notify.AAAFaultMessage : 
                     */

                    

                            public  org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse PauseSubscription(

                            org.oasis_open.docs.wsn.b_2.PauseSubscription pauseSubscription68)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,net.geant2.jra3.oscars.notify.PauseFailedFault
                        ,net.geant2.jra3.oscars.notify.ResourceUnknownFault
                        ,net.geant2.jra3.oscars.notify.AAAFaultMessage{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[4].getName());
              _operationClient.getOptions().setAction("http://oscars.es.net/OSCARS/PauseSubscription");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    pauseSubscription68,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                    "PauseSubscription")));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.PauseFailedFault){
                          throw (net.geant2.jra3.oscars.notify.PauseFailedFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.ResourceUnknownFault){
                          throw (net.geant2.jra3.oscars.notify.ResourceUnknownFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.AAAFaultMessage){
                          throw (net.geant2.jra3.oscars.notify.AAAFaultMessage)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * 
                     * @see net.geant2.jra3.oscars.notify.OSCARSNotify#Renew
                     * @param renew70
                    
                     * @throws net.geant2.jra3.oscars.notify.ResourceUnknownFault : 
                     * @throws net.geant2.jra3.oscars.notify.AAAFaultMessage : 
                     * @throws net.geant2.jra3.oscars.notify.UnacceptableTerminationTimeFault : 
                     */

                    

                            public  org.oasis_open.docs.wsn.b_2.RenewResponse Renew(

                            org.oasis_open.docs.wsn.b_2.Renew renew70)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,net.geant2.jra3.oscars.notify.ResourceUnknownFault
                        ,net.geant2.jra3.oscars.notify.AAAFaultMessage
                        ,net.geant2.jra3.oscars.notify.UnacceptableTerminationTimeFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[5].getName());
              _operationClient.getOptions().setAction("http://oscars.es.net/OSCARS/Renew");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    renew70,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                    "Renew")));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             org.oasis_open.docs.wsn.b_2.RenewResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (org.oasis_open.docs.wsn.b_2.RenewResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.ResourceUnknownFault){
                          throw (net.geant2.jra3.oscars.notify.ResourceUnknownFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.AAAFaultMessage){
                          throw (net.geant2.jra3.oscars.notify.AAAFaultMessage)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.UnacceptableTerminationTimeFault){
                          throw (net.geant2.jra3.oscars.notify.UnacceptableTerminationTimeFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
             
                
                /**
                  * Auto generated method signature
                  * 
                  */
                public void  Notify(
                 org.oasis_open.docs.wsn.b_2.Notify notify72

                ) throws java.rmi.RemoteException
                
                
                {
                org.apache.axis2.context.MessageContext _messageContext = null;

                
                org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[6].getName());
                _operationClient.getOptions().setAction("http://oscars.es.net/OSCARS/Notify");
                _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

                
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              
                org.apache.axiom.soap.SOAPEnvelope env = null;
                 _messageContext = new org.apache.axis2.context.MessageContext();

                
                                                    //Style is Doc.
                                                    
                                                                    
                                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                                    notify72,
                                                                    optimizeContent(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                    "Notify")));
                                                                

              //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
                // create message context with that soap envelope

            _messageContext.setEnvelope(env);

            // add the message contxt to the operation client
            _operationClient.addMessageContext(_messageContext);

             _operationClient.execute(true);

           
              _messageContext.getTransportOut().getSender().cleanup(_messageContext); 
           
             return;
           }
            
                    /**
                     * Auto generated method signature
                     * 
                     * @see net.geant2.jra3.oscars.notify.OSCARSNotify#DestroyRegistration
                     * @param destroyRegistration73
                    
                     * @throws net.geant2.jra3.oscars.notify.ResourceUnknownFault : 
                     * @throws net.geant2.jra3.oscars.notify.ResourceNotDestroyedFault : 
                     * @throws net.geant2.jra3.oscars.notify.AAAFaultMessage : 
                     */

                    

                            public  org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse DestroyRegistration(

                            org.oasis_open.docs.wsn.br_2.DestroyRegistration destroyRegistration73)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,net.geant2.jra3.oscars.notify.ResourceUnknownFault
                        ,net.geant2.jra3.oscars.notify.ResourceNotDestroyedFault
                        ,net.geant2.jra3.oscars.notify.AAAFaultMessage{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[7].getName());
              _operationClient.getOptions().setAction("http://oscars.es.net/OSCARS/DestroyRegistration");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    destroyRegistration73,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                    "DestroyRegistration")));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.ResourceUnknownFault){
                          throw (net.geant2.jra3.oscars.notify.ResourceUnknownFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.ResourceNotDestroyedFault){
                          throw (net.geant2.jra3.oscars.notify.ResourceNotDestroyedFault)ex;
                        }
                        
                        if (ex instanceof net.geant2.jra3.oscars.notify.AAAFaultMessage){
                          throw (net.geant2.jra3.oscars.notify.AAAFaultMessage)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            


       /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
       private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
            org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
       return returnMap;
    }

    
    ////////////////////////////////////////////////////////////////////////
    
    private static org.apache.neethi.Policy getPolicy (java.lang.String policyString) {
    	java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(policyString.getBytes());
    	return org.apache.neethi.PolicyEngine.getPolicy(bais);
    }
    
    /////////////////////////////////////////////////////////////////////////

    
    
    private javax.xml.namespace.QName[] opNameArray = null;
    private boolean optimizeContent(javax.xml.namespace.QName opName) {
        

        if (opNameArray == null) {
            return false;
        }
        for (int i = 0; i < opNameArray.length; i++) {
            if (opName.equals(opNameArray[i])) {
                return true;   
            }
        }
        return false;
    }
     //https://oscars-dev.es.net:9090/axis2/services/OSCARSNotify
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.Subscribe param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.Subscribe.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.SubscribeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.SubscribeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.InvalidFilterFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.InvalidFilterFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.TopicNotSupportedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.TopicNotSupportedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.UnrecognizedPolicyRequestFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.UnrecognizedPolicyRequestFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.AAAFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.AAAFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.ResumeSubscription param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.ResumeSubscription.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.ResumeFailedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.ResumeFailedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.br_2.RegisterPublisher param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.br_2.RegisterPublisher.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.Unsubscribe param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.Unsubscribe.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.UnsubscribeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.UnsubscribeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.PauseSubscription param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.PauseSubscription.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.PauseFailedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.PauseFailedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.Renew param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.Renew.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.RenewResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.RenewResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.UnacceptableTerminationTimeFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.UnacceptableTerminationTimeFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.Notify param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.Notify.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.br_2.DestroyRegistration param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.br_2.DestroyRegistration.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.oasis_open.docs.wsn.b_2.Subscribe param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(org.oasis_open.docs.wsn.b_2.Subscribe.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.oasis_open.docs.wsn.b_2.ResumeSubscription param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(org.oasis_open.docs.wsn.b_2.ResumeSubscription.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.oasis_open.docs.wsn.br_2.RegisterPublisher param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(org.oasis_open.docs.wsn.br_2.RegisterPublisher.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.oasis_open.docs.wsn.b_2.Unsubscribe param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(org.oasis_open.docs.wsn.b_2.Unsubscribe.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.oasis_open.docs.wsn.b_2.PauseSubscription param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(org.oasis_open.docs.wsn.b_2.PauseSubscription.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.oasis_open.docs.wsn.b_2.Renew param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(org.oasis_open.docs.wsn.b_2.Renew.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.oasis_open.docs.wsn.b_2.Notify param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(org.oasis_open.docs.wsn.b_2.Notify.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.oasis_open.docs.wsn.br_2.DestroyRegistration param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(org.oasis_open.docs.wsn.br_2.DestroyRegistration.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (org.oasis_open.docs.wsn.b_2.Subscribe.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.Subscribe.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.SubscribeResponse.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.SubscribeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.InvalidFilterFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.InvalidFilterFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.TopicNotSupportedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.TopicNotSupportedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.UnrecognizedPolicyRequestFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.UnrecognizedPolicyRequestFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.ResumeSubscription.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.ResumeSubscription.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.ResumeFailedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.ResumeFailedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.br_2.RegisterPublisher.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.br_2.RegisterPublisher.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.TopicNotSupportedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.TopicNotSupportedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.Unsubscribe.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.Unsubscribe.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.UnsubscribeResponse.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.UnsubscribeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.PauseSubscription.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.PauseSubscription.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.PauseFailedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.PauseFailedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.Renew.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.Renew.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.RenewResponse.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.RenewResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.UnacceptableTerminationTimeFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.UnacceptableTerminationTimeFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.Notify.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.Notify.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.br_2.DestroyRegistration.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.br_2.DestroyRegistration.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    
   }
   