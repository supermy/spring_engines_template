mvn clean         		--> cleans the project
mvn clean test    		--> cleans the project and runs all tests
mvn clean package 		--> cleans the project and builds the WAR
mvn tomcat:run	 		--> web run
mvn eclipse:eclipse		--> gen eclipse .project and .classpath config file
mvn jar:jar				--> only build jar file.
mvn javadoc:javadoc     --> gen api files;

mvn dependency:copy-dependencies  拷贝jar
mvn dependency:tree

mvn test -Dtest={测试文件名称}

2015-05-13
    Curator 调用 zookeeper 示例；
    template.mustache模板调用；

    json配置文件格式到zookeeper,分布式配置初始化;分布式文件配置，使用kafka代替呢？
    

2014-12-27
    gradle test ok

2014-12-25
    全注解框架:
        分层 ok
        aop ok
        test ok
        async Scheduled  ok
        memcached ok
        properties ok
        
        
  find . -type d -name ".svn"|xargs rm -rf 
  find . -type d -iname ".svn" -exec rm -rf {} \; 


  mvn to gradle
  http://mvn2gradle.codetutr.com/
        
        
2014-12-24
    逻辑层单独一个项目进行测试，不依赖于数据库
    
2014-12-08
    myService,逻辑层：
        单独框架，不更换，mybatis+spring-service
        +aop
        +asyc
        +memcached
    myWeb,MVC:
        单独的框架，可更换；
    myWebJs:WebJs:
        单独框架，可更换；
    
    示例:
        myService+BI
        

    