Android系统基于Linux内核，Linux内核继承和兼容了Unix系统进程间通信机制（IPC）。

> 传统的
* 管道（Pipe）
* 信号（Signal）
* 跟踪（Trace）

这三种仅仅用于父进程与子进程之间的通信或者兄弟进程之间的通信。

>后来增加
* 命令管道（nameed pipe）

进程间通信不再局限于父子进程或者兄弟进程之间

>为更好支持商业应用中的事务处理，增加称为“System V IPC”的进程间通信机制
* 报文队列（Message）
* 共享内存（Share Memory）
* 信号量（Semaphore）

> 重要扩展
* socket

而Android采用的Binder机制，类似于COM和CORBA的分布式架构，** 提供远程过程调用** 功能。

在Android系统的Binder机制中，由系统组件组成
> * Client：运行在用户空间，在Binder驱动和Service Manager提供的基础设施上，与Server进行通信
* Server：运行在用户空间，在Binder驱动和Service Manager提供的基础设施上，与Client进行通信
* Service Manager：运行在用户空间，提供了辅助管理的功能，Android平台已经实现好
* Binder驱动程序：运行在内和空间，Android平台已经实现好

由于Service Manager和Binder程序已经实现好，开发者只需要按照规范实现自己的Client和Server组件就可以。



## 深入浅出之Binder机制
