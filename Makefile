JC=javac
JFLAGS=-g -d ./


run: all
	java Main

all: Main.class Window.class

Main.class: src/Main.java
	$(JC) $^ $(JFLAGS)

Window.class: src/Window.java
	$(JC) $^ $(JFLAGS)

clean:
	rm *.class
