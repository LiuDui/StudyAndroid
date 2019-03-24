# 认识
## 硬件/内核/shell
> 几个层：
用户 <--> 用户界面（Shell/KED/application） <--> 核心（Kernel） <--> 硬件（hardware） 

# 系统上有多个shell

系统上可以用的shell有多个。

查看一下/etc/shells文件下(centos7.x)，有多少可用shell
```bahs
[aurora@localhost ~]$ more /etc/shells
/bin/sh
/bin/bash
/sbin/nologin
/usr/bin/sh
/usr/bin/bash
/usr/sbin/nologin
/bin/tcsh
/bin/csh
```
系统上合法的shell要写入/etc/shells文件里面，因为系统某些服务在运行过程中，会去检查用户能够使用的shells，该检查工作就是通过查看这个文件。
不同的shell提供的服务不同，当用户登陆系统时，系统会默认分配给用户一个shell，而这个shell拥有的服务是不同的。

登录后系统分配的shell在/etc/passwd这个文件中
```bash
[aurora@localhost ~]$ cat /etc/passwd
root:x:0:0:root:/root:/bin/bash
bin:x:1:1:bin:/bin:/sbin/nologin
daemon:x:2:2:daemon:/sbin:/sbin/nologin
...
```
## bash shell的功能
`/bin/bash`是系统默认的shell，功能强：
* 命令记忆功能
    * 通过上/下键查找历史命令，默认1000个
    * 历史命令，前一次登陆时使用的：存放在主文件夹.bash_history；这一次登陆的：放在临时内存中，当系统注销时，记录到文件。
    * 好处是可以知道曾经做过的操作。
* 命令补全功能 
    * `tab`接在一串命令的第一个字后面，是命令补全
    * `tab`接在一串命令的第二个字后面，是文件补全
    * 两次`tab`，查看所有命令
* 命令别名设置功能（alias）
    * `alias lm=ls -al`
* 作业控制/前台/后台控制
    * 在单一用户上，达到多任务的目的
* 程序脚本
    * 类似与.bat
* 通配符
    * `ls -al /usr/bin/l*`

## 内置命令：`type`
常用命令可以分为两种，一种是bash内置命令，一种不是内置命令，如何区别是不是，用`type`命令：
```bash
[aurora@localhost ~]$ man type
BASH_BUILTINS(1)            General Commands Manual           BASH_BUILTINS(1)

NAME
       bash,  :,  .,  [, alias, bg, bind, break, builtin, caller, cd, command,
       compgen, complete, compopt,  continue,  declare,  dirs,  disown,  echo,
       enable,  eval,  exec, exit, export, false, fc, fg, getopts, hash, help,
       history, jobs, kill, let, local, logout, mapfile, popd, printf,  pushd,
       pwd,  read, readonly, return, set, shift, shopt, source, suspend, test,
       times, trap, true, type, typeset, ulimit, umask, unalias, unset, wait -
       bash built-in commands, see bash(1)
...
[aurora@localhost ~]$ type -t cd
builtin
[aurora@localhost ~]$ type -p cd
[aurora@localhost ~]$ type -a cd
cd 是 shell 内嵌
cd 是 /usr/bin/cd
cd 是 /bin/cd

```
通过type可以知道每个命令是不是内置命令，另外由于利用type，如果后面的名字不是可执行文件时，是不会显示出来的，他找的是可执行文件，可以类似which工作。

# shell变量功能
* 用户的信息变量，不同用户登陆，执行相同命令，通过变量可以区分，比如各自的mail
* 环境变量，PATH/MAIL等
* 在脚本文件中使用变量，好作出更改或移植。

## 变量的使用
（使用的方式比较多，少记，记容错率高的）
### 变量赋值

```bash
[aurora@localhost ~]$ name = lr
bash: name: 未找到命令...    /* 千万别有空格，有空格请用\转义
[aurora@localhost ~]$ name=lr
[aurora@localhost ~]$ name="this is a name"
[aurora@localhost ~]$ name="this's a name"
[aurora@localhost ~]$ another="${name} is right"
[aurora@localhost ~]$ filenames="$(ls -l) is file names"
[aurora@localhost ~]$ PATH=${PATH}:"this is test"
[aurora@localhost ~]$ echo ${name}
this's a name
[aurora@localhost ~]$ echo ${another}
this's a name is right
[aurora@localhost ~]$ echo ${filenames}
总用量 0 drwxr-xr-x. 3 aurora root 92 3月 12 21:02 Desktop drwxr-xr-x. 2 aurora root 6 3月 27 2018 Documents drwxr-xr-x. 2 aurora root 168 3月 7 16:30 Downloads drwxr-xr-x. 6 aurora root 90 3月 11 13:42 eclipse-workspace drwxr-xr-x. 2 aurora root 6 3月 27 2018 Music drwxr-xr-x. 3 aurora root 71 3月 11 08:59 Pictures drwxr-xr-x. 5 aurora root 72 3月 8 14:18 Program drwxr-xr-x. 2 aurora root 6 3月 27 2018 Public drwxr-xr-x. 2 aurora root 26 4月 28 2018 Templates drwxr-xr-x. 2 aurora root 6 3月 27 2018 Videos is file names
[aurora@localhost ~]$ echo ${PATH}
/usr/local/java/jdk1.8.0_171/bin:/usr/lib64/qt-3.3/bin:/usr/local/bin:/usr/local/sbin:/usr/bin:/usr/sbin:/bin:/sbin:/home/aurora/.local/bin:/home/aurora/bin:this is test
[aurora@localhost ~]$ 
```
总的来看，变量使用的时候，有几个经常出错的地方，一个是空格，一个是反单引号，一个是单引号，最好还是避免。其实具体的大概有几种：
* 简单定义一个变量，给个值： var=“xxx”
* 变量赋值的时候，里面再引用一个变量的值：var=“${anothervar 或者 var}xxx”
* 变量赋值的时候，里面应用了一个命令执行结果的值：var=“$(command -xx xx)xxxx”
* 定义变量，变量的值里面有特殊字符，Enter，$等，直接反斜杠`\`转义
另外有一些不好区分的使用，比如在变量的设置中，单引号与双引号的区别：
```bash
[aurora@localhost ~]$ name="this is a name"
[aurora@localhost ~]$ anothername="${name}"
[aurora@localhost ~]$ name="${name} is a name"
[aurora@localhost ~]$ anothername='${name} is a name'
[aurora@localhost ~]$ echo ${name}
this is a name is a name
[aurora@localhost ~]$ echo ${anothername}
${name} is a name
```


