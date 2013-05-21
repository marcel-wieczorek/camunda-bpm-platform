/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camunda.bpm.engine.impl.persistence.entity;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.history.HistoricProcessInstance;

/**
 * @author Tom Baeyens
 * @author Christian Stettler
 */
public class HistoricProcessInstanceEntity extends HistoricScopeInstanceEntity implements HistoricProcessInstance {

  private static final long serialVersionUID = 1L;
  
  protected String endActivityId;
  protected String businessKey;
  protected String startUserId;
  protected String startActivityId;
  protected String superProcessInstanceId;

  public HistoricProcessInstanceEntity() {
  }
  
  public Object getPersistentState() {
    Map<String, Object> persistentState = (Map<String, Object>) new HashMap<String, Object>();
    persistentState.put("endTime", endTime);
    persistentState.put("durationInMillis", durationInMillis);
    persistentState.put("deleteReason", deleteReason);
    persistentState.put("endStateName", endActivityId);
    persistentState.put("superProcessInstanceId", superProcessInstanceId);
    persistentState.put("processDefinitionId", processDefinitionId);
    return persistentState;
  }

  // getters and setters //////////////////////////////////////////////////////
  
  
  public String getEndActivityId() {
    return endActivityId;
  }
  public String getBusinessKey() {
    return businessKey;
  }
  public void setBusinessKey(String businessKey) {
    this.businessKey = businessKey;
  }
  public void setEndActivityId(String endActivityId) {
    this.endActivityId = endActivityId;
  }
  public String getStartUserId() {
    return startUserId;
  }
  public void setStartUserId(String startUserId) {
    this.startUserId = startUserId;
  }
  public String getStartActivityId() {
    return startActivityId;
  }
  public void setStartActivityId(String startUserId) {
    this.startActivityId = startUserId;
  }
  public String getSuperProcessInstanceId() {
	return superProcessInstanceId;
  }
  public void setSuperProcessInstanceId(String superProcessInstanceId) {
	this.superProcessInstanceId = superProcessInstanceId;
  }
  
}
