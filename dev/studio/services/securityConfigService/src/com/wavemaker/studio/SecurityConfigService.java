/*
 * Copyright (C) 2007-2011 VMWare, Inc. All rights reserved.
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
 
package com.wavemaker.studio;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import com.wavemaker.common.util.SpringUtils;
import com.wavemaker.common.WMRuntimeException;
import com.wavemaker.common.CommonConstants;
import com.wavemaker.tools.data.DataModelManager;
import com.wavemaker.tools.data.ColumnInfo;
import com.wavemaker.tools.data.DataModelConfiguration;
import com.wavemaker.tools.data.EntityInfo;
import com.wavemaker.tools.data.PropertyInfo;
import com.wavemaker.tools.project.ProjectManager;
import com.wavemaker.tools.security.DatabaseOptions;
import com.wavemaker.tools.security.DemoOptions;
import com.wavemaker.tools.security.DemoUser;
import com.wavemaker.tools.security.GeneralOptions;
import com.wavemaker.tools.security.LDAPOptions;
import com.wavemaker.tools.security.SecurityToolsManager;
import com.wavemaker.tools.service.DesignServiceManager;
import com.wavemaker.runtime.WMAppContext;
import com.wavemaker.runtime.data.util.DataServiceConstants;

/**
 * This service provides methods to config security settings for the project.
 * 
 * @author $Author$
 * @version $Revision$
 * Last changed: $LastChangedDate$
 */
public class SecurityConfigService {

    private ProjectManager projectMgr;

    private DesignServiceManager designServiceMgr;

    private DataModelManager dataModelMgr;

    private SecurityToolsManager securityToolsMgr;

    public void setProjectManager(ProjectManager projectMgr) {
        this.projectMgr = projectMgr;
    }

    public void setDesignServiceManager(DesignServiceManager designServiceMgr) {
        this.designServiceMgr = designServiceMgr;
    }

    public void setDataModelManager(DataModelManager dataModelMgr) {
        this.dataModelMgr = dataModelMgr;
    }

    private SecurityToolsManager getSecToolsMgr() {
        if (securityToolsMgr == null) {
            if (projectMgr == null) {
                SpringUtils.throwSpringNotInitializedError(ProjectManager.class);
            }
            if (designServiceMgr == null) {
                SpringUtils.throwSpringNotInitializedError(DesignServiceManager.class);
            }
            securityToolsMgr = new SecurityToolsManager(projectMgr,
                    designServiceMgr);
        }
        return securityToolsMgr;
    }

    public GeneralOptions getGeneralOptions() throws JAXBException, IOException {
        return getSecToolsMgr().getGeneralOptions();
    }

    public List<String> getJOSSOOptions() throws JAXBException, IOException {
        return getSecToolsMgr().getJOSSOOptions();
    }
    
    public boolean isSecurityEnabled() throws JAXBException, IOException {
        GeneralOptions options = getSecToolsMgr().getGeneralOptions();
        if (options != null) {
            return options.isEnforceSecurity();
        }
        return false;
    }

    public DemoOptions getDemoOptions() throws JAXBException, IOException {
        return getSecToolsMgr().getDemoOptions();
    }

    public void configDemo(DemoUser[] demoUsers, boolean enforceSecurity,
            boolean enforceIndexHtml) throws JAXBException, IOException {
        getSecToolsMgr().configDemo(demoUsers);
        getSecToolsMgr().setGeneralOptions(enforceSecurity, enforceIndexHtml);
    }

    public DatabaseOptions getDatabaseOptions() throws IOException,
            JAXBException {
        DatabaseOptions options = getSecToolsMgr().getDatabaseOptions();
        options = populateJavaSpecificDatabaseParams(options);
        options = setMultiTenancyParms(options);
        return options;
    }

    public Collection<PropertyInfo> getDatabaseProperties(String dataModelName, String entityName) {
        return dataModelMgr.getDataModel(dataModelName).getProperties(entityName);
    }

