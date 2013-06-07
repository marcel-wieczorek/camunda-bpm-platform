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
import org.camunda.bpm.engine.impl.history.event.HistoricActivityInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoricScopeInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoricTaskInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;

/**
 * @author  Marcel Wieczorek
 */
public class HistoricTaskInstanceEventProducer extends HistoricScopeInstanceEventProducer {

    public HistoricTaskInstanceEventProducer(final String eventType) {
        super(eventType);
    }

    @Override
    protected HistoricScopeInstanceEventEntity createEventInstance(final DelegateExecution execution) {
        return new HistoricTaskInstanceEventEntity();
    }

    @Override
    public HistoryEvent createHistoryEvent(final DelegateExecution execution, final DelegateTask task) {
        HistoricTaskInstanceEventEntity eventInstance = (HistoricTaskInstanceEventEntity) createHistoryEvent(execution);
        String id = task.getId();
        String assignee = task.getAssignee();
        eventInstance.setId(id);
        eventInstance.setAssignee(assignee);

        return eventInstance;
    }

    /**
     * initializes the event.
     */
    protected void initEvent(final DelegateExecution execution, final HistoricScopeInstanceEventEntity evt) {
        super.initEvent(execution, evt);

        final ExecutionEntity executionEntity = (ExecutionEntity) execution;
        final HistoricActivityInstanceEventEntity activityInstance = (HistoricActivityInstanceEventEntity) evt;

        final ExecutionEntity subProcessInstance = executionEntity.getSubProcessInstance();
        if (subProcessInstance != null) {
            activityInstance.setCalledProcessInstanceId(subProcessInstance.getId());
        }
    }

}
