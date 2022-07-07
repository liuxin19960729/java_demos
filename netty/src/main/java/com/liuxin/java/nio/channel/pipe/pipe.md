## Pipe
```
 Pipe open = Pipe.open(); 创建Piple实例
Pipe.SourceChannel source = open.source();
Pipe.SinkChannel sink = open.sink(); 
SourceChannel
SinkChannel
全部都继承AbstracrtSelectedChannel  所以可以和Selector一起使用

```

## SourceChannel (读端)
```
Pipe 提供回环机制的Channel对象
Pipe.SourceChannel source = open.source();

```

## SinkChannel(写端)
```
 Pipe.SinkChannel sink = open.sink();

```