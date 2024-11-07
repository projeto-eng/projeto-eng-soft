from processFiles import ProcessFiles

zip_url = ['https://download.inep.gov.br/microdados/enem_por_escola/2005_a_2015/microdados_enem_por_escola.zip',
           'https://download.inep.gov.br/dados_abertos/microdados_censo_escolar_2023.zip',
           'https://download.inep.gov.br/dados_abertos/microdados_censo_escolar_2022.zip',
           'https://download.inep.gov.br/dados_abertos/microdados_censo_escolar_2021.zip',
           'https://download.inep.gov.br/microdados/microdados_encceja_2023.zip',
           'https://download.inep.gov.br/microdados/microdados_encceja_2022.zip',
           'https://download.inep.gov.br/microdados/microdados_encceja_2020.zip']


if __name__ == '__main__':

    processor = ProcessFiles()
    for url in zip_url:
        dataFrameImportado = processor.importaURL(url)

        processor.exportaMongo(dataFrameImportado)


