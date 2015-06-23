JDOSMaster
========

Java Denial of Service Server Master

INFO
====

This same code was used in JStresser. http://www.jscanner.net/jstresser

Make sure to add all JDOSServers to the ATTACK_SERVERS array list. (i.e. new Server(id, ipAddress, port))

Make sure that all attack servers have hping3 installed and are running JDOSServer as root


API
==================

/attack?id=1&ip=127.0.0.1&port=80&method=syn&time=60 - starts an attack on a server with user id 1

/stop?id=1 - stops the server that user id 1 started

/check - lists times of all servers


Requirements For Compilation
============

Java 8 (JDK) - http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html


Donations
=========

Bitcoin Address - 14ESpgxqm1eJ1mnhbg1XeVEum54gejcKXg
