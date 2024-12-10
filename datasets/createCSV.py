import os
import csv
import requests
import zipfile
import pandas as pd
from io import BytesIO

class FileDownloader:
    def __init__(self):
        # Directory to save CSV files
        self.save_directory = 'datasets'

        # Ensure the save directory exists
        os.makedirs(self.save_directory, exist_ok=True)

    def __download_file(self, file_url):
        response = requests.get(file_url)
        response.raise_for_status()  # Raise error if the download fails
        return BytesIO(response.content)

    def merge_txt_to_csv(self, input_folder, output_csv, delimiter=";"):
        with open(output_csv, "w", encoding="utf-8", newline="") as csv_file:
            writer = None  
            header_written = False

            for filename in os.listdir(input_folder):
                if filename.endswith(".txt"):
                    file_path = os.path.join(input_folder, filename)

                    with open(file_path, "r", encoding="utf-8") as txt_file:
                        reader = csv.reader(txt_file, delimiter=delimiter)

                        header = next(reader)  
                        data = list(reader)    

                        if not header_written:
                            writer = csv.writer(csv_file, delimiter=delimiter)
                            writer.writerow(header) 
                            header_written = True
        
                        writer.writerows(data)

    def __extract_and_save_csv_from_zip(self, zip_file, csvname):
        with zipfile.ZipFile(zip_file) as z:
            
            txt_folder = self.save_directory

            csv_found = False

            for filename in z.namelist():
                if filename.lower().endswith('.csv'):
                    with z.open(filename) as f:
                        try:
                            dataframe = pd.read_csv(
                                f,
                                encoding='cp1252',
                                sep=';',
                                on_bad_lines='skip',
                                dtype={20: str},
                                low_memory=False
                            )
                            
                            filename = filename.split('/')[-1]
                            local_path = os.path.join(self.save_directory, filename)
                            dataframe.to_csv(local_path, index=False, sep=";")
                            print(f"Saved: {local_path}")
                            csv_found = True  
                        except Exception as e:
                            print(f"Failed to read or save {filename}: {e}")
            
            # If no CSV files were found, check for .txt files and merge them
            if not csv_found:
                txt_files = [filename for filename in z.namelist() if filename.endswith('.txt')]

                if txt_files:
                    # Extract .txt files to the temporary folder
                    for txt_filename in txt_files:
                        with z.open(txt_filename) as f:
                            temp_txt_path = os.path.join(txt_folder, txt_filename.split('/')[-1])
                            with open(temp_txt_path, 'wb') as temp_file:
                                temp_file.write(f.read())
                    
                    
                    output_csv = os.path.join(self.save_directory, csvname)
                    self.merge_txt_to_csv(txt_folder, output_csv) 
                    print(f"Generated CSV from TXT files: {output_csv}")
                    
                    for txt_filename in txt_files:
                        txt_path = os.path.join(txt_folder, txt_filename.split('/')[-1])
                        os.remove(txt_path)
                else:
                    print("No .txt files found in the ZIP file.")

            


    def download_and_extract(self, file_url):
        try:
            # Download the zip file
            zip_file = self.__download_file(file_url)
            # Extract and save CSV files from the zip
            filename = file_url.split('/')[-1].replace("zip","csv")
            self.__extract_and_save_csv_from_zip(zip_file, filename)
        except Exception as e:
            print(f"Error downloading or extracting file: {e}")

def process_multiple_files(file_urls):
    downloader = FileDownloader()
    for url in file_urls:
        print(f"Processing URL: {url}")
        downloader.download_and_extract(url)

# Example usage:
zip_url = ['https://download.inep.gov.br/microdados/enem_por_escola/2005_a_2015/microdados_enem_por_escola.zip',
           'https://download.inep.gov.br/microdados/microdados_enem_2023.zip',
           'https://download.inep.gov.br/microdados/microdados_enem_2022.zip',
           'https://download.inep.gov.br/microdados/microdados_enem_2021.zip',
           'https://download.inep.gov.br/microdados/microdados_saeb_2021_ensino_fundamental_e_medio.zip',
           'https://download.inep.gov.br/microdados/microdados_saeb_2021_educacao_infantil.zip',
           'https://download.inep.gov.br/microdados/microdados_saeb_2019.zip',
           'https://download.inep.gov.br/microdados/microdados_saeb_2017.zip',
           'https://download.inep.gov.br/microdados/microdados_enade_2021.zip',
           'https://download.inep.gov.br/microdados/microdados_enade_2019_LGPD.zip',
           'https://download.inep.gov.br/microdados/microdados_enade_2018_LGPD.zip',
           'https://download.inep.gov.br/dados_abertos/microdados_censo_escolar_2023.zip',
           'https://download.inep.gov.br/dados_abertos/microdados_censo_escolar_2022.zip',
           'https://download.inep.gov.br/dados_abertos/microdados_censo_escolar_2021.zip',
           'https://download.inep.gov.br/microdados/microdados_censo_da_educacao_superior_2023.zip',
           'https://download.inep.gov.br/microdados/microdados_censo_da_educacao_superior_2022.zip',
           'https://download.inep.gov.br/microdados/microdados_censo_da_educacao_superior_2021.zip',
           'https://download.inep.gov.br/microdados/microdados_encceja_2023.zip',
           'https://download.inep.gov.br/microdados/microdados_encceja_2022.zip',
           'https://download.inep.gov.br/microdados/microdados_encceja_2020.zip'
]

process_multiple_files(zip_url)