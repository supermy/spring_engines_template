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
        

    