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

package org.camunda.bpm.engine.impl.history.handler.refactor;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.producer.HistoryEventProducer;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;


/**
 * @author Tom Baeyens
 */
public class HistoricUserTaskAssignmentListener implements TaskListener {

	
  protected HistoryEventProducer eventProducer;

public HistoricUserTaskAssignmentListener(
			HistoryEventProducer historicActivityInstanceUpdateEventProducer) {
				this.eventProducer = historicActivityInstanceUpdateEventProducer;
	}

public void notify(DelegateTask task) {
    ExecutionEntity execution = ((TaskEntity) task).getExecution();
    if (execution != null) {
      ProcessEngineConfigurationImpl processEngineConfiguration = Context.getProcessEngineConfiguration();
      HistoryEvent historyEvent = eventProducer.createHistoryEvent(execution, task);
      processEngineConfiguration.getHistoryEventHandler().handleEvent(historyEvent);
    }
  }
  
}
