package com.pivotal.gemfire.samples.functions;

import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.cache.execute.RegionFunctionContext;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by markito on 4/11/14.
 */
public class ExternalScriptFunctionAdapter extends FunctionAdapter {

  public void ExternalScriptFunctionAdapter () {
  }

  @Override
  public void execute(FunctionContext fc) {

    ScriptEngineManager engineManager = new ScriptEngineManager();
    ScriptEngine engine = engineManager.getEngineByName("nashorn");

    if ((fc.getArguments() != null)) {
      // full path to javascript file
      final String jsFile = ((String[]) fc.getArguments())[0];      // javascript file
      final String method = ((String[]) fc.getArguments())[1];     // method to be called

      try {
        engine.eval(new FileReader(jsFile));

        Invocable invocable = (Invocable) engine;
        RegionFunctionContext rfc = (RegionFunctionContext) fc;

        // call execute function on javaScript side
        invocable.invokeFunction(method, rfc);

      } catch (FileNotFoundException | ScriptException | NoSuchMethodException ex) {
        Logger.getLogger(ExternalScriptFunctionAdapter.class.getName()).log(Level.SEVERE, null, ex);

      }
    }
  }

  @Override
  public String getId() {
    return getClass().getName();
  }

  public boolean isHA() {
    return false;
  }

  @Override
  public boolean hasResult() {
    return true;
  }
}
