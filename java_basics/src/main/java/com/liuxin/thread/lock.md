## lock指令
````
1.总线锁


2.cacheLine锁


lock xxx 指令

情况一

thread1     thread2       memory
  lock x2...                x=2

thread1 和 thread2 缓存行没有数据  会使用总线锁来锁定整个内存



thread1     thread2       memory
cache x e   lock x..       x
               
线程2 lock x ,cahcex invalid   使用总线锁 
总线锁释放  thread1 使用 x,发现 invalid 会重新读取内存更新到缓冲区



thread1  thread2  memory
x           x        x

t1 x t2 x现在的状态数据 share准固态

t1 lock x  t1 变为 modified , t 2 变为 invalid 并且锁定 
当t1 数据写入内存的时候释放锁 t2 变为 invaild 要使用的时候会重新加载到缓冲行 t1,t2 并且 share


thread1   thread2  memory
x  execlusive                x

t1 execlusive 状态 lock操作的时候 使用的是缓冲行锁,告诉其他cpu这个缓冲行锁定

t1 execlusive--t1 modified


有些cpu lock不支持缓存行锁

lock xxx 的时候若跨越多个缓存行操作会启用总线锁




````