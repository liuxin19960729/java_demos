## Selector 
```
一个或多个可选择通道(可选择通道:extends SelectableChannel)注册到选择器
```

## Selector作用
```
1.记住你关心的通道
2.追踪你注册的通道是否准备就绪
3.selector.select() 相关key被更新,检查被注册到Selector的SelectableChannel
    3.1 获取所有注册到selector 的SelectableChannel
    3.2 获取Selector上所有Key的集合(select()更新)
    3.3 check 获取key 集合 对应的元素和注册在Selector上的channel 对应的事件(OP_*...)是否准备就绪
```
## Selector具备的功能
```
1.询问不同的注册SelectableChannel是否具备各种IO操作是否准备就绪
    ServerSocketChannel isAccept()  SocketChannel  isRead() isWrite()

两种方式Selector询问那些注册在自己上面的SelectableChannel是否准备就绪

    1.selector 休眠
     直到 SelectableChannel 一个或多个准备就绪
    
    2.周期性的轮询 
      检查 SelectableChannel 是否有准备就绪
```

## 传统Socket(阻塞)
```
  
 ServerSocket serverSocket = new ServerSocket();

 Socket socket = new Socket();

 connect() read() write() accept()... 会阻塞 等待OS对jvm的通知准备就绪

 相应的操作完成 jvm 线程调度器切回到执行线程继续往下执行 
 


劣势:jvm 线程调度和管理  和 阻塞线程(程序员) 操作 都增加了操作复杂性和性能损耗,尤其在线程数非常多的情况下更加明显(jvm线程调度和管理复杂度增加
线程切换更加频繁 性能损耗代价增加)
一旦阻塞jvm线程可用于的线程数就是n-1
```

## Socket and Selector in OS
```
真正的就绪必须由OS系统来做

传统Socket 
javaSocket.read()-->jvm.read()-->os kernel.read() 阻塞--> kernel.read()完成-->jdv.read()完成--> javaSocket.read() 完成



Selector 提供了OS的准备就绪通知的这种抽象能力

```

## SelectableChannel
```
一个SelectableChannel允许注册多个Selector 
一个Selector 只能注册一次SelectableChanenel

非阻塞IO 和可选性 是紧密相连的

SelectableChannel 管理阻塞IO, 使它成为非阻塞是必须要有可选性API的

configureBlocking( )  设置Channel 是阻塞还是非阻塞
isBlocking() 判断channel 属于阻塞还是非阻塞


非阻塞IO好处：
    非常容易的管理多个socket
    例:gui 可以专注用户请求,并维护一个会多个于服务器的会话 


Object blockingLock() 
  只有拥有此对象锁的线程才能够修改阻塞模式

    public final SelectableChannel configureBlocking(boolean block)
        throws IOException
    {
        synchronized (regLock) {
            if (!isOpen())
                throw new ClosedChannelException();
            if (blocking == block)
                return this;
            if (block && haveValidKeys())
                throw new IllegalBlockingModeException();
            implConfigureBlocking(block);
            blocking = block;
        }
        return this;
    }

  public final Object blockingLock() {
        return regLock;
}


lockingLock简单使用
        ServerSocketChannel open = ServerSocketChannel.open();
        Object lock = open.blockingLock();
        open.bind(new InetSocketAddress(8080));
        /*
         * 在同步代码块里面的代码其他线程不能够执行
         * */
        SocketChannel accept = null;
        synchronized (lock) {
            boolean preIsBlock = open.isBlocking();

            open.configureBlocking(true);

            accept = open.accept();

            open.configureBlocking(preIsBlock);
        }

        System.out.println(accept);
    }


```
### SelectableChannel(abstract class)   register
```
public abstract SelectionKey register(Selector sel, int ops, Object att)
        throws ClosedChannelException;
//atta: 方便方法    key.attachment(); 获取注册的atta 引用
public final SelectionKey register(Selector sel, int ops)
        throws ClosedChannelException
    {
        return register(sel, ops, null);
    }


register只允许非阻塞Channel 注册 Selector   throw IllegalBlockModeException

register-->configureBlocking(true) throw IllegalBlockModeException



```
###  channel.validOps()
```
获取所有的操作数

```
### channel.isRegistered();
```
selectableChannel 是否注册到Selector上(不是准确答案) key.cancle()可能有延迟

```

### channel.keyFor();
```
SelectionKey selectionKey = channel.keyFor(selector);
返回改通道在selector上面注册key or  null 


```
### channel.close();
```
所有相关key会被自动取消

note:一个selectableChannel可以注册多个selector
channel.close() 会注销所有selector channel
all keys is invalid
call key 相关的方法 throw CancelledKeyException

```

## SelectionKey
```

  channel  <-- key --->selector 关系


 ServerSocketChannel open = ServerSocketChannel.open();
 SelectionKey register = open.register();

 SelectionKey register(Selector sel, int ops)

在对应的选择器上面注册对应的OP_*




```

### key.channel()
```
key.channel() 返回的是 channel.register(selector,ops) 注册时是channel对象 

```

### key.selector()
```
Selector selector =  key.selector()
key在那个selector上面

```
### key.isValid()
```
key 表达的是注册关系
key.cancle() 终结 channel key  selector 三者关系(取消注册)

isValid() check 是否 channel 还注册在selector




```
**note:key.cancle()  key.isVaild()==false 但是 channel.isRegister()==true 原因:selector会<br>
将取消的key 放在取消集合，驻车不会立即取消,再次selector,select()时才会把取消key集合删除掉,在同步channel注册状态为非注册**
 
### ops相关
```
int i = key.readyOps();//ready 好要操作的集合 
int i1 = key.interestOps();//channel selector 组合所关心的操作

//可以先将channel.register(selector,0)
在 key.interestOps(ops)进行改变


key.interestOps(ops);//改变注册的值
note: key.interestOps(ops) 改变不会立即改变 会在下一次seletor.seletc() 才正式改变
``` 

## Selector
```
选择器对象控制着注册他之上SelectableChannel 的选择过程

Selector selector = Selector.open();// 静态实例化Selector对象 SPI 发送请求 




selector.close();
   1.释放selector 占用的资源 
   2.将所有选择key设置无效(检查是否有效  key.isValid())

selector.isOpen() 判断Selector是否是打开的状态
非打开 调用方法 throwClosedSelectException



```
### selector.select()
```
选择器 是永远不会改变 interest 集合



```