    public void configDatabase(String modelName, String entityName,
            String unamePropertyName, String uidPropertyName,
            String pwPropertyName, String rolePropertyName,
            String tenantIdField, int defTenantId,
            String rolesByUsernameQuery, boolean enforceSecurity,
            boolean enforceIndexHtml) throws JAXBException, IOException {
        DataModelConfiguration dataModel = dataModelMgr.getDataModel(modelName);
        EntityInfo entity = dataModel.getEntity(entityName);
        String tableName = entity.getTableName();
        String qualifiedTablePrefix = entity.getSchemaName() == null ? 
                entity.getCatalogName() : entity.getSchemaName();
        if (qualifiedTablePrefix != null && qualifiedTablePrefix.length() > 0) {
            tableName = qualifiedTablePrefix + "." + tableName;
        }
        ColumnInfo unameColumn = getColumn(entity, unamePropertyName);
        String unameColumnName = unameColumn.getName();
        ColumnInfo uidColumn = getColumn(entity, uidPropertyName);
        String uidColumnName = uidColumn.getName();
        String uidColumnSqlType = uidColumn.getSqlType();
        ColumnInfo pwColumn = getColumn(entity, pwPropertyName);
        String pwColumnName = pwColumn.getName();
        String roleColumnName = null;
        String tenantIdColum = null;

        if (rolePropertyName != null && rolePropertyName.length() != 0) {
            ColumnInfo roleColumn = getColumn(entity, rolePropertyName);
            roleColumnName = roleColumn.getName();
        }
        getSecToolsMgr().configDatabase(modelName, tableName, unameColumnName,
                uidColumnName, uidColumnSqlType, pwColumnName, roleColumnName,
                rolesByUsernameQuery);
        getSecToolsMgr().setGeneralOptions(enforceSecurity, enforceIndexHtml);

				// only write Tenant information to file if the tenantIdField has a value
				// meaning, if tenantIdField is null then the value for default tenant id will not be stored.
        if (tenantIdField != null && tenantIdField.length() != 0) {
		        String[] propertyNames = {DataServiceConstants.TENANT_FIELD_PROPERTY_NAME,
                DataServiceConstants.DEFAULT_TENANT_ID_PROPERTY_NAME,
                DataServiceConstants.TENANT_COLUMN_PROPERTY_NAME};

						ColumnInfo tenantIdColumInfo = getColumn(entity, tenantIdField);
        		tenantIdColum = tenantIdColumInfo.getName();

        		String[] values = {tenantIdField, (new Integer(defTenantId)).toString(), tenantIdColum};
        		writeToAppProperty(propertyNames, values);
        }
        else {
        		deleteAppProperty();
        }
    }

    private void writeToAppProperty(String[] propNames, String[] propValues) throws IOException {
        Properties props = new Properties();
        for (int i=0; i<propNames.length; i++) {
            props.setProperty(propNames[i], propValues[i]);
        }

        File appProperties =
                new File(projectMgr.getCurrentProject().getProjectRoot() + "/src", CommonConstants.APP_PROPERTY_FILE);
        if (!appProperties.exists()) appProperties.createNewFile();
        FileOutputStream os = new FileOutputStream(appProperties);
        props.store(os, null);
        os.close();
    }
    
    private void deleteAppProperty() throws IOException {
        File appProperties =
                new File(projectMgr.getCurrentProject().getProjectRoot() + "/src", CommonConstants.APP_PROPERTY_FILE);
        if (appProperties.exists()) appProperties.delete();
 		}

    private DatabaseOptions setMultiTenancyParms(DatabaseOptions options) throws IOException {
        Properties props = new Properties();
        File appProperties =
                new File(projectMgr.getCurrentProject().getProjectRoot() + "/src", CommonConstants.APP_PROPERTY_FILE);
        if (!appProperties.exists()) return options;
        FileInputStream is = new FileInputStream(appProperties);
        props.load(is);

        options.setTenantIdField(props.getProperty(DataServiceConstants.TENANT_FIELD_PROPERTY_NAME));
        options.setDefTenantId(Integer.parseInt(props.getProperty(DataServiceConstants.DEFAULT_TENANT_ID_PROPERTY_NAME)));
        is.close();

        return options;
    }

    public List<String> testRolesByUsernameQuery(String modelName,
            String query, String username) {
        DataModelConfiguration dataModel = dataModelMgr.getDataModel(modelName);
        return getSecToolsMgr().testRolesByUsernameQuery(dataModel, query,
                username);
    }

