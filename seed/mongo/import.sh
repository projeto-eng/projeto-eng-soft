#!/bin/bash

mkdir /seed/mongo/csv

if [ -z "$( ls -A '/seed/mongo/csv' )" ]; then
   echo "DOWNLOAD E TRATAMENTO DE CSVs"
else
   echo "MIGRACAO JA FOI REALIZADA"
   echo "FIM"
   exit
fi

python3 importaCSV.py

iconv -f ISO_8859-1 -t UTF-8//TRANSLIT /seed/mongo/csv/MICRODADOS_ENEM_ESCOLA.csv -o /seed/mongo/csv/MICRODADOS_ENEM_ESCOLA-UTF8.csv

tr ";" "\t" < /seed/mongo/csv/MICRODADOS_ENEM_ESCOLA-UTF8.csv | mongoimport -d projeto-eng-soft -c MICRODADOS_ENEM_ESCOLA --type tsv --headerline --host mongo_dev:27017 --authenticationDatabase admin --username ${MONGO_INITDB_ROOT_USERNAME} --password ${MONGO_INITDB_ROOT_PASSWORD}

rm /seed/mongo/csv/MICRODADOS_ENEM_ESCOLA-UTF8.csv


chmod 777 -R /seed
echo "FIM"




