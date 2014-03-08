JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: $(classes)

clean :
	rm -f ../build/*.class

%.class : %.java
	$(JAVAC) $< -d ../build

run: ../build/ProjectDriver.class
	java ../build/ProjectDriver
