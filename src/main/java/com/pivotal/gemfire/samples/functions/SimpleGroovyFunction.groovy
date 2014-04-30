package com.pivotal.gemfire.samples.functions

import com.gemstone.gemfire.cache.Region
import com.gemstone.gemfire.cache.execute.FunctionAdapter
import com.gemstone.gemfire.cache.execute.FunctionContext
import com.gemstone.gemfire.cache.execute.RegionFunctionContext
import com.gemstone.gemfire.cache.partition.PartitionRegionHelper
/**
 * @author markito
 */
public class SimpleGroovyFunction extends FunctionAdapter {
  def creditCadGen = Class.forName("com.pivotal.gemfire.samples.generator.RandomCreditCardGenerator");

  @Override
  void execute(FunctionContext functionContext) {
    RegionFunctionContext rfc = (RegionFunctionContext) functionContext;
    Region<Object,Object> region = PartitionRegionHelper.getLocalDataForContext(rfc);
    int counter = 0;
    // check every credit card and clear invalid ones
    region.collect({ id, customer ->
      if (!creditCadGen.isValidCreditCardNumber(customer.ccNumber)) {
        customer.ccNumber = ""
        region.put(id, customer);
        println("Customer $id has an invalid credit card.");
        counter++;
      }
    });
    rfc.getResultSender().lastResult("Done. " + counter + " changed objects");
  }

  @Override
  String getId() {
    return this.getClass().getCanonicalName();
  }

  @Override
  boolean hasResult()   {
    return true;
  }

  @Override
  boolean isHA()   {
    return false;
  }

}