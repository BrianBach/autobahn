
package org.oasis_open.docs.wsn.b_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FilterType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FilterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/wsn/b-2}TopicExpression" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsn/b-2}ProducerProperties" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsn/b-2}MessageContent" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;any/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FilterType", propOrder = {
    "topicExpression",
    "producerProperties",
    "messageContent",
    "any"
})
public class FilterType {

    @XmlElement(name = "TopicExpression")
    protected List<TopicExpressionType> topicExpression;
    @XmlElement(name = "ProducerProperties")
    protected List<QueryExpressionType> producerProperties;
    @XmlElement(name = "MessageContent")
    protected List<QueryExpressionType> messageContent;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the topicExpression property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the topicExpression property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTopicExpression().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TopicExpressionType }
     * 
     * 
     */
    public List<TopicExpressionType> getTopicExpression() {
        if (topicExpression == null) {
            topicExpression = new ArrayList<TopicExpressionType>();
        }
        return this.topicExpression;
    }

    /**
     * Gets the value of the producerProperties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the producerProperties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProducerProperties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QueryExpressionType }
     * 
     * 
     */
    public List<QueryExpressionType> getProducerProperties() {
        if (producerProperties == null) {
            producerProperties = new ArrayList<QueryExpressionType>();
        }
        return this.producerProperties;
    }

    /**
     * Gets the value of the messageContent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messageContent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessageContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QueryExpressionType }
     * 
     * 
     */
    public List<QueryExpressionType> getMessageContent() {
        if (messageContent == null) {
            messageContent = new ArrayList<QueryExpressionType>();
        }
        return this.messageContent;
    }

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }

}
