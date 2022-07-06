
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


## FileChannle extends AbstractInterruptibleChannel
```
1.如果一个通道上线程被阻塞
2.另一个线程 blockThread.interrupt() 
3.通道关闭 通道产生ClosedByInterruptException

```

