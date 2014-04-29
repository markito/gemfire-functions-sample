package com.pivotal.gemfire.samples.functions;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.cache.execute.RegionFunctionContext;
import com.gemstone.gemfire.cache.partition.PartitionRegionHelper;
import com.pivotal.gemfire.samples.entity.Customer;
import com.pivotal.gemfire.samples.generator.RandomCreditCardGenerator;

/**
 * Created by markito on 4/9/14.
 */
public class SimpleFunction extends FunctionAdapter {

  @Override
  public void execute(FunctionContext functionContext) {
    RegionFunctionContext rfc = (RegionFunctionContext) functionContext;
    Region<Object, Customer> region = PartitionRegionHelper.getLocalDataForContext(rfc);

    // check every credit card and clear invalid ones
    region.forEach((Object id, Customer customer) -> {
      if (!RandomCreditCardGenerator.isValidCreditCardNumber(customer.getCcNumber())) {
        customer.setCcNumber("");
        System.out.println(String.format("Customer %s has an invalid credit card.", id));
        region.put(id, customer);
      }
    });

    rfc.getResultSender().lastResult("Done.");
  }

  @Override
  public String getId() {
    return this.getClass().getCanonicalName();
  }

  @Override
  public boolean hasResult() {
    return true;
  }

  @Override
  public boolean isHA() {
    return false;
  }
}
