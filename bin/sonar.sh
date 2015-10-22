#!/bin/bash

cd ../
mvn clean sonar:sonar -DskipTests=true -Psonar
