#!/bin/bash

cd ../
mvn clean install -DskipTests=true
mvn clean package -DskipTests=true 
