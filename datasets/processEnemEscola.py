import os
import requests
import zipfile
import json
import pandas as pd
from pymongo import MongoClient
from io import BytesIO
from processFile import ProcessFile

class ProcessEnemEscola(ProcessFile):
    def __init__(self, file_url):
        super().__init__(file_url)

    def __download_file(self):
        response = requests.get(self.file_url)
        response.raise_for_status()
        return BytesIO(response.content)

    def __process_dataframe(self, dataframe):
        #discutir com o time quais as informações que vão ser usadas no dataframe
        relevant_information = ['NU_ANO', 'CO_UF_ESCOLA', 'SG_UF_ESCOLA', 'NO_MUNICIPIO_ESCOLA']

        processed_dataframe = dataframe[relevant_information]

        return processed_dataframe


    def __extract_data_from_zip(self, file):
            with zipfile.ZipFile(file) as z:
                for filename in z.namelist():
                    if filename.endswith('.csv'):
                        with z.open(filename) as f:
                            # Read CSV with ANSI encoding and handle bad lines
                            try:
                                dataframe = pd.read_csv(
                                    f,
                                    encoding='latin1',
                                    sep=';',  # Change this if your CSV uses a different delimiter
                                    on_bad_lines='skip',  # Log a warning for bad lines
                                    dtype={20: str}
                                )
                                filename = filename.split('/')[-1]
                                dataframe = self.__process_dataframe(dataframe)
                                self.dataframes.append((filename, dataframe))
                            except Exception as e:
                                print(f"Failed to read {filename}: {e}")
            if not self.dataframes:
                raise ValueError("No .csv files found in the zip.")

    def __extract_data_from_json(self):
        # Define the specific JSON file path
        file_path = 'datasets/MICRODADOS_ENEM_ESCOLA.json'

        # Check if the file exists
        if not os.path.exists(file_path):
            raise FileNotFoundError(f"File {file_path} not found.")

        # Read the JSON file
        with open(file_path, 'r', encoding='latin1') as file:
            data = json.load(file)

        # Convert the JSON to a dataframe
        temp_dataframe = pd.json_normalize(data)

        # Return a list with one tuple (filename, dataframe)
        self.dataframes.append[(os.path.basename(file_path), temp_dataframe)]

    def __save_as_json(self):
        # Create the 'datasets' directory if it doesn't exist
        os.makedirs('datasets', exist_ok=True)

        for filename, dataframe in self.dataframes:
            # Define the full path for the JSON file
            json_file_path = os.path.join('datasets', filename.replace('.csv', '.json'))

            # Save the DataFrame as a JSON file
            try:
                dataframe.to_json(json_file_path, orient='records', lines=False, force_ascii=False)
                print(f"DataFrame saved as JSON at: {json_file_path}")
            except Exception as e:
                print(f"Failed to save DataFrame as JSON for {filename}: {e}")

    def __insert_data_to_mongodb(self):
        client = MongoClient(self.mongo_uri)
        for filename, dataframe in self.dataframes:
            # Create a database for each .csv file
            collection_name = os.path.splitext(filename)[0]  # Use the filename without extension as the collection name
            db = client[self.database_name]
            collection = db[collection_name]

            # Convert dataframe to dictionary and insert into MongoDB
            records = dataframe.to_dict(orient='records')

            collection.insert_many(records)

    def __check_dev_files(self):
        file_path = 'datasets/MICRODADOS_ENEM_ESCOLA.json'

        # Check if the specific JSON file exists
        if os.path.exists(file_path):
            return '1'  # The specific file exists
        return '0'  # The file doesn't exist

    def importa(self):
        dev_mode = self.__check_dev_files()

        try:
            if dev_mode == '1':
                print('Extraindo dados do json')
                self.__extract_data_from_json()

            else:
                print('Baixando zip')
                zipfile = self.__download_file()

                print('Extraindo dados do zip')
                self.__extract_data_from_zip(zipfile)

                print('Salvando dados como json')
                self.__save_as_json()

        except Exception as e:
            print(f"An error occurred: {e}")

    def exporta(self):
        try:
            print('Inserindo dados no mongo')
            self.__insert_data_to_mongodb()

        except Exception as e:
            print(f"An error occurred: {e}")