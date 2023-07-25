package io.graphql.dao;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nullable;

import org.jboss.logging.Logger;

import io.graphql.core.Greeting;

public class AbstractGreetingRepository implements GreetingRepository {

  //private final Logger logger = LoggerFactory.getLogger(AbstractGreetingRepository.class);
  private Logger logger = Logger.getLogger(AbstractGreetingRepository.class);

  @Nullable
  private Map<Integer, Greeting> map;

  public AbstractGreetingRepository(@Nullable Map<Integer, Greeting> map) {
    this.map = map;
  }

  @Override
  public Greeting findGreetingById(Long id) {
    for (Map.Entry<Integer, Greeting> ids : map.entrySet()) {
      if (id == ids.getValue().getId()) {
        logger.info("Greeting " + ids.getValue().getGreeting() + " with id "
          + ids.getValue().getId() + " was retrieved.");
        logger.info("List with selected greeting retrieved: " + map.values());
        return map.get(ids.getKey());
      }
    }
    return null;
  }

  @Override
  public Greeting createGreeting(Greeting greeting) {
    final Greeting newGreeting = Greeting.newInstance();
    long id = map.size();
    id++;
    newGreeting.setId(id);
    newGreeting.setGreeting(greeting.getGreeting());
    saveGreeting(newGreeting);
    logger.info("Create Greeting: " + newGreeting);
    logger.info("New list of greetings: " + map.values());
    return newGreeting;
  }

  @Override
  public Greeting updateGreeting(Long id, Greeting greeting) {
    for (Map.Entry<Integer, Greeting> ids : map.entrySet()) {
      logger.info("Greeting to be updated: " + "Id: " + ids.getValue().getId()
          + ", Greeting: " + ids.getValue().getGreeting());
      if (ids.getValue().getId() == id) {
        ids.getValue().setId(id);
        ids.getValue().setGreeting(greeting.getGreeting());
        updateGreeting(ids.getKey(), ids.getValue());
        logger.info("Greeting updated: " + "Id: " + ids.getValue().getId()
          + ", Greeting: " + ids.getValue().getGreeting());
        logger.info("Greeting updated in list: " + map.values());
        return ids.getValue();
      }
    }
    return null; 
  }

  @Override
  public Collection<Greeting> findGreetings() {
    logger.info("Receive a list of greetings: " + map.values());
    return map.values();
  }

  @Override
  public void deleteGreeting(Long id) {
    for (Map.Entry<Integer, Greeting> ids : map.entrySet()) {
      if (id == ids.getValue().getId()) {
        map.remove(ids.getKey());
        logger.info("Greeting " + ids.getValue().getGreeting() + " with id "
          + ids.getValue().getId() + " was removed.");
        logger.info("List with selected greeting removed: " + map.values());
        return;
      }
    }
  }

  private void saveGreeting(Greeting greeting) {
    Integer keySet = map.size();
    keySet++;
    map.put(keySet, greeting);
  }
  
  private void updateGreeting(int key, Greeting greeting) {
    map.put(key, greeting);
  }
   
}
