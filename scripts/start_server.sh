#!/bin/bash
java -jar -Dspring.profiles.active=prod /home/ec2-user/Zource_Backend_App-1.0-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null &