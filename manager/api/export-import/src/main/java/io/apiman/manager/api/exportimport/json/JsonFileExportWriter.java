/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.apiman.manager.api.exportimport.json;

import io.apiman.manager.api.beans.apps.ApplicationBean;
import io.apiman.manager.api.beans.apps.ApplicationVersionBean;
import io.apiman.manager.api.beans.audit.AuditEntryBean;
import io.apiman.manager.api.beans.contracts.ContractBean;
import io.apiman.manager.api.beans.gateways.GatewayBean;
import io.apiman.manager.api.beans.idm.RoleBean;
import io.apiman.manager.api.beans.idm.RoleMembershipBean;
import io.apiman.manager.api.beans.idm.UserBean;
import io.apiman.manager.api.beans.orgs.OrganizationBean;
import io.apiman.manager.api.beans.plans.PlanBean;
import io.apiman.manager.api.beans.plans.PlanVersionBean;
import io.apiman.manager.api.beans.plugins.PluginBean;
import io.apiman.manager.api.beans.policies.PolicyBean;
import io.apiman.manager.api.beans.policies.PolicyDefinitionBean;
import io.apiman.manager.api.beans.services.ServiceBean;
import io.apiman.manager.api.beans.services.ServiceVersionBean;
import io.apiman.manager.api.core.logging.IApimanLogger;
import io.apiman.manager.api.exportimport.GlobalElementsEnum;
import io.apiman.manager.api.exportimport.OrgElementsEnum;
import io.apiman.manager.api.exportimport.beans.MetadataBean;
import io.apiman.manager.api.exportimport.write.IExportWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * Stream global elements
 *
 * @author Marc Savy {@literal <msavy@redhat.com>}
 */
public class JsonFileExportWriter extends AbstractJsonWriter<GlobalElementsEnum> implements IExportWriter {

    private JsonFactory jsonFactory = new JsonFactory();
    private JsonGenerator jg;
    private Map<Enum<GlobalElementsEnum>, Boolean> finished = new HashMap<>();
    private ObjectMapper om = new ObjectMapper();

    {
        for (GlobalElementsEnum v : GlobalElementsEnum.values()) {
            finished.put(v, false);
        }
    }

    /**
     * Constructor.
     * @param targetFile
     * @param logger
     * @throws IOException
     */
    public JsonFileExportWriter(OutputStream targetFile, IApimanLogger logger) throws IOException {
        super(logger);
        om.setSerializationInclusion(Inclusion.NON_NULL);
        jg = jsonFactory.createJsonGenerator(targetFile, JsonEncoding.UTF8);
        jg.useDefaultPrettyPrinter();
        jg.setCodec(om);
        jg.writeStartObject(); // Set out the base/root object
    }

    @Override
    protected JsonGenerator jsonGenerator() {
        return jg;
    }

    @Override
    protected Map<Enum<GlobalElementsEnum>, Boolean> finished() {
        return finished;
    }

    @Override
    public IExportWriter writeMetadata(MetadataBean metadata) {
        validityCheckStart(GlobalElementsEnum.Metadata);
        writePojo(GlobalElementsEnum.Metadata, metadata);
        return this;
    }

    @Override
    public IExportWriter startGateways() {
        validityCheckStart(GlobalElementsEnum.Gateways);
        lock(GlobalElementsEnum.Gateways);
        writeStartArray(GlobalElementsEnum.Gateways);
        return this;
    }

    @Override
    public IExportWriter writeGateway(GatewayBean gb) {
        writeCheck(GlobalElementsEnum.Gateways);
        writePojo(gb);
        return this;
    }

    @Override
    public IExportWriter endGateways() {
        validityCheckEnd(GlobalElementsEnum.Gateways);
        writeEndArray(GlobalElementsEnum.Gateways);
        unlock(GlobalElementsEnum.Gateways);
        return this;
    }

    @Override
    public IExportWriter startPlugins() {
        validityCheckStart(GlobalElementsEnum.Plugins);
        lock(GlobalElementsEnum.Plugins);
        writeStartArray(GlobalElementsEnum.Plugins);
        return this;
    }

    @Override
    public IExportWriter writePlugin(PluginBean pb) {
        writeCheck(GlobalElementsEnum.Plugins);
        writePojo(pb);
        return this;
    }

