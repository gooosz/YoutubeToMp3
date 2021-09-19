#!/bin/bash
input=$1
path=$PWD"/songs/"

#check if directory exists
if [ -d "$path" ]
then
	echo "#directory ok"
else
	mkdir songs
fi

#download song into /songs/ directory
youtube-dl --extract-audio --audio-format mp3 -o $path'%(title)s.%(ext)s' $input

#output the filename
youtube-dl --get-title $input
