  # type 命令
  
 ## 命令描述  

>With no options, indicate how each name would be interpreted if used as a command name.  If the -t option is used, type prints a string which is
one  of  alias, keyword, function, builtin, or file if name is an alias, shell reserved word, function, builtin, or disk file, respectively.  If
the name is not found, then nothing is printed, and an exit status of false is returned.  If the -p option is used, type either returns the name
of  the disk file that would be executed if name were specified as a command name, or nothing if ``type -t name'' would not return file.  The -P
option forces a PATH search for each name, even if ``type -t name'' would not return file.  If a command is hashed, -p and -P print  the  hashed
value,  not necessarily the file that appears first in PATH.  If the -a option is used, type prints all of the places that contain an executable
named name.  This includes aliases and functions, if and only if the -p option is not also used.  The table of hashed commands is not  consulted
when  using  -a.   The  -f  option suppresses shell function lookup, as with the command builtin.  type returns true if all of the arguments are
found, false if any are not found.
