/*
 *  Copyright (C) 2009-2013 VMware, Inc. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1.4-10/02/2007 10:39 AM(ffu)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.10.23 at 10:17:33 AM PDT 
//

package com.wavemaker.tools.service.definitions;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.wavemaker.json.type.OperationEnumeration;

/**
 * <p>
 * Java class for dataobject complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataobject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="element" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="require" type="{http://www.activegrid.com/namespaces/ServiceDefinitions/1.0}OperationEnumeration" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="noChange" type="{http://www.activegrid.com/namespaces/ServiceDefinitions/1.0}OperationEnumeration" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="exclude" type="{http://www.activegrid.com/namespaces/ServiceDefinitions/1.0}OperationEnumeration" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="subType" type="{http://www.w3.org/2001/XMLSchema}string" default="string" />
 *                 &lt;attribute name="typeRef" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="isList" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                 &lt;attribute name="allowNull" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="javaType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="supportsQuickData" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="internal" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="jsType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataobject", propOrder = { "element" })
public class DataObject {

    protected List<DataObject.Element> element;

    @XmlAttribute(required = true)
    protected String javaType;

    @XmlAttribute(required = true)
    protected String name;

    @XmlAttribute
    protected Boolean supportsQuickData;

    @XmlAttribute
    protected Boolean internal;

    @XmlAttribute
    protected String jsType;

    /**
     * Gets the value of the element property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the element property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getElement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link DataObject.Element }
     * 
     * 
     */
    public List<DataObject.Element> getElement() {
        if (this.element == null) {
            this.element = new ArrayList<DataObject.Element>();
        }
        return this.element;
    }

    /**
     * Gets the value of the javaType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getJavaType() {
        return this.javaType;
    }

    /**
     * Sets the value of the javaType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setJavaType(String value) {
        this.javaType = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the supportsQuickData property.
     * 
     * @return possible object is {@link Boolean }
     * 
     */
    public boolean isSupportsQuickData() {
        if (this.supportsQuickData == null) {
            return false;
        } else {
            return this.supportsQuickData;
        }
    }

    /**
     * Sets the value of the supportsQuickData property.
     * 
     * @param value allowed object is {@link Boolean }
     * 
     */
    public void setSupportsQuickData(Boolean value) {
        this.supportsQuickData = value;
    }

    /**
     * Gets the value of the internal property.
     * 
     * @return possible object is {@link Boolean }
     * 
     */
    public boolean isInternal() {
        if (this.internal == null) {
            return false;
        } else {
            return this.internal;
        }
    }

    /**
     * Sets the value of the internal property.
     * 
     * @param value allowed object is {@link Boolean }
     * 
     */
    public void setInternal(Boolean value) {
        this.internal = value;
    }

    /**
     * Gets the value of the jsType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getJsType() {
        return this.jsType;
    }

    /**
     * Sets the value of the jsType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setJsType(String value) {
        this.jsType = value;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     * 
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="require" type="{http://www.activegrid.com/namespaces/ServiceDefinitions/1.0}OperationEnumeration" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="noChange" type="{http://www.activegrid.com/namespaces/ServiceDefinitions/1.0}OperationEnumeration" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="exclude" type="{http://www.activegrid.com/namespaces/ServiceDefinitions/1.0}OperationEnumeration" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="subType" type="{http://www.w3.org/2001/XMLSchema}string" default="string"/>
     *       &lt;attribute name="typeRef" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="isList" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="allowNull" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />    
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "require", "noChange", "exclude" })
    public static class Element {

        @XmlElement(type = String.class)
        @XmlJavaTypeAdapter(Adapter1.class)
        protected List<OperationEnumeration> require;

        @XmlElement(type = String.class)
        @XmlJavaTypeAdapter(Adapter1.class)
        protected List<OperationEnumeration> noChange;

        @XmlElement(type = String.class)
        @XmlJavaTypeAdapter(Adapter1.class)
        protected List<OperationEnumeration> exclude;

        @XmlAttribute(required = true)
        protected String name;

        @XmlAttribute
        protected String subType; // salesforce

        @XmlAttribute(required = true)
        protected String typeRef;

        @XmlAttribute
        protected Boolean isList;

        @XmlAttribute
        protected Boolean allowNull;

        /**
         * Gets the value of the require property.
         * 
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you
         * make to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE>
         * method for the require property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * 
         * <pre>
         * getRequire().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list {@link String }
         * 
         * 
         */
        public List<OperationEnumeration> getRequire() {
            if (this.require == null) {
                this.require = new ArrayList<OperationEnumeration>();
            }
            return this.require;
        }

        /**
         * Gets the value of the noChange property.
         * 
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you
         * make to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE>
         * method for the noChange property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * 
         * <pre>
         * getNoChange().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list {@link String }
         * 
         * 
         */
        public List<OperationEnumeration> getNoChange() {
            if (this.noChange == null) {
                this.noChange = new ArrayList<OperationEnumeration>();
            }
            return this.noChange;
        }

        /**
         * Gets the value of the exclude property.
         * 
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you
         * make to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE>
         * method for the exclude property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * 
         * <pre>
         * getExclude().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list {@link String }
         * 
         * 
         */
        public List<OperationEnumeration> getExclude() {
            if (this.exclude == null) {
                this.exclude = new ArrayList<OperationEnumeration>();
            }
            return this.exclude;
        }

        /**
         * Gets the value of the name property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getName() {
            return this.name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value allowed object is {@link String }
         * 
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the typeRef property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getTypeRef() {
            return this.typeRef;
        }

        /**
         * Sets the value of the typeRef property.
         * 
         * @param value allowed object is {@link String }
         * 
         */
        public void setTypeRef(String value) {
            this.typeRef = value;
        }

        /**
         * Gets the value of the isList property.
         * 
         * @return possible object is {@link Boolean }
         * 
         */
        public boolean isIsList() {
            if (this.isList == null) {
                return false;
            } else {
                return this.isList;
            }
        }

        /**
         * Sets the value of the isList property.
         * 
         * @param value allowed object is {@link Boolean }
         * 
         */
        public void setIsList(Boolean value) {
            this.isList = value;
        }

        /**
         * Gets the value of the allowNull property.
         * 
         * @return possible object is {@link Boolean }
         * 
         */
        public boolean isAllowNull() {
            if (this.allowNull == null) {
                return false;
            } else {
                return this.allowNull;
            }
        }

        /**
         * Sets the value of the allowNull property.
         * 
         * @param value allowed object is {@link Boolean }
         * 
         */
        public void setAllowNull(Boolean value) {
            this.allowNull = value;
        }

        /**
         * Gets the value of the subType property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getSubType() {
            return this.subType;
        }

        /**
         * Sets the value of the subType property.
         * 
         * @param value allowed object is {@link String }
         * 
         */
        public void setSubType(String value) {
            this.subType = value;
        }

    }

}
