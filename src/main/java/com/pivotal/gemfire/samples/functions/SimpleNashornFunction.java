package com.pivotal.gemfire.samples.functions;

import com.gemstone.gemfire.cache.execute.Function;
import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;

/**
 * Created by markito on 4/11/14.
 */
public class SimpleNashornFunction extends FunctionAdapter {

  private static ScriptEngineManager engineManager;
  private static ScriptEngine engine;
  private static Function _function;

  private final String jsFile = "NashornFunction.js";

  public SimpleNashornFunction() throws ScriptException, FileNotFoundException, UnsupportedEncodingException {

    engineManager = new ScriptEngineManager();
    engine = engineManager.getEngineByName("nashorn");

    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(jsFile);
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

    engine.eval(reader);

    Invocable invocable = (Invocable) engine;
    _function = invocable.getInterface(com.gemstone.gemfire.cache.execute.Function.class);
  }

  @Override
  public void execute(FunctionContext fc) {
    _function.execute(fc);
  }

  @Override
  public String getId() {
    return _function.getId();
  }

}
