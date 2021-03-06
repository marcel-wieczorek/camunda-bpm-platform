package org.camunda.bpm.engine.rest.wink;

import org.camunda.bpm.engine.rest.AbstractExecutionRestServiceInteractionTest;
import org.camunda.bpm.engine.rest.util.EmbeddedServerBootstrap;
import org.camunda.bpm.engine.rest.util.WinkTomcatServerBootstrap;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class ExecutionRestServiceInteractionTest extends AbstractExecutionRestServiceInteractionTest {

  protected static EmbeddedServerBootstrap serverBootstrap;  
  
  @BeforeClass
  public static void setUpEmbeddedRuntime() {
    serverBootstrap = new WinkTomcatServerBootstrap();
    serverBootstrap.start();
  }
  
  @AfterClass
  public static void tearDownEmbeddedRuntime() {
    serverBootstrap.stop();
  }
}
