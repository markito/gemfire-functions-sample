package com.pivotal.gemfire.samples.loader;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.pivotal.gemfire.samples.generator.RandomCreditCardGenerator;
import com.pivotal.gemfire.samples.entity.Customer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

/**
 * Created by markito on 4/08/14.
 */
public class LoadData {

  static Logger logger = Logger.getAnonymousLogger();
  static final int ENTRIES = 500;
  final static String REGION = "exampleRegion";
  private static ScriptEngineManager engineManager;
  private static ScriptEngine engine;

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws ScriptException, FileNotFoundException {

    logger.info("Connecting to the client...");

    ClientCache cache = new ClientCacheFactory()
            .set("name", "NashornClient")
            .set("cache-xml-file", "cacheClient.xml")
            .create();

    logger.info("Connected.");

    Region<String, Customer> exampleRegion = cache.getRegion(REGION);

    for (int i = 1; i <= ENTRIES; i++) {
      exampleRegion.put(String.valueOf(i), generateCustomer(i));
    }
    logger.info("Data loaded.");
  }

  private static Customer generateCustomer(final int i) {
    final String name = ((i % 2 == 0) ? "John" : "Mary") + i;
    // missing @ - some invalid e-mails
    final String mail = ((i % 2 == 0) ? "example.com" : "@example.com");

    String ccNumber = ((i % 2 == 0) ? RandomCreditCardGenerator.generateMasterCardNumber(): RandomCreditCardGenerator.generateVisaCardNumber() + 1);

    return new Customer(i, name, name.toLowerCase() + mail, ccNumber);
  }

}
