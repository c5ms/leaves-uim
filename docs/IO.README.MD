## URL & URI

1. URL 叫做 统一资源定位系统,因特网的万维网服务程序上用于指定信息位置的表示方法.
    - sftp://wangzc:admin@127.0.0.1/home/wangzc?passvemode=true&ssh=true
    - 它的目的是告诉你怎么访问到这个资源

2. URI 叫做 统一资源标识符,是一个用于标识某一互联网资源名称的字符串.
    - sftp://wangzc:admin@127.0.0.1/home/wangzc
    - 它的目的是描述一个唯一的资源

3. URI的访问方式称作schema常见的schema有:
    - ftp://
    - file://
    - http[s]://
    - hdfs://
    - smtp:// 
    
## 路径相关
   - 绝对定位就是相对于你在资源所在的根路径的相对定位.
   - 相对定位就是从某资源如何能到达另一资源的一种路径描述.
   - 我们的程序都是运行在某一路径下的.程序的相对路径是从它的工作目录开始的.
   - 上级目录用..表示,当前目录用.表示,根目录用/表示
   - 在windows上,根目录(/)表示工作空间所在磁盘分区(比如D:\)
   - 在linux上,根目录(/)表示linux的/目录(linux只有一个根)

# 字节相关
   - 计算机的存储最小单位是bit代表一个0|1
   - 8个bit表示一个byte,字节流传输的就是byte
   - 汉字占多个(看编码)字节,ascii占1个byte,字符流传输的就是这个组合后的内容(一般人类的眼睛能看懂) 
   
# 推荐资料
- https://www.cnblogs.com/zoe233/p/13141487.html
- http://ifeve.com/java-io/

## 推荐工具包
apache-io http://commons.apache.org/proper/commons-io/
> apache  提供的IO工具库,使用率超高

apache-vfs https://commons.apache.org/proper/commons-vfs/
> apache 打通各个文件系统的统一API 