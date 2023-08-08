# Midi Player (MIDI播放器)

A simple MIDI music player developed using Java
一个使用JAVA开发的，简单的MIDI播放器（用于播放midi音乐）


## 打包说明
打包成可执行的JAR，依赖库会放在lib目录下

```
mvn clean package
```

打包成可执行的Fat JAR，这个JAR 包含了项目本身和其所有依赖项，能够不依赖lib独立运行

```
mvn clean package -P fat
```

## 常用 maven-plugin 说明
maven-compiler-plugin: 用于编译项目中的 Java 源代码。
maven-jar-plugin：用于创建Java项目的JAR文件（Java Archive），将编译后的类文件和资源文件打包成JAR文件。
maven-dependency-plugin：该插件用于处理项目的依赖项，包括复制依赖、解压缩依赖、分析依赖等。