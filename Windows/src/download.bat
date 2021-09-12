@echo off
set input=%1
set path="%cd%"
shift
shift

if exist songs\ {
	echo "Directory ok"
} else {
	md "songs"
}

youtube-dl --extract-audio --audio-format mp3 -o %path%'%(title)s.%(ext)s' %input%
