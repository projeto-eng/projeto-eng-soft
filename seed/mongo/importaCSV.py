
import zipfile
import os
import wget
import shutil

dir = os.listdir("/seed/mongo/csv")
if len(dir) != 0:
    exit(1)

database_csv_urls = [
    [ "MICRODADOS_ENEM_ESCOLA", "https://download.inep.gov.br/microdados/enem_por_escola/2005_a_2015/microdados_enem_por_escola.zip",  "DADOS/MICRODADOS_ENEM_ESCOLA.csv" ]
]

print("DOWNLOAD E TRATAMENTO DE CSVs")

for [ database, url, caminho_csv ] in database_csv_urls:
    caminho_zip = "./csv/" + database + ".zip"
    caminho_pasta = "./csv/" + database
    nome_csv = database + '.csv'

    filename = wget.download(url, out=caminho_zip)

    with zipfile.ZipFile(caminho_zip, "r") as zip_ref:
        zip_ref.extractall(caminho_pasta)

    os.rename("/seed/mongo/csv/" + database + '/' + caminho_csv, "/seed/mongo/csv/" + database + ".csv")
    os.remove("/seed/mongo/csv/" + database + ".zip")
    shutil.rmtree("/seed/mongo/csv/" + database)