#default target
.PHONE: default
default: youtube-dl ;

#compiler / -flags
JC=javac
JFLAGS=-g -d ./


run: all
	java Main

all: Window.class Main.class

Main.class: src/Main.java
	$(JC) $^ $(JFLAGS)

Window.class: src/Window.java
	$(JC) $^ $(JFLAGS)

clean:
	rm *.class



#install youtube-dl on UNIX
#by writing 'make' in console

#gives youtube-dl read, write and execute
youtube-dl: install_youtube-dl
	sudo chmod a+rx /usr/local/bin/youtube-dl

install_youtube-dl:
	sudo curl -L https://yt-dl.org/downloads/latest/youtube-dl -o /usr/local/bin/youtube-dl
