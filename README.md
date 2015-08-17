## About the project

The project is builded in __Scala 2.11.7__ Language using __sbt__. You can find a minimum test coverage in folder _src/test_ and also the application with the given example (__input.txt__) in _src/co/hg/drawingtool/app/Application.scala_. Run that application object and you can obtain in console the desired response.

There are two input files that you can provide in order to test _Application.scala_. The two input files are located under src/main/resources. For swicth between one and the other change the line 14 in _src/co/hg/drawingtool/app/Application.scala_:

    val lines: List[String] = FileReader.readFile("/input.txt")

or

    val lines: List[String] = FileReader.readFile("/input2.txt")


1. input.txt

    Input
    C 20 4
    L 1 2 6 2
    L 6 3 6 4
    R 16 1 20 3
    B 10 3 o


    Output
    ----------------------
    |                    |
    |                    |
    |                    |
    |                    |
    ----------------------      
    ----------------------
    |                    |
    |xxxxxx              |
    |                    |
    |                    |
    ----------------------
    ----------------------
    |                    |
    |xxxxxx              |
    |     x              |
    |     x              |
    ----------------------
    ----------------------
    |               xxxxx|
    |xxxxxx         x   x|
    |     x         xxxxx|
    |     x              |
    ----------------------
    ----------------------
    |oooooooooooooooxxxxx|
    |xxxxxxooooooooox   x|
    |     xoooooooooxxxxx|
    |     xoooooooooooooo|
    ----------------------    


2. input2.txt

    Input      
    C 8 6
    R 1 1 3 3
    R 4 3 7 5
    L 1 5 3 5
    B 2 2 v
    B 2 4 r
    B 5 4 a

    Output
    ----------
    |        |
    |        |
    |        |
    |        |
    |        |
    |        |
    ----------
    ----------
    |xxx     |
    |x x     |
    |xxx     |
    |        |
    |        |
    |        |
    ----------
    ----------
    |xxx     |
    |x x     |
    |xxxxxxx |
    |   x  x |
    |   xxxx |
    |        |
    ----------
    ----------
    |xxx     |
    |x x     |
    |xxxxxxx |
    |   x  x |
    |xxxxxxx |
    |        |
    ----------
    ----------
    |xxx     |
    |xvx     |
    |xxxxxxx |
    |   x  x |
    |xxxxxxx |
    |        |
    ----------
    ----------
    |xxx     |
    |xvx     |
    |xxxxxxx |
    |rrrx  x |
    |xxxxxxx |
    |        |
    ----------
    ----------
    |xxx     |
    |xvx     |
    |xxxxxxx |
    |rrrxaax |
    |xxxxxxx |
    |        |
    ----------    
