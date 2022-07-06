
**由于不同的OS有着不同的实现,所以API都是由接口指定**

## Interface Channel 
```java
public interface Channel extends Closeable {
    //通道是否打开
    public boolean isOpen();
    // 关闭通道
    public void close() throws IOException;
}
```

## interface InterruptibleChannel
```
InterruptibleChannel mark接口

implement InterruptibleChannel 该接口允许中断

```

## SPI上的两个类
```
interface InterruptiableChannel  java.nio.channels
abstract AbstractInterruptibleChannel java.nio.channels.spi


abstract  SelectableChannel
abstract  AbstractSelectableChannel  java.nio.channels.spi

```

## 基本概念
```
  通道实力化的对象  <-(返回)--通道-（连接）->特定IO服务
such as 
    readOnlychannel
          读磁盘IO服务
          只允许读的channel对象(write方法的执行 throw exception)  
```

## 同步关闭
### 对 clsoe channel访问
```
当channel 已经关闭，任然 access channel
throw ClosedChannelException

 ClosedChannelException
    extends java.io.IOException
```

## 异步关闭 
### FileChannle extends AbstractInterruptibleChannel
```
通过中断使InterruptedChannel 关闭

类型一
1.如果一个通道上线程被阻塞
2.另一个线程 blockThread.interrupt() 
3.通道关闭 通道产生ClosedByInterruptException

类型二
1.线程1把线程2中断(interrupt status=true)
if(curentThread.isInterrupted()) 来判断 也可以清除interrupted status
2.线程2 访问通道 通道立即关闭 throw CloseByInterrupException


InterruptibleChannel interrupte  是异步关闭
    即使当前通道阻塞执行某种操作  interrupte 线程
    立即退出 throw AsynchronousClosedExceptioon 
```


## Scatter  Gather
```
作用:在多个缓冲区实现IO操作

gather:write() 收集
   [bufer1[pos:0,limit10],bufer2[pos:3,limit5]....]
   合并成
    连接成一片大的内存
scatter:
   read()
  按顺序读到多个缓冲区(将缓存区一个消耗完才进行下一个的操作)


最大的性能优势:
  ByteBuffer buffer = ByteBuffer.allocateDirect(100); 性能最好
  
 jvm 堆内的内存在 read 和 write 会先创建临时 direcorBuffer 在进行拷贝

```

## FileChannel
```
FileChannel 没记继承 abstract  SelectableChannel
所以FileChannel 不支持非阻塞
fileChannel 总是阻塞式的
os 多层缓存机制和预取机制 所以请求磁盘会延迟较少

FileChannel 是线程安全

file --write append remove  等修改 file大小操作必须是单线程来执行

FileChannel 是反应jvm 对外部具体对象的一个抽象。同一个jvm 上看到的fileChannel 视图的实例是一致的,(不是同一个jvm并不保证)
  同一个jvm进程 多个线程报保证 fileChannel 视图一致

多个进程 jvm并不保证 ，而是由OS +file system 保证,一般而言是一致的

```

