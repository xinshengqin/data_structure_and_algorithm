# define a makefile variable for the java compiler
JCC = javac

SRC = ./src
LIB = ./bin
BIN = ./bin
# define a makefile variable for compilation flags
# the -g flag compiles with debugging information
JFLAGS = -g -d $(BIN) -cp $(LIB)


# typing 'make' will invoke the first target entry in the makefile 
# (the default one in this case)
#default: Average.class Convert.class Volume.class
default: Edge.class Graph.class Path.class Vertex.class FindPaths.class MyGraph.class testMyGraph.class

# this target entry builds the Average class
# the Average.class file is dependent on the Average.java file
# and the rule associated with this entry gives the command to create it
#
#Edge.class: $(SRC)/Edge.java Vertex.class
Edge.class: Vertex.class
	$(JCC) $(JFLAGS) $(SRC)/Edge.java

Graph.class: Vertex.class
	$(JCC) $(JFLAGS) $(SRC)/Graph.java

Path.class: 
	$(JCC) $(JFLAGS) $(SRC)/Path.java

Vertex.class: 
	$(JCC) $(JFLAGS) $(SRC)/Vertex.java

FindPaths.class: MyGraph.class 
	$(JCC) $(JFLAGS) $(SRC)/FindPaths.java

MyGraph.class: 
	$(JCC) $(JFLAGS) $(SRC)/MyGraph.java

testMyGraph.class: 
	$(JCC) $(JFLAGS) $(SRC)/testMyGraph.java


# To start over from scratch, type 'make clean'.  
#Removes all .class files, so that the next make rebuilds them
clean: 
	$(RM) *.class
