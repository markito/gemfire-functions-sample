package com.pivotal.gemfire.samples.functions;

import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;

/**
 * Created by markito on 4/29/14.
 */
public class HelloFunction extends FunctionAdapter {

  @Override
  public void execute(FunctionContext functionContext) {
    System.out.println("Hello, I'm running here");
  }

  @Override
  public String getId() {
    return HelloFunction.class.getCanonicalName();
  }

  @Override
  public boolean hasResult() {
    return false;
  }

  @Override
  public boolean isHA() {
    return false;
  }
}
