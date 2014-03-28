Geographic probes for multi-scales distributed systems
============

This repository is about the final project we have been working on during our last year in Telecom SudParis with Arnaud SAUNIER. It’s a part of the ANR (French research national agency) [project INCOME](http://www.irit.fr/income/), that aims to provide a middleware infrastructure for the management of multiscale contexts for the Internet of Things with different kinds of services (acquisition of context data, production of higher level of abstraction information).
 
Our work focuses on a geographic approach to the system, with geographic localization measures and development of multiscale geographic probes. The problem was the following: how do we consider the geographic scales associated to a consumer in the data exchange with other consumers or producers ? We first explain how to define and determine a geographic scale associated to a consumer, and the advantage of using a “multiscale” system: every consumer will be defined by his/her geographic information, i.e. by different instances of scales (country, region, city, suburb, neighbourhood, street, building, coordinates).
 
We have used many Java API and libraries (for the geographic localization, to determine a higher level of abstraction of information and to display geographic information on maps) and our report explains why we decided to use those APIs and libraries and not other ones.
 
We describe in our report all the services our final API will offer, such as the determination of the smallest common “geographic path” (i.e. the common instances of scales) between a list of clients or the determination of the list of all clients who are in the same instance of scale as another client.


You'll find on this repository the source code in the OSM directory, the javadoc and our report (in french). 
