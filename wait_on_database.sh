#!/usr/bin/env bash

#until nc -z -v -w30 $CFG_MYSQL_HOST 3306
#do
#  echo "Waiting for database connection..."
  # wait for 5 seconds before check again
#  sleep 5
#done

echo "API IS SLEEPING UNTIL DB IS UP"
sleep 10
echo "API IS SLEEPING UNTIL DB IS UP"
sleep 10
echo "API IS SLEEPING UNTIL DB IS UP"
sleep 10
echo "API IS SLEEPING UNTIL DB IS UP"
sleep 10
echo "API IS SLEEPING UNTIL DB IS UP"
sleep 10
echo "API IS SLEEPING UNTIL DB IS UP"
sleep 10
echo "API IS SLEEPING UNTIL DB IS UP"
sleep 10
echo "API IS SLEEPING UNTIL DB IS UP"
sleep 10
echo "API IS SLEEPING UNTIL DB IS UP"
sleep 10
echo "API IS SLEEPING UNTIL DB IS UP"
sleep 10
echo "API IS SLEEPING UNTIL DB IS UP"
sleep 10
echo "API IS SLEEPING UNTIL DB IS UP"
sleep 10
echo "ATTEMPTING TO START THE API!!!!"

mvn clean spring-boot:run