### Channel 和RandomAccessFile API区别
```
read
     RandomAccessFile accessFile = new RandomAccessFile(completePath, "rws");
        FileChannel channel = accessFile.getChannel();
        accessFile.read();
        channel.read()
     EOF 返回 -1

write
        accessFile.write();
        channel.write()
        

size :文件带下
        accessFile.length();
        channel.size()
文件位置
       accessFile.getFilePointer();
          channel.position() 返回当前文件指针指的位置
设置文件指针位置
            accessFile.seek();
            channel.position(pos)设置文件指针位置
                 pos <0 throw IllegalArgumenException
                 pos>file len 
            
            [a,b,c,EOF,[..文件空洞....]a,b,c] pos=100;  read return eof
            write : abc  
            文件空洞:磁盘只会写入实际写入的数据分配空间
                 例[a,b,c........d.e]
                磁盘实际只写入abcde 中间的那些0实际是不写入的
                我们看到的文件大小可能只有10kb 但是读取到内存可能有很大，中间空洞的数据大部分是0
 
        accessFile.seek(length <= 2 ? 0 : length - 2); //设置文件指针
        FileChannel channel = accessFile.getChannel();
        System.out.println(channel.position()); //获取文件指针
             通过fd-->os fileSystem获取

长度设置
   accessFile.setLength();
        channel.truncate()

同步
  accessFile.getFD().sync();
        channel.force();//强制将修改的数据全被输入到磁盘

           channel.force(boolean metadata);  metadata false 只同步数据
           true 同步数据+元数据(修改时间 所有者  权限等信息)
  
```
## 文件锁定
```
文件锁的实现：OS+FileSystem 
独占锁 
共享锁：并不是所有OS都支持共享锁，若没有共享锁会升级为独占锁

锁的对象：文件 对任何程序想要访问这个文件都会上锁
    jvm 进程 获取独占文件锁
        thread1 
        thread2 
        共享独占锁

public abstract FileLock lock(long position, long size, boolean shared)

share lock only read 权限
exclusive lock r+w 权限

size可以大于文件带下

要写入该区域数据 先把该区域锁住

例
[      [     ]  [   lock    ] [   ]          ]

我锁住一块区域,然而 position 超过 lock 则后面区域写的那块区域不会保证保护


if(lock()){//阻塞到知道获得锁

}

threa2:
    thread1  filechannel.close()  // 抛出 asynchrousCloseException()
    thread.interrupt() FileLockInterruptionException,如果在调用lock()之前
     interrupt已经被设置也会 throw FileLockInterruptionException

trylock() 飞阻塞
```
## FileChannel.map()  MappedByteBuffer
```
map() file --mapping -- byteBuffer
建立一个虚拟内存映射

虚拟内存:
    内核地址空间和虚拟地址映射到同一地址


我做的修改会对文件的其他人可见


FileChannel.MapMode.PRIVATE 在写时拷贝 
   put() 修改 只有MappedByteBuffer 可以看到
   必须 rw权限打开

若文件大小发生变化 则 mmap buffer 可能部分也可能全部都不能访问

mapped buf 内存在jvm堆外


API 介绍

channel.map()  文件是否读取到内存(从磁盘) 取决于 OS的实现--可能只是建立了映射在虚拟页上

process  java heap  heap堆外
   1.在堆外找到虚拟内存区域
   2.加载数据到kernel buffer
   3.堆外内存----------kernel 之间做映射 
       也验证(防止读取错误) 
        
note:磁盘读取单位 4kb or 512 byte ,现在一般 4kb
现在 内存也分页 一页4kb(大多数情况)


public final boolean isLoaded() 
   判断被映射的文件是否还存在内存
   true:映射缓冲区延迟少或者根本没有延迟,不能完全保证
   false:不一定不是常驻缓冲区或不一定未完成常驻内存
   
isLoaded() 是暗示（底层操作内存分配动态 谁时可能 page in swap ）

public final MappedByteBuffer load()  小心使用load
    加载整个文件为常驻内存

    操作代价较高,不应定能全部加载到内存，具体大小取决于文件被映射区域实际大小 
    
    文件并不能保证加载到内存里面的数据就是常驻(新的其他加载进来-->被替换) 动态存在

2.
对于那些要求近乎实时的程序，解决方案预加载

具有交互性和事件驱动的程序：提前加载消耗非常不划算
    解决方案：分摊页进行调入只加入需要的部分(减少IO活动次数)

主要作用 提前访问埋单，以防后面可以直接访问,这样访问速度可能更加快

public final MappedByteBuffer force()
  当使用mappedbuffer 进行修改时不能使用 FileChannel进行更新(fileChannel.force()不知道数据改变)

MAP_MODEL.READ_ONLY MAP_MODEL.RRIVATE
FileChannel.MapMode.PRIVATE 在写时拷贝 
   put() 修改 只有MappedByteBuffer 可以看到
   必须 rw权限打开
上面的两种操作 force() 执行 并不起作用(没有修改到 虚拟内存对应的物理夜上)


```










