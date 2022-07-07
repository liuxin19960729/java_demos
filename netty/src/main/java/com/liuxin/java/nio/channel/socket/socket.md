```
1.socket 受线程安全 任何时候都只有一个read()  or write()

socket 发出去的stream
  保证顺序按照顺序接收 tcp(保证接收顺序)
  不保证 接受数据和发送数组 一样一次能够全部接受完
    ABCDE -->write()
     AB   CD  E  拆包(size>最大发送的大小  route 可能继续拆包 ) 有一个最大流出数据的限制
     A  BC  C
     AB CD  AB 粘包  SO_NODELAY=false  A 太小会等待下一次发送数据 AB 拼接一起发送


note:
     connect() 和 finish() 和 write() read() 互相同步,(即使在非阻塞情况下)
     
若 不能承受读和写的阻塞 请 isConneted()  isConnectionPending()

```

## DatagramSocketChannel
```

SocketAddress receive(buf):
阻塞:
    无期限休眠到有包到达
非阻塞:
    没有包返回 null 

receive 接收的数据> buf remain 就会 丢弃

int send(buf,remoteAddress)
阻塞模式
  线程休眠到buf被加入到传输队列
非阻塞 
    要么全部发送 要么 一个不发送
 ret 0
      没有发送


note:
   数据包太大  发送数据的时候可能分片  一个数据包丢失不见 整个 数据将丢弃(分片数据越多风险越大)
 

connect()
   将DatagramChannel的数据置于可连接准状态
   connect() 指正限制成 设置的地址的接收和发送(现在在固定的两端发送),其他地址的数据包将会被丢弃
  connect 连接成功 
 send() 不能将数据包发送到 连接的地址端口以外的数据





 server.isConnected()
   数据报通道的连接状态

可以任意的连接和断开 
先断开连接 disconnect() 在 connect()
channel.disconnect().connect(new InetSocketAddress("127.0.0.1", nextPort()));



read() 和 write()必须是在 isConnect()==true 的状态下才允许read() write()

非阻塞模式 read() write() 要么发送/接收完全 要么一点没发送/接收



````