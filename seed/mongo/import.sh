#!/bin/bash

mkdir /seed/mongo/csv

chmod 777 -R /seed

if [ -z "$( ls -A '/seed/mongo/csv' )" ]; then
   echo "IMPORTAÇÃO DE CSVs..."

   tr ";" "\t" < /csv-utf8/MICRODADOS_ENEM_ESCOLA-UTF8.csv | mongoimport -d projeto-eng-soft -c MICRODADOS_ENEM_ESCOLA --type tsv --headerline --host mongo_dev:27017 --authenticationDatabase admin --username ${MONGO_INITDB_ROOT_USERNAME} --password ${MONGO_INITDB_ROOT_PASSWORD} &\
   tr ";" "\t" < /csv-utf8/MICRODADOS_ED_BASICA_2023-UTF8.csv | mongoimport -d projeto-eng-soft -c MICRODADOS_ED_BASICA_2023 --type tsv --headerline --host mongo_dev:27017 --authenticationDatabase admin --username ${MONGO_INITDB_ROOT_USERNAME} --password ${MONGO_INITDB_ROOT_PASSWORD} &\
   tr ";" "\t" < /csv-utf8/MICRODADOS_ED_BASICA_2022-UTF8.csv | mongoimport -d projeto-eng-soft -c MICRODADOS_ED_BASICA_2022 --type tsv --headerline --host mongo_dev:27017 --authenticationDatabase admin --username ${MONGO_INITDB_ROOT_USERNAME} --password ${MONGO_INITDB_ROOT_PASSWORD} &\
   tr ";" "\t" < /csv-utf8/MICRODADOS_ED_BASICA_2021-UTF8.csv | mongoimport -d projeto-eng-soft -c MICRODADOS_ED_BASICA_2021 --type tsv --headerline --host mongo_dev:27017 --authenticationDatabase admin --username ${MONGO_INITDB_ROOT_USERNAME} --password ${MONGO_INITDB_ROOT_PASSWORD} &\
   tr ";" "\t" < /csv-utf8/MICRODADOS_ENCCEJA_2023-UTF8.csv | mongoimport -d projeto-eng-soft -c MICRODADOS_ENCCEJA_2023 --type tsv --headerline --host mongo_dev:27017 --authenticationDatabase admin --username ${MONGO_INITDB_ROOT_USERNAME} --password ${MONGO_INITDB_ROOT_PASSWORD} &\
   tr ";" "\t" < /csv-utf8/MICRODADOS_ENCCEJA_2022-UTF8.csv | mongoimport -d projeto-eng-soft -c MICRODADOS_ENCCEJA_2022 --type tsv --headerline --host mongo_dev:27017 --authenticationDatabase admin --username ${MONGO_INITDB_ROOT_USERNAME} --password ${MONGO_INITDB_ROOT_PASSWORD} &\
   tr ";" "\t" < /csv-utf8/MICRODADOS_ENCCEJA_2020-UTF8.csv | mongoimport -d projeto-eng-soft -c MICRODADOS_ENCCEJA_2020 --type tsv --headerline --host mongo_dev:27017 --authenticationDatabase admin --username ${MONGO_INITDB_ROOT_USERNAME} --password ${MONGO_INITDB_ROOT_PASSWORD}

   touch /seed/mongo/csv/log
   echo "MICRODADOS_ENEM_ESCOLA" >> /seed/mongo/csv/log
   echo "MICRODADOS_ED_BASICA_2023" >> /seed/mongo/csv/log
   echo "MICRODADOS_ED_BASICA_2022" >> /seed/mongo/csv/log
   echo "MICRODADOS_ED_BASICA_2021" >> /seed/mongo/csv/log
   echo "MICRODADOS_ENCCEJA_2023" >> /seed/mongo/csv/log
   echo "MICRODADOS_ENCCEJA_2022" >> /seed/mongo/csv/log
   echo "MICRODADOS_ENCCEJA_2020" >> /seed/mongo/csv/log

   chmod 777 -R /seed
   echo "FIM"
else
   echo "MIGRACAO JA FOI REALIZADA"
   echo "FIM"
   # Spring precisa de um tempo mínimo para verificações antes de encerrar o container
   sleep 30
fi





