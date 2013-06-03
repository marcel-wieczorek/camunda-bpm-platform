/* Licensed under the Apache License, Version 2.0 (the "License");
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
package org.camunda.bpm.engine.impl.history.producer;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.impl.cfg.IdGenerator;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.history.event.HistoricScopeInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.util.ClockUtil;

/**
 * @author Marcel Wieczorek
 * 
 */
public abstract class HistoricScopeInstanceEventProducer implements HistoryEventProducer {

  protected final String eventType;

  public HistoricScopeInstanceEventProducer(String eventType) {
    this.eventType = eventType;
  }

  public HistoryEvent createHistoryEvent(DelegateExecution execution) {

    // create the event instance
    HistoricScopeInstanceEventEntity evt = createEventInstance(execution);

    // initialize the event
    initEvent(execution, evt);

    return evt;
  }

  /** creates the actual event instance. */
  protected abstract HistoricScopeInstanceEventEntity createEventInstance(DelegateExecution execution);

  /*
   * (non-Javadoc)
   * 
   * @see org.camunda.bpm.engine.impl.history.producer.HistoryEventProducer#
   * createHistoryEvent(org.camunda.bpm.engine.delegate.DelegateExecution,
   * org.camunda.bpm.engine.delegate.DelegateTask)
   */
  @Override
  public HistoryEvent createHistoryEvent(DelegateExecution execution, DelegateTask task) {
    return null;
  }

  /** initializes the event */
  protected void initEvent(DelegateExecution execution, HistoricScopeInstanceEventEntity evt) {

    final ExecutionEntity executionEntity = (ExecutionEntity) execution;
    final IdGenerator idGenerator = Context.getProcessEngineConfiguration().getIdGenerator();

    String processDefinitionId = executionEntity.getProcessDefinitionId();
    String processInstanceId = executionEntity.getProcessInstanceId();
    String executionId = execution.getId();
    String activityId = executionEntity.getActivityId();
    String activityInstanceId = execution.getActivityInstanceId();
    String parentActivityInstanceId = execution.getParentActivityInstanceId();

    evt.setTimestamp(ClockUtil.getCurrentTime());
    evt.setId(idGenerator.getNextId());
    evt.setProcessDefinitionId(processDefinitionId);
    evt.setProcessInstanceId(processInstanceId);
    evt.setExecutionId(executionId);
    evt.setActivityInstanceId(activityInstanceId);
    evt.setParentActivityInstanceId(parentActivityInstanceId);

    if (activityId != null) {
      evt.setActivityId(activityId);
      evt.setActivityName((String) executionEntity.getActivity().getProperty("name"));
      evt.setActivityType((String) executionEntity.getActivity().getProperty("type"));
    }

    // set the event type
    evt.setEventType(eventType);
  }

}