    private ColumnInfo getColumn(EntityInfo entity, String propertyName) {
        Collection<PropertyInfo> props = entity.getProperties();
        for (PropertyInfo prop : props) {
            List<PropertyInfo> cProps = prop.getCompositeProperties();
            if (cProps != null && !cProps.isEmpty()) {
                for (PropertyInfo cProp : cProps) {
                    if (cProp.getName().equals(propertyName)) {
                        return cProp.getColumn();
                    }
                }
            } else if (prop.getName().equals(propertyName)) {
                return prop.getColumn();
            }
        }
        return null;
    }

    private PropertyInfo getProperty(Collection<PropertyInfo> props,
            String columnName) {
        for (PropertyInfo prop : props) {
            List<PropertyInfo> cProps = prop.getCompositeProperties();
            if (cProps != null && !cProps.isEmpty()) {
                for (PropertyInfo cProp : cProps) {
                    if (cProp.getColumn() != null
                            && cProp.getColumn().getName().equals(columnName)) {
                        return cProp;
                    }
                }
            } else if (prop.getColumn() != null
                    && prop.getColumn().getName().equals(columnName)) {
                return prop;
            }
        }
        return null;
    }

    private DatabaseOptions populateJavaSpecificDatabaseParams (
            DatabaseOptions options) {
        DataModelConfiguration dataModel = dataModelMgr.getDataModel(options
                .getModelName());
        Collection<EntityInfo> entities = dataModel.getEntities();
        for (EntityInfo entity : entities) {
            if (entity.getTableName().equals(options.getTableName())) {
                options.setEntityName(entity.getEntityName());
                break;
            }
        }
        Collection<PropertyInfo> properties = dataModel.getProperties(options
                .getEntityName());
        PropertyInfo unameProp = getProperty(properties, options
                .getUnameColumnName());
        if (unameProp != null) {
            options.setUnamePropertyName(unameProp.getName());
        }
        PropertyInfo uidProp = getProperty(properties, options
                .getUidColumnName());
        if (uidProp != null) {
            options.setUidPropertyName(uidProp.getName());
        }
        //System.out.println("PW:" + options.getPwColumnName());
        String pw = options.getPwColumnName().replace(", 1","");
        //System.out.println("PW2:" + pw);
        PropertyInfo pwProp = getProperty(properties, pw);
        if (pwProp != null) {
            options.setPwPropertyName(pwProp.getName());
        }
        String roleColumnName = options.getRoleColumnName();
        if (roleColumnName != null) {
            PropertyInfo roleProp = getProperty(properties, roleColumnName);
            if (roleProp != null) {
                options.setRolePropertyName(roleProp.getName());
            }
        }
        return options;
    }

    public LDAPOptions getLDAPOptions() throws IOException, JAXBException {
        return getSecToolsMgr().getLDAPOptions();
    }

    public void configLDAP(String ldapUrl, String managerDn,
            String managerPassword, String userDnPattern,
            boolean groupSearchingDisabled, String groupSearchBase,
            String groupRoleAttribute, String groupSearchFilter,
            boolean enforceSecurity, boolean enforceIndexHtml)
            throws IOException, JAXBException {
        getSecToolsMgr().configLDAP(ldapUrl, managerDn, managerPassword,
                userDnPattern, groupSearchingDisabled, groupSearchBase,
                groupRoleAttribute, groupSearchFilter);
        getSecToolsMgr().setGeneralOptions(enforceSecurity, enforceIndexHtml);
    }

    public void testLDAPConnection(String ldapUrl, String managerDn,
            String managerPassword) {
        SecurityToolsManager.testLDAPConnection(ldapUrl, managerDn, managerPassword);
    }

    public void configJOSSO( boolean enforceSecurity, String primaryRole) throws JAXBException, IOException {
    	if(enforceSecurity){
    		getSecToolsMgr().registerJOSSOSecurityService();
    		getSecToolsMgr().setJOSSOOptions(primaryRole);
    		//	getSecToolsMgr().setGeneralOptions(enforceSecurity);
    	}
    	else {
    		getSecToolsMgr().removeJOSSOConfig();
    		
    	}
    }
    
    public List<String> getRoles(Boolean isJoSSO) throws IOException, JAXBException {
        if(Boolean.valueOf("true")){
      	 return getSecToolsMgr().getJOSSORoles();
        }
        else
    	return getSecToolsMgr().getRoles();
    }
    
    public List<String> getRoles() throws IOException, JAXBException {
        return getSecToolsMgr().getRoles();
    }