    @Override
    public IExportWriter endPlugins() {
        validityCheckEnd(GlobalElementsEnum.Plugins);
        writeEndArray(GlobalElementsEnum.Plugins);
        unlock(GlobalElementsEnum.Plugins);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startPolicyDefs()
     */
    @Override
    public IExportWriter startPolicyDefs() {
        validityCheckStart(GlobalElementsEnum.PolicyDefinitions);
        lock(GlobalElementsEnum.PolicyDefinitions);
        writeStartArray(GlobalElementsEnum.PolicyDefinitions);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#writePolicyDef(io.apiman.manager.api.beans.policies.PolicyDefinitionBean)
     */
    @Override
    public IExportWriter writePolicyDef(PolicyDefinitionBean policyDef) {
        writeCheck(GlobalElementsEnum.PolicyDefinitions);
        writePojo(policyDef);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endPolicyDefs()
     */
    @Override
    public IExportWriter endPolicyDefs() {
        validityCheckEnd(GlobalElementsEnum.PolicyDefinitions);
        writeEndArray(GlobalElementsEnum.PolicyDefinitions);
        unlock(GlobalElementsEnum.PolicyDefinitions);
        return this;
    }

    @Override
    public IExportWriter startUsers() {
        validityCheckStart(GlobalElementsEnum.Users);
        lock(GlobalElementsEnum.Users);
        writeStartArray(GlobalElementsEnum.Users);
        return this;
    }

    @Override
    public IExportWriter writeUser(UserBean user) {
        writeCheck(GlobalElementsEnum.Users);
        writePojo(user);
        return this;
    }

    @Override
    public IExportWriter endUsers() {
        validityCheckEnd(GlobalElementsEnum.Users);
        writeEndArray(GlobalElementsEnum.Users);
        unlock(GlobalElementsEnum.Users);
        return this;
    }

    @Override
    public IExportWriter startRoles() {
        validityCheckStart(GlobalElementsEnum.Roles);
        lock(GlobalElementsEnum.Roles);
        writeStartArray(GlobalElementsEnum.Roles);
        return this;
    }

    @Override
    public IExportWriter writeRole(RoleBean role) {
        writeCheck(GlobalElementsEnum.Roles);
        writePojo(role);
        return this;
    }

    @Override
    public IExportWriter endRoles() {
        validityCheckEnd(GlobalElementsEnum.Roles);
        writeEndArray(GlobalElementsEnum.Roles);
        unlock(GlobalElementsEnum.Roles);
        return this;
    }

    @Override
    public IExportWriter startOrgs() {
        validityCheckStart(GlobalElementsEnum.Orgs);
        lock(GlobalElementsEnum.Orgs);
        writeStartArray(GlobalElementsEnum.Orgs);
        return this;
    }
    
    @Override
    public IExportWriter startOrg(OrganizationBean org) {
        writeStartObject();
        try {
            jg.writeObjectField(OrganizationBean.class.getSimpleName(), org);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public IExportWriter startMemberships() {
        writeStartArray(OrgElementsEnum.Memberships);
        return this;
    }

    @Override
    public IExportWriter writeMembership(RoleMembershipBean membership) {
        writePojo(membership);
        return this;
    }

    @Override
    public IExportWriter endMemberships() {
        writeEndArray(OrgElementsEnum.Memberships);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startPlans()
     */
    @Override
    public IExportWriter startPlans() {
        writeStartArray(OrgElementsEnum.Plans);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startPlan(io.apiman.manager.api.beans.plans.PlanBean)
     */
    @Override
    public IExportWriter startPlan(PlanBean plan) {
        writeStartObject();
        try {
            plan.setOrganization(null);
            jg.writeObjectField(PlanBean.class.getSimpleName(), plan);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startPlanVersions()
     */
    @Override
    public IExportWriter startPlanVersions() {
        writeStartArray(OrgElementsEnum.Versions);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startPlanVersion(io.apiman.manager.api.beans.plans.PlanVersionBean)
     */
    @Override
    public IExportWriter startPlanVersion(PlanVersionBean pvb) {
        writeStartObject();
        try {
            pvb.setPlan(null);
            jg.writeObjectField(PlanVersionBean.class.getSimpleName(), pvb);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startPlanPolicies()
     */
    @Override
    public IExportWriter startPlanPolicies() {
        writeStartArray(OrgElementsEnum.Policies);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#writePlanPolicy(io.apiman.manager.api.beans.policies.PolicyBean)
     */
    @Override
    public IExportWriter writePlanPolicy(PolicyBean policy) {
        return writePolicy(policy);
    }
    
    /**
     * @param policy
     * @return
     */
    private IExportWriter writePolicy(PolicyBean policy) {
        PolicyDefinitionBean definition = new PolicyDefinitionBean();
        definition.setId(policy.getDefinition().getId());
        policy.setDefinition(definition);
        writePojo(policy);
        return this;
    }

    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endPlanPolicies()
     */
    @Override
    public IExportWriter endPlanPolicies() {
        writeEndArray(OrgElementsEnum.Policies);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endPlanVersion()
     */
    @Override
    public IExportWriter endPlanVersion() {
        writeEndObject();
        return this;
    }

    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endPlanVersions()
     */
    @Override
    public IExportWriter endPlanVersions() {
        writeEndArray();
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endPlan()
     */
    @Override
    public IExportWriter endPlan() {
        writeEndObject();
        return this;
    }

    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endPlans()
     */
    @Override
    public IExportWriter endPlans() {
        writeEndArray(OrgElementsEnum.Plans);
        return this;
    }

    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startServices()
     */
    @Override
    public IExportWriter startServices() {
        writeStartArray(OrgElementsEnum.Services);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startService(io.apiman.manager.api.beans.services.ServiceBean)
     */
    @Override
    public IExportWriter startService(ServiceBean service) {
        writeStartObject();
        try {
            service.setOrganization(null);
            jg.writeObjectField(ServiceBean.class.getSimpleName(), service);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startServiceVersions()
     */
    @Override
    public IExportWriter startServiceVersions() {
        writeStartArray(OrgElementsEnum.Versions);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startServiceVersion(io.apiman.manager.api.beans.services.ServiceVersionBean)
     */
    @Override
    public IExportWriter startServiceVersion(ServiceVersionBean pvb) {
        writeStartObject();
        try {
            pvb.setService(null);
            jg.writeObjectField(ServiceVersionBean.class.getSimpleName(), pvb);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startServicePolicies()
     */
    @Override
    public IExportWriter startServicePolicies() {
        writeStartArray(OrgElementsEnum.Policies);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#writeServicePolicy(io.apiman.manager.api.beans.policies.PolicyBean)
     */
    @Override
    public IExportWriter writeServicePolicy(PolicyBean policy) {
        return writePolicy(policy);
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endServicePolicies()
     */
    @Override
    public IExportWriter endServicePolicies() {
        writeEndArray(OrgElementsEnum.Policies);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endServiceVersion()
     */
    @Override
    public IExportWriter endServiceVersion() {
        writeEndObject();
        return this;
    }

    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endServiceVersions()
     */
    @Override
    public IExportWriter endServiceVersions() {
        writeEndArray();
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endService()
     */
    @Override
    public IExportWriter endService() {
        writeEndObject();
        return this;
    }

    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endServices()
     */
    @Override
    public IExportWriter endServices() {
        writeEndArray(OrgElementsEnum.Services);
        return this;
    }

    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startApplications()
     */
    @Override
    public IExportWriter startApplications() {
        writeStartArray(OrgElementsEnum.Apps);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startApplication(io.apiman.manager.api.beans.applications.ApplicationBean)
     */
    @Override
    public IExportWriter startApplication(ApplicationBean application) {
        writeStartObject();
        try {
            application.setOrganization(null);
            jg.writeObjectField(ApplicationBean.class.getSimpleName(), application);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startApplicationVersions()
     */
    @Override
    public IExportWriter startApplicationVersions() {
        writeStartArray(OrgElementsEnum.Versions);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startApplicationVersion(io.apiman.manager.api.beans.applications.ApplicationVersionBean)
     */
    @Override
    public IExportWriter startApplicationVersion(ApplicationVersionBean pvb) {
        writeStartObject();
        try {
            pvb.setApplication(null);
            jg.writeObjectField(ApplicationVersionBean.class.getSimpleName(), pvb);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startApplicationPolicies()
     */
    @Override
    public IExportWriter startApplicationPolicies() {
        writeStartArray(OrgElementsEnum.Policies);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#writeApplicationPolicy(io.apiman.manager.api.beans.policies.PolicyBean)
     */
    @Override
    public IExportWriter writeApplicationPolicy(PolicyBean policy) {
        return writePolicy(policy);
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endApplicationPolicies()
     */
    @Override
    public IExportWriter endApplicationPolicies() {
        writeEndArray(OrgElementsEnum.Policies);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startApplicationContracts()
     */
    @Override
    public IExportWriter startApplicationContracts() {
        writeStartArray(OrgElementsEnum.Contracts);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#writeApplicationContract(io.apiman.manager.api.beans.contracts.ContractBean)
     */
    @Override
    public IExportWriter writeApplicationContract(ContractBean cb) {
        writePojo(cb);
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endApplicationContracts()
     */
    @Override
    public IExportWriter endApplicationContracts() {
        writeEndArray();
        return this;
    }

    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endApplicationVersion()
     */
    @Override
    public IExportWriter endApplicationVersion() {
        writeEndObject();
        return this;
    }

    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endApplicationVersions()
     */
    @Override
    public IExportWriter endApplicationVersions() {
        writeEndArray();
        return this;
    }
    
    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endApplication()
     */
    @Override
    public IExportWriter endApplication() {
        writeEndObject();
        return this;
    }

    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endApplications()
     */
    @Override
    public IExportWriter endApplications() {
        writeEndArray(OrgElementsEnum.Apps);
        return this;
    }

    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#startAudits()
     */
    @Override
    public IExportWriter startAudits() {
        writeStartArray(OrgElementsEnum.Audits);
        return this;
    }

    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#writeAudit(io.apiman.manager.api.beans.audit.AuditEntryBean)
     */
    @Override
    public IExportWriter writeAudit(AuditEntryBean ab) {
        writePojo(ab);
        return this;
    }

    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endAudits()
     */
    @Override
    public IExportWriter endAudits() {
        writeEndArray(OrgElementsEnum.Audits);
        return this;
    }

    /**
     * @see io.apiman.manager.api.exportimport.write.IExportWriter#endOrg()
     */
    @Override
    public IExportWriter endOrg() {
        writeEndObject();
        return this;
    }

    @Override
    public IExportWriter endOrgs() {
        validityCheckEnd(GlobalElementsEnum.Orgs);
        writeEndArray();
        unlock(GlobalElementsEnum.Orgs);
        return this;
    }

    @Override
    public void close() {
        try {
            writeEndObject();
            jg.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
