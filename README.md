# infinispan.playground.remote.listener
Infinispan HotRod remote listener events
===============================

Author: Wolf-Dieter Fink
Level: Basics
Technologies: Infinispan, Hot Rod


What is it?
-----------

Example how to use HotRod client side listener to receive cache events

Hot Rod is a binary TCP client-server protocol. The Hot Rod protocol facilitates faster client and server interactions in comparison to other text based protocols and allows clients to make decisions about load balancing, failover and data location operations.

This example demonstrates how to register a client side CacheListener for the create, remove, modify and expire events.


Prepare a server instance
-------------
Simple start a Infinispan 10+ server and change the configuration within the infinispan.xml

   <cache-container name="default" statistics="true">
      ...
      <distributed-cache name="default"/>
   </cache-container>

Note that the authorization has been removed from the default configuration.
To let the client run please remove the security for the endpoint as well.
It is possible to start 1 or more servers, the code will use the server on localhost with the default port 11222.

     bin/server.sh [-o 100] -n <node name> [-s <server directory>]


Build and Run the examples
-------------------------
1. Type this command to build :

        mvn clean package

2. Run the client

   Use maven to start one or more clients

         mvn exec:java

   This example will add and modify entries with key 0....99.
   The client output will show that events are received, note that the expired events will happen in chunks if the ExpirationReaper will remove them.

