package org.infinispan.wfink.playground.listeners;

import java.util.concurrent.TimeUnit;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

/**
 * A simple client which use the Message class with annotations to generate the schema and marshaller for Protobuf. The queries are using a simple field and one analyzed for full-text search. If the server side cache does not have Indexing enables it shows that the full-text query will not work
 * without.
 *
 * @author <a href="mailto:WolfDieter.Fink@gmail.com">Wolf-Dieter Fink</a>
 */
public class SimpleListenerHotRodClient {
  private RemoteCacheManager remoteCacheManager;
  private RemoteCache<String, String> remoteCache;

  public SimpleListenerHotRodClient(String host, String port, String cacheName) {
    ConfigurationBuilder remoteBuilder = new ConfigurationBuilder();
    remoteBuilder.addServer().host(host).port(Integer.parseInt(port));

    remoteCacheManager = new RemoteCacheManager(remoteBuilder.build());
    remoteCache = remoteCacheManager.getCache(cacheName);

    if (remoteCache == null) {
      throw new RuntimeException("Cache '" + cacheName + "' not found. Please make sure the server is properly configured");
    }
    remoteCache.addClientListener(new SimplePrintListener());
  }

  private void stop() {
    remoteCacheManager.stop();
  }

  private void loop() {
    for (int i = 0; i < 100; i++) {
      try {
        System.out.println("LOOP " + i);
        remoteCache.put(String.valueOf(i), String.valueOf(i));
        Thread.sleep(1000);
        remoteCache.put(String.valueOf(i), String.valueOf(i) + "change", 10, TimeUnit.SECONDS);
        Thread.sleep(500);
      } catch (InterruptedException e) {
        // ignore
      } catch (Exception e) {
        e.printStackTrace();
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e1) {
        }
      }
    }

    System.out.println("Start remove");
    for (int i = 0; i < 100; i++) {
      remoteCache.remove(String.valueOf(i));
    }
    System.out.println("LOOP done");
  }

  public static void main(String[] args) {
    String host = "localhost";
    String port = "11222";
    String cacheName = "default";

    if (args.length > 0) {
      port = args[0];
    }
    if (args.length > 1) {
      port = args[1];
    }
    SimpleListenerHotRodClient client = new SimpleListenerHotRodClient(host, port, cacheName);

    client.loop();

    client.stop();
    System.out.println("\nDone !");
  }
}
