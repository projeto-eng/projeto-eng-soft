FROM mongo:8.0

RUN apt update && apt install locales wget unzip

RUN sed -i -e 's/# en_US.UTF-8 UTF-8/en_US.UTF-8 UTF-8/' /etc/locale.gen && \
    dpkg-reconfigure --frontend=noninteractive locales && \
    update-locale LANG=en_US.UTF-8

ENV LANG en_US.UTF-8

WORKDIR /csv/

# Enem Escola
RUN wget https://download.inep.gov.br/microdados/enem_por_escola/2005_a_2015/microdados_enem_por_escola.zip
RUN unzip microdados_enem_por_escola.zip -d microdados_enem_por_escola
RUN mv ./microdados_enem_por_escola/DADOS/MICRODADOS_ENEM_ESCOLA.csv /csv/microdados_enem_por_escola.csv
RUN rm microdados_enem_por_escola.zip
RUN rm -R microdados_enem_por_escola

# Censo Escolar 2023
RUN wget https://download.inep.gov.br/dados_abertos/microdados_censo_escolar_2023.zip
RUN unzip microdados_censo_escolar_2023.zip -d .
RUN mv ./microdados_censo_escolar_2023/dados/microdados_ed_basica_2023.csv /csv/microdados_ed_basica_2023.csv
RUN rm microdados_censo_escolar_2023.zip
RUN rm -R microdados_censo_escolar_2023

# Censo Escolar 2022
RUN wget https://download.inep.gov.br/dados_abertos/microdados_censo_escolar_2022.zip
RUN unzip microdados_censo_escolar_2022.zip -d microdados_censo_escolar_2022
RUN mv .//microdados_censo_escolar_2022/Microdados\ do\ Censo\ Escolar\ da\ EducaЗ╞o\ Bаsica\ 2022/dados/microdados_ed_basica_2022.csv /csv/microdados_ed_basica_2022.csv
RUN rm microdados_censo_escolar_2022.zip
RUN rm -R microdados_censo_escolar_2022

# Censo Escolar 2021
RUN wget https://download.inep.gov.br/dados_abertos/microdados_censo_escolar_2021.zip
RUN unzip microdados_censo_escolar_2021.zip -d .
RUN mv ./microdados_ed_basica_2021/dados/microdados_ed_basica_2021.csv /csv/microdados_ed_basica_2021.csv
RUN rm microdados_censo_escolar_2021.zip
RUN rm -R microdados_ed_basica_2021

# Encceja 2023
RUN wget https://download.inep.gov.br/microdados/microdados_encceja_2023.zip
RUN unzip microdados_encceja_2023.zip -d microdados_encceja_2023
RUN mv ./microdados_encceja_2023/DADOS/MICRODADOS_ENCCEJA_2023_REG_NAC.csv /csv/microdados_encceja_2023.csv
RUN rm microdados_encceja_2023.zip
RUN rm -R microdados_encceja_2023

# Encceja 2022
RUN wget https://download.inep.gov.br/microdados/microdados_encceja_2022.zip
RUN unzip microdados_encceja_2022.zip -d microdados_encceja_2022
RUN mv ./microdados_encceja_2022/DADOS/MICRODADOS_ENCCEJA_2022_REG_NAC.csv /csv/microdados_encceja_2022.csv
RUN rm microdados_encceja_2022.zip
RUN rm -R microdados_encceja_2022

# Encceja 2020
RUN wget https://download.inep.gov.br/microdados/microdados_encceja_2020.zip
RUN unzip microdados_encceja_2020.zip -d microdados_encceja_2020
RUN mv ./microdados_encceja_2020/DADOS/MICRODADOS_ENCCEJA_NACIONAL_REGULAR_2020.csv /csv/microdados_encceja_2020.csv
RUN rm microdados_encceja_2020.zip
RUN rm -R microdados_encceja_2020

# Converte para UTF-8
WORKDIR /csv-utf8
RUN iconv -f ISO_8859-1 -t UTF-8//TRANSLIT /csv/microdados_enem_por_escola.csv -o /csv-utf8/MICRODADOS_ENEM_ESCOLA-UTF8.csv & \
   iconv -f ISO_8859-1 -t UTF-8//TRANSLIT /csv/microdados_ed_basica_2023.csv -o /csv-utf8/MICRODADOS_ED_BASICA_2023-UTF8.csv & \
   iconv -f ISO_8859-1 -t UTF-8//TRANSLIT /csv/microdados_ed_basica_2022.csv -o /csv-utf8/MICRODADOS_ED_BASICA_2022-UTF8.csv & \
   iconv -f ISO_8859-1 -t UTF-8//TRANSLIT /csv/microdados_ed_basica_2021.csv -o /csv-utf8/MICRODADOS_ED_BASICA_2021-UTF8.csv & \
   iconv -f ISO_8859-1 -t UTF-8//TRANSLIT /csv/microdados_encceja_2023.csv -o /csv-utf8/MICRODADOS_ENCCEJA_2023-UTF8.csv & \
   iconv -f ISO_8859-1 -t UTF-8//TRANSLIT /csv/microdados_encceja_2022.csv -o /csv-utf8/MICRODADOS_ENCCEJA_2022-UTF8.csv & \
   iconv -f ISO_8859-1 -t UTF-8//TRANSLIT /csv/microdados_encceja_2020.csv -o /csv-utf8/MICRODADOS_ENCCEJA_2020-UTF8.csv

RUN rm -R /csv

WORKDIR /seed/mongo/

ENTRYPOINT /seed/mongo/import.sh
