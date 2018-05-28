# kinesit
This project aims at making it easier to test integrations with Kinesis, 
it contains a docker image that has both [kinesalite](https://github.com/mhart/kinesalite) 
and [dynalite](https://github.com/mhart/dynalite), and a junit rule that can be used in your tests, 
a simple String record processor is also included in the repository, 
I also tried to make it easy to create different processors, 
extend RecordProcessor and override the single method there.


## How to use it
Go to docker directory
**cd docker**

Run a docker-compose with kinesalite and dynalite:
**docker-compose up**

This will create a container with the stream 'test-stream' ready to receive and send records

add this dependency to pom.xml

     <dependency>
        <groupId>com.github.slimish</groupId>
        <artifactId>kinesis-junit-rule</artifactId>
        <version>1.0.0</version>
     </dependency>
        
After that you can use the rule in the test class

    @Rule
    public KinesisJunitRule<String> kinesisRule = new KinesisJunitRule<String>(new KinesisConfigProviderImpl(),
                                                                               new StringRecordProcessor()); 
                                                                               

and then inside the test:

    String record = kinesisRule.waitForRecord(WAIT_TIME_MILLI);         