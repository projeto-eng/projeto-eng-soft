from processFile import ProcessFile

zip_url = ['https://download.inep.gov.br/microdados/enem_por_escola/2005_a_2015/microdados_enem_por_escola.zip']

if __name__ == '__main__':
    for url in zip_url:
        processor = ProcessFile.create_processor(url)

        processor.importa()

        processor.exporta()