    public List<String> getJOSSORoles() throws IOException, JAXBException {
        return getSecToolsMgr().getJOSSORoles();
    }
    
	public void setRoles(List<String> roles) throws IOException, JAXBException {
		getSecToolsMgr().setRoles(roles);
	}
	
	public void setJOSSORoles(List<String> roles) throws IOException, JAXBException {
		getSecToolsMgr().setJOSSORoles(roles);
	}
	
	/**
	 * Get the current Object Definition Map.
	 * Only the first attribute per URL is returned.
	 * @return The projects Object Definition Source URL Map
	 * @throws JAXBException
	 * @throws IOException
	 */
	public List<SecurityURLMap> getSecurityFilterODS()throws JAXBException, IOException {
		Map<String, List<String>> urlMap = getSecToolsMgr()
				.getSecurityFilterODS();
		List<SecurityURLMap> securityURLMap = new ArrayList<SecurityURLMap>();
		for (String url : urlMap.keySet()) {
			SecurityURLMap secMap = new SecurityURLMap();
			secMap.setURL(url);
			List<String> attributes = urlMap.get(url);
			secMap.setAttributes(attributes.get(0));
			securityURLMap.add(secMap);
		}
		return securityURLMap;
	}

	/**
	 * Adds a new URL Rule to the ODS Filter Map. New entries go after all entries except the default entry.
	 * @param newEntry SecurityURLMap to be added to top of Filter Map
	 */
	public void addODSMapEntry(String URL, String attributes) throws
	JAXBException, IOException {
		SecurityURLMap newEntry = new SecurityURLMap(URL, attributes);
		List<SecurityURLMap> odsMap = getSecurityFilterODS();
		odsMap.add(newEntry);
		setSecurityFilterODS(odsMap);
	}
	
	/**
	 * Remove the first rule matching the URL passed from the ODS Filter Map 
	 * @param deleteMe
	 */
	public void deleteODSMapEntry(String deleteURL) throws
	JAXBException, IOException {
			List<SecurityURLMap> odsMap = getSecurityFilterODS();
			SecurityURLMap newMap = new SecurityURLMap(deleteURL);
			odsMap.remove(newMap);
			setSecurityFilterODS(odsMap);
		
	}
	/**
	 * Set a new Object Definition Source Filter. Replaces previous definition.
	 * Only a single attribute per URL is supported by this interface.
	 * @param securityURLMap The new Object Definition Source URL map
	 * @param preserveOrder Preserve map order
	 * @throws JAXBException
	 * @throws IOException
	 */

	 public void setSecurityFilterODS(List<SecurityURLMap> securityURLMap, Boolean preserveOrder)
	 throws JAXBException, IOException {
		Map<String, List<String>> urlMap = new LinkedHashMap<String, List<String>>();
		Iterator<SecurityURLMap> itr = securityURLMap.iterator();
		while (itr.hasNext()) {
			SecurityURLMap thisEntry = itr.next();
			List<String> attributes = new ArrayList<String>();
			attributes.add(thisEntry.getAttributes());
			urlMap.put(thisEntry.getURL().trim(), attributes);
		}
		//ensure *.json is last if present unless preserve oder specified 
		if(preserveOrder.booleanValue() == false && (urlMap.get("/*.json") != null)){ 
			System.out.println("Moving /*.json entry");
			List<String> jsonEntry = urlMap.remove("/*.json");
			urlMap.put("/*.json", jsonEntry);
			//TODO: Ensure puts in correct place
		}
		getSecToolsMgr().setSecurityFilterODS(urlMap);
	}
	 
	 public void setSecurityFilterODS(List<SecurityURLMap> securityURLMap)
	 throws JAXBException, IOException {
		 setSecurityFilterODS(securityURLMap, Boolean.valueOf("false"));
	 }
	
	public class SecurityURLMap {
		private String URL;
		private String Attributes;

		public SecurityURLMap()
		{
			
		}
		public SecurityURLMap(String URL){
			this.URL = URL;
		}
		public SecurityURLMap(String URL, String Attributes){
			this.URL = URL;
			this.Attributes = Attributes;
		}
		public String getURL() {
			return URL;
		}

		public void setURL(String URL) {
			this.URL = URL;
		}

		public String getAttributes() {
			return Attributes;
		}

		public void setAttributes(String Attributes) {
			this.Attributes = Attributes;
		}

	}
}