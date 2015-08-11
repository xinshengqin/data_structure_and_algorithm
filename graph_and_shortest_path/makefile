# define a makefile variable for the java compiler
JCC = javac

# define a makefile variable for compilation flags
# the -g flag compiles with debugging information
JFLAGS = -g -d bin

# typing 'make' will invoke the first target entry in the makefile 
# (the default one in this case)
#default: Average.class Convert.class Volume.class
default: Edge.class Graph.class Path.class Vertex.class FindPaths.class MyGraph.class testMyGraph.class

# this target entry builds the Average class
# the Average.class file is dependent on the Average.java file
# and the rule associated with this entry gives the command to create it
#
Edge.class: Edge.java
	$(JCC) $(JFLAGS) Edge.java

Graph.class: Graph.java
	$(JCC) $(JFLAGS) Graph.java

Path.class: Path.java
	$(JCC) $(JFLAGS) Path.java

Vertex.class: Vertex.java
	$(JCC) $(JFLAGS) Vertex.java

FindPaths.class: FindPaths.java
	$(JCC) $(JFLAGS) FindPaths.java

MyGraph.class: MyGraph.java
	$(JCC) $(JFLAGS) MyGraph.java

testMyGraph.class: testMyGraph.java
	$(JCC) $(JFLAGS) testMyGraph.java


# To start over from scratch, type 'make clean'.  
#Removes all .class files, so that the next make rebuilds them
clean: 
	$(RM) *.class
