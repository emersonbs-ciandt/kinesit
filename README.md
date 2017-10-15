# kinesit
This project aims at making it easier to test integrations with Kinesis, 
it contains a docker image that has both [kinesalite](https://github.com/mhart/kinesalite) 
and [dynalite](https://github.com/mhart/dynalite), and a junit rule that can be used in your tests, 
a simple String record processor is also included in the repository, 
I also tried to make it easy to create different processors, 
extend RecordProcessor and override the single method there.
