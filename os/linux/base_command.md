# Linux Commands

> Basic Linux Commands. Open for contributions.

| S. No | Name | URL         | Usage                                                                                               |
| ----- | ---- | ----------- | --------------------------------------------------------------------------------------------------- |
| 1     | grep | [🔗](#grep) | search for expressions                                                                              |
| 2     | cat  | [🔗](#cat)  | reading data                                                                                        |
| 3     | cut  | [🔗](#cut)  | cutting out the selections                                                                          |
| 4     | sed  | [🔗](#sed)  | find, filtering and replacing text                                                                  |
| 5     | tee  | [🔗](#tee)  | reads from standard input and writes to both standard output and one or more files at the same time |
| 6     | head | [🔗](#head) | printing top part of files                                                                          |
| 7     | tail | [🔗](#tail) | printing last part of files                                                                         |
| 8     | find | [🔗](#find) | finding and replacing files                                                                         |

1. #### **grep**

   - print any line containig the text

     `grep "hack" terminal.txt`

   * print any line containig the text in multiple files

     `grep "hack" terminal.txt terminal2.txt treminal3.txt`

   * print any line containing the text in the whole directory

     `grep "hack" *`

   * print whole words only

     `grep -w "hack" *`

   * ignoring case in grep searches

     `grep -i "admin" *`

   * search in subdirectories

     `grep -r "admin" *`

   * inverse search exlcluding lines

     `grep -v "admin" *`

   * showing the lines that match a string

     `grep -x "admin" *`

   * print the name of files that contain the words

     `grep -l "admin" *`

   * print total count of the matches

     `grep -c "admin" *`

   * printing 3 lines after the match

     `grep -A 3 "admin" *`

   * printing 3 lines before the match

     `grep -B 3 "admin" *`

   * printing 3 lines before and after the match

     `grep -C 3 "admin" *`

   * display lines number with the matches

     `grep -n -C 2 "admin" *`

   * set output to a fixed number of lines

     `grep -m2 "admin" *`

   * run grep with regular expression

     `grep -E "[0-9] terminal.txt"`

   * only show the matching part of the string

     `grep -o "admin" *`

     [Back to Top](#linux-commands)

2. #### **cat**

   - view single file

     `cat file.txt`

   - view multiple files

     `cat file1.txt file2.txt`

   * view file with line numbers

     `cat -n file.txt`

     `cat -b file.txt`

   * copy contents of one file into another

     `cat [file1.txt] > [file2.txt]`

   * supress empty lines in output

     `cat -s file.txt`

   - append contents of one file into another

     `cat file.txt >> file2.txt`

   * reverse order using tac

     `tac file.txt`

   - highlight the end of line

     `cat -E file.txt`

   - using -v, -E, -T together

     `cat -A file.txt`

   * open dashed file

     `cat -- _file.txt`

   * more text

     `cat file.txt | more`

   - merge the content of multiple files

     `cat file.txt file2.txt file3.txt > masterFile.txt`

   * display content of all files in folder

     `cat *.txt`

   - append existing file

     `cat >> file.txt`

   * create a file

     `cat >file`

   * insert new line while concatening

     `cat - file.txt file2.txt > newfile.txt`

   * creating new file by displaying end marked at the end

     `cat > file.txt <<EOF`

   - displaying tab characters

     `cat -eT file.txt`

     [Back to Top](#linux-commands)

3. #### **cut**

   - by using hyphen as a delimiter

     `cut -d- -f2 file.txt`

   - by using space as a delimiter

     `cut -d " " -f2 file.txt`

   - cut by byte

     `cut -b 1 file.txt`

     `cut -b 1-3 file.txt`

   - cut by character

     `cut -c 1,3 file.txt`

     `cut -c 1-3 file.txt`

   - cut by complement

     `cut --complement -c 1,2 file.txt`

     `cut --complement -c 1-3 file.txt`

   * select range of fields

     `cut -f 2- file.txt`

     [Back to Top](#linux-commands)

4. #### **sed**

   - replace the first text with the second patter

     `echo Ehsaan | sed 's/Ehsaan/Hacker'`

   - replace all the matched pattern

     `echo My name is Ehsaan and I am Ehsaan | sed 's/Ehsaan/Hacker/'g`

   * delete the lines that match the pattern

     `cat test.txt | sed '/cd/d'`

   * multiple sed commands

     `sed -e '/cd/d; /burp/d' file.txt`

   * run sed commands from a file

     `sed -f sed.txt file.txt`

   * apply the command on the specific line

     `sed '3s/Ehsaan/Hacker/'`

   - specify the command on the range of lines

     `sed '1,3/Red/Purple/'`

   - inserting text before the match

     `echo "Line 1" | sed 'i\Line 2'`

   - inserting text after the match

     `echo "Line 2" | sed 'a\Line 1'`

   - modify lines

     `sed '3c/This is new line' file.txt`

   - modify line by regex

     `sed /Apple is /c Line Updated' file.txt`

   - transformaiton of characters

     `sed 'y/abc/def/' file.txt`

   - printing the line numbers

     `sed '=' file.txt`

   - displaying line numbers by match

     `sed -n '/mango/=' file.txt`

   - replace the nth occurance

     `` sed 's/color/colour/1` file.txt ``

   - replace from nth occurance to all occurance

     `sed 's/color/colour/3/g' file.txt`

   - print only the replaced string

     `sed -n 's/apple/mango/p' file.txt`

   - print the replaced string twice

     `sed 's/Apple/mango/p' file.txt`

   - to delte particular line

     `sed '5d' file.txt`

   * to delte last line

     `sed '$d' file.txt`

   - delete from range

     `sed '3,6d' file.txt`

   - delete from nth line to last line

     `sed '12,$d' file.txt`

   - view entire file except give range

     `sed '20,35d' file.txt`

   - display lines in more than 1 range

     `sed -n -e '5,7p' -e '1,2p' file.txt`

   - replace characters by ignoring case senstive

     `sed 's/word/character/gi' file.txt`

   - remove multiple blank spaces with single space

     `sed 's/ */ /g' file.txt`

   * insert one blank after each line

     `sed G file.txt`

   - insert two blank lines

     `sed 'G;G' file.txt`

   - insert black line above every match

     `sed '/Apple/{x;p;x;};' file.txt`

   - insert blank line below evrey line

     `sed '/love/G' a.txt`

   * insert five spaces on the left of every line

     `sed '/^/ /' a.txt`

   * number each line of a file

     `sed = a.txt | sed 'N; s/^/ /; s/ *\(.\{4,\}\)\n/\1 /'`

   * number each file if it's not blank

     `sed '/./=' a.txt | sed '/./N; s/\n/ /'`

   * delete empty lines or those begin with #

     `sed -i '/^#/d;/^$/d' file.txt`

     [Back to Top](#linux-commands)

5. #### **tee**

   - write output of the file to the next file

     `cat file.txt | tee file2.txt`

   * append data to a file

     `echo "Hello" | tee -a file.txt`

   * the snap of working directory is store in file

     `ls ~/| tee pipe1.txt | grep ^b | tee pipe2.txt | sort -r`

   * write data to multiple files

     `echo "New Line" | tee file.txt file2.txt`

   * Hide the output

     `echo "new" | tee file.txt >/dev/null`

   * Ignoring interupts

     `command | tee -i file.txt`

     [Back to Top](#linux-commands)

6. #### **head**

   - displays top 10 lines of the file

     `head file.txt`

   * prints the number of lines

     `head -n 3 file.txt`

   * prints the num bytes

     `head -c 3 file.txt`

   * multiple fies
     `head -q file.txt file1.txt`

   * prints along with file name

     `head -v file.txt`

   * get three recently used files

     `ls -t | head -n 3`

   * get three recently used files by alphaetical order

     `ls -t | head -n 3 | sort`

     [Back to Top](#linux-commands)

7. #### **tail**

   - prints the last 10 lines

     `tail file.txt`

   - prints the number of lines

     `tail -n 3 file.txt`

   * prints the num bytes

     `tail -c 3 file.txt`

   * multiple fies
     `tail -q file.txt file1.txt`

   * prints along with file name

     `tail -v file.txt`

   * get three recently used files

     `ls -t | tail -n 3`

   * get three recently used files by alphaetical order

     `ls -t | tail -n 3 | sort`

   * will print form the nth line number to the end

     `tail +10 file.txt`

   * print from the last of file

     `tail -c -20 file.txt`

   * prints all data after skipping number

     `tail -c 50 file.txt`

     [Back to Top](#linux-commands)

8. #### **find**

   - prints the last 10 lines

     `tail file.txt`

   - Find all files whose name ends with ".xml"

     `find / -type f -name *.xml`

   - Find all files in the /home directory (recursive) whose name is "user.txt" (case insensitive)

     `find /home -type f -name user.txt`

   - Find all directories whose name contains the word "exploits"

     `find / -type d -name "exploits*"`

   - Find all files owned by the user "kittycat"

     `find / -type f -user kittycat`

   - Find all files that are exactly 150 bytes in size

     `find / -type f -size 150`

   - Find all files in the /home directory (recursive) with size less than 2 KiB’s and extension ".txt"

     `find /home -type f -size -2k -name *.txt`

   - Find all files that are exactly readable and writeable by the owner, and readable by everyone else (use octal format)

     `find / -type f -perm 644`

   - Find all files that are only readable by anyone (use octal format)

     `find / -type f -perm /44`

   - Find all files with write permission for the group "others", regardless of any other permissions, with extension ".sh" (use symbolic format)

     `find / -type f -perm -o=w -name "*.sh"`

   - Find all files in the /usr/bin directory (recursive) that are owned by root and have at least the SUID permission (use symbolic format)

     `find /usr/bin -type f -user root -perm -u=s`

   - Find all files that were not accessed in the last 10 days with extension ".png"

     `find / -type f -atime +10 -name "*.png"`

   - Find all files in the /usr/bin directory (recursive) that have been modified within the last 2 hours

     `find /usr/bin -type f -mmin -120`

   - Find files without displaying the error

     `find / -name flag.txt 2>/dev/null`

     [Back to Top](#linux-commands)
