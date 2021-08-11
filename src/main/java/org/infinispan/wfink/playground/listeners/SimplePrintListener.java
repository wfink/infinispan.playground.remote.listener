package org.infinispan.wfink.playground.listeners;

import org.infinispan.client.hotrod.annotation.ClientCacheEntryCreated;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryExpired;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryModified;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryRemoved;
import org.infinispan.client.hotrod.annotation.ClientListener;
import org.infinispan.client.hotrod.event.ClientCacheEntryCreatedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryExpiredEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryModifiedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryRemovedEvent;

/**
 * Listener for cache entry events used by Hotrod client.
 *
 * @author Wolf Dieter Fink
 */
@ClientListener
public class SimplePrintListener {
  @ClientCacheEntryCreated
  public void printCreated(ClientCacheEntryCreatedEvent<String> event) {
    System.out.println("LISTENER created : " + event.getKey());
  }

  @ClientCacheEntryRemoved
  public void printRemoved(ClientCacheEntryRemovedEvent<String> event) {
    System.out.println("LISTENER removed : " + event.getKey());
  }

  @ClientCacheEntryExpired
  public void printExpiration(ClientCacheEntryExpiredEvent<String> event) {
    System.out.println("LISTENER expired : " + event.getKey() + "  type=" + event.getType());
  }

  @ClientCacheEntryModified
  public void printModified(ClientCacheEntryModifiedEvent<String> event) {
    System.out.println("LISTENER modified : " + event.getKey() + "  type=" + event.getType());
  }
}